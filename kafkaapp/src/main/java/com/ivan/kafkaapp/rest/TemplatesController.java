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
@RequestMapping("/kafkaapi/templates")
public class TemplatesController {
	private KafkaMessagingService kafkaService;
	
	@Autowired
	public TemplatesController(KafkaMessagingService kafkaMessagingService) {
		kafkaService = kafkaMessagingService;
	}
	
	//TODO: add endpoint to create a new template - POST
	
	//TODO: add enpoint to get all templates - GET
	
	//TODO: add endpoint to get one template - GET
	
	//TODO: add endpoint to update a template - PUT
	
	@GetMapping(value = "/{templateId}/messages")
	public Response<UserMessageDataResponse> getMessagesForTemplate(@PathVariable int templateId){
		log.info("Service call /kafka/{}/templates", templateId);
		UserMessageDataResponse resp = kafkaService.getMessagesForTemplate(templateId);
		return Response.successResponse(resp);
		
	}
}
