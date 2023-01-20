package com.peoplist.peoplistTss.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="interactions")
public class Interaction {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@Enumerated
	private InteractionType interactionType;
	private String content;
	private LocalDateTime date;
	private boolean candidateResponded;
	
	@ManyToOne
    @JoinColumn(name="candidate_id", nullable=true)
	@JsonIgnore
	private Candidate candidate;
	
	public Interaction() {}
	
	public Interaction(UUID id, Candidate candidate, InteractionType interactionType, String content, LocalDateTime date,
			boolean candidateResponded) {
		this.id = id;
		this.candidate = candidate;
		this.interactionType = interactionType;
		this.content = content;
		this.date = date;
		this.candidateResponded = candidateResponded;
	}
	
	public Interaction(Candidate candidate, InteractionType interactionType, String content, LocalDateTime date,
			boolean candidateResponded) {
		this.candidate = candidate;
		this.interactionType = interactionType;
		this.content = content;
		this.date = date;
		this.candidateResponded = candidateResponded;
	}

	public UUID getId() {
		return id;
	}

	public InteractionType getInteractionType() {
		return interactionType;
	}

	public void setInteractionType(InteractionType interactionType) {
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

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	
}
