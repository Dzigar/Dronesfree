package com.dronesfree.proposal.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.joda.time.DateTime;

import com.dronesfree.common.model.BaseEntity;
import com.dronesfree.notification.model.Notification;
import com.dronesfree.order.model.Order;
import com.dronesfree.user.model.User;

@Entity
@Table(name = "_T_PROPOSAL", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class Proposal extends BaseEntity<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6613040115758749735L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "T_PROPOSAL_T_ORDER", joinColumns = { @JoinColumn(name = "proposal_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "order_id", referencedColumnName = "id") })
	private Order order;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "T_PROPOSAL_T_USER", joinColumns = { @JoinColumn(name = "proposal_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") })
	private User user;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "T_NOTIFICATION_T_PROPOSAL", joinColumns = { @JoinColumn(name = "proposal_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "notification_id", referencedColumnName = "id") })
	private Notification notification;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean accepted;

	public Proposal() {
		// TODO Auto-generated constructor stub
	}

	public static Builder getBuilder() {
		return new Builder();
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
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

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
	 * @return the accepted
	 */
	public boolean isAccepted() {
		return accepted;
	}

	/**
	 * @param accepted
	 *            the accepted to set
	 */
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof Proposal)) {
			return false;
		}
		return user.equals(((Proposal) obj).getUser());
	}

	/**
	 * @return the notification
	 */
	public Notification getNotification() {
		return notification;
	}

	/**
	 * @param notification
	 *            the notification to set
	 */
	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public static class Builder {

		private Proposal proposal;

		public Builder() {
			proposal = new Proposal();
		}

		public Builder user(User user) {
			proposal.user = user;
			return this;
		}

		public Builder order(Order order) {
			proposal.order = order;
			return this;
		}

		public Proposal build() {
			proposal.creationTime = proposal.modificationTime = DateTime.now();
			return proposal;
		}

	}
}
