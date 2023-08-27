package com.tmax.commerce.itemmodule.controller.shop.owner;

import com.tmax.commerce.itemmodule.common.CommonResponse;
import com.tmax.commerce.itemmodule.service.command.shop.owner.ShopCategoryCommand;
import com.tmax.commerce.itemmodule.service.command.shop.owner.ShopCategoryCommandService;
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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("cm-owner/api/v1")
@Tag(name = "cm-owner-categories")
public class ShopCategoryCommandController {

    private final ShopCategoryCommandService shopCategoryCommandService;

    @Operation(
            summary = "샵 상품 카테고리 생성",
            description = "상품 카테고리를 추가합니다")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "카테고리를 성공적으로 생성했습니다.",
                    headers = @Header(name = "Location", description = "생성된 카테고리의 URL", schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "카테고리 생성을 실패했습니다. 자세한 원인은 message를 확인해주세요",
                    content = {@Content(schema = @Schema(implementation = CommonResponse.class))}
            )
    })
    @PostMapping(value = "/categories")
    ResponseEntity<Void> registerShopCategory(@RequestBody RegisterCategoryRequest registerCategoryRequest) {
        ShopCategoryCommand.RegisterCategoryCommand command = registerCategoryRequest.toCommand();

        Long CategoryId = shopCategoryCommandService.registerShopCategory(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{CategoryId}")
                .buildAndExpand(CategoryId)
                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(location)
                .build();
    }

    @Getter
    @NoArgsConstructor
    public static class RegisterCategoryRequest {
        private UUID shopId;
        private String name;
        private String description;

        public ShopCategoryCommand.RegisterCategoryCommand toCommand() {
            return ShopCategoryCommand.RegisterCategoryCommand.builder()
                    .shopId(shopId)
                    .name(name)
                    .description(description)
                    .build();
        }

    }

    @Operation(
            summary = "샵 카테고리 삭제",
            description = "카테고리 삭제"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
    })
    @DeleteMapping(value = "/categories/{categoryId}")
    ResponseEntity<Void> deleteShopCategory(@PathVariable Long categoryId) {
        ShopCategoryCommand.DeleteCategoryCommand command = ShopCategoryCommand.DeleteCategoryCommand.builder()
                .categoryId(categoryId)
                .build();
        shopCategoryCommandService.deleteShopCategory(command);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "샵 카테고리 정보 수정",
            description = "카테고리  디테일 수정합니다. 카테고리 명 등"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
    })
    @PatchMapping(value = "/categories/{categoryId}")
    ResponseEntity<Void> updateShopCategory(@PathVariable Long categoryId, @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        ShopCategoryCommand.UpdateCategoryCommand command = updateCategoryRequest.toCommand(categoryId);
        shopCategoryCommandService.updateShopCategory(command);
        return ResponseEntity.noContent().build();
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateCategoryRequest {
        private Long categoryId;
        private String name;
        private String description;

        public ShopCategoryCommand.UpdateCategoryCommand toCommand(Long categoryId) {
            return ShopCategoryCommand.UpdateCategoryCommand.builder()
                    .categoryId(categoryId)
                    .name(name)
                    .description(description)
                    .build();
        }

    }

    @Operation(
            summary = "샵 카테고리 순서 변경",
            description = "카테고리 순서를 변경합니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
    })
    @PutMapping(value = "/categories/sequence")
    ResponseEntity<Void> updateShopCategorySequences(@RequestBody UpdateCategorySequenceRequests updateCategorySequenceRequests) {
        ShopCategoryCommand.UpdateCategorySequencesCommand command = updateCategorySequenceRequests.toCommand();
        shopCategoryCommandService.updateShopCategorySequence(command);
        return ResponseEntity.noContent().build();
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateCategorySequenceRequests {
        private List<UpdateCategorySequenceRequest> updateCategorySequences;

        public ShopCategoryCommand.UpdateCategorySequencesCommand toCommand() {
            return ShopCategoryCommand.UpdateCategorySequencesCommand.builder()
                    .updateCategorySequenceCommands(updateCategorySequences.stream()
                            .map(UpdateCategorySequenceRequest::toCommand)
                            .collect(Collectors.toList()))
                    .build();
        }

        @Getter
        @NoArgsConstructor
        public static class UpdateCategorySequenceRequest {
            private Long categoryId;
            private int sequence;

            public ShopCategoryCommand.UpdateCategorySequencesCommand.UpdateCategorySequenceCommand toCommand() {
                return ShopCategoryCommand.UpdateCategorySequencesCommand.UpdateCategorySequenceCommand.builder()
                        .categoryId(categoryId)
                        .sequence(sequence)
                        .build();
            }
        }
    }

}
