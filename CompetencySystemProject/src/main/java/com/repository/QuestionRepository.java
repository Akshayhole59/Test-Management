package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Question;



public interface QuestionRepository extends JpaRepository<Question, Long> {

	List<Question> getQuestionByCategoryId(Long category_id);


	
}
