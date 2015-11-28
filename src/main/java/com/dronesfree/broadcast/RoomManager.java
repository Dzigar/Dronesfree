package com.dronesfree.broadcast;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.kurento.client.KurentoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dronesfree.broadcast.model.Room;

public class RoomManager {

	private final Logger log = LoggerFactory.getLogger(RoomManager.class);

	@Autowired
	private KurentoClient kurento;

	@Autowired
	private UserRegistry registry;

	private final ConcurrentMap<String, Room> rooms = new ConcurrentHashMap<>();

	/**
	 * @param roomName
	 *            the name of the room
	 * @return the room if it was already created, or a new one if it is the
	 *         first time this room is accessed
	 */
	public Room getRoom(String roomName) {

		log.debug("Searching for room {}", roomName);
		Room room = rooms.get(roomName);

		if (room == null) {

			log.debug("Room {} not existent. Will create now!", roomName);

			room = new Room(roomName, kurento.createMediaPipeline(), registry,
					this);
			rooms.put(roomName, room);

		}

		log.debug("Room {} found!", roomName);

		return room;
	}

	/**
	 * Removes a room from the list of available rooms
	 *
	 * @param room
	 * @throws IOException
	 */
	public void removeRoom(Room room) {
		this.rooms.remove(room.getRoomName());
		// room.close();
		log.info("Room {} removed and closed", room.getRoomName());
	}

}