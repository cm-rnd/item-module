package com.tmax.commerce.itemmodule.service.command;

import com.tmax.commerce.itemmodule.service.dto.category.CategoryDto;
import com.tmax.commerce.itemmodule.service.dto.category.user.GetAllCategoriesResponseDto;
import com.tmax.commerce.itemmodule.common.utils.comparator.GetAllCategoryComp;
import com.tmax.commerce.itemmodule.controller.dto.ConnectProductsWithCategoryRequestDto;
import com.tmax.commerce.itemmodule.controller.dto.PostCategoryRequestDto;
import com.tmax.commerce.itemmodule.controller.dto.UpdateCategoryRequestDto;
import com.tmax.commerce.itemmodule.entity.category.Category;
import com.tmax.commerce.itemmodule.repository.CategoryRepository;
import com.tmax.commerce.itemmodule.service.dto.category.master.GetConnectionProductsCandidResponseDto;
import com.tmax.commerce.itemmodule.service.dto.category.master.GetProductsCountOfCategoryResponseDto;
import com.tmax.commerce.itemmodule.service.dto.category.master.PostCategoryResponseDto;
import com.tmax.commerce.itemmodule.service.dto.category.master.UpdateCategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.tmax.commerce.itemmodule.controller.dto.PostCategoryRequestDto.NEW_CATEGORY_NAME;

