package com.peoplist.peoplistTss.controller;

import org.springframework.data.domain.Page;
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

import com.peoplist.peoplistTss.dto.InteractionDto;
import com.peoplist.peoplistTss.requests.CreateInteractionRequest;
import com.peoplist.peoplistTss.requests.UpdateInteractionRequest;
import com.peoplist.peoplistTss.service.InteractionService;

@RestController
@RequestMapping("/api/v1/interactions")
public class InteractionController {
	private final InteractionService interactionService;

	public InteractionController(InteractionService interactionService) {
		this.interactionService = interactionService;
	}
	
	@GetMapping(value="", params={"candidateId", "page", "size"})
	public ResponseEntity<Page<InteractionDto>> getAllInteractionsByCandidateId(@RequestParam(value="candidateId") String candidateId, 
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size) {
		return ResponseEntity.ok(interactionService.getAllInteractionsByCandidateId(candidateId, page, size));
	}
	
	@GetMapping(value="", params={"candidateId", "page", "size", "sortedBy", "sortOrder"})
	public ResponseEntity<Page<InteractionDto>> getAllInteractionsByCandidateIdSorted(@RequestParam String candidateId, 
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size,
			@RequestParam(value = "sortedBy", defaultValue = "name") String sortedBy,
			@RequestParam(value = "sortOrder", defaultValue = "DESC") String sortOrder) {
		return ResponseEntity.ok(interactionService.getAllInteractionsByCandidateIdSorted(candidateId, page, size, sortedBy, sortOrder));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<InteractionDto> getInteractionById(@PathVariable String id) {
		return ResponseEntity.ok(interactionService.getInteractionById(id));
	}
	
	@PostMapping("")
	public ResponseEntity<InteractionDto> saveInteraction(@RequestParam String candidateId, @RequestBody CreateInteractionRequest createInteractionRequest) {
		return ResponseEntity.ok(interactionService.saveInteraction(candidateId, createInteractionRequest));
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<InteractionDto> updateInteractionInfo(@PathVariable String id, @RequestBody UpdateInteractionRequest updateInteractionRequest) {
		return ResponseEntity.ok(interactionService.updateInteractionInfo(id, updateInteractionRequest));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteInteraction(@PathVariable String id) {
		return ResponseEntity.ok("Interaction with candidate " + interactionService.deleteInteraction(id) + " successfully deleted.");
	}
	
}
