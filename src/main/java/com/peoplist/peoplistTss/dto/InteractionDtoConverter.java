package com.peoplist.peoplistTss.dto;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.peoplist.peoplistTss.entities.Interaction;

@Service
public class InteractionDtoConverter {
	
	public InteractionDto convertToDto(Interaction from) {
		return new InteractionDto(from.getId().toString(), from.getInteractionType().getType(), from.getContent(), from.getDate(), from.isCandidateResponded(), from.getCandidate().getId().toString());
	}
	
	public Page<InteractionDto> convertToDtoList(Page<Interaction> from) {
		return from.map(this::convertToDto);
	}
}
