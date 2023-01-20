package com.peoplist.peoplistTss.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.peoplist.peoplistTss.entities.Candidate;
import com.peoplist.peoplistTss.entities.Interaction;
import com.peoplist.peoplistTss.entities.InteractionType;
import com.peoplist.peoplistTss.exceptions.CandidateNotFoundException;
import com.peoplist.peoplistTss.exceptions.InteractionNotFoundException;
import com.peoplist.peoplistTss.repository.InteractionRepository;
import com.peoplist.peoplistTss.requests.CreateInteractionRequest;
import com.peoplist.peoplistTss.requests.UpdateInteractionRequest;

@Service
public class InteractionService {
	
	private final InteractionRepository interactionRepository;
	private final CandidateService candidateService;
	
	public InteractionService(InteractionRepository interactionRepository, CandidateService candidateService) {
		this.interactionRepository = interactionRepository;
		this.candidateService = candidateService;
	}

	public List<Interaction> getLastThreeInteractionsOfCandidate(String candidateId, int page, int size) {
		Page<Interaction> interactions = interactionRepository.findAll(PageRequest.of(page, size));
		String candidateName = extractCandidateName(candidateId);
		if(interactions.isEmpty()) {
			throw new InteractionNotFoundException("There exists no interactions with candidate " + candidateName + ".");
		}
		return interactions.getContent();
	}

	public List<Interaction> getAllInteractionsByCandidateId(String candidateId, int page, int size) {
		Page<Interaction> interactions = interactionRepository.findAllByCandidateId(UUID.fromString(candidateId), PageRequest.of(page, size));
		String candidateName = extractCandidateName(candidateId);
		if(interactions.isEmpty()) {
			throw new CandidateNotFoundException("There exists no interactions with candidate " + candidateName + ".");
		}
		return interactions.getContent();
	}
	
	private String extractCandidateName(String candidateId) {
		return candidateService.findCandidateById(candidateId).getName();
	}

	public Interaction saveInteraction(String candidateId, CreateInteractionRequest createInteractionRequest) {
		InteractionType interactionType = InteractionType.valueOf(createInteractionRequest.getInteraction().toUpperCase().replaceAll(" ", "_"));
		UUID id = UUID.randomUUID();
		Candidate candidate = candidateService.getCandidateById(candidateId);
		Interaction interaction = new Interaction(id, candidate, interactionType, createInteractionRequest.getContent(),
				createInteractionRequest.getDate(), createInteractionRequest.isCandidateResponded());
		return interactionRepository.save(interaction);
	}

	public Interaction updateInteractionInfo(String id, UpdateInteractionRequest updateInteractionRequest) {
		Interaction interaction = findInteractionById(id);
		//Claimi extract ettigin methodu yaz bunun için ;)
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
		return interactionRepository.save(interaction);
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

}
