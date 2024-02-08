package com.service;

import java.util.List;

import com.entity.Test;


public interface TestService {

	public Test addTest(Test test);

	public Test updateTest(Test test);

	public List<Test> getTest();

	public Test getTestById(Long testId);

	public void deleteTestById(Long testId);
}
