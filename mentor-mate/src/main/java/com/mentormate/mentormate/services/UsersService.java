package com.mentormate.mentormate.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.management.relation.RoleNotFoundException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mentormate.mentormate.entities.Users;
import com.mentormate.mentormate.models.UsersModel;

public interface UsersService {

	// Get all users from the database
	List<Users> getAllUsers();

	// Create a new user based on the provided user model
	// Throws RoleNotFoundException if the specified role is not found
	Users createUsers(UsersModel usersModel) throws RoleNotFoundException;

	boolean isUserExists(String email);

	Users findByEmail(String username);

	Users getUserById(long userId);

	Users findById(long id);

	UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

	String sendEmail(Users user);

	boolean hasExipred(LocalDateTime expiryDateTime);
}
