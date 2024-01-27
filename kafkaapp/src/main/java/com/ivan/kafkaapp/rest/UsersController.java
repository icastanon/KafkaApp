package com.ivan.kafkaapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivan.kafkaapp.dto.Response;
import com.ivan.kafkaapp.dto.UserMessageDataResponse;
import com.ivan.kafkaapp.service.KafkaMessagingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/kafkaapi/users")
public class UsersController {
	private KafkaMessagingService kafkaService;
	
	@Autowired
	public UsersController(KafkaMessagingService kafkaMessagingService) {
		kafkaService = kafkaMessagingService;
	}
	
	@GetMapping(value = "/{userId}/messages")
	public Response<UserMessageDataResponse> getMessagesForUser(@PathVariable String userId){
		log.info("Service call /kafka/{}/messages", userId);
		UserMessageDataResponse resp = kafkaService.getMessagesForUser(userId);
		return Response.successResponse(resp);
	}
	
	//TODO: add endpoint to get all users who have sent messages - GET
}
