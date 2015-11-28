package com.dronesfree.order.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;

import com.dronesfree.common.model.BaseEntity;
import com.dronesfree.location.model.Location;
import com.dronesfree.proposal.model.Proposal;
import com.dronesfree.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "_T_ORDER", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class Order extends BaseEntity<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1311970081137009177L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { javax.persistence.CascadeType.ALL })
	@JoinTable(name = "T_ORDER_T_USER", joinColumns = { @JoinColumn(name = "order_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") })
	private User user;

	@OneToOne(fetch = FetchType.EAGER, cascade = { javax.persistence.CascadeType.ALL }, orphanRemoval = true)
	@JoinColumn(name = "location_id", referencedColumnName = "id")
	private Location geolocation;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = { javax.persistence.CascadeType.ALL })
	@JoinTable(name = "T_ORDER_PERFORMER", joinColumns = { @JoinColumn(name = "order_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "performer_id", referencedColumnName = "id") })
	private User performer;

	@OneToMany(fetch = FetchType.EAGER, cascade = { javax.persistence.CascadeType.ALL }, orphanRemoval = true)
	@JoinTable(name = "T_PROPOSAL_T_ORDER", joinColumns = { @JoinColumn(name = "order_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "proposal_id", referencedColumnName = "id") })
	private List<Proposal> proposals = new ArrayList<Proposal>();

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public Order() {
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
	public void setId(long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("creationTime", this.getCreationTime())
				.append("description", this.description)
				.append("lat", this.geolocation.getLatitude())
				.append("lng", this.geolocation.getLongitude())
				.append("address", this.geolocation.getAddress())
				.append("user",
						this.user.getFirstName() + " "
								+ this.user.getLastName()).toString();
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

	/**
	 * @return the performer
	 */
	public User getPerformer() {
		return performer;
	}

	/**
	 * @param performer
	 *            the performer to set
	 */
	public void setPerformer(User performer) {
		this.performer = performer;
	}

	/**
	 * @return the status
	 */
	public OrderStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	/**
	 * @return the proposals
	 */
	public List<Proposal> getProposals() {
		return proposals;
	}

	/**
	 * @param proposals
	 *            the proposals to set
	 */
	public void setProposals(List<Proposal> proposals) {
		this.proposals = proposals;
	}

	public boolean containsProposal(Proposal proposal) {
		Iterator<Proposal> i = proposals.iterator();
		while (i.hasNext()) {
			if (i.next().equals(proposal)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the geolocation
	 */
	public Location getGeolocation() {
		return geolocation;
	}

	/**
	 * @param geolocation
	 *            the geolocation to set
	 */
	public void setGeolocation(Location geolocation) {
		this.geolocation = geolocation;
	}

	public static class Builder {

		private Order order;

		public Builder() {
			order = new Order();
		}

		public Builder description(String description) {
			order.description = description;
			return this;
		}

		public Builder location(Location location) {
			order.geolocation = location;
			return this;
		}

		public Builder user(User user) {
			order.user = user;
			return this;
		}

		public Builder orderStatus(OrderStatus status) {
			order.status = status;
			return this;
		}

		public Order build() {
			order.creationTime = order.modificationTime = DateTime.now();
			return order;
		}
	}

}
