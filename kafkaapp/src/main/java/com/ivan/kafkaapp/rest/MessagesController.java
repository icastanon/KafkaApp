package com.ivan.kafkaapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.kafkaapp.dto.LatestMessageResponse;
import com.ivan.kafkaapp.dto.MessagingRequest;
import com.ivan.kafkaapp.dto.Response;
import com.ivan.kafkaapp.service.KafkaMessagingService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/kafkaapi/messages")
public class MessagesController {
	private KafkaMessagingService kafkaService;
	
	@Autowired
	public MessagesController(KafkaMessagingService kafkaMessagingService) {
		kafkaService = kafkaMessagingService;
	}
	
	@Operation(
			summary = "POST endpoint to send a message",
			description = "This endpoint uses the specified template to send a message to the kafka topic. It also stores metadata in the kafka_message_data db table."
			)
	@PostMapping
	public Response<?> sendMessage(@RequestHeader("userId") String userId, 
		@RequestBody MessagingRequest kafkaRequest) throws JsonProcessingException{
		log.info("Service call /kafka/sendmessage, Request: {}", new ObjectMapper().writer().writeValueAsString(kafkaRequest));
		kafkaService.sendMessage(userId, kafkaRequest);
		return Response.successResponse("Succesfully sent message to kafka topic");
	}
	
	@Operation(
			summary = "GET endpoint to get the latest message",
			description = "This endpoint returns the last sent message. It gets the row with the largest created date from kafka_message_data db table."
			)
	@GetMapping(value = "/latestmessage")
	public Response<LatestMessageResponse> getLatestMessage(){
		log.info("Service call /kafka/latestmessage");
		LatestMessageResponse resp = kafkaService.getLatestMessage();
		return Response.successResponse(resp);
	}
}
