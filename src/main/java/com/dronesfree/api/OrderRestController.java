package com.dronesfree.api;

import java.security.Principal;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dronesfree.order.dto.OrderForm;
import com.dronesfree.order.model.Order;
import com.dronesfree.order.model.OrderStatus;
import com.dronesfree.order.service.IOrderService;
import com.dronesfree.user.service.IUserService;

@RestController
public class OrderRestController {

	@Autowired
	private IOrderService orderService;

	@Autowired
	private IUserService userService;

	private static final Logger log = Logger
			.getLogger(OrderRestController.class);

	@RequestMapping(value = "/api/order/add", method = RequestMethod.POST)
	public @ResponseBody Order createOrder(Principal principal,
			@Valid @RequestBody OrderForm orderForm) {
		orderForm.setUser(userService.getUserByUsername(principal.getName()));
		return orderService.createNewOrder(orderForm);
	}

	@RequestMapping(value = "/api/order/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<Order> getOrder(@PathVariable("id") long id) {
		Order order = orderService.getOrderById(id);
		if (order == null) {
			log.error("Order with id " + id + " not found");
			new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/order/{id}/complete", method = RequestMethod.POST)
	public ResponseEntity<Void> completeOrder(@PathVariable("id") long id) {
		Order order = orderService.getOrderById(id);
		order.setStatus(OrderStatus.COMPLETED);
		orderService.updateOrder(order);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/api/order/getMessage", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Message getMessage() {
		log.info("Accessing protected resource");
		return new Message(100, "Congratulations!",
				"You have accessed a Basic Auth protected resource.");
	}

	@XmlRootElement
	private class Message {

		private long id;

		private String subject;

		private String text;

		public Message() {
		}

		public Message(long id, String subject, String text) {
			this.id = id;
			this.subject = subject;
			this.text = text;
		}

		public String toString() {
			return "Id:[" + this.id + "] Subject:[" + this.subject + "] Text:["
					+ this.text + "]";
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

	}
}
