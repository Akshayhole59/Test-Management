package com.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.entity.Category;
import com.entity.Question;
import com.exception.CategoryNotFoundException;
import com.repository.CategoryRepository;
import com.repository.QuestionRepository;
import com.service.CategoryService;
import com.service.QuestionService;

@Service
public class CategoryServiceImple implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	QuestionRepository questionRepo;

	@Override
	public Category addCategory(Category category) {
		return this.categoryRepository.save(category);
	}

	@Override
	public ArrayList<Category> getAllCatogory() {
		return (ArrayList<Category>) this.categoryRepository.findAll();
	}

	@Override
	public Category getCategoryById(Long categoryId) {
		try {
			return this.categoryRepository.findById(categoryId)
					.orElseThrow(() -> new CategoryNotFoundException("Category with id " + categoryId + " not found"));
		} catch (CategoryNotFoundException categoryNotFoundException) {
			throw categoryNotFoundException;
		}
	}

	@Override
	public Category updateCategory(Category category) {
		try {
			return this.categoryRepository.save(category);
		} catch (Exception e) {
			throw new RuntimeException("Failed to Update category", e);
		}
	}

	@Override
	public void deleteCategory(Long categoryId) {
		try {
			if (exists(categoryId)) {
				Category category = new Category();
				category.setCategory_id(categoryId);
				Category categoryNew = categoryRepository.findById(categoryId).orElse(null);
				
				if (categoryNew != null) {
			        
			        List<Question> questions = categoryNew.getQuestions();
			        if (questions != null) {
			            questions.forEach(question -> {
			                question.setCategory(null); 
			                questionRepo.save(question); 
			            });
			        }
				
				this.categoryRepository.delete(category);
				}
			} else {
				throw new CategoryNotFoundException("Category with id " + categoryId + " not found");
			}
			
		
		} catch (CategoryNotFoundException categoryNotFoundException) {
			throw new CategoryNotFoundException("Category with id " + categoryId + " not found");
		} catch (Exception e) {
			throw new RuntimeException("Failed to delete category", e);
		}

	}
	
	public boolean exists(Long categoryId) {
		return categoryRepository.existsById(categoryId);
	}
	
	public Category getCategoryByName(String selectedCategory) {
		Category category = categoryRepository.findByTitle(selectedCategory);
		return category;
	}

}
