package com.tmax.commerce.itemmodule.controller.shop.owner;

import com.tmax.commerce.itemmodule.common.CommonResponse;
import com.tmax.commerce.itemmodule.service.command.shop.owner.OptionGroupCommand;
import com.tmax.commerce.itemmodule.service.command.shop.owner.OptionGroupCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("cm-owner/api/v1")
@Tag(name = "cm-owner-option-groups")
public class OptionGroupCommandController {
    private final OptionGroupCommandService optionGroupCommandService;

    @Operation(
            summary = "상품 옵션 그룹을 생성합니다.",
            description = "상품 옵션 그룹을 생성합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "상품 옵션 그룹을 성공적으로 생성했습니다.",
                    headers = @Header(name = "Location", description = "생성된 상품 옵션 그룹의 URL", schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "상품 옵션 그룹 생성을 실패했습니다. 자세한 원인은 message를 확인해주세요",
                    content = {@Content(schema = @Schema(implementation = CommonResponse.class))}
            )
    })
    @PostMapping(value = "/option-groups")
    ResponseEntity<Void> registerOptionGroup(@RequestBody RegisterOptionGroupRequest registerOptionGroupRequest) {
        OptionGroupCommand.RegisterOptionGroupCommand command = registerOptionGroupRequest.toCommand();
        Long optionGroupId = optionGroupCommandService.registerOptionGroup(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{optionGroupId}")
                .buildAndExpand(optionGroupId)
                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(location)
                .build();
    }

    @Getter
    @Schema
    @NoArgsConstructor
    public static class RegisterOptionGroupRequest {
        @Schema(description = "샵의 ID")
        private UUID shopId;

        @Schema(description = "상품 옵션 그룹명")
        private String name;

        @Schema(description = "필수 옵션 or 선택 옵션")
        private boolean required;

        public OptionGroupCommand.RegisterOptionGroupCommand toCommand() {
            return OptionGroupCommand.RegisterOptionGroupCommand.builder()
                    .shopId(shopId)
                    .name(name)
                    .required(required)
                    .build();
        }
    }

    @Operation(
            summary = "상품 옵션 그룹을 삭제합니다.",
            description = "상품 옵션 그룹을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "상품 옵션 그룹을 성공적으로 삭제했습니다.",
                    headers = @Header(name = "Location", description = "삭제된 상품 옵션 그룹의 URL", schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "상품 옵션 그룹 삭제를 실패했습니다. 자세한 원인은 message를 확인해주세요",
                    content = {@Content(schema = @Schema(implementation = CommonResponse.class))}
            )
    })
    @DeleteMapping(value = "/option-groups/{optionGroupId}")
    ResponseEntity<Void> deleteOptionGroup(@PathVariable Long optionGroupId) {
        OptionGroupCommand.DeleteOptionGroupCommand command = OptionGroupCommand.DeleteOptionGroupCommand.builder()
                .optionGroupId(optionGroupId)
                .build();

        optionGroupCommandService.deleteOptionGroup(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "상품 옵션 그룹을 수정합니다.",
            description = "상품 옵션 그룹을 수정합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "상품 옵션 그룹을 성공적으로 수정했습니다.",
                    headers = @Header(name = "Location", description = "수정된 상품 옵션 그룹의 URL", schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "상품 옵션 그룹 수정을 실패했습니다. 자세한 원인은 message를 확인해주세요",
                    content = {@Content(schema = @Schema(implementation = CommonResponse.class))}
            )
    })
    @PatchMapping(value = "/option-groups/{optionGroupId}")
    ResponseEntity<Void> updateOptionGroup(@PathVariable Long optionGroupId, @RequestBody UpdateOptionGroupRequest updateOptionGroupRequest) {
        OptionGroupCommand.UpdateOptionGroupCommand command = updateOptionGroupRequest.toCommand(optionGroupId);
        optionGroupCommandService.updateOptionGroup(command);
        return ResponseEntity.noContent().build();
    }

    @Getter
    @Schema
    @NoArgsConstructor
    public static class UpdateOptionGroupRequest {
        @Schema(description = "상품 옵션 그룹명")
        private String name;
        @Schema(description = "필수 옵션 or 선택 옵션")
        private boolean required;

        public OptionGroupCommand.UpdateOptionGroupCommand toCommand(Long optionGroupId) {
            return OptionGroupCommand.UpdateOptionGroupCommand.builder()
                    .optionGroupId(optionGroupId)
                    .name(name)
                    .required(required)
                    .build();
        }
    }

    @Operation(
            summary = "상품 옵션 수를 선택합니다.",
            description = "옵션 그룹 내 상품 옵션 수를 선택합니다. 필수 옵션 그룹일 경우 최소 옵션 선택수는 1개이며, 선택 옵션 그룹일 경우 최소 옵션 선택수는 0개입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "상품 옵션 수를 성공적으로 등록(수정)했습니다.",
                    headers = @Header(name = "Location", description = "수정된 상품 옵션의 URL", schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "상품 옵션 수 등록(수정)을 실패했습니다. 자세한 원인은 message를 확인해주세요",
                    content = {@Content(schema = @Schema(implementation = CommonResponse.class))}
            )
    })
    @PutMapping(value = "/option-groups/{optionGroupId}/options/counts")
    ResponseEntity<Void> updateChoiceCount(@PathVariable Long optionGroupId, @RequestBody UpdateChoiceCountRequest updateChoiceCountRequest) {
        OptionGroupCommand.UpdateChoiceCountCommand command = updateChoiceCountRequest.toCommand();
        optionGroupCommandService.updateChoiceCount(command);
        return ResponseEntity.noContent().build();
    }

    @Getter
    @Schema
    @NoArgsConstructor
    public static class UpdateChoiceCountRequest {
        @Schema(description = "상품 옵션 그룹 ID")
        private Long optionGroupId;
        @Schema(description = "상품 옵션 선택 수")
        private int choiceCount;

        public OptionGroupCommand.UpdateChoiceCountCommand toCommand() {
            return OptionGroupCommand.UpdateChoiceCountCommand.builder()
                    .optionGroupId(optionGroupId)
                    .choiceCount(choiceCount)
                    .build();
        }
    }
    @Operation(
            summary = "상품 옵션 그룹과 상품를 연결합니다.",
            description = "상품 옵션 그룹과 상품를 연결합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "상품 옵션 그룹과 상품를 성공적으로 연결했습니다.",
                    headers = @Header(name = "Location", description = "상품 옵션 그룹 URL", schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "상품 옵션 그룹을 실패했습니다. 자세한 원인은 message를 확인해주세요",
                    content = {@Content(schema = @Schema(implementation = CommonResponse.class))}
            )
    })
    @PostMapping(value = "/option-groups/connection")
    ResponseEntity<Void> connectOptionGroupToProduct(@RequestBody ConnectOptionGroupToItemRequest connectOptionGroupToItemRequest) {
        OptionGroupCommand.ConnectOptionGroupToItemCommand command = connectOptionGroupToItemRequest.toCommand();
        Long optionGroupId = optionGroupCommandService.connectOptionGroupToItem(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{optionGroupId}")
                .buildAndExpand(optionGroupId)
                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(location)
                .build();
    }

    @Getter
    @Schema
    @NoArgsConstructor
    public static class ConnectOptionGroupToItemRequest {

        @Schema(description = "상품 옵션 그룹 ID")
        private Long optionGroupId;
        @Schema(description = "상품 ID 목록")
        private List<Long> itemGroupIds;

        public OptionGroupCommand.ConnectOptionGroupToItemCommand toCommand() {
            return OptionGroupCommand.ConnectOptionGroupToItemCommand.builder()
                    .optionGroupId(optionGroupId)
                    .itemGroupIds(itemGroupIds)
                    .build();
        }
    }
}
