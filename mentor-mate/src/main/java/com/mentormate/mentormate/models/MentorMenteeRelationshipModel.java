package com.mentormate.mentormate.models;

import com.mentormate.mentormate.entities.MentorMenteeRelationship;
import com.mentormate.mentormate.entities.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MentorMenteeRelationshipModel {
	private long id;
	private Users mentor;
	private Users mentee;

	//Constructor to initialize all fields
	public MentorMenteeRelationshipModel(MentorMenteeRelationship mentorMeneeRelationship) {
		this.id = mentorMeneeRelationship.getId();
		this.mentor = mentorMeneeRelationship.getMentor();
		this.mentee = mentorMeneeRelationship.getMentee();
	}

}