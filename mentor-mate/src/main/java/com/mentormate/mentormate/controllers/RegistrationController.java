package com.mentormate.mentormate.controllers;

import javax.management.relation.RoleNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mentormate.mentormate.entities.PasswordResetToken;
import com.mentormate.mentormate.entities.Users;
import com.mentormate.mentormate.models.UsersModel;
import com.mentormate.mentormate.repositories.TokenRepository;
import com.mentormate.mentormate.repositories.UsersRepository;
import com.mentormate.mentormate.services.UsersService;

@Controller
public class RegistrationController {
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
	
	// Autowired annotation for injecting UsersService dependency
	@Autowired
	private UsersService usersService;

// Autowired annotation for injecting PasswordEncoder dependency
	@Autowired
	PasswordEncoder passwordEncoder;

	// Autowired annotation for injecting UsersRepository dependency
	@Autowired
	private UsersRepository usersRepository;
	
	// Autowired annotation for injecting TokenRepository dependency
	@Autowired
	private TokenRepository tokenRepository;
	

	// Handle the GET request to show the home page
	@GetMapping("/home")
    public String home() {
        return "home";
    }

	// Handle the GET request to show the about-us page
	@GetMapping("/about-us")
    public String aboutUs() {
        return "about-us";
    }

	// Handle the GET request to show the contact-us page
    @GetMapping("/contact-us")
    public String contactUs() {
        return "contact-us";
    }


// Handle the POST request for user registration
	@PostMapping("/registration")
	public String registerUser(@ModelAttribute("user") UsersModel usersModel, Model model)
			throws RoleNotFoundException {
		
		logger.info("Registering user");
		try {
			// Check if the user with the given email already exists
			if (usersService.isUserExists(usersModel.getEmail())) {
				model.addAttribute("error", "User with this email already exists.");
				return "registration";
			}

			// Check if the entered password matches the confirmed password
			if (usersModel.getPassword().equals(usersModel.getConfirmPassword())) {
				// Create a new user based on the provided user model
				usersService.createUsers(usersModel);
				logger.info("user created successfully");
				// Redirect to the login page after successful registration
				return "redirect:/login";
			} else {
				// Set an error message for displaying a password mismatch message
				model.addAttribute("error", "Password did not match");
				// Return the registration page to correct the password mismatch
				return "registration";
			}
		} catch (RuntimeException e) {
			logger.error("Registration failed: {}", e.getMessage());
			// Log the exception for debugging purposes
			e.printStackTrace();

			// Add an error message for the user
			model.addAttribute("error", "An error occurred during registration. Please try again.");

			// Return to the registration page with the error message
			return "registration";
		}
	}

// Handle the GET request to show the registration form
	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		// Add an empty user model to be populated in the form
		model.addAttribute("user", new UsersModel());
		return "registration";
	}

	// Handle the GET request to show the login form
	@GetMapping("/login")
	public String showLoginForm() {
		// Return the login page view
		return "login";
	}
	// Handle the GET request to show the forgot password form
	@GetMapping("/forgotPassword")
	public String forgotPassword() {
		return "forgotPassword";
	}

	// Handle the POST request for forgot password
	@PostMapping("/forgotPassword")
	public String forgotPassordProcess(@ModelAttribute UsersModel userModel) {
		String output = "";
		Users user = usersRepository.findByEmail(userModel.getEmail());
		if (user != null) {
			output = usersService.sendEmail(user);
		}
		if (output.equals("success")) {
			return "redirect:/forgotPassword?success";
		}
		return "redirect:/login?error";
	}
	
	@GetMapping("/resetPassword/{token}")
	public String resetPasswordForm(@PathVariable String token, Model model) {
		PasswordResetToken reset = tokenRepository.findByToken(token);
		if (reset != null && usersService.hasExipred(reset.getExpiryDateTime())){
			model.addAttribute("email", reset.getUser().getEmail());
			return "resetPassword";
		}
		return "redirect:/forgotPassword?error";
	}
	
	@PostMapping("/resetPassword")
	public String passwordResetProcess(@ModelAttribute UsersModel userModel) {
		Users user = usersRepository.findByEmail(userModel.getEmail());
		if(user != null) {
			user.setPassword(passwordEncoder.encode(userModel.getPassword()));
			usersRepository.save(user);
		}
		return "redirect:/login";
	}

}
