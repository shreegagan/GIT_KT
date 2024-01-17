package com.mentormate.mentormate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mentormate.mentormate.entities.KeyResults;
import com.mentormate.mentormate.entities.Objectives;

@Repository
public interface KeyResultsRepository extends JpaRepository<KeyResults, Long> {
	
	//fetch record based on objective
	List<KeyResults> findAllByObjective(Objectives objective);
}
