package com.dronesfree.notification.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.joda.time.DateTime;

import com.dronesfree.common.model.BaseEntity;
import com.dronesfree.proposal.model.Proposal;
import com.dronesfree.user.model.User;

@Entity
@Table(name = "_T_NOTIFICATION", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class Notification extends BaseEntity<Long> {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { javax.persistence.CascadeType.ALL })
	@JoinTable(name = "T_NOTIFICATION_T_USER", joinColumns = { @JoinColumn(name = "notification_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") })
	private User user;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "T_NOTIFICATION_T_PROPOSAL", joinColumns = { @JoinColumn(name = "notification_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "proposal_id", referencedColumnName = "id") })
	private Proposal proposal;

	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private NotificationType type;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean revised;

	public Notification() {

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
	 * @return the type
	 */
	public NotificationType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(NotificationType type) {
		this.type = type;
	}

	public boolean isRevised() {
		return revised;
	}

	public void setRevised(boolean revised) {
		this.revised = revised;
	}

	/**
	 * @return the proposal
	 */
	public Proposal getProposal() {
		return proposal;
	}

	/**
	 * @param proposal
	 *            the proposal to set
	 */
	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
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

	public static class Builder {

		private Notification notification;

		public Builder() {
			notification = new Notification();
		}

		public Builder user(User user) {
			notification.setUser(user);
			return this;
		}

		public Builder proposal(Proposal proposal) {
			notification.setProposal(proposal);
			return this;
		}

		public Builder notificationType(NotificationType type) {
			notification.type = type;
			return this;
		}

		public Notification build() {
			notification.creationTime = notification.modificationTime = DateTime
					.now();
			return notification;
		}
	}

}
