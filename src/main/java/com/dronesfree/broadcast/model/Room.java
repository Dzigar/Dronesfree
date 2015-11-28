package com.dronesfree.broadcast.model;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.kurento.client.EventListener;
import org.kurento.client.IceCandidate;
import org.kurento.client.MediaPipeline;
import org.kurento.client.OnIceCandidateEvent;
import org.kurento.client.WebRtcEndpoint;
import org.kurento.jsonrpc.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.dronesfree.broadcast.RoomManager;
import com.dronesfree.broadcast.UserRegistry;
import com.dronesfree.broadcast.UserSession;
import com.google.gson.JsonObject;

public class Room {

	private ConcurrentHashMap<String, UserSession> viewers = new ConcurrentHashMap<>();

	private UserSession presenterUserSession;

	private RoomManager roomManager;

	private MediaPipeline pipeline;

	private UserRegistry registry;

	private String roomName;

	private final Logger log = LoggerFactory.getLogger(Room.class);

	public Room(String roomName, MediaPipeline pipeline, UserRegistry registry,
			RoomManager roomManager) {
		this.roomManager = roomManager;
		this.roomName = roomName;
		this.pipeline = pipeline;
		this.registry = registry;
		log.info("ROOM {} has been created", roomName);
	}

	public synchronized void handleMessage(WebSocketSession session,
			JsonObject jsonMessage) throws IOException {

		switch (jsonMessage.get("id").getAsString()) {
		case "presenter":
			try {
				presenter(session, jsonMessage);
			} catch (Throwable t) {
				stop(session);
				log.error(t.getMessage(), t);
				JsonObject response = new JsonObject();
				response.addProperty("id", "presenterResponse");
				response.addProperty("response", "rejected");
				response.addProperty("message", t.getMessage());
				session.sendMessage(new TextMessage(response.toString()));
			}
			break;
		case "viewer":
			try {
				viewer(session, jsonMessage);
			} catch (Throwable t) {
				stop(session);
				log.error(t.getMessage(), t);
				JsonObject response = new JsonObject();
				response.addProperty("id", "viewerResponse");
				response.addProperty("response", "rejected");
				response.addProperty("message", t.getMessage());
				session.sendMessage(new TextMessage(response.toString()));
			}
			break;
		case "onIceCandidate": {
			JsonObject candidate = jsonMessage.get("candidate")
					.getAsJsonObject();

			UserSession user = null;
			if (presenterUserSession.getSession() == session) {
				user = presenterUserSession;
			} else {
				user = viewers.get(session.getId());
			}
			if (user != null) {
				IceCandidate cand = new IceCandidate(candidate.get("candidate")
						.getAsString(), candidate.get("sdpMid").getAsString(),
						candidate.get("sdpMLineIndex").getAsInt());
				user.addCandidate(cand);
			}
			break;
		}
		case "stop":
			stop(session);
			break;
		default:
			break;
		}
	}

	protected synchronized void presenter(final WebSocketSession session,
			JsonObject jsonMessage) throws IOException {
		if (presenterUserSession == null) {
			presenterUserSession = new UserSession(session, getRoomName());

			presenterUserSession.setWebRtcEndpoint(new WebRtcEndpoint.Builder(
					pipeline).build());

			registry.register(presenterUserSession);

			WebRtcEndpoint presenterWebRtc = presenterUserSession
					.getWebRtcEndpoint();

			presenterWebRtc
					.addOnIceCandidateListener(new EventListener<OnIceCandidateEvent>() {

						@Override
						public void onEvent(OnIceCandidateEvent event) {
							JsonObject response = new JsonObject();
							response.addProperty("id", "iceCandidate");
							response.add("candidate", JsonUtils
									.toJsonObject(event.getCandidate()));
							try {
								synchronized (session) {
									session.sendMessage(new TextMessage(
											response.toString()));
								}
							} catch (IOException e) {
								log.debug(e.getMessage());
							}
						}
					});

			String sdpOffer = jsonMessage.getAsJsonPrimitive("sdpOffer")
					.getAsString();
			String sdpAnswer = presenterWebRtc.processOffer(sdpOffer);

			JsonObject response = new JsonObject();
			response.addProperty("id", "presenterResponse");
			response.addProperty("response", "accepted");
			response.addProperty("sdpAnswer", sdpAnswer);
			response.addProperty("roomId", jsonMessage.get("roomId").getAsString());
			response.addProperty("userName", jsonMessage.get("userName").getAsString());
			
			synchronized (session) {
				presenterUserSession.sendMessage(response);
			}
			presenterWebRtc.gatherCandidates();

		} else {
			JsonObject response = new JsonObject();
			response.addProperty("id", "presenterResponse");
			response.addProperty("response", "rejected");
			response.addProperty("message",
					"Another user is currently acting as sender. Try again later ...");
			session.sendMessage(new TextMessage(response.toString()));
		}
	}

