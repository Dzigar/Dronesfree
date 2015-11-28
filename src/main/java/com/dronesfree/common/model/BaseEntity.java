package com.dronesfree.common.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@MappedSuperclass
public abstract class BaseEntity<ID> {

	@Column(name = "creation_time", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	protected DateTime creationTime;

	@Column(name = "modification_time", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	protected DateTime modificationTime;

	public abstract ID getId();

	public DateTime getCreationTime() {
		return creationTime;
	}

	public DateTime getModificationTime() {
		return modificationTime;
	}

	@PrePersist
	public void prePersist() {
		DateTime now = DateTime.now();
		this.creationTime = now;
		this.modificationTime = now;
	}

	@PreUpdate
	public void preUpdate() {
		this.modificationTime = DateTime.now();
	}
}