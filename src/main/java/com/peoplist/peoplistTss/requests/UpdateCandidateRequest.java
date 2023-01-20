package com.peoplist.peoplistTss.requests;

public class UpdateCandidateRequest {
	private String phone;
	private String mail;
	private String candidateStatus;
	public UpdateCandidateRequest(String phone, String mail, String candidateStatus) {
		this.phone = phone;
		this.mail = mail;
		this.candidateStatus = candidateStatus;
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
