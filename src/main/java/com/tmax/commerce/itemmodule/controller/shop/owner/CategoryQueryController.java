package com.tmax.commerce.itemmodule.controller.shop.owner;

import com.tmax.commerce.itemmodule.common.CommonResponse;
import com.tmax.commerce.itemmodule.service.query.shop.owner.CategoryQuery;
import com.tmax.commerce.itemmodule.service.query.shop.owner.CategoryQueryService;
import com.tmax.commerce.itemmodule.service.query.shop.owner.info.CategoryInfo;
import com.tmax.commerce.itemmodule.service.query.shop.owner.info.CategoryItemInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("cm-owner/api/v1")
@Tag(name = "cm-owner-categories")
public class CategoryQueryController {
    private final CategoryQueryService categoryQueryService;

    @Operation(
            summary = "상품 카테고리 다건 조회(스크롤 방식)",
            description = "상품 카테고리를 조회합니다.(단순 조회용)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CategoryInfo.class), mediaType = "application/json")}),
    })
    @GetMapping(value = "/categories")
    ResponseEntity<CommonResponse<Slice<CategoryInfo>>> retrieveShopCategories(
            @PageableDefault(size = 20, sort = "sequence", direction = Sort.Direction.ASC) Pageable pageable,
            @ParameterObject @ModelAttribute RetrieveProductCategoriesRequest request) {
        Slice<CategoryInfo> productCategoryInfos = categoryQueryService.retrieveShopCategories(request.toQuery(pageable));
        return ResponseEntity.ok(CommonResponse.success("페이징 처리된 카테고리 다건 조회 성공", productCategoryInfos));
    }

    @Getter
    @AllArgsConstructor
    public static class RetrieveProductCategoriesRequest {

        @NotNull
        private UUID shopId;

        public CategoryQuery.RetrieveCategoriesQuery toQuery(Pageable pageable) {
            return CategoryQuery.RetrieveCategoriesQuery.builder()
                    .shopId(shopId)
                    .pageable(pageable)
                    .build();
        }
    }

    @Operation(
            summary = "상품 카테고리 다건 조회(상품 옵션 그룹 연결용)",
            description = "상품 카테고리 및 하위 상품을 조회합니다.(상품 옵션 그룹 연결용)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CategoryItemInfo.class), mediaType = "application/json")}),
    })
    @GetMapping(value = "/categories/connect")
    ResponseEntity<CommonResponse<Slice<CategoryItemInfo>>> retrieveShopCategoryAndItem(
            @PageableDefault(size = 20, sort = "sequence", direction = Sort.Direction.ASC) Pageable pageable,
            @ParameterObject @ModelAttribute RetrieveProductCategoriesRequest request) {
        Slice<CategoryItemInfo> categoryItemInfos = categoryQueryService.retrieveShopCategoryAndItem(request.toQuery(pageable));
        return ResponseEntity.ok(CommonResponse.success("해당 샵에 해당하는 카테고리 및 메뉴 목록 조회 성공", categoryItemInfos));
    }

}
