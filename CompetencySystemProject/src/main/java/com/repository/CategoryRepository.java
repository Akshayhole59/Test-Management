package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	Category findByTitle(String title);

}
