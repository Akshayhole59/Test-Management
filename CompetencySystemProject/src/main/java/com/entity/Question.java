package com.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionId;

	private String content;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private String answer;
	private int marks;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@JsonIgnore
	@ManyToMany(mappedBy = "questions", cascade = CascadeType.ALL)
	private List<TestManagement> tests;

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", content=" + content + ", option1=" + option1 + ", option2="
				+ option2 + ", option3=" + option3 + ", option4=" + option4 + ", answer=" + answer + ", marks=" + marks
				+ ", category=" + category + ", tests=" + tests + "]";
	}
	
	
	

}
