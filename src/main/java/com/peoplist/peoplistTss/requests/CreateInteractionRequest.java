package com.peoplist.peoplistTss.requests;

import java.time.LocalDateTime;

public class CreateInteractionRequest {
	private String interaction;
	private String content;
	private LocalDateTime date;
	private boolean candidateResponded;
	public CreateInteractionRequest(String interaction, String content, LocalDateTime date,
			boolean candidateResponded) {
		this.interaction = interaction;
		this.content = content;
		this.date = date;
		this.candidateResponded = candidateResponded;
	}
	public String getInteraction() {
		return interaction;
	}
	public void setInteraction(String interaction) {
		this.interaction = interaction;
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
