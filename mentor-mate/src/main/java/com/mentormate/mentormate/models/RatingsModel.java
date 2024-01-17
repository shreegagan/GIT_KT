package com.mentormate.mentormate.models;

import com.mentormate.mentormate.entities.KeyResults;
import com.mentormate.mentormate.entities.Ratings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RatingsModel {
	private long id;
	private KeyResults keyResults;
	private int rating;


	//Constructor to initialize all fields
	public RatingsModel(Ratings rating) {
		this.id = rating.getId();
		this.keyResults = rating.getKeyResults();
		this.rating = rating.getRating();
	}

}
