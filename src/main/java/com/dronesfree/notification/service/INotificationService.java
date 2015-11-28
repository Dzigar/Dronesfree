package com.dronesfree.notification.service;

import java.util.List;

import com.dronesfree.notification.model.Notification;
import com.dronesfree.notification.model.NotificationType;
import com.dronesfree.proposal.model.Proposal;

public interface INotificationService {

	void createNewNotification(Proposal proposal,
			NotificationType notificationType);

	void updateNotification(Notification notification);

	List<Notification> getNotificationByUsername(String username);

	List<Notification> getNewNotificationsByUsername(String username);

	void deleteNotification(Notification notification);
}
