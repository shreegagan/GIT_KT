package com.mentormate.mentormate.models;

import com.mentormate.mentormate.entities.Objectives;
import com.mentormate.mentormate.entities.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ObjectivesModel {
	private long id;
	private Users user;
	private String objective;
	

	//Constructor to initialize all fields
	public ObjectivesModel(Objectives objective) {
		this.id = objective.getId();
		this.user = objective.getUser();
		this.objective = objective.getObjective();
	}
}
