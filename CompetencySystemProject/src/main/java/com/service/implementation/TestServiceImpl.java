package com.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Question;
import com.entity.TestManagement;
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

	@Override
	public TestManagement addTest(TestManagement exam) {
		//we need to fetch alln question by its id
		List<Question> question= fetchRecordsByItsId(exam);
		exam.setQuestions(question);
		return repository.save(exam);
	}

	private List<Question> fetchRecordsByItsId(TestManagement exam) {
		List<Question> questionList = new ArrayList<>();
		for(Question question : exam.getQuestions()) {
			long questionId = question.getQuestionId();
			Question existingQuestion= questionService.getQuestionById(questionId);
			questionList.add(existingQuestion);
		}
		
		return questionList;
	}

	@Override
	public TestManagement updateTest(TestManagement exam) {
		return repository.save(exam);
	}

	@Override
	public List<TestManagement> getTest() {
		return new ArrayList<>(repository.findAll());
	}

	@Override
	public TestManagement getTestById(Long testId) {
		return repository.findById(testId).get();
	}

	@Override
	public void deleteTestById(Long testId) {
		repository.deleteById(testId);
	}

}
