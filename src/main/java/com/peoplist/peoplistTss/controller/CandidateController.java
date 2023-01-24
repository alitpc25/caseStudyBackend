package com.peoplist.peoplistTss.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.peoplist.peoplistTss.dto.CandidateDto;
import com.peoplist.peoplistTss.requests.CreateCandidateRequest;
import com.peoplist.peoplistTss.requests.UpdateCandidateRequest;
import com.peoplist.peoplistTss.service.CandidateService;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {
	
	private final CandidateService candidateService;

	public CandidateController(CandidateService candidateService) {
		this.candidateService = candidateService;
	}
	
	@GetMapping(value="", params={"page", "size"})
	public ResponseEntity<Page<CandidateDto>> getAllCandidates(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size) {
		return ResponseEntity.ok(candidateService.getAllCandidates(page, size));
	}
	
	@GetMapping(value="", params={"page", "size", "sortedBy", "sortOrder"})
	public ResponseEntity<Page<CandidateDto>> getAllCandidatesSorted(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size,
			@RequestParam(value = "sortedBy", defaultValue = "name") String sortedBy,
			@RequestParam(value = "sortOrder", defaultValue = "DESC") String sortOrder) {
		return ResponseEntity.ok(candidateService.getAllCandidatesSorted(page, size, sortedBy, sortOrder));
	}
	
	@GetMapping(value="", params={"page", "size", "name", "surname"})
	public ResponseEntity<Page<CandidateDto>> getAllCandidatesSearchByNameAndOrSurname(
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "surname", defaultValue = "") String surname) {
		return ResponseEntity.ok(candidateService.getAllCandidatesSearchByNameAndOrSurname(page, size, name, surname));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CandidateDto> getCandidateById(@PathVariable String id) {
		return ResponseEntity.ok(candidateService.getCandidateById(id));
	}
	
	@PostMapping("")
	public ResponseEntity<CandidateDto> saveCandidate(@RequestBody CreateCandidateRequest createCandidateRequest) {
		return new ResponseEntity<CandidateDto>(candidateService.saveCandidate(createCandidateRequest), HttpStatus.CREATED);
	}
	
	@PatchMapping("/updateInfo/{id}")
	public ResponseEntity<CandidateDto> updateCandidateInfo(@PathVariable String id, @RequestBody UpdateCandidateRequest updateCandidateRequest) {
		return ResponseEntity.ok(candidateService.updateCandidateInfo(id, updateCandidateRequest));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCandidateStatus(@PathVariable String id) {
		return ResponseEntity.ok("Candidate " + candidateService.deleteCandidate(id) + " successfully deleted.");
	}

}
