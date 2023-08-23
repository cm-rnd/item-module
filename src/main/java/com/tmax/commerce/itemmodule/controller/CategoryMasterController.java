package com.tmax.commerce.itemmodule.controller;

import com.tmax.commerce.itemmodule.service.dto.category.user.GetAllCategoriesResponseDto;
import com.tmax.commerce.itemmodule.controller.dto.ConnectProductsWithCategoryRequestDto;
import com.tmax.commerce.itemmodule.controller.dto.PostCategoryRequestDto;
import com.tmax.commerce.itemmodule.controller.dto.UpdateCategoryRequestDto;
import com.tmax.commerce.itemmodule.service.command.CategoryMasterService;
import com.tmax.commerce.itemmodule.service.dto.category.master.GetConnectionProductsCandidResponseDto;
import com.tmax.commerce.itemmodule.service.dto.category.master.GetProductsCountOfCategoryResponseDto;
import com.tmax.commerce.itemmodule.service.dto.category.master.PostCategoryResponseDto;
import com.tmax.commerce.itemmodule.service.dto.category.master.UpdateCategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/category/master")
@RequiredArgsConstructor
public class CategoryMasterController {

    private final CategoryMasterService masterCategoryService;

    @GetMapping()
    public GetAllCategoriesResponseDto getAllCategoriesMaster(){
        return masterCategoryService.getAllCategoriesMaster();
    }

    @PostMapping()
    public PostCategoryResponseDto postCategory(@RequestBody PostCategoryRequestDto dto){
        return masterCategoryService.postCategory(dto);
    }

    @PatchMapping()
    public UpdateCategoryResponseDto updateCategory(@RequestBody UpdateCategoryRequestDto dto){
        return masterCategoryService.updateCategory(dto);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId){
        masterCategoryService.deleteCategory(categoryId);
    }

    @PatchMapping("/product/connection")
    public void connectProductsWithCategory(
            @RequestBody ConnectProductsWithCategoryRequestDto dto
    ){
        masterCategoryService.connectProductsWithCategory(dto);
    }
    @GetMapping("/product")
    public GetConnectionProductsCandidResponseDto getConnectionProductsCandid(
            @RequestParam("categoryNull") Boolean CategoryNull,
            @RequestParam(value = "searchWord", required = false) String searchWord
    ){
        return masterCategoryService.getConnectionProductsCandid(CategoryNull, searchWord);
    }
    @GetMapping("/product/count/{categoryId}")
    public GetProductsCountOfCategoryResponseDto getProductsCountOfCategory(@PathVariable Long categoryId){
        return masterCategoryService.getProductsCountOfCategory(categoryId);
    }

}
