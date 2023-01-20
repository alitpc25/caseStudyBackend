package com.peoplist.peoplistTss.entities;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="candidates")
public class Candidate {
	
	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	private String surname;
	private String phone;
	private String mail;
	
	@OneToMany(mappedBy="candidate")
	private Set<Interaction> interactions;
	
	@Enumerated
	private CandidateStatusType candidateStatus;
	
	public Candidate() {}
	
	public Candidate(UUID id, String name, String surname, String phone, String mail, Set<Interaction> interactions,
			CandidateStatusType candidateStatus) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.mail = mail;
		this.interactions = interactions;
		this.candidateStatus = candidateStatus;
	}
	
	public Candidate(UUID id, String name, String surname, String phone, String mail, CandidateStatusType candidateStatus) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.mail = mail;
		this.candidateStatus = candidateStatus;
	}
	
	public Candidate(String name) {
		this.name = name;
	}
	
	public UUID getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
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
	public Set<Interaction> getInteractions() {
		return interactions;
	}
	public CandidateStatusType getCandidateStatus() {
		return candidateStatus;
	}
	public void setCandidateStatus(CandidateStatusType candidateStatus) {
		this.candidateStatus = candidateStatus;
	}

}
