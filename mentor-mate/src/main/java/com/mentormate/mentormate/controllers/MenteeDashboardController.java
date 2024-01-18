package com.mentormate.mentormate.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.mentormate.mentormate.dto.OkrDTO;
import com.mentormate.mentormate.entities.Users;
import com.mentormate.mentormate.models.UsersModel;
import com.mentormate.mentormate.services.MentorMenteeRelationshipService;
import com.mentormate.mentormate.services.OKRService;
import com.mentormate.mentormate.services.UsersService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MenteeDashboardController {
	
	private static final Logger logger  = LoggerFactory.getLogger(MenteeDashboardController.class);

	@Autowired
	private UsersService usersService;

	@Autowired
	private MentorMenteeRelationshipService mentorMenteeRelationshipService;

	@Autowired
	private OKRService okrService;

	@GetMapping("/mentee")
	public String menteeDashboard(HttpServletRequest request, Model model) {
		
		logger.info("Retrieving mentee dashboard");
		try {
			// get the firstName of the user
			String currentUserfirstName = request.getParameter("firstName");
			// get the user Id of the user
			Long currentUserId = Long.valueOf(request.getParameter("userId"));
	
			// Fetching profile information
			Users loggedInMentee = usersService.getUserById(currentUserId);
			UsersModel loggedInMenteeModel = new UsersModel(loggedInMentee);
	
			// Fetching List of Mentees under a Mentor
			List<UsersModel> listOfMentees = mentorMenteeRelationshipService.getMenteesWithSameMentor(loggedInMentee);
			
			// Fetching Okrs
			List<OkrDTO> listOfOkrs = okrService.getAllObjectivesAndKeyResultsForMentee(loggedInMentee);
	
			// using model to pass objects to View
			model.addAttribute("listOfOkrs", listOfOkrs);
			model.addAttribute("listOfMentees", listOfMentees);
			model.addAttribute("firstName", currentUserfirstName);
			model.addAttribute("mentee", loggedInMenteeModel);
	
			// Return the mentee dashboard view
			return "mentee";
		} catch (Exception e) {
	        logger.error("Failed to retrieve mentee dashboard: {}", e.getMessage());
	        throw new RuntimeException("Failed to retrieve mentee dashboard", e); // Rethrow for proper handling
	    }

	}

}
