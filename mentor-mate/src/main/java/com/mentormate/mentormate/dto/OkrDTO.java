package com.mentormate.mentormate.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

//Data Transfer Object for getting objective and it's respective key-results
public class OkrDTO {

	private String objective;
	private List<String> keyResults;
}
