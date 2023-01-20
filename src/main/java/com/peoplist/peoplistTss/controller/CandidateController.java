package com.peoplist.peoplistTss.controller;

import java.util.List;

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

import com.peoplist.peoplistTss.entities.Candidate;
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
	
	@GetMapping("")
	public ResponseEntity<List<Candidate>> getAllCandidates(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		return ResponseEntity.ok(candidateService.getAllCandidates(page, size));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Candidate> getCandidateById(@PathVariable String id) {
		return ResponseEntity.ok(candidateService.getCandidateById(id));
	}
	
	@PostMapping("")
	public ResponseEntity<Candidate> saveCandidate(@RequestBody CreateCandidateRequest createCandidateRequest) {
		return ResponseEntity.ok(candidateService.saveCandidate(createCandidateRequest));
	}
	
	@PatchMapping("/updateStatus/{id}")
	public ResponseEntity<Candidate> updateCandidateStatus(@PathVariable String id, @PathVariable String newStatus) {
		return ResponseEntity.ok(candidateService.updateCandidateStatus(id, newStatus));
	}
	
	@PatchMapping("/updateInfo/{id}")
	public ResponseEntity<Candidate> updateCandidateInfo(@PathVariable String id, @RequestBody UpdateCandidateRequest updateCandidateRequest) {
		return ResponseEntity.ok(candidateService.updateCandidateInfo(id, updateCandidateRequest));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCandidateStatus(@PathVariable String id) {
		return ResponseEntity.ok("Candidate " + candidateService.deleteCandidate(id) + " successfully deleted.");
	}

}
