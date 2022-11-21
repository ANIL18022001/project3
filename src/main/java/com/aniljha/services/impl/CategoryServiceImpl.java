package com.aniljha.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aniljha.entity.Category;
import com.aniljha.entity.User;
import com.aniljha.exception.ResourceNotFoundException;
import com.aniljha.payloads.CategoryDto;
import com.aniljha.payloads.UserDto;
import com.aniljha.repository.CategoryRepo;
import com.aniljha.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CategoryRepo categoryRepo;

	///create 
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.dtoToCategory(categoryDto);
		Category savedCategory = this.categoryRepo.save(category);
		return this.CategoryToDto(savedCategory);
	}
	
//update
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());

		Category updatecategory = this.categoryRepo.save(category);
		CategoryDto categoryDto1 = this.CategoryToDto(updatecategory);
		return categoryDto1;
	}

	//delete
	@Override
	public void deleteCategory(Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "Id", categoryId));
		this.categoryRepo.delete(category);
	}

	//getbyid
	@Override
	public CategoryDto getCategoryById(Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "Id", categoryId));

		return this.CategoryToDto(category);
	}

	//getall
	@Override
	public List<CategoryDto> getCategoryAll() {
		List<Category> categorys = this.categoryRepo.findAll();
		List<CategoryDto> categorydtos = categorys.stream().map(category -> this.CategoryToDto(category))
				.collect(Collectors.toList());

		// List<CategoryDto>categorydtos=categorys.stream()
		// .map((category)->this.modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
		return categorydtos;
	}

	/// model paper
	public Category dtoToCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		return category;

	}

	public CategoryDto CategoryToDto(Category category) {
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}

}
