package com.peoplist.peoplistTss.dto;
import java.util.List;
import java.util.stream.Collectors;

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
	
	public List<InteractionDto> convertToDtoList(List<Interaction> interactionList) {
		List<InteractionDto> interactionDtoList = interactionList.stream().map(c -> modelMapper.map(c, InteractionDto.class)).collect(Collectors.toList());
	    return interactionDtoList;
	}
	
	public List<Interaction> convertToEntity(List<InteractionDto> interactionDtoList) {
		List<Interaction> interactionList = interactionDtoList.stream().map(c -> modelMapper.map(c, Interaction.class)).collect(Collectors.toList());
	    return interactionList;
	}
}
