package com.aniljha.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aniljha.payloads.ApiResponse;
import com.aniljha.payloads.CategoryDto;
import com.aniljha.payloads.PostDto;
import com.aniljha.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryControllers {

	@Autowired
	private CategoryService categoryservice;

	// create
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto categorydto = this.categoryservice.createCategory(categoryDto);
		return new ResponseEntity<>(categorydto, HttpStatus.CREATED);
	}

	/// update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("categoryIdId") Integer cid) {

		CategoryDto updatecategory = this.categoryservice.updateCategory(categoryDto, cid);
		return ResponseEntity.ok(updatecategory);
	}
	//// delete

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer cid) {
		this.categoryservice.deleteCategory(cid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully", true), HttpStatus.OK);
	}

	// Getbyid

	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getbyid(@PathVariable Integer categoryId) {
		return ResponseEntity.ok(this.categoryservice.getCategoryById(categoryId));
	}

	// getallcategory
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllcategory() {
		List<CategoryDto>allCategory=categoryservice.getCategoryAll();
		return new  ResponseEntity<List<CategoryDto>>(allCategory,HttpStatus.OK);
	}
	
}
