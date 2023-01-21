package com.peoplist.peoplistTss.requests;

public class UpdateCandidateRequest {
	private String phone;
	private String mail;
	private String status;
	public UpdateCandidateRequest(String phone, String mail, String status) {
		this.phone = phone;
		this.mail = mail;
		this.status = status;
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
