package com.peoplist.peoplistTss.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.peoplist.peoplistTss.dto.InteractionDto;
import com.peoplist.peoplistTss.dto.InteractionDtoConverter;
import com.peoplist.peoplistTss.entities.Candidate;
import com.peoplist.peoplistTss.entities.Interaction;
import com.peoplist.peoplistTss.entities.InteractionType;
import com.peoplist.peoplistTss.exceptions.InteractionNotFoundException;
import com.peoplist.peoplistTss.repository.InteractionRepository;
import com.peoplist.peoplistTss.requests.CreateInteractionRequest;
import com.peoplist.peoplistTss.requests.UpdateInteractionRequest;

@Service
public class InteractionService {
	
	private final InteractionRepository interactionRepository;
	private final CandidateService candidateService;
	private final InteractionDtoConverter interactionDtoConverter;
	
	public InteractionService(InteractionRepository interactionRepository, CandidateService candidateService, InteractionDtoConverter interactionDtoConverter) {
		this.interactionRepository = interactionRepository;
		this.candidateService = candidateService;
		this.interactionDtoConverter = interactionDtoConverter;
	}

	public Page<InteractionDto> getAllInteractionsByCandidateId(String candidateId, int page, int size) {
		Page<Interaction> interactions = interactionRepository.findAllByCandidateId(UUID.fromString(candidateId), PageRequest.of(page-1, size));
		String candidateName = extractCandidateName(candidateId);
		if(interactions.isEmpty()) {
			throw new InteractionNotFoundException("There exists no interactions with candidate " + candidateName + ".");
		}
		return interactions.map(interactionDtoConverter::convertToDto);
	}
	
	public Page<InteractionDto> getAllInteractionsByCandidateIdSorted(String candidateId, Integer page, Integer size, String sortedBy,
			String sortOrder) {
		Page<Interaction> interactions = interactionRepository.findAllByCandidateId(UUID.fromString(candidateId), 
				PageRequest.of(page-1, size, Sort.by(Sort.Direction.fromString(sortOrder) ,sortedBy)));
		String candidateName = extractCandidateName(candidateId);
		if(interactions.isEmpty()) {
			throw new InteractionNotFoundException("There exists no interactions with candidate " + candidateName + ".");
		}
		return interactions.map(interactionDtoConverter::convertToDto);
	}
	
	private String extractCandidateName(String candidateId) {
		return candidateService.findCandidateById(candidateId).getName();
	}

	public InteractionDto saveInteraction(String candidateId, CreateInteractionRequest createInteractionRequest) {
		InteractionType interactionType = InteractionType.valueOf(createInteractionRequest.getInteractionType().toUpperCase().replaceAll(" ", "_"));
		UUID id = UUID.randomUUID();
		Candidate candidate = candidateService.findCandidateById(candidateId);
		Interaction interaction = new Interaction(id, candidate, interactionType, createInteractionRequest.getContent(),
				createInteractionRequest.getDate(), createInteractionRequest.isCandidateResponded());
		return interactionDtoConverter.convertToDto(interactionRepository.save(interaction));
	}

	public InteractionDto updateInteractionInfo(String id, UpdateInteractionRequest updateInteractionRequest) {
		Interaction interaction = findInteractionById(id);
		if(!interaction.getInteractionType().equals(updateInteractionRequest.getInteractionType())) {
			interaction.setInteractionType(InteractionType.valueOf(updateInteractionRequest.getInteractionType().toUpperCase().replaceAll(" ", "_")));
		}
		if(!interaction.getContent().equals(updateInteractionRequest.getContent())) {
			interaction.setContent(updateInteractionRequest.getContent());
		}
		if(!interaction.getDate().equals(updateInteractionRequest.getDate())) {
			interaction.setDate(updateInteractionRequest.getDate());
		}
		if(!interaction.isCandidateResponded() == updateInteractionRequest.isCandidateResponded()) {
			interaction.setCandidateResponded(updateInteractionRequest.isCandidateResponded());
		}
		return interactionDtoConverter.convertToDto(interactionRepository.save(interaction));
	}
	
	protected Interaction findInteractionById(String id) {
		return interactionRepository.findById(UUID.fromString(id)).orElseThrow(() -> new InteractionNotFoundException("Interaction not found."));
	}

	public String deleteInteraction(String id) {
		Interaction interaction = findInteractionById(id);
		String candidateName = extractCandidateName(interaction.getCandidate().getId().toString());
		interactionRepository.delete(interaction);
		return candidateName;
	}

	public InteractionDto getInteractionById(String id) {
		return interactionDtoConverter.convertToDto(findInteractionById(id));
	}

}
