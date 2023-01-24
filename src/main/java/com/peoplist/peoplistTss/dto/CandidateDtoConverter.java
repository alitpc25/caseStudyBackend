package com.peoplist.peoplistTss.dto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.peoplist.peoplistTss.entities.Candidate;

@Service
public class CandidateDtoConverter {
	
	public CandidateDto convertToDto(Candidate from) {
		return new CandidateDto(from.getId().toString(), from.getName(), from.getSurname(), from.getPhone(), from.getMail(), from.getStatus().getStatus());
	}
	
	public Page<CandidateDto> convertToDtoList(Page<Candidate> from) {
		return from.map(this::convertToDto);
	}
}
