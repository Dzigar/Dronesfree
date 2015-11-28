package com.dronesfree.location.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "_T_LOCATION", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class Location implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4367663597714301107L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "lat", nullable = false)
	private double latitude;

	@Column(name = "lng", nullable = false)
	private double longitude;

	@Column(name = "address", nullable = false)
	private String address;

	public Location() {
		// TODO Auto-generated constructor stub
	}

	public Location(double latitude, double longitude, String address) {
		setLatitude(latitude);
		setLongitude(longitude);
		setAddress(address);
	}

	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public static class Builder {

		private Location location;

		public Builder latitude(double latitude) {
			location.latitude = latitude;
			return this;
		}

		public Builder longitude(double longitude) {
			location.longitude = longitude;
			return this;
		}

		public Builder address(String address) {
			location.address = address;
			return this;
		}

		public Location build() {
			return location;
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id)
				.append("lat", latitude).append("lng", longitude)
				.append("address", address).toString();
	}
}
