package com.peoplist.peoplistTss.requests;

public class CreateCandidateRequest {
	private String name;
	private String surname;
	private String phone;
	private String mail;
	private String candidateStatus;
	
	public CreateCandidateRequest(String name, String surname, String phone, String mail, String candidateStatus) {
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.mail = mail;
		this.candidateStatus = candidateStatus;
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
	public String getCandidateStatus() {
		return candidateStatus;
	}
	public void setCandidateStatus(String candidateStatus) {
		this.candidateStatus = candidateStatus;
	}
	
	
}
