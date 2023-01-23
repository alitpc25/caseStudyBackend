package com.peoplist.peoplistTss.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

	public Page<CandidateDto> getAllCandidates(int page, int size) {
		Page<Candidate> candidates = candidateRepository.findAll(PageRequest.of(page-1, size));
		if(candidates == null || candidates.isEmpty()) {
			throw new CandidateNotFoundException("There exists no candidate.");
		}
		return new PageImpl<CandidateDto>(candidateDtoConverter.convertToDtoList(candidates.toList()));
	}
	
	public Page<CandidateDto> getAllCandidatesSorted(Integer page, Integer size, String sortedBy, String sortOrder) {
		Page<Candidate> candidates = candidateRepository.findAll(PageRequest.of(page-1, size, Sort.by(Sort.Direction.fromString(sortOrder) ,sortedBy)));
		if(candidates == null || candidates.isEmpty()) {
			throw new CandidateNotFoundException("There exists no candidate.");
		}
		return new PageImpl<CandidateDto>(candidateDtoConverter.convertToDtoList(candidates.toList()));
	}
	
	public Page<CandidateDto> getAllCandidatesSearchByNameAndOrSurname(Integer page, Integer size, String name, String surname) {
		Page<Candidate> candidates = candidateRepository.findByNameOrSurnameIgnoreCase(PageRequest.of(page-1, size), name, surname);
		if(candidates == null || candidates.isEmpty()) {
			throw new CandidateNotFoundException("There exists no candidate called "+ name + " " + surname);
		}
		return new PageImpl<CandidateDto>(candidateDtoConverter.convertToDtoList(candidates.toList()));
	}
	
	protected Candidate findCandidateById(String id) {
		return candidateRepository.findById(UUID.fromString(id)).orElseThrow(() -> new CandidateNotFoundException("User not found."));
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
		CandidateStatusType candidateStatus = CandidateStatusType.valueOf(createCandidateRequest.getStatus().toUpperCase().replaceAll(" ", "_"));
		UUID id = UUID.randomUUID();
		Candidate candidate = new Candidate(id, createCandidateRequest.getName(), createCandidateRequest.getSurname(),
				createCandidateRequest.getPhone(), createCandidateRequest.getMail(), candidateStatus);
		return candidateDtoConverter.convertToDto(candidateRepository.save(candidate));
	}

	public CandidateDto updateCandidateInfo(String id, UpdateCandidateRequest updateCandidateRequest) {
		Candidate candidate = findCandidateById(id);
		if(!candidate.getMail().equals(updateCandidateRequest.getMail())) {
			if(candidateRepository.existsByMail(updateCandidateRequest.getMail())) {
				throw new EmailAlreadyInUseException("Email already in use.");
			}
			candidate.setMail(updateCandidateRequest.getMail());
		}
		if(!candidate.getPhone().equals(updateCandidateRequest.getPhone())) {
			if(candidateRepository.existsByPhone(updateCandidateRequest.getPhone())) {
				throw new PhoneAlreadyInUseException("Phone already in use.");
			}
			candidate.setPhone(updateCandidateRequest.getPhone());
		}
		if(!candidate.getStatus().equals(updateCandidateRequest.getStatus())) {
			candidate.setStatus(CandidateStatusType.valueOf(updateCandidateRequest.getStatus().toUpperCase().replaceAll(" ", "_")));
		}
		return candidateDtoConverter.convertToDto(candidateRepository.save(candidate));
	}

	public String deleteCandidate(String id) {
		Candidate candidate = findCandidateById(id);
		candidateRepository.delete(candidate);
		return candidate.getName();
	}

}
