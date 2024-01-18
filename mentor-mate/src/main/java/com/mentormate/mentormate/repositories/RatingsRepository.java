package com.mentormate.mentormate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentormate.mentormate.entities.Ratings;

public interface RatingsRepository extends JpaRepository<Ratings, Long>{

}
