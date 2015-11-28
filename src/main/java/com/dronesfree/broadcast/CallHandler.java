package com.dronesfree.broadcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.dronesfree.broadcast.model.Room;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class CallHandler extends TextWebSocketHandler {

	@Autowired
	private RoomManager roomManager;

	@Autowired
	private UserRegistry registry;

	private static final Logger log = LoggerFactory
			.getLogger(CallHandler.class);
	private static final Gson gson = new GsonBuilder().create();

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws Exception {
		JsonObject jsonMessage = gson.fromJson(message.getPayload(),
				JsonObject.class);
		log.debug("Incoming message from session '{}': {}", session.getId(),
				jsonMessage);

		roomManager.getRoom(jsonMessage.get("roomId").getAsString())
				.handleMessage(session, jsonMessage);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception {
		UserSession user = registry.removeBySession(session);
		Room room = roomManager.getRoom(user.getRoomName());
		room.stop(session);
	}
}