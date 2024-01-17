package com.mentormate.mentormate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mentormate.mentormate.entities.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	
	// Custom query method to find a user by email
	Users findByEmail(String email);
}
