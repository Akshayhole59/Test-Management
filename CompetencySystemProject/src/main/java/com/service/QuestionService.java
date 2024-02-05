package com.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import com.entity.Question;
import com.entity.TestManagement;



public interface QuestionService {

    List<Question> getAllQuestions();

    Question getQuestionById(Long id);

    Question saveQuestion(Question question);

    void deleteQuestion(Long id);

	List<Question> importQuestionsFromExcel(InputStream excelInputStream) throws IOException;


	
}
