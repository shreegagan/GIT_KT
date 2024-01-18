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
@Table(name = "keyResults")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KeyResults {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne(targetEntity = Objectives.class, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "objectivesId", referencedColumnName = "id")
	private Objectives objective;
	private String keyResult;

	public KeyResults(Objectives objective, String keyResult) {
		this.objective = objective;
		this.keyResult = keyResult;
	}

}
