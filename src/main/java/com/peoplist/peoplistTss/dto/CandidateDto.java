package com.peoplist.peoplistTss.dto;

import java.util.Set;

public class CandidateDto {
	private String id;
	private String name;
	private String surname;
	private String phone;
	private String mail;
	private Set<InteractionDto> interactions;
	private String candidateStatus;
	
	public CandidateDto() {};
	
	public CandidateDto(String id, String name, String surname, String phone, String mail,
			Set<InteractionDto> interactions, String candidateStatus) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.mail = mail;
		this.interactions = interactions;
		this.candidateStatus = candidateStatus;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Set<InteractionDto> getInteractions() {
		return interactions;
	}
	public void setInteractions(Set<InteractionDto> interactions) {
		this.interactions = interactions;
	}
	public String getCandidateStatus() {
		return candidateStatus;
	}
	public void setCandidateStatus(String candidateStatus) {
		this.candidateStatus = candidateStatus;
	}
	
}
