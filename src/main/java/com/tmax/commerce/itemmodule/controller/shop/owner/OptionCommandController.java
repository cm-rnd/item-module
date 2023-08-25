package com.tmax.commerce.itemmodule.controller.shop.owner;

import com.tmax.commerce.itemmodule.common.CommonResponse;
import com.tmax.commerce.itemmodule.entity.item.ItemStatus;
import com.tmax.commerce.itemmodule.service.command.shop.owner.OptionCommand;
import com.tmax.commerce.itemmodule.service.command.shop.owner.OptionCommandService;
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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("cm-owner/api/v1")
@Tag(name = "cm-owner-option-groups")
public class OptionCommandController {
    private final OptionCommandService optionCommandService;

    @Operation(
            summary = "상품 옵션을 생성합니다.",
            description = "상품 옵션을 생성합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "상품 옵션을 성공적으로 생성했습니다.",
                    headers = @Header(name = "Location", description = "생성된 상품 옵션의 URL", schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "상품 옵션을 실패했습니다. 자세한 원인은 message를 확인해주세요",
                    content = {@Content(schema = @Schema(implementation = CommonResponse.class))}
            )
    })
    @PostMapping(value = "/option-groups/options")
    ResponseEntity<Void> registerOption(@RequestBody RegisterOptionRequest registerOptionRequest) {
        OptionCommand.RegisterOptionCommand command = registerOptionRequest.toCommand();
        Long optionId = optionCommandService.registerOption(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{optionId}")
                .buildAndExpand(optionId)
                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(location)
                .build();
    }

    @Getter
    @Schema
    @NoArgsConstructor
    public static class RegisterOptionRequest {

        @Schema(description = "상품 옵션 그룹 ID")
        private Long optionGroupId;

        @Schema(description = "상품 옵션명")
        private String name;

        @Schema(description = "상품 옵션 가격")
        private int price;

        @Schema(description = "상품 옵션 판매 상태. TODAY_SOLD_OUT(오늘 품절), ON_SALE(판매중),CONTINUE_SOLD_OUT(계속품절) 중 1가지 선택 ")
        private ItemStatus itemStatus;

        public OptionCommand.RegisterOptionCommand toCommand() {
            return OptionCommand.RegisterOptionCommand.builder()
                    .optionGroupId(optionGroupId)
                    .name(name)
                    .price(price)
                    .itemStatus(itemStatus)
                    .build();
        }
    }

    @Operation(
            summary = "상품 옵션을 수정합니다.",
            description = "상품 옵션을 수정합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "상품 옵션을 성공적으로 수정했습니다.",
                    headers = @Header(name = "Location", description = "수정된 상품 옵션의 URL", schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "상품 옵션 수정을 실패했습니다. 자세한 원인은 message를 확인해주세요",
                    content = {@Content(schema = @Schema(implementation = CommonResponse.class))}
            )
    })
    @PatchMapping(value = "/options/{optionId}")
    ResponseEntity<Void> updateOption(@PathVariable Long optionId, @RequestBody UpdateOptionRequest updateOptionRequest) {
        OptionCommand.UpdateOptionCommand command = updateOptionRequest.toCommand(optionId);
        optionCommandService.updateOption(command);
        return ResponseEntity.noContent().build();
    }

    @Getter
    @Schema
    @NoArgsConstructor
    public static class UpdateOptionRequest {
        @Schema(description = "상품 옵션명")
        private String name;
        @Schema(description = "상품 옵션 가격")
        private int price;
        @Schema(description = "상품 옵션 판매 상태. TODAY_SOLD_OUT(오늘 품절), ON_SALE(판매중),CONTINUE_SOLD_OUT(계속품절) 중 1가지 선택 ")
        private ItemStatus itemStatus;

        public OptionCommand.UpdateOptionCommand toCommand(Long optionId) {
            return OptionCommand.UpdateOptionCommand.builder()
                    .optionId(optionId)
                    .name(name)
                    .price(price)
                    .itemStatus(itemStatus)
                    .build();
        }
    }

    @Operation(
            summary = "상품 옵션을 삭제합니다.",
            description = "상품 옵션을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "상품 옵션을 성공적으로 삭제했습니다.",
                    headers = @Header(name = "Location", description = "삭제된 상품 옵션 그룹의 URL", schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "상품 옵션 삭제를 실패했습니다. 자세한 원인은 message를 확인해주세요",
                    content = {@Content(schema = @Schema(implementation = CommonResponse.class))}
            )
    })
    @DeleteMapping(value = "/option-groups/{optionGroupId}/options/{optionId}")
    ResponseEntity<Void> deleteOption(@PathVariable Long optionGroupId, @PathVariable Long optionId) {
        OptionCommand.DeleteOptionCommand command = OptionCommand.DeleteOptionCommand.builder()
                .optionGroupId(optionGroupId)
                .optionId(optionId)
                .build();
        optionCommandService.deleteOption(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "상품 옵션 순서를 변경합니다.",
            description = "옵션 옵션 순서를 변경합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "상품 옵션 순서를 성공적으로 변경했습니다.",
                    headers = @Header(name = "Location", description = "상품 옵션 순서 변경된 URL", schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "상품 옵션 순서 변경을 실패했습니다. 자세한 원인은 message를 확인해주세요",
                    content = {@Content(schema = @Schema(implementation = CommonResponse.class))}
            )
    })
    @PutMapping(value = "/option-groups/{optionGroupId}/options/sequence")
    ResponseEntity<Void> updateOptionSequences(@PathVariable Long optionGroupId, @RequestBody UpdateOptionSequenceRequests updateOptionSequenceRequests) {
        OptionCommand.UpdateOptionSequencesCommand command = updateOptionSequenceRequests.toCommand(optionGroupId);
        optionCommandService.updateOptionSequences(command);
        return ResponseEntity.noContent().build();
    }

    @Getter
    @Schema
    @NoArgsConstructor
    public static class UpdateOptionSequenceRequests {

        @Schema(description = "상품 옵션 순서 리스트")
        private List<UpdateOptionSequenceRequest> optionSequences;

        public OptionCommand.UpdateOptionSequencesCommand toCommand(Long optionGroupId) {
            return OptionCommand.UpdateOptionSequencesCommand.builder()
                    .updateOptionSequenceCommands(optionSequences.stream()
                            .map(UpdateOptionSequenceRequest::toCommand)
                            .collect(Collectors.toList()))
                    .build();
        }

        @Getter
        @Schema
        @NoArgsConstructor
        public static class UpdateOptionSequenceRequest {
            @Schema(description = "상품 옵션 ID")
            private Long optionId;

            @Schema(description = "상품 옵션 순서")
            private int sequence;

            public OptionCommand.UpdateOptionSequenceCommand toCommand() {
                return OptionCommand.UpdateOptionSequenceCommand.builder()
                        .optionId(optionId)
                        .sequence(sequence)
                        .build();
            }
        }
    }
}

