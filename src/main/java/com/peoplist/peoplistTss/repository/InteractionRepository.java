package com.peoplist.peoplistTss.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peoplist.peoplistTss.entities.Interaction;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, UUID> {
	Page<Interaction> findAllByCandidateId(UUID candidateId, Pageable pageable);
}
