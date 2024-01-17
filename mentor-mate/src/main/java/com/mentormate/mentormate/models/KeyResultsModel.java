package com.mentormate.mentormate.models;

import com.mentormate.mentormate.entities.KeyResults;
import com.mentormate.mentormate.entities.Objectives;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KeyResultsModel {
	private long id;
	private Objectives objective;
	private String keyResult;

	// Constructor to initialize all fields
	public KeyResultsModel(KeyResults keyResult) {
		this.id = keyResult.getId();
		this.objective = keyResult.getObjective();
		this.keyResult = keyResult.getKeyResult();
	}
}
