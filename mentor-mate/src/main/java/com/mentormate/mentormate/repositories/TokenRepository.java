package com.mentormate.mentormate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentormate.mentormate.entities.PasswordResetToken;
import com.mentormate.mentormate.entities.Users;

public interface TokenRepository extends JpaRepository<PasswordResetToken, Integer> {
	PasswordResetToken findByToken(String token) ;
}
