package com.peoplist.peoplistTss.repository;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peoplist.peoplistTss.entities.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, UUID> {
	
	Page<Candidate> findAll(Pageable pageable);
	boolean existsByPhone(String phone);
	boolean existsByMail(String phone);
}
