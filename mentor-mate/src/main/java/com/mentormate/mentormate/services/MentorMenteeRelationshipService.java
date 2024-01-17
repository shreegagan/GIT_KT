package com.mentormate.mentormate.services;

import java.util.List;

import com.mentormate.mentormate.entities.MentorMenteeRelationship;
import com.mentormate.mentormate.entities.Users;
import com.mentormate.mentormate.models.UsersModel;

public interface MentorMenteeRelationshipService {

	// Returns one mentee-mentor mapping based on id
	MentorMenteeRelationship getMentorMenteeRelationship(long id);

	// Returns a mentor (user object) based on mentee's user Id. Assuming a mentee
	// has only one mentor
	Users getMentorForMentee(Users mentee);

	// Returns a list of all mentees under a single mentor based on mentor's user Id
	List<UsersModel> getMenteesWithSameMentor(Users mentee);

	List<Users> getMenteesForMentor(Users mentor);

}