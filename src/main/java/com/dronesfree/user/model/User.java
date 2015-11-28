package com.dronesfree.user.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;

import com.dronesfree.common.model.BaseEntity;
import com.dronesfree.notification.model.Notification;
import com.dronesfree.order.model.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "_T_USER", uniqueConstraints = @UniqueConstraint(columnNames = {
		"id", "username", "email" }))
public class User extends BaseEntity<Long> {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;

	@Column(name = "username", unique = true, nullable = false, length = 100)
	private String username;

	@Email
	@JsonIgnore
	@Column(name = "email", length = 100, nullable = false, unique = true)
	private String email;

	@JsonIgnore
	@Column(name = "password", length = 255)
	private String password;

	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Enumerated(EnumType.STRING)
	@Column(name = "sign_in_provider", length = 20)
	private SocialMediaService signInProvider;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, cascade = { javax.persistence.CascadeType.ALL })
	@JoinTable(name = "T_ORDER_T_USER", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "order_id", referencedColumnName = "id") })
	private Set<Order> orders;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "T_NOTIFICATION_T_USER", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "notification_id", referencedColumnName = "id") })
	private Set<Notification> notifications;

	public User() {

	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the orders
	 */
	public Set<Order> getOrders() {
		return orders;
	}

	/**
	 * @param orders
	 *            the orders to set
	 */
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the signInProvider
	 */
	public SocialMediaService getSignInProvider() {
		return signInProvider;
	}

	/**
	 * @param signInProvider
	 *            the signInProvider to set
	 */
	public void setSignInProvider(SocialMediaService signInProvider) {
		this.signInProvider = signInProvider;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof User)) {
			return false;
		}
		return username.equals(((User) obj).getUsername());
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id)
				.append("email", email)
				.append("name", firstName + " " + lastName)
				.append("role", role)
				.append("signInProvider", this.getSignInProvider()).toString();
	}

	/**
	 * @return the notifications
	 */
	public Set<Notification> getNotifications() {
		return notifications;
	}

	/**
	 * @param notifications
	 *            the notifications to set
	 */
	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}

	public static class Builder {

		private User user;

		public Builder() {
			user = new User();
			user.role = Role.ROLE_USER;
		}

		public Builder email(String email) {
			user.email = email;
			return this;
		}

		public Builder username(String username) {
			user.username = username;
			return this;
		}

		public Builder firstName(String firstName) {
			user.firstName = firstName;
			return this;
		}

		public Builder lastName(String lastName) {
			user.lastName = lastName;
			return this;
		}

		public Builder password(String password) {
			user.password = password;
			return this;
		}

		public Builder signInProvider(SocialMediaService signInProvider) {
			user.signInProvider = signInProvider;
			return this;
		}

		public User build() {
			user.creationTime = user.modificationTime = DateTime.now();
			return user;
		}
	}

}
