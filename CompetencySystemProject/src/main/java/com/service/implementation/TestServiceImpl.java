package com.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Question;
import com.entity.Test;
import com.exception.QuestionNotFoundException;
import com.repository.QuestionRepository;
import com.repository.TestRepository;
import com.service.QuestionService;
import com.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestRepository repository;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public Test addTest(Test exam) {
		//Validation for Question 
		validateQuestionArePresentInDatabase(exam);
		//we need to fetch alln question by its id
		List<Question> question= fetchRecordsByItsId(exam);
		exam.setQuestions(question);
		return repository.save(exam);
	}
	
	 private void validateQuestionArePresentInDatabase(Test exam) {
			for(Question question : exam.getQuestions()) {
				if(!questionRepository.existsById(question.getQuestionId())) {
					throw new QuestionNotFoundException("Question not found with id: " + question.getQuestionId(), null);
				}
			}
			
		}

	
	private List<Question> fetchRecordsByItsId(Test exam) {
		List<Question> questionList = new ArrayList<>();
		for(Question question : exam.getQuestions()) {
			long questionId = question.getQuestionId();
			Question existingQuestion= questionService.getQuestionById(questionId);
			questionList.add(existingQuestion);
		}
		
		return questionList;
	}

	@Override
	public Test updateTest(Test exam) {
		return repository.save(exam);
	}

	@Override
	public List<Test> getTest() {
		return new ArrayList<>(repository.findAll());
	}

	@Override
	public Test getTestById(Long testId) {
		return repository.findById(testId).get();
	}

	@Override
	public void deleteTestById(Long testId) {
		repository.deleteById(testId);
	}

}
