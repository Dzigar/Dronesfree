package com.dronesfree.order.dao;

import java.util.List;

import com.dronesfree.order.model.Order;
import com.dronesfree.user.model.User;

public interface IOrderDao {

	List<Order> getAllOrders();

	Order getOrderById(Long id);

	void saveOrder(Order order);

	void updateOrder(Order order);

	void removeOrder(Order order);

	List<Order> getUncompletedOrders();

	List<Order> getUserOrders(User user);

}
