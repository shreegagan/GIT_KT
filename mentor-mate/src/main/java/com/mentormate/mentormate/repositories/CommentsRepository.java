package com.mentormate.mentormate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mentormate.mentormate.entities.Comments;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {

}
