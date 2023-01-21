package com.peoplist.peoplistTss.requests;

public class CreateCandidateRequest {
	private String name;
	private String surname;
	private String phone;
	private String mail;
	private String status;
	
	public CreateCandidateRequest(String name, String surname, String phone, String mail, String status) {
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.mail = mail;
		this.status = status;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
