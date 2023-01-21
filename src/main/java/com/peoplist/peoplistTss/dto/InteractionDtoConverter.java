package com.peoplist.peoplistTss.dto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.peoplist.peoplistTss.entities.Interaction;

@Service
public class InteractionDtoConverter {
	private final ModelMapper modelMapper;
	
	public InteractionDtoConverter(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public InteractionDto convertToDto(Interaction interaction) {
		InteractionDto interactionDto = modelMapper.map(interaction, InteractionDto.class);
	    return interactionDto;
	}
	
	public Interaction convertToEntity(InteractionDto interactionDto) {
		Interaction interaction = modelMapper.map(interactionDto, Interaction.class);
	    return interaction;
	}
}
