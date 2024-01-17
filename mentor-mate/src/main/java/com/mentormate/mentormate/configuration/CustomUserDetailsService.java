package com.mentormate.mentormate.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mentormate.mentormate.entities.Users;
import com.mentormate.mentormate.repositories.UsersRepository;

//Service responsible for loading user details during authentication
@Service
public class CustomUserDetailsService implements UserDetailsService {

	// Injecting the UsersRepository for accessing user data in the database
	@Autowired
	private UsersRepository usersRepository;

	// UserDetailsService interface to load user details by username
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// Find the user by email (username) in the repository
		Users user = usersRepository.findByEmail(username);

		// If the user is not found, throw an exception
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
			 
		// Return a new instance of CustomUserDetails with the found user
		return new CustomUserDetails(user);

	}

}
