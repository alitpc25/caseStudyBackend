package com.peoplist.peoplistTss.dto;

public class CandidateDto {
	private String id;
	private String name;
	private String surname;
	private String phone;
	private String mail;
	private String status;
	
	public CandidateDto() {};
	
	public CandidateDto(String id, String name, String surname, String phone, String mail, String status) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.mail = mail;
		this.status = status;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
