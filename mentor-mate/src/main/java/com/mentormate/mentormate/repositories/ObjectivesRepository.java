package com.mentormate.mentormate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mentormate.mentormate.entities.Objectives;
import com.mentormate.mentormate.entities.Users;

@Repository
public interface ObjectivesRepository extends JpaRepository<Objectives, Long> {
	
	//fetch by user
	List<Objectives> findAllByUser(Users user);
}
