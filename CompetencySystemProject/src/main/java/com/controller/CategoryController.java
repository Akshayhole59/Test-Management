package com.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.entity.Category;
import com.exception.CategoryNotFoundException;
import com.service.CategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;


@RestController
@CrossOrigin("*")
@RequestMapping("/testmanagement/api/v1/categories")
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	
	@PostMapping
	public Category addNewCategory(@RequestBody Category category) {
		log.info("Adding a new category: {}", category);
		return categoryService.addCategory(category);
	}

	
	@GetMapping
	public ArrayList<Category> getAllCategory() {
		log.info("Getting all categories");
		return categoryService.getAllCatogory();
	}

	
	@GetMapping("/{categoryId}")
	public ResponseEntity<?> getCategoryById(@PathVariable("categoryId") Long categoryId) {
		try {
			Category category = categoryService.getCategoryById(categoryId);
			if (category != null) {
				return ResponseEntity.ok(category);
			} else {
				log.error("Category not found with ID: {}", categoryId);
				return ResponseEntity.status(404).body("Category not found with ID: " + categoryId);
			}
		} catch (CategoryNotFoundException e) {
			log.error("Category not found with ID: {}", categoryId);
			return ResponseEntity.status(404).body("Category not found with ID: " + categoryId);
		} catch (Exception e) {
			log.error("Error occurred while retrieving category by ID", e);
			return ResponseEntity.status(500).body("Internal server error");
		}
	}

	@PutMapping("/{categoryId}")
	public ResponseEntity<?> updateCategory(@PathVariable("categoryId") Long categoryId,
			@RequestBody Category updatedCategory) {
		try {
			log.info("Updating category with ID {}: {}", categoryId, updatedCategory);

			if (categoryService.exists(categoryId)) {
				updatedCategory.setCategory_id(categoryId);
				Category updatedCategoryResponse = categoryService.updateCategory(updatedCategory);
				return ResponseEntity.ok(updatedCategoryResponse);
			} else {
				log.error("Category not found with ID: {}", categoryId);
				return ResponseEntity.status(404).body("Category not found with ID: " + categoryId);
			}
		} catch (Exception e) {
			log.error("Error occurred while updating category", e);
			return ResponseEntity.status(500).body("Internal server error");
		}
	}

	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
		try {
			log.info("Deleting category with ID: {}", categoryId);

				this.categoryService.deleteCategory(categoryId);
				return ResponseEntity.ok().build();
		
	     	} catch (Exception e) {
			log.error("Error occurred while deleting category", e);
			return ResponseEntity.status(500).body("Internal server error");
		}
	}
}
