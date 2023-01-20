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

import com.peoplist.peoplistTss.entities.Interaction;
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
	
	@GetMapping("/getLastThree")
	public ResponseEntity<List<Interaction>> getLastThreeInteractionsOfCandidate(@RequestParam String candidateId) {
		return ResponseEntity.ok(interactionService.getLastThreeInteractionsOfCandidate(candidateId, 0, 3));
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Interaction>> getAllInteractionsByCandidateId(@RequestParam String candidateId, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		return ResponseEntity.ok(interactionService.getAllInteractionsByCandidateId(candidateId, page, size));
	}
	
	@PostMapping("")
	public ResponseEntity<Interaction> saveInteraction(@RequestParam String candidateId, @RequestBody CreateInteractionRequest createInteractionRequest) {
		return ResponseEntity.ok(interactionService.saveInteraction(candidateId, createInteractionRequest));
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Interaction> updateInteractionInfo(@PathVariable String id, @RequestBody UpdateInteractionRequest updateInteractionRequest) {
		return ResponseEntity.ok(interactionService.updateInteractionInfo(id, updateInteractionRequest));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteInteraction(@PathVariable String id) {
		return ResponseEntity.ok("Interaction with candidate " + interactionService.deleteInteraction(id) + " successfully deleted.");
	}
	
}
