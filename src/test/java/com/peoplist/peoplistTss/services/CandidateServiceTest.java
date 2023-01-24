package com.peoplist.peoplistTss.services;

import static org.junit.jupiter.api.Assertions.*;

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
import com.peoplist.peoplistTss.service.CandidateService;

public class CandidateServiceTest {
	
	private CandidateRepository candidateRepository;
	private CandidateDtoConverter candidateDtoConverter;
	
	private CandidateService candidateService;
	
	@BeforeEach
	void setUp() throws Exception {
		candidateRepository = Mockito.mock(CandidateRepository.class);
		candidateDtoConverter = Mockito.mock(CandidateDtoConverter.class);

		candidateService = new CandidateService(candidateRepository, candidateDtoConverter);
	}
	
	@Test
	void testGetAllCandidates_whenValidRequest_thenShouldReturnPageOfCandidateDto() {
		//Data for testing
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		Candidate candidate1 = new Candidate("test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		CandidateDto candidateDto1 = new CandidateDto(id1.toString(), "test-name1","test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED.getStatus());
		Candidate candidate2 = new Candidate("test-name2", "test-surname2", "test-phone2", "test-mail2@email.com", CandidateStatusType.SOURCED);
		CandidateDto candidateDto2 = new CandidateDto(id2.toString(), "test-name2","test-surname2", "test-phone2", "test-mail2@email.com", CandidateStatusType.SOURCED.getStatus());
		
		Page<Candidate> candidates = new PageImpl<Candidate>(List.of(candidate1, candidate2));
		Page<CandidateDto> candidatesDto = new PageImpl<CandidateDto>(List.of(candidateDto1, candidateDto2));
		
		//Test case
		Mockito.when(candidateRepository.findAll(PageRequest.of(0, 2))).thenReturn(candidates);
		Mockito.when(candidateDtoConverter.convertToDtoList(candidates)).thenReturn(candidatesDto);
		
		//Verify
		Page<CandidateDto> result = candidateService.getAllCandidates(1, 2);
		assertEquals(result.getContent(), candidatesDto.getContent());
		
		Mockito.verify(candidateRepository).findAll(PageRequest.of(0, 2));
		Mockito.verify(candidateDtoConverter).convertToDtoList(candidates);
	}
	
	@Test
	void testGetAllCandidates_whenCandidatesNull_thenShouldThrowCandidateNotFoundException() {
		//Data for testing
		
		//Test case
		Mockito.when(candidateRepository.findAll(PageRequest.of(0, 2))).thenReturn(null);
		
		//Verify
		assertThrows(CandidateNotFoundException.class, () -> {
			candidateService.getAllCandidates(1, 2);
		});
		Mockito.verify(candidateRepository).findAll(PageRequest.of(0, 2));
	}
	
	@Test
	void testGetAllCandidates_whenCandidatesIsEmpty_thenShouldThrowCandidateNotFoundException() {
		//Data for testing
		
		//Test case
		Mockito.when(candidateRepository.findAll(PageRequest.of(0, 2))).thenReturn(new PageImpl<Candidate>(List.of()));
		
		//Verify
		assertThrows(CandidateNotFoundException.class, () -> {
			candidateService.getAllCandidates(1, 2);
		});
		Mockito.verify(candidateRepository).findAll(PageRequest.of(0, 2));
	}
	
