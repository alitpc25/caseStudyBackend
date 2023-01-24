package com.peoplist.peoplistTss.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.peoplist.peoplistTss.dto.InteractionDto;
import com.peoplist.peoplistTss.dto.InteractionDtoConverter;
import com.peoplist.peoplistTss.entities.Candidate;
import com.peoplist.peoplistTss.entities.CandidateStatusType;
import com.peoplist.peoplistTss.entities.Interaction;
import com.peoplist.peoplistTss.entities.InteractionType;
import com.peoplist.peoplistTss.exceptions.InteractionNotFoundException;
import com.peoplist.peoplistTss.repository.InteractionRepository;
import com.peoplist.peoplistTss.requests.CreateInteractionRequest;
import com.peoplist.peoplistTss.requests.UpdateInteractionRequest;
import com.peoplist.peoplistTss.service.CandidateService;
import com.peoplist.peoplistTss.service.InteractionService;

public class InteractionServiceTest {
	
	private InteractionRepository interactionRepository;
	private CandidateService candidateService;
	private InteractionDtoConverter interactionDtoConverter;

	private InteractionService interactionService;
	
	@BeforeEach
	void setUp() throws Exception {
		interactionRepository = Mockito.mock(InteractionRepository.class);
		candidateService = Mockito.mock(CandidateService.class);
		interactionDtoConverter = Mockito.mock(InteractionDtoConverter.class);

		interactionService = new InteractionService(interactionRepository, candidateService, interactionDtoConverter);
	}
	
