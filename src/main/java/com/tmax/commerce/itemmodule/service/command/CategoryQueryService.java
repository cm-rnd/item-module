package com.tmax.commerce.itemmodule.service.command;

import com.tmax.commerce.itemmodule.service.dto.category.CategoriesByDepthDto;
import com.tmax.commerce.itemmodule.service.dto.category.CategoryDto;
import com.tmax.commerce.itemmodule.service.dto.category.OneDepthCategoryDto;
import com.tmax.commerce.itemmodule.service.dto.mapper.CategoryMapper;
import com.tmax.commerce.itemmodule.service.dto.category.user.GetAllCategoriesResponseDto;
import com.tmax.commerce.itemmodule.service.dto.category.user.GetAllOneDepthCategoriesResponseDto;
import com.tmax.commerce.itemmodule.service.dto.category.user.GetCategoriesByDepthResponseDto;
import com.tmax.commerce.itemmodule.entity.category.Category;
import com.tmax.commerce.itemmodule.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryQueryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public GetAllCategoriesResponseDto getAllCategories() {
        List<Category> rootCategories = categoryRepository.findByParentCategoryIsNull();
        List<CategoryDto> categoryDtos = rootCategories.stream()
                .map(category -> getSubCategories(category))
                .collect(Collectors.toList());

        return GetAllCategoriesResponseDto.builder()
                .subCategories(categoryDtos)
                .build();
    }

    public GetCategoriesByDepthResponseDto getCategoriesByDepth(Integer depth) {
        List<Category> categories = categoryRepository.findAll();

        List<CategoriesByDepthDto> categoryDtos = categories.stream()
                .filter(category -> getCategoryDepth(category) == depth)
                .map(categoryMapper::toCategoryByDepthDto)
                .collect(Collectors.toList());

        return GetCategoriesByDepthResponseDto.builder()
                .categories(categoryDtos)
                .build();
    }

    public GetAllOneDepthCategoriesResponseDto getAllOneDepthCategories() {
        List<Category> rootCategories = categoryRepository.findByParentCategoryIsNull();
        List<OneDepthCategoryDto> categoryDtos = rootCategories.stream()
                .map(rootCategory -> {

                    return mappingOneDepthCategory(rootCategory);
                })
                .collect(Collectors.toList());

        return GetAllOneDepthCategoriesResponseDto.builder()
                .categories(categoryDtos)
                .build();
    }

//    public OneDepthCategoryDto getOnedepthCategory(Long categoryId) {
//        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
//
//        return mappingOneDepthCategory(category);
//
//    }


    public Integer getCategoryDepth(Category category) {
        Integer depth = 0;
        Category parent = category.getParentCategory();

        while (parent != null) {
            depth++;
            parent = parent.getParentCategory();
        }

        return depth;
    }

    private OneDepthCategoryDto mappingOneDepthCategory(Category category){
        List<Category> subCategories = categoryRepository.findSubCategories(category);
        List<CategoriesByDepthDto> subCategoryDtos = subCategories.stream()
                .map(subCategory -> categoryMapper.toCategoryByDepthDto(subCategory))
                .collect(Collectors.toList());

        Long parentId = category.getParentCategory() != null ? category.getParentCategory().getId() : null;

        return new OneDepthCategoryDto(
                category.getId(),
                category.getName(),
                parentId,
                category.getCategoryColor() != null ? category.getCategoryColor().getColorCode() : "",
                subCategoryDtos);
    }

    public CategoryDto getSubCategories(Category category) {
        Long parentId = category.getParentCategory() != null ? category.getParentCategory().getId() : null;

        List<Category> subCategories = categoryRepository.findSubCategories(category);

        List<CategoryDto> subCategoriesDto = subCategories.stream()
                .map(this::getSubCategories)
                .collect(Collectors.toList());

        return new CategoryDto(
                category.getId(),
                category.getName(),
                parentId,
                category.getCategoryColor() != null ? category.getCategoryColor().getColorCode() : "",
                subCategoriesDto,
                null
        );
    }

    @Transactional(readOnly = true)
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

}
