package com.ivan.kafkaapp.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.kafkaapp.dto.Response;
import com.ivan.kafkaapp.dto.UserMessageDataResponse;
import com.ivan.kafkaapp.entity.KafkaMessageTemplate;
import com.ivan.kafkaapp.service.KafkaMessagingService;

import io.swagger.v3.oas.annotations.Operation;
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
	
	@Operation(
			summary = "POST endpoint to create a new template",
			description = "This endpoint stores a new template into the kafka_message_template table."
			)
	@PostMapping
	public Response<KafkaMessageTemplate> saveTemplate(@RequestHeader("userId") String userId, 
		@RequestBody KafkaMessageTemplate newTemplateRequest) throws JsonProcessingException{
		log.info("Service call /kafkaapi/templates, Request: {}", new ObjectMapper().writer().writeValueAsString(newTemplateRequest));
		KafkaMessageTemplate savedTemplate = kafkaService.saveTemplate(newTemplateRequest, userId);
		return Response.successResponse(savedTemplate, "Succesfully saved template to db");
	}
	
	@Operation(
			summary = "GET endpoint to get all templates",
			description = "This endpoint returns a list of all templates in the kafka_message_template db table."
			)
	@GetMapping
	public Response<List<KafkaMessageTemplate>> getAllTemplates(){
		log.info("Service call /kafkaapi/templates");
		List<KafkaMessageTemplate> resp = kafkaService.getAllTemplates();
		return Response.successResponse(resp);
	}
	
	@Operation(
			summary = "GET endpoint to get a template",
			description = "This endpoint gets by id the specified template from the kafka_message_template db table."
			)
	@GetMapping(value = "/{templateId}")
	public Response<KafkaMessageTemplate> getTemplate(@PathVariable int templateId){
		log.info("Service call /kafkaapi/templates/{}", templateId);
		KafkaMessageTemplate resp = kafkaService.getTemplate(templateId);
		return Response.successResponse(resp);
		
	}
	
	@Operation(
			summary = "PUT endpoint to update a template",
			description = "This endpoint updates the template with the id that is received in the request."
			)
	@PutMapping
	public Response<KafkaMessageTemplate> updateTemplate(@RequestHeader("userId") String userId, 
		@RequestBody KafkaMessageTemplate updateTemplateRequest) throws JsonProcessingException{
		log.info("Service call /kafkaapi/templates, Request: {}", new ObjectMapper().writer().writeValueAsString(updateTemplateRequest));
		KafkaMessageTemplate updatedTemplate = kafkaService.updateTemplate(updateTemplateRequest, userId);
		return Response.successResponse(updatedTemplate, "Succesfully saved template to db");
	}
	
	@Operation(
			summary = "DELETE endpoint to delete a template",
			description = "This endpoint deletes the template with the id that is received in the request."
			)
	@DeleteMapping(value = "/{templateId}")
	public Response<KafkaMessageTemplate> deleteTemplate(@PathVariable int templateId){
		log.info("Service call /kafkaapi/templates/{}", templateId);
		kafkaService.deleteTemplate(templateId);
		return Response.successResponse("Succesfully delete template with id " + templateId);
		
	}
	
	@Operation(
			summary = "GET endpoint to get all messages for a template",
			description = "This endpoint gets all messages that have been sent using the template with the template id that is received in the request."
			)
	@GetMapping(value = "/{templateId}/messages")
	public Response<UserMessageDataResponse> getMessagesForTemplate(@PathVariable int templateId){
		log.info("Service call /kafkaapi/templates/{}/messages", templateId);
		UserMessageDataResponse resp = kafkaService.getMessagesForTemplate(templateId);
		return Response.successResponse(resp);
		
	}
}
