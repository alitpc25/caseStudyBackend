package com.peoplist.peoplistTss.entities;

public enum CandidateStatusType {
	SOURCED("Sourced"),
	INTERVIEWING("Interviewing"),
	OFFER_SENT("Offer sent"),
	HIRED("Hired");
	
	private String status;

	CandidateStatusType(String status) {
		this.status = status;
	}
 
    public String getStatus() {
        return status;
    }
}
