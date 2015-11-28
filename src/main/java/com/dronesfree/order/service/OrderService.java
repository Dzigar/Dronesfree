package com.dronesfree.order.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dronesfree.location.model.Location;
import com.dronesfree.location.service.ILocationService;
import com.dronesfree.notification.service.INotificationService;
import com.dronesfree.order.dao.IOrderDao;
import com.dronesfree.order.dto.OrderForm;
import com.dronesfree.order.model.Order;
import com.dronesfree.order.model.OrderStatus;
import com.dronesfree.proposal.service.IProposalService;
import com.dronesfree.user.model.User;

@Service
@Transactional
public class OrderService implements IOrderService {

	@Autowired
	private IOrderDao orderDao;

	@Autowired
	private ILocationService locationService;

	@Autowired
	private INotificationService notificationService;

	@Autowired
	private IProposalService proposalService;

	private static final Logger LOGGER = Logger.getLogger(OrderService.class);

	@Override
	public Order createNewOrder(OrderForm orderForm) {

		// Create location and save it
		Location location = new Location(orderForm.getLatitude(),
				orderForm.getLongitude(), orderForm.getAddress());
		locationService.saveNewLocation(location);
		// Create and save new order
		Order order = Order.getBuilder()
				.description(orderForm.getDescription())
				.orderStatus(OrderStatus.NEW_ORDER).build();
		orderDao.saveOrder(order);
		// Related order with location and user
		order.setGeolocation(location);
		order.setUser(orderForm.getUser());
		// Update order
		orderDao.updateOrder(order);
		return order;
	}

	@Override
	public List<Order> getAllOrders() {
		return orderDao.getAllOrders();
	}

	@Override
	public Order getOrderById(Long id) {
		return orderDao.getOrderById(id);
	}

	@Override
	public boolean removeOrderById(long id) {
		// Get order by id
		Order order = getOrderById(id);
		// Get order location
		Location location = order.getGeolocation();
		// Detach order from user
		order.setUser(null);
		// Delete all of proposals
		proposalService.deleteOrderProposals(order);
		// Detach proposals from order
		order.getProposals().clear();
		// remove order
		orderDao.removeOrder(order);
		// remove order location
		locationService.removeLocation(location);
		return true;
	}

	@Override
	public List<Order> getUserOrders(User user) {
		return orderDao.getUserOrders(user);
	}

	@Override
	public void checkin(User user, Location location) {
		orderDao.saveOrder(Order.getBuilder().location(location).user(user)
				.build());
	}

	@Override
	public void updateOrder(Order order) {
		orderDao.updateOrder(order);
	}

	@Override
	public List<Order> getUncompletedOrders() {
		return orderDao.getUncompletedOrders();
	}

}
