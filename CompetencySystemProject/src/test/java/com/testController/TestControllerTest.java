package com.testController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.controller.TestController;
import com.entity.TestManagement;
import com.service.TestService;

@SpringBootTest
public class TestControllerTest {
	@InjectMocks
	private TestController testController;

	@Mock
	private TestService testService;

	@Test
	void addTest() {
		TestManagement testToAdd = new TestManagement();
		when(testService.addTest(testToAdd)).thenReturn(testToAdd);
		ResponseEntity<?> responseEntity = testController.addTest(testToAdd);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo(testToAdd);
		verify(testService, times(1)).addTest(testToAdd);
	}

	@Test
	void getTestById() {
		Long testId = 1L;
		TestManagement test = new TestManagement();
		when(testService.getTestById(testId)).thenReturn(test);
		ResponseEntity<?> responseEntity = testController.getTestById(testId);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo(test);
		verify(testService, times(1)).getTestById(testId);
	}

	@Test
	void getAllTest() {
		List<TestManagement> testList = new ArrayList<>();
		when(testService.getTest()).thenReturn(testList);
		ResponseEntity<?> responseEntity = testController.getAllTest();
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo(testList);
		verify(testService, times(1)).getTest();
	}

	@Test
	void updateTest() {
		Long testId = 1L;
		TestManagement existingTest = new TestManagement(/* provide necessary data for the test */);
		TestManagement updatedTest = new TestManagement(/* provide necessary data for the test */);
		when(testService.getTestById(testId)).thenReturn(existingTest);
		when(testService.updateTest(existingTest)).thenReturn(updatedTest);
		ResponseEntity<?> responseEntity = testController.updateTest(testId, updatedTest);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo(updatedTest);
		verify(testService, times(1)).getTestById(testId);
		verify(testService, times(2)).updateTest(existingTest);
	}

	@Test
	void deleteTest() {
		Long testId = 1L;
		ResponseEntity<String> responseEntity = testController.deleteTest(testId);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo("Test with ID " + testId + " deleted successfully");
		verify(testService, times(1)).deleteTestById(testId);
	}

}
