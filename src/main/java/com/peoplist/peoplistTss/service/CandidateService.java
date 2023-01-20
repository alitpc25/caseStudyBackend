package com.peoplist.peoplistTss.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.peoplist.peoplistTss.entities.Candidate;
import com.peoplist.peoplistTss.entities.CandidateStatusType;
import com.peoplist.peoplistTss.exceptions.CandidateNotFoundException;
import com.peoplist.peoplistTss.repository.CandidateRepository;
import com.peoplist.peoplistTss.requests.CreateCandidateRequest;
import com.peoplist.peoplistTss.requests.UpdateCandidateRequest;

@Service
public class CandidateService {

	private final CandidateRepository candidateRepository;

	public CandidateService(CandidateRepository candidateRepository) {
		this.candidateRepository = candidateRepository;
	}

	public List<Candidate> getAllCandidates(int page, int size) {
		Page<Candidate> candidates = candidateRepository.findAll(PageRequest.of(page, size));
		if(candidates.isEmpty()) {
			throw new CandidateNotFoundException("There exists no candidate.");
		}
		return candidates.getContent();
	}

	public Candidate getCandidateById(String id) {
		return findCandidateById(id);
	}

	public Candidate saveCandidate(CreateCandidateRequest createCandidateRequest) {
		CandidateStatusType candidateStatus = CandidateStatusType.valueOf(createCandidateRequest.getCandidateStatus().toUpperCase().replaceAll(" ", "_"));
		UUID id = UUID.randomUUID();
		Candidate candidate = new Candidate(id, createCandidateRequest.getName(), createCandidateRequest.getSurname(),
				createCandidateRequest.getPhone(), createCandidateRequest.getMail(), candidateStatus);
		return candidateRepository.save(candidate);
	}

	public Candidate updateCandidateStatus(String id, String newStatus) {
		// add dtos
		Candidate candidate = findCandidateById(id);
		candidate.setCandidateStatus(CandidateStatusType.valueOf(newStatus.toUpperCase().replaceAll(" ", "_")));
		return candidateRepository.save(candidate);
	}
	
	public Candidate updateCandidateInfo(String id, String newStatus) {
		// add dtos
		Candidate candidate = findCandidateById(id);
		candidate.setCandidateStatus(CandidateStatusType.valueOf(newStatus.toUpperCase().replaceAll(" ", "_")));
		return candidateRepository.save(candidate);
	}
	
	protected Candidate findCandidateById(String id) {
		return candidateRepository.findById(UUID.fromString(id)).orElseThrow(() -> new CandidateNotFoundException("User not found."));
	}

	public Candidate updateCandidateInfo(String id, UpdateCandidateRequest updateCandidateRequest) {
		Candidate candidate = findCandidateById(id);
		if(!candidate.getPhone().equals(updateCandidateRequest.getPhone())) {
			candidate.setPhone(updateCandidateRequest.getPhone());
		}
		if(!candidate.getMail().equals(updateCandidateRequest.getMail())) {
			candidate.setMail(updateCandidateRequest.getMail());
		}
		if(!candidate.getCandidateStatus().equals(updateCandidateRequest.getCandidateStatus())) {
			candidate.setCandidateStatus(CandidateStatusType.valueOf(updateCandidateRequest.getCandidateStatus().toUpperCase().replaceAll(" ", "_")));
		}
		return candidateRepository.save(candidate);
	}

	public String deleteCandidate(String id) {
		Candidate candidate = findCandidateById(id);
		candidateRepository.delete(candidate);
		return candidate.getName();
	}
	
}
