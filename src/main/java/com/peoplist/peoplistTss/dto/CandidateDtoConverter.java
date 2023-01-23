package com.peoplist.peoplistTss.dto;

import java.util.List;
import java.util.stream.Collectors;

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
	
	public List<CandidateDto> convertToDtoList(List<Candidate> candidateList) {
		List<CandidateDto> candidateDtoList = candidateList.stream().map(c -> modelMapper.map(c, CandidateDto.class)).collect(Collectors.toList());
	    return candidateDtoList;
	}
	
	public List<Candidate> convertToEntity(List<CandidateDto> candidateDtoList) {
		List<Candidate> candidateList = candidateDtoList.stream().map(c -> modelMapper.map(c, Candidate.class)).collect(Collectors.toList());
	    return candidateList;
	}
}