	protected synchronized void viewer(final WebSocketSession session,
			JsonObject jsonMessage) throws IOException {
		if (presenterUserSession == null
				|| presenterUserSession.getWebRtcEndpoint() == null) {
			JsonObject response = new JsonObject();
			response.addProperty("id", "viewerResponse");
			response.addProperty("response", "rejected");
			response.addProperty("message",
					"No active sender now. Become sender or . Try again later ...");
			session.sendMessage(new TextMessage(response.toString()));
		} else {
			if (viewers.containsKey(session.getId())) {
				JsonObject response = new JsonObject();
				response.addProperty("id", "viewerResponse");
				response.addProperty("response", "rejected");
				response.addProperty(
						"message",
						"You are already viewing in this session. Use a different browser to add additional viewers.");
				session.sendMessage(new TextMessage(response.toString()));
				return;
			}
			UserSession viewer = new UserSession(session, getRoomName());
			viewers.put(session.getId(), viewer);
			registry.register(viewer);
			String sdpOffer = jsonMessage.getAsJsonPrimitive("sdpOffer")
					.getAsString();

			WebRtcEndpoint nextWebRtc = new WebRtcEndpoint.Builder(pipeline)
					.build();

			nextWebRtc
					.addOnIceCandidateListener(new EventListener<OnIceCandidateEvent>() {

						@Override
						public void onEvent(OnIceCandidateEvent event) {
							JsonObject response = new JsonObject();
							response.addProperty("id", "iceCandidate");
							response.add("candidate", JsonUtils
									.toJsonObject(event.getCandidate()));
							try {
								synchronized (session) {
									session.sendMessage(new TextMessage(
											response.toString()));
								}
							} catch (IOException e) {
								log.debug(e.getMessage());
							}
						}
					});

			viewer.setWebRtcEndpoint(nextWebRtc);
			presenterUserSession.getWebRtcEndpoint().connect(nextWebRtc);
			String sdpAnswer = nextWebRtc.processOffer(sdpOffer);

			JsonObject response = new JsonObject();
			response.addProperty("id", "viewerResponse");
			response.addProperty("response", "accepted");
			response.addProperty("sdpAnswer", sdpAnswer);

			synchronized (session) {
				viewer.sendMessage(response);
			}
			nextWebRtc.gatherCandidates();
		}
	}

	public synchronized void stop(WebSocketSession session) throws IOException {
		String sessionId = session.getId();
		if (presenterUserSession != null
				&& presenterUserSession.getSession().getId().equals(sessionId)) {
			for (UserSession viewer : viewers.values()) {
				JsonObject response = new JsonObject();
				response.addProperty("id", "stopCommunication");
				viewer.sendMessage(response);
			}

			log.info("Releasing media pipeline");
			if (pipeline != null) {
				pipeline.release();
			}
			pipeline = null;
			presenterUserSession = null;
			roomManager.removeRoom(this);
		} else if (viewers.containsKey(sessionId)) {
			if (viewers.get(sessionId).getWebRtcEndpoint() != null) {
				viewers.get(sessionId).getWebRtcEndpoint().release();
			}
			viewers.remove(sessionId);
		}
	}

	public String getRoomName() {
		return roomName;
	}

}