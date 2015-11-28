package com.dronesfree.notification.dao;

import java.util.List;

import com.dronesfree.notification.model.Notification;
import com.dronesfree.user.model.User;

public interface INotificationDao {

	void saveNewNotification(Notification notification);

	void updateNotification(Notification notification);

	Notification getNotificationById(Long notificationId);
	
	List<Notification> getAllUserNotifications(User user);

	List<Notification> getNewUserNotifications(User user);

	void deleteNotification(Notification notification);
}
