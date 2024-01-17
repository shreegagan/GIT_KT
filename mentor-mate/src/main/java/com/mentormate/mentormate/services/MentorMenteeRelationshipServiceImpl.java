package com.mentormate.mentormate.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.mentormate.mentormate.entities.MentorMenteeRelationship;
import com.mentormate.mentormate.entities.Users;
import com.mentormate.mentormate.models.UsersModel;
import com.mentormate.mentormate.repositories.MentorMenteeRelationshipRepository;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MentorMenteeRelationshipServiceImpl implements MentorMenteeRelationshipService {

	// adding logger object
	private static final Logger logger = LoggerFactory.getLogger(MentorMenteeRelationshipServiceImpl.class);

	@Autowired
	private MentorMenteeRelationshipRepository mentorMenteeRelationshipRepo;

	// Returns one mentee-mentor mapping based on id
	@Override
	public MentorMenteeRelationship getMentorMenteeRelationship(long id) {
		return mentorMenteeRelationshipRepo.getReferenceById(id);
	}

	// Returns a mentor (user object) based on mentee's user Id. Assuming a mentee
	// has only one mentor
	@Override
	public Users getMentorForMentee(Users mentee) {
		MentorMenteeRelationship menteemapping = mentorMenteeRelationshipRepo.findByMentee(mentee);
		if (menteemapping != null) {
			logger.debug("Mnetor-mentee mapping found: {}", menteemapping);
			logger.info("Mentor found for mentee: {}", mentee.getEmail());
			return menteemapping.getMentor();
		} else {
			logger.info("No mentor found for mentee: {}, ID: {}", mentee.getFirstName(), mentee.getId()); 
			return null;
		}
	}

	@Override
	public List<UsersModel> getMenteesWithSameMentor(Users mentee) {
		Users mentor = getMentorForMentee(mentee);
		if (mentor != null) {
			// fetches list of records based on mentor. Using stream, getMentee() getter is
			// called for each element, returns a List<Users> containing mentees
			return mentorMenteeRelationshipRepo.findByMentorAndMenteeNot(mentor, mentee).stream()
					.map(m -> new UsersModel(m.getMentee())).toList();
		} else {
			// adding information about current method into the logger object
			logger.info("No mentor found for mentee: {}", mentee.getEmail());
			return Collections.emptyList();
		}
	}

	@Override
	public List<Users> getMenteesForMentor(Users mentor) {

		// adding information about current method into the logger object
		logger.info("Retrieving mentees for mentor: {}, ID: {}", mentor.getFirstName(), mentor.getId());

		List<Users> mentees = new ArrayList<Users>();
		try {
			mentees = mentorMenteeRelationshipRepo.findByMentor(mentor).stream().map(m -> m.getMentee()).toList();
		} catch (Exception e) {
			logger.error("Failed to retrieve mentees for mentor: {get mentees for mentor", e);
		} 
		return mentees;
	}
}
