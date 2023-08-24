package com.tmax.commerce.itemmodule.controller;

import com.tmax.commerce.itemmodule.service.dto.category.OneDepthCategoryDto;
import com.tmax.commerce.itemmodule.service.dto.category.user.GetAllCategoriesResponseDto;
import com.tmax.commerce.itemmodule.service.dto.category.user.GetAllOneDepthCategoriesResponseDto;
import com.tmax.commerce.itemmodule.service.dto.category.user.GetCategoriesByDepthResponseDto;
import com.tmax.commerce.itemmodule.service.command.CategoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryQueryService categoryQueryService;

    @GetMapping
    public GetAllCategoriesResponseDto getAllCategories() {
        return categoryQueryService.getAllCategories();
    }

    @GetMapping("/root")
    public GetAllOneDepthCategoriesResponseDto getCategory() {
        return categoryQueryService.getAllOneDepthCategories();
    }

//    @GetMapping("/{categoryId}")
//    public OneDepthCategoryDto getCategory(
//            @PathVariable(value = "categoryId") Long categoryId
//    ) {
//        return categoryQueryService.getOnedepthCategory(categoryId);
//    }

    @GetMapping("/depth")
    public GetCategoriesByDepthResponseDto getAllCategoriesByDepth(
            @RequestParam Integer depth
    ) {
        return categoryQueryService.getCategoriesByDepth(depth);
    }
}
