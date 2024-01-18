package com.mentormate.mentormate.models;

import com.mentormate.mentormate.entities.Comments;
import com.mentormate.mentormate.entities.KeyResults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentsModel {

	private long id;
	private KeyResults keyResults;
	private String comment;

	// Constructor to initialize all fields
	public CommentsModel(Comments comment) {
		this.id = comment.getId();
		this.keyResults = comment.getKeyResults();
		this.comment = comment.getComment();
	}
}
