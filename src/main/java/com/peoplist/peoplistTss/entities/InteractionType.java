package com.peoplist.peoplistTss.entities;

public enum InteractionType {
	PHONE("Phone"),
	MAIL("Mail");
	
	private String type;

	InteractionType(String type) {
		this.type = type;
	}
	
    public String getType() {
        return type;
    }
}
