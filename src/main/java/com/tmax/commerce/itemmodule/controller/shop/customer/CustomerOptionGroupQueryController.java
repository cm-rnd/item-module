package com.tmax.commerce.itemmodule.controller.shop.customer;

import com.tmax.commerce.itemmodule.common.CommonResponse;
import com.tmax.commerce.itemmodule.service.query.shop.customer.CustomerOptionGroupQueryService;
import com.tmax.commerce.itemmodule.service.query.shop.customer.info.CustomerOptionGroupInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("cm-customer/api/v1")
@Tag(name = "cm-customer-option-groups")
public class CustomerOptionGroupQueryController {
    private final CustomerOptionGroupQueryService customerOptionGroupQueryService;
    @Operation(
            summary = "상품 옵션 그룹 조회",
            description = "선택된 상품의 옵션 그룹과 옵션을 조회합니다. ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CustomerOptionGroupInfo.class), mediaType = "application/json")}),
    })
    @GetMapping(value = "/option-groups/list")
    ResponseEntity<CommonResponse<List<CustomerOptionGroupInfo>>> retrieveOptionGroups(@RequestParam("itemGroupId") Long itemGroupId) {
        List<CustomerOptionGroupInfo> customerOptionGroupInfos = customerOptionGroupQueryService.retrieveOptionGroupsByItemGroupId(itemGroupId);
        return ResponseEntity.ok(CommonResponse.success("상품 옵션 그룹 다건 조회 성공", customerOptionGroupInfos));
    }
}
