package com.dronesfree.order.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.dronesfree.user.model.User;

public class OrderForm {

	@NotNull
	private double latitude;

	@NotNull
	private double longitude;

	// @NotNull
	// private double price;

	@NotNull
	private String description;

	@NotNull
	private String address;

	private User user;

	public OrderForm() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	// /**
	// * @return the price
	// */
	// public double getPrice() {
	// return price;
	// }
	//
	// /**
	// * @param price
	// * the price to set
	// */
	// public void setPrice(double price) {
	// this.price = price;
	// }

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("latitude", latitude)
				.append("longitude", longitude)
				.append("user", user.getUsername()).toString();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}