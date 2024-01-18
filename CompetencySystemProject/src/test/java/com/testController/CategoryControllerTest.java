package com.testController;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.controller.CategoryController;
import com.entity.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.CategoryService;

@SpringBootTest
class CategoryControllerTest {

	private MockMvc mockMvc;

	@Mock
	private CategoryService categoryService;

	@InjectMocks
	private CategoryController categoryController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}

	@Test
	void testAddNewCategory() throws Exception {
		Category category = new Category(1L, "Test Category", "Test Description");

		when(categoryService.addCategory(any(Category.class))).thenReturn(category);

		mockMvc.perform(MockMvcRequestBuilders.post("/test-management/api/v1/categories/addCategory")
				.content(asJsonString(category)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.category_id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Category"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Description"));
	}

	@Test
	void testGetAllCategory() throws Exception {
		ArrayList<Category> categories = new ArrayList<>();
		categories.add(new Category(1L, "Category 1", "Description 1"));
		categories.add(new Category(2L, "Category 2", "Description 2"));

		when(categoryService.getAllCatogory()).thenReturn(categories);

		mockMvc.perform(MockMvcRequestBuilders.get("/test-management/api/v1/categories/getAllCategory")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].category_id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Category 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Description 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].category_id").value(2L))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Category 2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("Description 2"));
	}

	@Test
	void testGetCategoryById() throws Exception {
		Long categoryId = 1L;
		Category category = new Category(categoryId, "Category 1", "Description 1");

		when(categoryService.getCategoryById(categoryId)).thenReturn(category);

		mockMvc.perform(MockMvcRequestBuilders.get("/test-management/api/v1/categories/{categoryId}", categoryId)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.category_id").value(categoryId))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Category 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description 1"));
	}

	@Test
	void testUpdateCategory() throws Exception {
		Category category = new Category(1L, "Updated Category", "Updated Description");

		when(categoryService.updateCategory(any(Category.class))).thenReturn(category);

		mockMvc.perform(MockMvcRequestBuilders.put("/test-management/api/v1/categories/updateCategory")
				.content(asJsonString(category)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.category_id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Category"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Updated Description"));
	}

	@Test
	void testDeleteCategory() throws Exception {
		Long categoryId = 1L;

		mockMvc.perform(MockMvcRequestBuilders.delete("/test-management/api/v1/categories/{categoryId}", categoryId)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}