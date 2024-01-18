package com.entity;



import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



@Entity
@Table(name="category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long category_id;
	
	private String title;
	
	private String description;
	
	@OneToMany(mappedBy = "category",cascade =CascadeType.ALL)
	@JsonIgnore
	private List<Question> questions=new ArrayList<>();
	

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category(Long category_id, String title, String description) {
		super();
		this.category_id = category_id;
		this.title = title;
		this.description = description;
	}

	public Category() {
		super();
		
	}

	@Override
	public String toString() {
		return "Category [category_id=" + category_id + ", title=" + title + ", description=" + description + "]";
	}
	
	

}
