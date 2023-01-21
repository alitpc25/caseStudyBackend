package com.peoplist.peoplistTss.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.peoplist.peoplistTss.dto.CandidateDto;
import com.peoplist.peoplistTss.dto.CandidateDtoConverter;
import com.peoplist.peoplistTss.entities.Candidate;
import com.peoplist.peoplistTss.entities.CandidateStatusType;
import com.peoplist.peoplistTss.exceptions.CandidateNotFoundException;
import com.peoplist.peoplistTss.exceptions.EmailAlreadyInUseException;
import com.peoplist.peoplistTss.exceptions.PhoneAlreadyInUseException;
import com.peoplist.peoplistTss.repository.CandidateRepository;
import com.peoplist.peoplistTss.requests.CreateCandidateRequest;
import com.peoplist.peoplistTss.requests.UpdateCandidateRequest;

@Service
public class CandidateService {

	private final CandidateRepository candidateRepository;
	private final CandidateDtoConverter candidateDtoConverter;

	public CandidateService(CandidateRepository candidateRepository, CandidateDtoConverter candidateDtoConverter) {
		this.candidateRepository = candidateRepository;
		this.candidateDtoConverter = candidateDtoConverter;
	}

	public List<CandidateDto> getAllCandidates(int page, int size) {
		Page<Candidate> candidates = candidateRepository.findAll(PageRequest.of(page, size));
		if(candidates.isEmpty()) {
			throw new CandidateNotFoundException("There exists no candidate.");
		}
		return candidates.getContent().stream().map(candidateDtoConverter::convertToDto).collect(Collectors.toList());
	}

	public CandidateDto getCandidateById(String id) {
		return candidateDtoConverter.convertToDto(findCandidateById(id));
	}

	public CandidateDto saveCandidate(CreateCandidateRequest createCandidateRequest) {
		if(candidateRepository.existsByMail(createCandidateRequest.getMail())) {
			throw new EmailAlreadyInUseException("Email already in use.");
		}
		if(candidateRepository.existsByPhone(createCandidateRequest.getPhone())) {
			throw new PhoneAlreadyInUseException("Phone already in use.");
		}
		CandidateStatusType candidateStatus = CandidateStatusType.valueOf(createCandidateRequest.getCandidateStatus().toUpperCase().replaceAll(" ", "_"));
		UUID id = UUID.randomUUID();
		Candidate candidate = new Candidate(id, createCandidateRequest.getName(), createCandidateRequest.getSurname(),
				createCandidateRequest.getPhone(), createCandidateRequest.getMail(), candidateStatus);
		return candidateDtoConverter.convertToDto(candidateRepository.save(candidate));
	}
	
	public CandidateDto updateCandidateInfo(String id, String newStatus) {
		Candidate candidate = findCandidateById(id);
		candidate.setCandidateStatus(CandidateStatusType.valueOf(newStatus.toUpperCase().replaceAll(" ", "_")));
		return candidateDtoConverter.convertToDto(candidateRepository.save(candidate));
	}
	
	protected Candidate findCandidateById(String id) {
		return candidateRepository.findById(UUID.fromString(id)).orElseThrow(() -> new CandidateNotFoundException("User not found."));
	}

	public CandidateDto updateCandidateInfo(String id, UpdateCandidateRequest updateCandidateRequest) {
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
		return candidateDtoConverter.convertToDto(candidateRepository.save(candidate));
	}

	public String deleteCandidate(String id) {
		Candidate candidate = findCandidateById(id);
		candidateRepository.delete(candidate);
		return candidate.getName();
	}
	
}
