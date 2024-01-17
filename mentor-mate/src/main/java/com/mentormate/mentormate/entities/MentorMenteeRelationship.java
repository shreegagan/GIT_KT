package com.mentormate.mentormate.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mentorMenteeRelationship")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MentorMenteeRelationship {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(targetEntity = Users.class, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "mentorId", referencedColumnName = "id")
	private Users mentor;

	@ManyToOne(targetEntity = Users.class, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "menteeId", referencedColumnName = "id")
	private Users mentee;

	public MentorMenteeRelationship(Users mentor, Users mentee) {
		this.mentor = mentor;
		this.mentee = mentee;
	}

}