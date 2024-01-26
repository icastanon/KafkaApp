package com.ivan.kafkaapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.ivan.kafkaapp.dto.UserMessageDataResponse;
import com.ivan.kafkaapp.service.KafkaMessagingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/kafkaapi/messages")
public class KafkaMessagesController {
	private KafkaMessagingService kafkaService;
	
	@Autowired
	public KafkaMessagesController(KafkaMessagingService kafkaMessagingService) {
		kafkaService = kafkaMessagingService;
	}
	
	@PostMapping(value = "/")
	public Response<?> sendMessage(@RequestHeader("userId") String userId, 
		@RequestBody MessagingRequest kafkaRequest) throws JsonProcessingException{
		log.info("Service call /kafka/sendmessage, Request: {}", new ObjectMapper().writer().writeValueAsString(kafkaRequest));
		kafkaService.sendMessage(userId, kafkaRequest);
		return Response.successResponse("Succesfully sent message to kafka topic");
	}
	
	@GetMapping(value = "/latestmessage")
	public Response<LatestMessageResponse> getLatestMessage(){
		log.info("Service call /kafka/latestmessage");
		LatestMessageResponse resp = kafkaService.getLatestMessage();
		return Response.successResponse(resp);
	}
}
