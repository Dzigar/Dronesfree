package com.dronesfree.broadcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.dronesfree.broadcast.model.NotifyMessage;

@Controller
public class WebSocketController {

	private final MessageSendingOperations<String> messagingTemplate;

	@Autowired
	public WebSocketController(
			final MessageSendingOperations<String> messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@MessageMapping("/call")
	public void notifyOwner(NotifyMessage ms)
			throws Exception {
		this.messagingTemplate.convertAndSend("/notify/" + ms.getCustomerName(),
				ms);
	}
}
