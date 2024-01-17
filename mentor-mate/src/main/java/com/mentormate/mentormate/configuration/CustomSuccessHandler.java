package com.mentormate.mentormate.configuration;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import com.mentormate.mentormate.entities.Users;
import com.mentormate.mentormate.services.UsersService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UsersService usersService;

	private static final Logger logger = LogManager.getLogger(CustomSuccessHandler.class);

	// Method called on successful authentication
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		// Get the user from details
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();

		// Fetch the user details from the database using the username
		Users user = usersService.findByEmail(username);

		// Log authentication success
		logger.info("Authentication successful for user: {}", user.getEmail());

		// Get the authorities (roles) granted to the authenticated user
		var authourities = authentication.getAuthorities();

		// Map the authorities to role names and find the first one
		var roles = authourities.stream().map(r -> r.getAuthority()).findFirst();

		// Redirect the user based on their role
		if (roles.orElse("").equals("MENTOR")) {

			// Redirect to the mentor dashboard and pass the first name and user ID as parameters
			response.sendRedirect("/mentor?firstName=" + user.getFirstName() + "&userId=" + user.getId());

		} else if (roles.orElse("").equals("MENTEE")) {

			// Redirect to the mentee dashboard and pass the first name and user ID as parameters
			response.sendRedirect("/mentee?firstName=" + user.getFirstName() + "&userId=" + user.getId());

		} else {

			// Redirect to an error page for unknown roles
			response.sendRedirect("/error");

		}

	}

}
