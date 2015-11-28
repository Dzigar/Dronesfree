package com.dronesfree.broadcast.model;

public class NotifyMessage {

	private String performerName;
	private String customerName;
	private String roomId;

	public NotifyMessage(String customerName, String performerName, String roomId) {
		this.customerName = customerName;
		this.performerName = performerName;
		this.roomId = roomId;
	}

	public NotifyMessage() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the performerName
	 */
	public String getPerformerName() {
		return performerName;
	}

	/**
	 * @return the roomId
	 */
	public String getRoomId() {
		return roomId;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

}
