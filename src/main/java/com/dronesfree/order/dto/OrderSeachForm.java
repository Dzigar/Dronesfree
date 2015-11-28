package com.dronesfree.order.dto;

public class OrderSeachForm {

	private boolean isBid;

	private double price;

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the isBid
	 */
	public boolean getIsBid() {
		return isBid;
	}

	/**
	 * @param isBid
	 *            the isBid to set
	 */
	public void setBid(boolean isBid) {
		this.isBid = isBid;
	}

}
