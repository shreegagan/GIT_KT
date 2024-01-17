package com.mentormate.mentormate.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mentormate.mentormate.entities.MentorMenteeRelationship;
import com.mentormate.mentormate.entities.Users;

@Repository
public interface MentorMenteeRelationshipRepository extends JpaRepository<MentorMenteeRelationship, Long> {
	// fetch record based on menteeId
	MentorMenteeRelationship findByMentee(Users mentee);

	//fetch records based on mentor while excluding menteeToExclude (i.e currently logged in mentee)
	List<MentorMenteeRelationship> findByMentorAndMenteeNot(Users mentor, Users menteeToExclude);
	
	List<MentorMenteeRelationship> findByMentor(Users mentor);
}
