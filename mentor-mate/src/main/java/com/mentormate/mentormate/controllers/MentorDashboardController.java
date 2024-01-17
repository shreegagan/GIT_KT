package com.mentormate.mentormate.controllers;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mentormate.mentormate.dto.OkrDTO;
import com.mentormate.mentormate.entities.Objectives;
import com.mentormate.mentormate.entities.Users;
import com.mentormate.mentormate.services.MentorMenteeRelationshipService;
import com.mentormate.mentormate.services.OKRService;
import com.mentormate.mentormate.services.UsersService;

@Controller
public class MentorDashboardController {
	
	private static final Logger logger = LoggerFactory.getLogger(MentorDashboardController.class);

	@Autowired
	private MentorMenteeRelationshipService mentorMenteeRelationshipService;

	// Get the mentor dashboard page
	@Autowired
	private UsersService usersService;

	@Autowired
	private OKRService okrService;

	@GetMapping("/mentor")
	public String mentorDashboard(Model model) {
		logger.info("Retrieving mentor dashboard");
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
		
		try {
			Users currentUser = usersService.findByEmail(currentUserName);
			model.addAttribute("firstName", currentUser.getFirstName());
			model.addAttribute("userId", currentUser.getId());
			model.addAttribute("mentor", currentUser);

			List<Users> mentees = mentorMenteeRelationshipService.getMenteesForMentor(currentUser);
			model.addAttribute("mentees", mentees);
		} catch(Exception e) {
			logger.error("Failed to retrieve mentor dashboard data", e);
			throw new RuntimeException("Failed to retrieve mentor dashboard data", e);
		}

		}
		return "mentor";
	}

	// Get mentee details by mentee ID
	@GetMapping("/mentor/mentee/{menteeId}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> viewMenteeDetails(@PathVariable Long menteeId) {
		
		logger.info("Retrieving mentee details for mentee ID: {}", menteeId);
		try {
			// Find the mentee by ID
			Users mentee = usersService.findById(menteeId);
			// Fetching Okrs
			List<OkrDTO> listOfOkrs = okrService.getAllObjectivesAndKeyResultsForMentee(mentee);
			// Check if the mentee exists
			if (mentee != null) {
				// Return mentee details as JSON
				Map<String, Object> menteeDetails = new HashMap<>();
				menteeDetails.put("firstName", mentee.getFirstName());
				menteeDetails.put("lastName", mentee.getLastName());
				menteeDetails.put("email", mentee.getEmail());
				menteeDetails.put("designation", mentee.getDesignation());
				menteeDetails.put("objectives", listOfOkrs);
				// Encode the profile picture as Base64
				if (mentee.getProfilePicture() != null) {
					String base64ProfilePicture = Base64.getEncoder().encodeToString(mentee.getProfilePicture());
					menteeDetails.put("profilePicture", base64ProfilePicture);
				}
				return ResponseEntity.ok(menteeDetails);
			} else {
				// Handle the case where mentee is not found
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} catch(Exception e) {
			logger.error("Failed to retrieve mentee details: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve mentee details", e);
		}
	}

	// save the objectives and key-results entered in the form
	@PostMapping("/save")
	public String saveOKR(@ModelAttribute("objective") String objective,
			@ModelAttribute("keyResult1") String keyResult1, @ModelAttribute("keyResult2") String keyResult2,Long menteeId) {
		
		logger.info("Saving OKRs for mentee ID: {}", menteeId);
		
		try {
            Objectives savedObjective = okrService.createObjectives(usersService.getUserById(menteeId), objective);
            okrService.createKeyResults(savedObjective, keyResult1);
            okrService.createKeyResults(savedObjective, keyResult2);
            logger.info("OKRs saved successfully");
            return "redirect:/mentor";
        } catch (Exception e) {
            logger.error("Failed to save OKRs: {}", e.getMessage());
            throw new RuntimeException("Failed to save OKRs", e);
        }
	}
}
