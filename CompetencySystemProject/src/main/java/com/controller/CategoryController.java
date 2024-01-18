package com.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Category;
import com.exception.CategoryNotFoundException;
import com.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/testmanagement/api/v1/categories")
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/addCategory")
	public Category addNewCategory(@RequestBody Category category) {
		log.info("Adding a new category: {}", category);
		return categoryService.addCategory(category);
	}

	@GetMapping("/getAllCategory")
	public ArrayList<Category> getAllCategory() {
		log.info("Getting all categories");
		return categoryService.getAllCatogory();
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<?> getCategoryById(@PathVariable("categoryId") Long categoryId) {
		try {
			log.info("Getting category by ID: {}", categoryId);
			return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
		} catch (CategoryNotFoundException ex) {
			log.error("Category not found with ID: {}", categoryId, ex);
			return ResponseEntity.status(404).body(ex.getMessage());
		}
	}

	@PutMapping("/updateCategory")
	public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
		try {
			log.info("Updating category: {}", category);
			Category updatedCategory = categoryService.updateCategory(category);
			return ResponseEntity.ok(updatedCategory);
		} catch (Exception e) {
			log.error("Error occurred while updating category", e);
			throw new RuntimeException("Error occurred while updating category", e);
		}
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
		try {
			log.info("Deleting category with ID: {}", categoryId);
			this.categoryService.deleteCategory(categoryId);
			return ResponseEntity.ok().build();
		} catch (CategoryNotFoundException ex) {
			log.error("Category not found with ID: {}", categoryId, ex);
			return ResponseEntity.status(404).body(ex.getMessage());
		}
	}

}
