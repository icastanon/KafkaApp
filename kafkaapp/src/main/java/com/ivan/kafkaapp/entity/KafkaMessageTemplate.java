package com.ivan.kafkaapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="kafka_message_template")
public class KafkaMessageTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="kafka_message_template_id")
	private Integer templateId;
	
	@Column(name="message")
	private String message;
	
	@Column(name="message_name")
	private String messageName;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_date")
	private LocalDateTime createdDate;
	
	@Column(name="last_edited_by")
	private String lastEditedBy;
	
	@Column(name="last_edited_date")
	private LocalDateTime lastEditedDate;
	
	public KafkaMessageTemplate(Integer templateId, String message, String messageName, String createdBy,
			LocalDateTime createdDate, String lastEditedBy, LocalDateTime lastEditedDate) {
		super();
		this.templateId = templateId;
		this.message = message;
		this.messageName = messageName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastEditedBy = lastEditedBy;
		this.lastEditedDate = lastEditedDate;
	}

	public KafkaMessageTemplate() {
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastEditedBy() {
		return lastEditedBy;
	}

	public void setLastEditedBy(String lastEditedBy) {
		this.lastEditedBy = lastEditedBy;
	}

	public LocalDateTime getLastEditedDate() {
		return lastEditedDate;
	}

	public void setLastEditedDate(LocalDateTime lastEditedDate) {
		this.lastEditedDate = lastEditedDate;
	}
	
}
