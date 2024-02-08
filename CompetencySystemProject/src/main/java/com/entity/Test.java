package com.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "tests")
public class Test {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long testId;

	private String title;

	private String description;

	private int maxMarks;

	private int numberofQuestions;

	private boolean active = false;

	@ManyToMany
	@JoinTable(
	    name = "question_test",
	    joinColumns = @JoinColumn(name = "test_id"),
	    inverseJoinColumns = @JoinColumn(name = "question_id")
	)
	private List<Question> questions;
	
	
	

}
