package com.service;

import java.util.List;


import com.entity.Question;



public interface QuestionService {

    List<Question> getAllQuestions();

    Question getQuestionById(Long id);

    Question saveQuestion(Question question);

    void deleteQuestion(Long id);
}