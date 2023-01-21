package com.peoplist.peoplistTss.repository;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.peoplist.peoplistTss.entities.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, UUID> {
	
	@Query("select u from Candidate u where (lower(u.name) = :name) and (:surname = ''  or lower(u.surname) = :surname)")
	Page<Candidate> findByNameOrSurnameIgnoreCase(Pageable pageable, @Param("name") String name, @Param("surname") String surname);
	Page<Candidate> findAll(Pageable pageable);
	boolean existsByPhone(String phone);
	boolean existsByMail(String phone);
}
