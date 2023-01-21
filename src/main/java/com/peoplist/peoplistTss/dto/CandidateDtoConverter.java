package com.peoplist.peoplistTss.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.peoplist.peoplistTss.entities.Candidate;

@Service
public class CandidateDtoConverter {
	private final ModelMapper modelMapper;
	
	public CandidateDtoConverter(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public CandidateDto convertToDto(Candidate candidate) {
		CandidateDto candidateDto = modelMapper.map(candidate, CandidateDto.class);
	    return candidateDto;
	}
	
	public Candidate convertToEntity(CandidateDto candidateDto) {
		Candidate candidate = modelMapper.map(candidateDto, Candidate.class);
	    return candidate;
	}
}
