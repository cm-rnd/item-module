package com.tmax.commerce.itemmodule.controller.shop.owner;

import com.tmax.commerce.itemmodule.common.CommonResponse;
import com.tmax.commerce.itemmodule.service.query.shop.owner.OptionGroupQuery;
import com.tmax.commerce.itemmodule.service.query.shop.owner.OptionGroupQueryService;
import com.tmax.commerce.itemmodule.service.query.shop.owner.info.OptionGroupInfo;
import com.tmax.commerce.itemmodule.service.query.shop.owner.info.OptionGroupSimpleInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("cm-owner/api/v1")
@Tag(name = "cm-owner-option-groups")
public class OptionGroupQueryController {
    private final OptionGroupQueryService optionGroupQueryService;

    @Operation(
            summary = "상품 옵션 그룹 다건 조회(페이징 처리)",
            description = "샵에 있는 상품 옵션 그룹을 다건 조회합니다. 페이징 처리한 상품 옵션 그룹 및 하위 옵션들을 볼 수 있습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = OptionGroupInfo.class), mediaType = "application/json")}),
    })
    @GetMapping(value = "/option-groups")
    ResponseEntity<CommonResponse<Page<OptionGroupInfo>>> retrieveOptionGroups(@RequestParam UUID shopId,
                                                                               @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        OptionGroupQuery.RetrieveOptionGroupsQuery query = OptionGroupQuery.RetrieveOptionGroupsQuery.builder()
                .shopId(shopId)
                .pageable(pageable)
                .build();

        Page<OptionGroupInfo> optionGroupInfoPage = optionGroupQueryService.retrieveOptionGroups(query);
        return ResponseEntity.ok(CommonResponse.success("상품 옵션 그룹 다건 조회 성공", optionGroupInfoPage));
    }

    @Operation(
            summary = "상품 옵션 그룹 다건 조회(페이징 처리X), 상품과 옵션 그룹 연결용",
            description = "샵에 있는 상품 옵션 그룹을 다건 조회합니다. 상품 옵션 그룹 및 하위 옵션들을 볼 수 있습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = OptionGroupSimpleInfo.class), mediaType = "application/json")}),
    })
    @GetMapping(value = "/option-groups/list")
    ResponseEntity<CommonResponse<List<OptionGroupSimpleInfo>>> retrieveOptionGroups(@RequestParam("shopId") UUID shopId) {
        List<OptionGroupSimpleInfo> optionGroupSimpleInfos = optionGroupQueryService.retrieveOptionGroupsByShopId(shopId);
        return ResponseEntity.ok(CommonResponse.success("상품 옵션 그룹 다건 조회 성공", optionGroupSimpleInfos));
    }

    @Operation(
            summary = "상품 옵션 그룹 단건 조회",
            description = "상품 옵션 그룹 하나를 조회합니다. 상품 옵션 그룹 및 하위 옵션들을 볼 수 있습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = OptionGroupInfo.class), mediaType = "application/json")}),
    })
    @GetMapping(value = "/option-groups/{optionGroupId}")
    ResponseEntity<CommonResponse<OptionGroupInfo>> retrieveOptionGroup(@PathVariable Long optionGroupId) {
        OptionGroupQuery.RetrieveOptionGroupQuery query = OptionGroupQuery.RetrieveOptionGroupQuery.builder()
                .optionGroupId(optionGroupId)
                .build();

        OptionGroupInfo optionGroupInfo = optionGroupQueryService.retrieveOptionGroup(query);
        return ResponseEntity.ok(CommonResponse.success("상품 옵션 그룹 단건 조회 성공", optionGroupInfo));
    }
}
