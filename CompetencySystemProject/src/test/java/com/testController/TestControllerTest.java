package com.testController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import com.controller.TestController;
import com.entity.Question;
import com.entity.TestManagement;
import com.exception.QuestionNotFoundException;
import com.exception.TestIdNotExistException;
import com.service.TestService;
import static org.springframework.http.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestControllerTest {
	@Mock
	private TestService testService;

	@InjectMocks
	private TestController testController;
	

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddTestPositive() {
		TestManagement testToAdd = new TestManagement();

		when(testService.addTest(testToAdd)).thenReturn(testToAdd);

		ResponseEntity<?> responseEntity = testController.addTest(testToAdd);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	@Test
	void testAddTestNegative() {
		TestManagement testToAdd = new TestManagement();

		when(testService.addTest(testToAdd)).thenThrow(new RuntimeException("Error adding test"));

		ResponseEntity<?> responseEntity = testController.addTest(testToAdd);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	@Test
	void testGetTestByIdPositive() {
		Long testId = 1L;
		TestManagement test = new TestManagement();

		when(testService.getTestById(testId)).thenReturn(test);

		ResponseEntity<?> responseEntity = testController.getTestById(testId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	@Test
	void testGetTestByIdNegativeTestIdNotExistException() {
	    Long testId = 1L;

	    when(testService.getTestById(testId)).thenThrow(new TestIdNotExistException("Test ID not found"));

	    ResponseEntity<?> responseEntity = testController.getTestById(testId);

	    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	    assertNotNull(responseEntity.getBody());
	    }
	
	@Test
	void testGetTestByIdNegativeNoSuchElementException() {
		Long testId = null;

		ResponseEntity<?> responseEntity = testController.getTestById(testId);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	@Test
	void testGetAllTestPositive() {
		List<TestManagement> tests = new ArrayList<>();

		when(testService.getTest()).thenReturn(tests);

		ResponseEntity<?> responseEntity = testController.getAllTest();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	@Test
	void testGetAllTestNegative() {
		when(testService.getTest()).thenThrow(new RuntimeException("Error getting all tests"));

		ResponseEntity<?> responseEntity = testController.getAllTest();

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	@Test
	void testUpdateTestPositive() {
		Long testId = 1L;
		TestManagement updatedTest = new TestManagement();

		TestManagement existingTest = new TestManagement();
		when(testService.getTestById(testId)).thenReturn(existingTest);
		when(testService.updateTest(existingTest)).thenReturn(updatedTest);

		ResponseEntity<?> responseEntity = testController.updateTest(testId, updatedTest);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	@Test
	void testUpdateTestNegativeTestIdNotExistException() {
		Long testId = 1L;
		TestManagement updatedTest = new TestManagement();

		when(testService.getTestById(testId)).thenThrow(new TestIdNotExistException("Test ID not found"));

		ResponseEntity<?> responseEntity = testController.updateTest(testId, updatedTest);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	@Test
	void testDeleteTestPositive() {
		Long testId = 1L;
		    doNothing().when(testService).deleteTestById(testId);
		    testController.deleteTest(testId);
		    assertEquals(OK.value(), HttpServletResponse.SC_OK);
	        verify(testService).deleteTestById(testId);
	}

	@Test
  void testDeleteTestNegativeTestIdNotExistException() {
		
		 Long nonExistentTestId = 999L;
		 doThrow(new TestIdNotExistException("Test not found")).when(testService).deleteTestById(nonExistentTestId);
         assertThrows(ResponseStatusException.class, () -> testController.deleteTest(nonExistentTestId));
         verify(testService).deleteTestById(nonExistentTestId);

   }

	@Test
	void testDeleteTestNegative() {
		Long nullTestId = null;
	    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> testController.deleteTest(nullTestId));
	    verify(testService, never()).deleteTestById(nullTestId);
	    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Test id is null", exception.getReason());
	}
	
	

}
