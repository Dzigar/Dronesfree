package com.dronesfree.order.service;

import java.util.List;

import com.dronesfree.location.model.Location;
import com.dronesfree.order.dto.OrderForm;
import com.dronesfree.order.model.Order;
import com.dronesfree.user.model.User;

public interface IOrderService {

	List<Order> getAllOrders();

	Order getOrderById(Long id);

	void updateOrder(Order order);

	boolean removeOrderById(long id);

	List<Order> getUserOrders(User user);

	List<Order> getUncompletedOrders();

	Order createNewOrder(OrderForm orderForm);

	void checkin(User user, Location location);

}
