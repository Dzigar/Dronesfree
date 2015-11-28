package com.dronesfree.notification.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dronesfree.notification.dao.INotificationDao;
import com.dronesfree.notification.model.Notification;
import com.dronesfree.notification.model.NotificationType;
import com.dronesfree.proposal.model.Proposal;
import com.dronesfree.proposal.service.IProposalService;
import com.dronesfree.user.model.User;
import com.dronesfree.user.service.IUserService;

@Service
@Transactional
public class NotificationService implements INotificationService {

	@Autowired
	private INotificationDao notificationDao;

	@Autowired
	private IUserService userService;

	@Autowired
	private IProposalService proposalService;

	@Override
	public void createNewNotification(Proposal proposal,
			NotificationType notificationType) {
		User user = null;
		if (notificationType.equals(NotificationType.ACCEPT_PROPOSAL)) {
			user = proposal.getUser();
		} else
			user = proposal.getOrder().getUser();
		Notification notification = Notification.getBuilder()
				.proposal(proposal).notificationType(notificationType).build();
		notificationDao.saveNewNotification(notification);
		notification.setUser(user);
		notificationDao.updateNotification(notification);

	}

	@Override
	public void updateNotification(Notification notification) {
		notificationDao.updateNotification(notification);
	}

	@Override
	public List<Notification> getNotificationByUsername(String username) {
		return reviewedAllUserNotifications(notificationDao
				.getAllUserNotifications(userService
						.getUserByUsername(username)));
	}

	/**
	 * 
	 * @param notifications
	 * @return
	 */
	private List<Notification> reviewedAllUserNotifications(
			List<Notification> notifications) {
		Iterator<Notification> iterator = notifications.iterator();
		while (iterator.hasNext()) {
			Notification notification = iterator.next();
			if (!notification.isRevised()) {
				notification.setRevised(true);
				notificationDao.updateNotification(notification);
			}
		}
		return notifications;
	}

	@Override
	public List<Notification> getNewNotificationsByUsername(String username) {
		return notificationDao.getNewUserNotifications(userService
				.getUserByUsername(username));
	}

	@Override
	public void deleteNotification(Notification notification) {
		notificationDao.deleteNotification(notification);
	}

}
