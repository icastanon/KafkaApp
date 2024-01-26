package com.ivan.kafkaapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import com.ivan.kafkaapp.exception.TemplateNotFoundException;
import com.ivan.kafkaapp.exception.UserNotFoundException;
import com.ivan.kafkaapp.service.KafkaMessagingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/kafkaapi")
public class KafkaRestController {
	private KafkaMessagingService kafkaService;
	
	@Autowired
	public KafkaRestController(KafkaMessagingService kafkaMessagingService) {
		kafkaService = kafkaMessagingService;
	}
	
	@PostMapping(value = "/sendmessage")
	public Response<?> sendMessage(@RequestHeader("userId") String userId, 
		@RequestBody MessagingRequest kafkaRequest) throws JsonProcessingException{
		log.info("Service call /kafka/sendmessage, Request: {}", new ObjectMapper().writer().writeValueAsString(kafkaRequest));
		kafkaService.sendMessage(userId, kafkaRequest);
		return Response.successResponse("Succesfully sent message to kafka topic");
	}
	
	@GetMapping(value = "/users/{userId}/messages")
	public Response<UserMessageDataResponse> getMessagesForUser(@PathVariable String userId){
		log.info("Service call /kafka/{}/messages", userId);
		UserMessageDataResponse resp = kafkaService.getMessagesForUser(userId);
		return Response.successResponse(resp);
	}
	
	@GetMapping(value = "/templates/{templateId}/messages")
	public Response<UserMessageDataResponse> getMessagesForTemplate(@PathVariable int templateId){
		log.info("Service call /kafka/{}/templates", templateId);
		UserMessageDataResponse resp = kafkaService.getMessagesForTemplate(templateId);
		return Response.successResponse(resp);
		
	}
	
	@GetMapping(value = "/latestmessage")
	public Response<LatestMessageResponse> getLatestMessage(){
		log.info("Service call /kafka/latestmessage");
		LatestMessageResponse resp = kafkaService.getLatestMessage();
		return Response.successResponse(resp);
	}
	
	//TODO: add endpoint to get all users who have sent messages
	
	//TODO: add enpdoint to get all templates
	
	//TODO: add endpoint to create a new template
}