	@Test
	void testGetAllInteractionsByCandidateId_whenValidRequest_thenShouldReturnPageOfInteractionDto() {
		//Data for testing
		UUID candidateId = UUID.randomUUID();
		UUID interactionId = UUID.randomUUID();
		Candidate candidate1 = new Candidate(candidateId, "test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		LocalDateTime date = LocalDateTime.now();
		Interaction interaction1 = new Interaction(interactionId, candidate1, InteractionType.MAIL, "test-content", date, true);
		Page<Interaction> interactions = new PageImpl<>(List.of(interaction1));
		
		InteractionDto interactionDto1 = new InteractionDto(interactionId.toString(), InteractionType.MAIL.getType(), "test-content", date, true, candidate1.getId().toString());
		Page<InteractionDto> interactionDtos = new PageImpl<>(List.of(interactionDto1));
		
		//Test case
		Mockito.when(interactionRepository.findAllByCandidateId(candidateId, PageRequest.of(0, 1))).thenReturn(Optional.of(interactions).get());
		Mockito.when(candidateService.findCandidateById(candidateId.toString())).thenReturn(candidate1);
		Mockito.when(interactionDtoConverter.convertToDtoList(interactions)).thenReturn(interactionDtos);
		
		//Verify
		Page<InteractionDto> result = interactionService.getAllInteractionsByCandidateId(candidateId.toString(), 1, 1);
		assertEquals(result.getContent(), interactionDtos.getContent());
		
		Mockito.verify(interactionRepository).findAllByCandidateId(candidateId, PageRequest.of(0, 1));
		Mockito.verify(candidateService).findCandidateById(candidateId.toString());
		Mockito.verify(interactionDtoConverter).convertToDtoList(interactions);
	}
	
	@Test
	void testGetAllInteractionsByCandidateId_whenInteractionsEmpty_thenShouldThrowInteractionNotFoundException() {
		//Data for testing
		UUID candidateId = UUID.randomUUID();
		Candidate candidate1 = new Candidate(candidateId, "test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		Page<Interaction> interactions = new PageImpl<>(List.of());
		
		//Test case
		Mockito.when(interactionRepository.findAllByCandidateId(candidateId, PageRequest.of(0, 1))).thenReturn(interactions);
		Mockito.when(candidateService.findCandidateById(candidateId.toString())).thenReturn(candidate1);
		
		//Verify
		assertThrows(InteractionNotFoundException.class, () -> {
			interactionService.getAllInteractionsByCandidateId(candidate1.getId().toString(), 1, 1);
		});
		
		Mockito.verify(interactionRepository).findAllByCandidateId(candidateId, PageRequest.of(0, 1));
		Mockito.verify(candidateService).findCandidateById(candidateId.toString());
	}
	
	@Test
	void testGetAllInteractionsByCandidateIdSorted_whenValidRequest_thenShouldReturnPageOfInteractionDto() {
		//Data for testing
		UUID candidateId = UUID.randomUUID();
		UUID interactionId = UUID.randomUUID();
		Candidate candidate1 = new Candidate(candidateId, "test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		LocalDateTime date = LocalDateTime.now();
		Interaction interaction1 = new Interaction(interactionId, candidate1, InteractionType.MAIL, "test-content", date, true);
		Page<Interaction> interactions = new PageImpl<>(List.of(interaction1));
		
		InteractionDto interactionDto1 = new InteractionDto(interactionId.toString(), InteractionType.MAIL.getType(), "test-content", date, true, candidate1.getId().toString());
		Page<InteractionDto> interactionDtos = new PageImpl<>(List.of(interactionDto1));
		
		//Test case
		Mockito.when(interactionRepository.findAllByCandidateId(candidateId, PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC , "status")))).thenReturn(interactions);
		Mockito.when(candidateService.findCandidateById(candidateId.toString())).thenReturn(candidate1);
		Mockito.when(interactionDtoConverter.convertToDtoList(interactions)).thenReturn(interactionDtos);
		
		//Verify
		Page<InteractionDto> result = interactionService.getAllInteractionsByCandidateIdSorted(candidate1.getId().toString(), 1, 1, "status", "ASC");
		assertEquals(result, interactionDtos);
		
		Mockito.verify(interactionRepository).findAllByCandidateId(candidateId, PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC , "status")));
		Mockito.verify(candidateService).findCandidateById(candidateId.toString());
		Mockito.verify(interactionDtoConverter).convertToDtoList(interactions);
	}
	
	@Test
	void testGetAllInteractionsByCandidateIdSorted_whenInteractionsEmpty_thenShouldThrowInteractionNotFoundException() {
		//Data for testing
		UUID candidateId = UUID.randomUUID();
		Candidate candidate1 = new Candidate(candidateId, "test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		Page<Interaction> interactions = new PageImpl<>(List.of());
		
		//Test case
		Mockito.when(interactionRepository.findAllByCandidateId(candidateId, PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC , "status")))).thenReturn(interactions);
		Mockito.when(candidateService.findCandidateById(candidateId.toString())).thenReturn(candidate1);
		
		//Verify
		assertThrows(InteractionNotFoundException.class, () -> {
			interactionService.getAllInteractionsByCandidateIdSorted(candidate1.getId().toString(), 1, 1, "status", "ASC");
		});
		
		Mockito.verify(interactionRepository).findAllByCandidateId(candidateId, PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC , "status")));
		Mockito.verify(candidateService).findCandidateById(candidateId.toString());
	}
	
	@Test
	void testSaveInteraction_whenValidRequest_thenShouldReturnInteractionDto() {
		//Data for testing
		UUID candidateId = UUID.randomUUID();
		UUID interactionId = UUID.randomUUID();
		Candidate candidate1 = new Candidate(candidateId, "test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		LocalDateTime date = LocalDateTime.now();
		CreateInteractionRequest request = new CreateInteractionRequest("mail", "test-content", date, true);
		Interaction interaction1 = new Interaction(interactionId, candidate1, InteractionType.valueOf(request.getInteractionType().toUpperCase().replaceAll(" ", "_")), request.getContent(), request.getDate(), request.isCandidateResponded());
		
		InteractionDto interactionDto1 = new InteractionDto(interactionId.toString(), InteractionType.MAIL.getType(), "test-content", date, true, candidate1.getId().toString());
		
		//Test case
		Mockito.when(candidateService.findCandidateById(candidateId.toString())).thenReturn(candidate1);
		Mockito.when(interactionRepository.save(interaction1)).thenReturn(interaction1);
		Mockito.when(interactionDtoConverter.convertToDto(interaction1)).thenReturn(interactionDto1);
		
		//Verify
		InteractionDto result = interactionService.saveInteraction(candidate1.getId().toString(),request);
		assertEquals(result, interactionDto1);
		
		Mockito.verify(candidateService).findCandidateById(candidateId.toString());
		Mockito.verify(interactionRepository).save(interaction1);
		Mockito.verify(interactionDtoConverter).convertToDto(interaction1);
	}
	
	@Test
	void testUpdateInteractionInfo_whenNoFieldUpdated_thenShouldReturnInteractionDto() {
		//Data for testing
		UUID candidateId = UUID.randomUUID();
		UUID interactionId = UUID.randomUUID();
		Candidate candidate1 = new Candidate(candidateId, "test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		LocalDateTime date = LocalDateTime.now();
		UpdateInteractionRequest request = new UpdateInteractionRequest("mail", "test-content", date, true);
		Interaction interaction1 = new Interaction(interactionId, candidate1, InteractionType.MAIL, "test-content", date, true);
		
		InteractionDto interactionDto1 = new InteractionDto(interactionId.toString(), InteractionType.MAIL.getType(), "test-content", date, true, candidate1.getId().toString());
		
		//Test case
		Mockito.when(interactionRepository.findById(interactionId)).thenReturn(Optional.of(interaction1));
		Mockito.when(interactionRepository.save(interaction1)).thenReturn(interaction1);
		Mockito.when(interactionDtoConverter.convertToDto(interaction1)).thenReturn(interactionDto1);
		
		//Verify
		InteractionDto result = interactionService.updateInteractionInfo(interaction1.getId().toString(),request);
		assertEquals(result, interactionDto1);
		
		Mockito.verify(interactionRepository).findById(interactionId);
		Mockito.verify(interactionRepository).save(interaction1);
		Mockito.verify(interactionDtoConverter).convertToDto(interaction1);
	}
	
	@Test
	void testUpdateInteractionInfo_whenAllFieldsUpdated_thenShouldReturnInteractionDto() {
		//Data for testing
		UUID candidateId = UUID.randomUUID();
		UUID interactionId = UUID.randomUUID();
		Candidate candidate1 = new Candidate(candidateId, "test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		LocalDateTime date = LocalDateTime.now();
		LocalDateTime updatedDate = LocalDateTime.now().plusHours(2);
		UpdateInteractionRequest request = new UpdateInteractionRequest("phone", "test-content-updated", updatedDate, false);
		Interaction interaction1 = new Interaction(interactionId, candidate1, InteractionType.MAIL, "test-content", date, true);
		Interaction updatedInteraction1 = new Interaction(interactionId, candidate1, InteractionType.valueOf(request.getInteractionType().toUpperCase().replaceAll(" ", "_")), request.getContent(), request.getDate(), request.isCandidateResponded());
		
		InteractionDto updatedInteractionDto1 = new InteractionDto(interactionId.toString(), request.getInteractionType(), request.getContent(), request.getDate(), request.isCandidateResponded(), candidate1.getId().toString());
		
		//Test case
		Mockito.when(interactionRepository.findById(interactionId)).thenReturn(Optional.of(interaction1));
		Mockito.when(interactionRepository.save(updatedInteraction1)).thenReturn(updatedInteraction1);
		Mockito.when(interactionDtoConverter.convertToDto(updatedInteraction1)).thenReturn(updatedInteractionDto1);
		
		//Verify
		InteractionDto result = interactionService.updateInteractionInfo(interaction1.getId().toString(),request);
		assertEquals(result, updatedInteractionDto1);
		
		Mockito.verify(interactionRepository).findById(interactionId);
		Mockito.verify(interactionRepository).save(updatedInteraction1);
		Mockito.verify(interactionDtoConverter).convertToDto(updatedInteraction1);
	}
	
	@Test
	void testDeleteInteraction_whenValidRequest_thenShouldReturnInteractionName() {
		//Data for testing
		UUID candidateId = UUID.randomUUID();
		UUID interactionId = UUID.randomUUID();
		Candidate candidate1 = new Candidate(candidateId, "test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		LocalDateTime date = LocalDateTime.now();
		Interaction interaction1 = new Interaction(interactionId, candidate1, InteractionType.MAIL, "test-content", date, true);
		
				
		//Test case
		Mockito.when(interactionRepository.findById(interactionId)).thenReturn(Optional.of(interaction1));
		Mockito.when(candidateService.findCandidateById(candidateId.toString())).thenReturn(candidate1);
		
		//Verify
		String result = interactionService.deleteInteraction(interaction1.getId().toString());
		assertEquals(result, candidate1.getName());
		
		Mockito.verify(interactionRepository).findById(interactionId);
		Mockito.verify(candidateService).findCandidateById(candidateId.toString());
	}
	
	@Test
	void testGetInteractionById_whenValidRequest_thenShouldReturnInteractionDto() {
		//Data for testing
		UUID candidateId = UUID.randomUUID();
		UUID interactionId = UUID.randomUUID();
		Candidate candidate1 = new Candidate(candidateId, "test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		LocalDateTime date = LocalDateTime.now();
		Interaction interaction1 = new Interaction(interactionId, candidate1, InteractionType.MAIL, "test-content", date, true);

		InteractionDto interactionDto1 = new InteractionDto(interactionId.toString(), InteractionType.MAIL.getType(), "test-content", date, true, candidate1.getId().toString());
				
		//Test case
		Mockito.when(interactionRepository.findById(interactionId)).thenReturn(Optional.of(interaction1));
		Mockito.when(interactionDtoConverter.convertToDto(interaction1)).thenReturn(interactionDto1);
		
		//Verify
		InteractionDto result = interactionService.getInteractionById(interaction1.getId().toString());
		assertEquals(result, interactionDto1);
		
		Mockito.verify(interactionRepository).findById(interactionId);
		Mockito.verify(interactionDtoConverter).convertToDto(interaction1);
	}
	
}
