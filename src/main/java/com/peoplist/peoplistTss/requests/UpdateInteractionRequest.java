package com.peoplist.peoplistTss.requests;

import java.time.LocalDateTime;

public class UpdateInteractionRequest {
	private String interactionType;
	private String content;
	private LocalDateTime date;
	private boolean candidateResponded;
	public UpdateInteractionRequest(String interactionType, String content, LocalDateTime date,
			boolean candidateResponded) {
		this.interactionType = interactionType;
		this.content = content;
		this.date = date;
		this.candidateResponded = candidateResponded;
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
	
}
