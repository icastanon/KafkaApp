package com.ivan.kafkaapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.kafkaapp.dto.LatestMessageResponse;
import com.ivan.kafkaapp.dto.MessagingRequest;
import com.ivan.kafkaapp.dto.Response;
import com.ivan.kafkaapp.dto.UserMessageData;
import com.ivan.kafkaapp.dto.UserMessageDataResponse;
import com.ivan.kafkaapp.service.KafkaMessagingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/kafka")
public class KafkaRestController {
	private KafkaMessagingService kafkaService;
	
	@Autowired
	public KafkaRestController(KafkaMessagingService kafkaMessagingService) {
		kafkaService = kafkaMessagingService;
	}
	
	@PostMapping(value = "/sendmessage")
	public Response<Object> sendMessage(@RequestHeader("userId") String userId, 
			@RequestBody MessagingRequest kafkaRequest){
		try {
			log.info("Service call /kafka/sendmessage, Request: {}", new ObjectMapper().writer().writeValueAsString(kafkaRequest));
			kafkaService.sendMessage(userId, kafkaRequest);
			return Response.successResponse("Succesfully sent message to kafka topic");
		}catch(Exception e){
			log.error("Error: " + e.getMessage());
			return Response.failureResponse(e.getMessage());
		}
	}
	
	@GetMapping(value = "/messagesforuser")
	public Response<UserMessageDataResponse> getMessagesForUser(@RequestHeader("userId") String userId){
		try {
			log.info("Service call /kafka/messagesforuser, User Id: {}", userId);
			UserMessageDataResponse resp = kafkaService.getMessagesForUser(userId);
			return Response.successResponse(resp);
		}catch(Exception e){
			log.error("Error: " + e.getMessage());
			return Response.failureResponse(e.getMessage());
		}
	}
	
	@GetMapping(value = "/messagesfortemplate")
	public Response<UserMessageDataResponse> getMessagesForTemplate(@RequestParam("templateId") int templateId){
		try {
			log.info("Service call /kafka/messagesfortemplate, Template Id: {}", templateId);
			UserMessageDataResponse resp = kafkaService.getMessagesForTemplate(templateId);
			return Response.successResponse(resp);
		}catch(Exception e){
			log.error("Error: " + e.getMessage());
			return Response.failureResponse(e.getMessage());
		}
	}
	
	@GetMapping(value = "/latestmessage")
	public Response<LatestMessageResponse> getLatestMessage(){
		try {
			log.info("Service call /kafka/getlatestmessage");
			LatestMessageResponse resp = kafkaService.getLatestMessage();
			return Response.successResponse(resp);
		}catch(Exception e){
			log.error("Error: " + e.getMessage());
			return Response.failureResponse(e.getMessage());
		}
	}
	
	
}