	@Test
	void testGetAllCandidatesSorted_whenValidRequest_thenShouldReturnPageOfCandidateDto() {
		//Data for testing
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		Candidate candidate1 = new Candidate( "test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		CandidateDto candidateDto1 = new CandidateDto(id1.toString(), "test-name1","test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED.getStatus());
		Candidate candidate2 = new Candidate( "test-name2", "test-surname2", "test-phone2", "test-mail2@email.com", CandidateStatusType.SOURCED);
		CandidateDto candidateDto2 = new CandidateDto(id2.toString(), "test-name2","test-surname2", "test-phone2", "test-mail2@email.com", CandidateStatusType.SOURCED.getStatus());
		
		Page<Candidate> candidates = new PageImpl<Candidate>(List.of(candidate1, candidate2));
		Page<CandidateDto> candidatesDto = new PageImpl<CandidateDto>(List.of(candidateDto1, candidateDto2));
		
		//Test case
		Mockito.when(candidateRepository.findAll(PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "name")))).thenReturn(candidates);
		Mockito.when(candidateDtoConverter.convertToDtoList(candidates)).thenReturn(candidatesDto);
		
		//Verify
		Page<CandidateDto> result = candidateService.getAllCandidatesSorted(1, 2, "name", "ASC");
		assertEquals(result.getContent(), candidatesDto.getContent());
		
		Mockito.verify(candidateRepository).findAll(PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "name")));
		Mockito.verify(candidateDtoConverter).convertToDtoList(candidates);
	}
	
	@Test
	void testGetAllCandidatesSorted_whenCandidatesNull_thenShouldThrowCandidateNotFoundException() {
		//Data for testing
		
		//Test case
		Mockito.when(candidateRepository.findAll(PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "name")))).thenReturn(null);
		
		//Verify
		assertThrows(CandidateNotFoundException.class, () -> {
			candidateService.getAllCandidatesSorted(1, 2, "name", "ASC");
		});
		Mockito.verify(candidateRepository).findAll(PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "name")));
	}
	
	@Test
	void testGetAllCandidatesSorted_whenCandidatesIsEmpty_thenShouldThrowCandidateNotFoundException() {
		//Data for testing
		
		//Test case
		Mockito.when(candidateRepository.findAll(PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "name")))).thenReturn(new PageImpl<Candidate>(List.of()));
		
		//Verify
		assertThrows(CandidateNotFoundException.class, () -> {
			candidateService.getAllCandidatesSorted(1, 2, "name", "ASC");
		});
		Mockito.verify(candidateRepository).findAll(PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "name")));
	}
	
	@Test
	void testGetAllCandidatesSearchByNameAndOrSurname_whenValidRequest_thenShouldReturnPageOfCandidateDto() {
		//Data for testing
		UUID id1 = UUID.randomUUID();
		Candidate candidate1 = new Candidate( "test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		CandidateDto candidateDto1 = new CandidateDto(id1.toString(), "test-name1","test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED.getStatus());
		
		Page<Candidate> candidates = new PageImpl<Candidate>(List.of(candidate1));
		Page<CandidateDto> candidatesDto = new PageImpl<CandidateDto>(List.of(candidateDto1));
		
		//Test case
		Mockito.when(candidateRepository.findByNameOrSurnameIgnoreCase(PageRequest.of(0, 2), "test-name1", "test-surname1")).thenReturn(candidates);
		Mockito.when(candidateDtoConverter.convertToDtoList(candidates)).thenReturn(candidatesDto);
		
		//Verify
		Page<CandidateDto> result = candidateService.getAllCandidatesSearchByNameAndOrSurname(1, 2, "test-name1", "test-surname1");
		assertEquals(result.getContent(), candidatesDto.getContent());
		
		Mockito.verify(candidateRepository).findByNameOrSurnameIgnoreCase(PageRequest.of(0, 2), "test-name1", "test-surname1");
		Mockito.verify(candidateDtoConverter).convertToDtoList(candidates);
	}
	
	@Test
	void testGetAllCandidatesSearchByNameAndOrSurname_whenCandidatesNull_thenShouldThrowCandidateNotFoundException() {
		//Data for testing
		
		//Test case
		Mockito.when(candidateRepository.findByNameOrSurnameIgnoreCase(PageRequest.of(1, 2), "test-name1", "test-surname1")).thenReturn(null);
		
		//Verify
		assertThrows(CandidateNotFoundException.class, () -> {
			candidateService.getAllCandidatesSorted(1, 2, "name", "ASC");
		});
		Mockito.verify(candidateRepository).findAll(PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "name")));
	}
	
	@Test
	void testGetAllCandidatesSearchByNameAndOrSurname_whenCandidatesIsEmpty_thenShouldThrowCandidateNotFoundException() {
		//Data for testing
		
		//Test case
		Mockito.when(candidateRepository.findAll(PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "name")))).thenReturn(new PageImpl<Candidate>(List.of()));
		
		//Verify
		assertThrows(CandidateNotFoundException.class, () -> {
			candidateService.getAllCandidatesSorted(1, 2, "name", "ASC");
		});
		Mockito.verify(candidateRepository).findAll(PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "name")));
	}

	@Test
	void testGetCandidateById_whenValidRequest_thenShouldReturnCandidateDto() {
		//Data for testing
		UUID id1 = UUID.randomUUID();
		Candidate candidate1 = new Candidate( "test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		CandidateDto candidateDto1 = new CandidateDto(id1.toString(), "test-name1","test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED.getStatus());
		
		//Test case
		Mockito.when(candidateRepository.findById(id1)).thenReturn(Optional.of(candidate1));
		Mockito.when(candidateDtoConverter.convertToDto(candidate1)).thenReturn(candidateDto1);
		
		//Verify
		CandidateDto result = candidateService.getCandidateById(id1.toString());
		assertEquals(result, candidateDto1);
		
		Mockito.verify(candidateRepository).findById(id1);
		Mockito.verify(candidateDtoConverter).convertToDto(candidate1);
	}
	
	@Test
	void testGetCandidateById_whenValidRequest_whenCandidateNotFound_thenShouldThrowCandidateNotFoundException() {
		//Data for testing
		UUID id1 = UUID.randomUUID();
		
		//Test case
		Mockito.when(candidateRepository.findById(id1)).thenReturn(null);
		
		//Verify
		assertThrows(NullPointerException.class, () -> {
			candidateService.getCandidateById(id1.toString());
		});
		Mockito.verify(candidateRepository).findById(id1);
	}
	
	@Test
	void testSaveCandidate_whenValidRequest_thenShouldReturnCandidateDto() {
		//Data for testing
		UUID id1 = UUID.randomUUID();
		CreateCandidateRequest request = new CreateCandidateRequest("test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", "Hired");
		Candidate toSave = new Candidate(request.getName(), request.getSurname(), request.getPhone(), request.getMail(), CandidateStatusType.valueOf(request.getStatus().toUpperCase().replaceAll(" ", "_")));
		CandidateDto candidateDto1 = new CandidateDto(id1.toString(), "test-name1","test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED.getStatus());
		
		//Test case
		Mockito.when(candidateRepository.existsByMail(request.getMail())).thenReturn(false);
		Mockito.when(candidateRepository.existsByPhone(request.getPhone())).thenReturn(false);
		Mockito.when(candidateRepository.save(toSave)).thenReturn(toSave);
		Mockito.when(candidateDtoConverter.convertToDto(toSave)).thenReturn(candidateDto1);
		
		//Verify
		CandidateDto result = candidateService.saveCandidate(request);
		assertEquals(result, candidateDto1);
		
		Mockito.verify(candidateRepository).existsByMail(request.getMail());
		Mockito.verify(candidateRepository).existsByPhone(request.getPhone());
		Mockito.verify(candidateRepository).save(toSave);
		Mockito.verify(candidateDtoConverter).convertToDto(toSave);
	}
	
	@Test
	void testSaveCandidate_whenEmailAlreadyExists_thenShouldThrowEmailAlreadyInUseException() {
		//Data for testing
		CreateCandidateRequest request = new CreateCandidateRequest("test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", "Hired");
		
		//Test case
		Mockito.when(candidateRepository.existsByMail(request.getMail())).thenReturn(true);
		
		//Verify
		assertThrows(EmailAlreadyInUseException.class, () -> {
			candidateService.saveCandidate(request);
		});
		Mockito.verify(candidateRepository).existsByMail(request.getMail());
	}
	
	@Test
	void testSaveCandidate_whenPhoneAlreadyExists_thenShouldThrowPhoneAlreadyInUseException() {
		//Data for testing
		CreateCandidateRequest request = new CreateCandidateRequest("test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", "Hired");
		
		//Test case
		Mockito.when(candidateRepository.existsByMail(request.getMail())).thenReturn(false);
		Mockito.when(candidateRepository.existsByPhone(request.getPhone())).thenReturn(true);
		
		//Verify
		assertThrows(PhoneAlreadyInUseException.class, () -> {
			candidateService.saveCandidate(request);
		});
		Mockito.verify(candidateRepository).existsByMail(request.getMail());
		Mockito.verify(candidateRepository).existsByPhone(request.getPhone());
	}
	
	@Test
	void testUpdateCandidateInfo_whenEmailAndPhoneUpdated_thenShouldReturnCandidateDto() {
		//Data for testing
		UUID id1 = UUID.randomUUID();
		UpdateCandidateRequest request = new UpdateCandidateRequest("test-phone-updated-1", "test-mail1-updated@email.com", "Hired");
		Candidate candidate1 = new Candidate("test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		Candidate updatedCandidate = new Candidate("test-name1", "test-surname1", "test-phone-updated-1", "test-mail1-updated@email.com", CandidateStatusType.HIRED);
		CandidateDto candidateDto1 = new CandidateDto(id1.toString(), "test-name1","test-surname1", "test-phone-updated-1", "test-mail1@email.com", CandidateStatusType.HIRED.getStatus());
		
		//Test case
		Mockito.when(candidateRepository.findById(id1)).thenReturn(Optional.of(candidate1));
		Mockito.when(candidateRepository.existsByMail(request.getMail())).thenReturn(false);
		Mockito.when(candidateRepository.existsByPhone(request.getPhone())).thenReturn(false);
		Mockito.when(candidateRepository.save(updatedCandidate)).thenReturn(updatedCandidate);
		Mockito.when(candidateDtoConverter.convertToDto(updatedCandidate)).thenReturn(candidateDto1);
		
		//Verify
		CandidateDto result = candidateService.updateCandidateInfo(id1.toString(), request);
		assertEquals(result, candidateDto1);
		
		Mockito.verify(candidateRepository).findById(id1);
		Mockito.verify(candidateRepository).existsByMail(request.getMail());
		Mockito.verify(candidateRepository).existsByPhone(request.getPhone());
		Mockito.verify(candidateRepository).save(updatedCandidate);
		Mockito.verify(candidateDtoConverter).convertToDto(updatedCandidate);
	}
	
	@Test
	void testUpdateCandidateInfo_whenEmailAlreadyExists_thenShouldThrowEmailAlreadyInUseException() {
		//Data for testing
		UUID id1 = UUID.randomUUID();
		UpdateCandidateRequest request = new UpdateCandidateRequest("test-phone-updated-1", "test-mail1-updated@email.com", "Hired");
		Candidate candidate1 = new Candidate("test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		
		//Test case
		Mockito.when(candidateRepository.findById(id1)).thenReturn(Optional.of(candidate1));
		Mockito.when(candidateRepository.existsByMail(request.getMail())).thenReturn(true);
		
		//Verify
		assertThrows(EmailAlreadyInUseException.class, () -> {
			candidateService.updateCandidateInfo(id1.toString(), request);
		});
		Mockito.verify(candidateRepository).findById(id1);
		Mockito.verify(candidateRepository).existsByMail(request.getMail());
	}
	
	@Test
	void testUpdateCandidateInfo_whenPhoneAlreadyExists_thenShouldThrowPhoneAlreadyInUseException() {
		//Data for testing
		UUID id1 = UUID.randomUUID();
		UpdateCandidateRequest request = new UpdateCandidateRequest("test-phone-updated-1", "test-mail1-updated@email.com", "Hired");
		Candidate candidate1 = new Candidate("test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		
		//Test case
		Mockito.when(candidateRepository.findById(id1)).thenReturn(Optional.of(candidate1));
		Mockito.when(candidateRepository.existsByMail(request.getMail())).thenReturn(false);
		Mockito.when(candidateRepository.existsByPhone(request.getPhone())).thenReturn(true);
		
		//Verify
		assertThrows(PhoneAlreadyInUseException.class, () -> {
			candidateService.updateCandidateInfo(id1.toString(), request);
		});
		Mockito.verify(candidateRepository).findById(id1);
		Mockito.verify(candidateRepository).existsByMail(request.getMail());
		Mockito.verify(candidateRepository).existsByPhone(request.getPhone());
	}
	
	@Test
	void testDeleteCandidateInfo_whenValidRequest_thenShouldReturnString() {
		//Data for testing
		UUID id1 = UUID.randomUUID();
		Candidate candidate1 = new Candidate("test-name1", "test-surname1", "test-phone1", "test-mail1@email.com", CandidateStatusType.HIRED);
		String name = candidate1.getName();
		
		//Test case
		Mockito.when(candidateRepository.findById(id1)).thenReturn(Optional.of(candidate1));
		
		//Verify
		String result = candidateService.deleteCandidate(id1.toString());
		assertEquals(result, name);
		Mockito.verify(candidateRepository).findById(id1);
	}
}
