package com.peoplist.peoplistTss.dto;

import java.time.LocalDateTime;

public class InteractionDto {
	private String id;
	private String interactionType;
	private String content;
	private LocalDateTime date;
	private boolean candidateResponded;
	private String candidateId;
	
	public InteractionDto() {};
	
	public InteractionDto(String id, String interactionType, String content, LocalDateTime date,
			boolean candidateResponded, String candidateId) {
		this.id = id;
		this.interactionType = interactionType;
		this.content = content;
		this.date = date;
		this.candidateResponded = candidateResponded;
		this.candidateId = candidateId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInteractionType() {
		return interactionType;
	}
	public void setInteractionType(String interactionType) {
		this.interactionType = interactionType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public boolean isCandidateResponded() {
		return candidateResponded;
	}
	public void setCandidateResponded(boolean candidateResponded) {
		this.candidateResponded = candidateResponded;
	}
	public String getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}
	
	
}