@Service
@RequiredArgsConstructor
public class CategoryMasterService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public GetAllCategoriesResponseDto getAllCategoriesMaster() {
        List<Category> oneDepthCategories
                = findAllCategories().stream()
                .filter(category -> category.getParentCategory() == null)
                .collect(Collectors.toList());
        GetAllCategoriesResponseDto response = GetAllCategoriesResponseDto.builder()
                .subCategories(makeCategoryResponses(oneDepthCategories))
                .build();
        sortGetAllCategoriesMasterResponse(response.getSubCategories());
        return response;
    }

    private void sortGetAllCategoriesMasterResponse(List<CategoryDto> subCategories) {
        if(subCategories == null) return;
        Collections.sort(subCategories, new GetAllCategoryComp());
        for(CategoryDto categoryDto : subCategories){
            sortGetAllCategoriesMasterResponse(categoryDto.getSubCategories());
        }
    }

    private List<CategoryDto> makeCategoryResponses(List<Category> oneDepthCategories) {
        List<CategoryDto> categoryResponses = new ArrayList<>();
        for (Category category : oneDepthCategories) {
            categoryResponses.add(makeCategoryUsingDFS(category));
        }
        return categoryResponses;
    }

    private CategoryDto makeCategoryUsingDFS(Category category) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        List<Category> subCategories = category.getSubCategories();
        for (Category subCategory : subCategories) {
            categoryDtos.add(makeCategoryUsingDFS(subCategory));
        }
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .colorCode(category.getCategoryColor() != null ? category.getCategoryColor().getColorCode() : null)
                .parentId(category.getParentCategory() != null ? category.getParentCategory().getId() : null)
                .subCategories(categoryDtos)
                .createdAt(category.getCreatedAt())
                .build();
    }

    @Transactional
    public PostCategoryResponseDto postCategory(PostCategoryRequestDto dto){
        String newCategoryName = getNewCategoryName();
        int randomId = getCategoryRandomId();
        Category parentCategory = findCategoryById(dto.getParentCategoryId());
        Category newCategory = Category.builder()
                .id(Long.valueOf(randomId))
                .parentCategory(parentCategory)
                .name(newCategoryName)
                .build();
        Category savedCategory = categoryRepository.save(newCategory);
        return PostCategoryResponseDto.builder()
                .id(savedCategory.getId())
                .name(savedCategory.getName())
                .build();
    }

    // 시연을 위해 임시로 구현된 ID
    // 추후 sequence 디폴트값 수정 후 삭제 예정
    private int getCategoryRandomId() {
        return new Random().ints(1, 100000, 2000000).findFirst().getAsInt();
    }

    // 새로 생성될 카테고리의 이름을 정해준다.
    // 이름의 맨 뒤 숫자는 1씩 증가하고, 중간에 빠진 숫자가 있는 경우 그 숫자를 사용해서 이름을 정한다.
    private String getNewCategoryName() {
        HashSet<String> existCategoryNameSet = new HashSet<>();
        List<Category> existCategoryNameList = categoryRepository.findByNameContaining(NEW_CATEGORY_NAME);
        for (Category category : existCategoryNameList) {
            existCategoryNameSet.add(category.getName());
        }
        int willUsedNumber = 1;
        while(true){
            String newCategoryNameCandid = getCandidCategoryName(willUsedNumber);
            if(!existCategoryNameSet.contains(newCategoryNameCandid)) break;
            willUsedNumber++;
        }
        return getCandidCategoryName(willUsedNumber);
    }

    private String getCandidCategoryName(int willUsedNumber){
        return willUsedNumber == 1 ? NEW_CATEGORY_NAME : NEW_CATEGORY_NAME + willUsedNumber;
    }

    @Transactional
    public UpdateCategoryResponseDto updateCategory(UpdateCategoryRequestDto dto) {
        Category updatedCategory = findCategoryById(dto.getCategoryId());
        validateCategoryNameDuplicate(dto.getName());
        updatedCategory.updateName(dto.getName());
        return UpdateCategoryResponseDto.builder()
                .categoryId(updatedCategory.getId())
                .build();
    }

    private void validateCategoryNameDuplicate(String name) {
        if(categoryRepository.existsByName(name)){
            throw new AlreadyExistsCategoryNameException();
        }
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        validateOneDepthCategory(categoryId);
        List<Long> deletedCategoryIds = findDeletedCategoryIds(findCategoryById(categoryId));
        deletedCategoryIds.add(categoryId);
        // 삭제될 카테고리를 갖는 상품들의 카테고리를 모두 null로 update
        updateProductsCategory(deletedCategoryIds);

        // 모든 카테고리를 삭제(정합성 고려하여 순서대로 insert 되어있음)
        categoryRepository.deleteAllByIdInBatch(deletedCategoryIds);
    }

    private void validateOneDepthCategory(Long categoryId) {
        Category category = findCategoryById(categoryId);
        if(category.getParentCategory() == null){
            throw new CannotDeleteOneDepthCategoryException();
        }
    }

    private void updateProductsCategory(List<Long> deletedCategoryIds) {
        productRepository.updateProductsCategoryToNull(deletedCategoryIds);
    }

    private List<Long> findDeletedCategoryIds(Category category){
        List<Long> deletedCategoryIds = new ArrayList<>();
        for(Category subCategory : category.getSubCategories()){
            deletedCategoryIds.addAll(findDeletedCategoryIds(subCategory));
            deletedCategoryIds.add(subCategory.getId());
        }
        return deletedCategoryIds;
    }

    private Category findCategoryById(Long id){
        return id != null ? categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new) : null;
    }

    private List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public void connectProductsWithCategory(
            ConnectProductsWithCategoryRequestDto dto
    ) {
        productRepository.updateProductsCategory(dto.getCategoryId(), dto.getProductsIds());
    }

    @Transactional(readOnly = true)
    public GetProductsCountOfCategoryResponseDto getProductsCountOfCategory(Long categoryId
    ){
        return GetProductsCountOfCategoryResponseDto.builder()
                .count(productRepository.findTotalProductsCountByCategoryId(getTotalSubCategoryIds(categoryId)))
                .build();
    }

    public List<Long> getTotalSubCategoryIds(Long categoryId) {
        List<Long> totalCategoryIds = new ArrayList<>();
        Category category = findCategoryById(categoryId);
        if(category == null) return null;
        List<Category> subCategories = category.getSubCategories();
        for (Category subCategory : subCategories) {
            totalCategoryIds.addAll(getTotalSubCategoryIds(subCategory.getId()));
        }
        totalCategoryIds.add(categoryId);
        return totalCategoryIds;
    }

    @Transactional(readOnly = true)
    public GetConnectionProductsCandidResponseDto getConnectionProductsCandid(Boolean categoryNull, String searchWord) {
        List<Product> findProducts = productRepository.findAllOrderByCreatedAtDesc(categoryNull, searchWord);
        return GetConnectionProductsCandidResponseDto.builder()
                .products(getConnectionProductsCandidProducts(findProducts))
                .build();
    }

    @Transactional(readOnly = true)
    public List<GetConnectionProductsCandidResponseDto.Product> getConnectionProductsCandidProducts(
            List<Product> products
    ) {
        return products.stream()
                .map(product -> GetConnectionProductsCandidResponseDto.Product
                        .builder()
                        .id(product.getId())
                        .categoryName(product.getCategory() != null ? product.getCategory().getName() : "")
                        .sellerName("티맥스스토어") // 성능 이슈로 임시적으로 static value 대입
                        .sellingPrice(product.getSellingPrice())
                        .sellingStatus(convertToProductSellingStatus(product))
                        .name(product.getName())
                        .build())
                .collect(Collectors.toList());
    }

    private String convertToProductSellingStatus(Product product) {
        if(product.getProductStatusType() == null)
            return "";
        switch (product.getProductStatusType()){
            case STOP:
                return "판매중지";
            case SALE:
            default:
                return "판매중";
        }
    }
}
