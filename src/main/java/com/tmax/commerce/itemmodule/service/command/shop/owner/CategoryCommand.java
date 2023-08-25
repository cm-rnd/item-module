package com.tmax.commerce.itemmodule.service.command.shop.owner;

import com.tmax.commerce.itemmodule.common.exception.SelfValidating;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

public class CategoryCommand {

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class RegisterCategoryCommand extends SelfValidating<RegisterCategoryCommand> {

        @NotNull
        private final UUID shopId;

        @NotBlank
        @Size(max = 30)
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9\\s]*$")
        private final String name;

        @NotNull
        @Size(max = 50)
        private final String description;

        @Builder
        public RegisterCategoryCommand(UUID shopId, String name, String description) {
            this.shopId = shopId;
            this.name = name;
            this.description = description;
            validateSelf();
        }
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class DeleteCategoryCommand extends SelfValidating<DeleteCategoryCommand> {
        @NotNull
        private final Long categoryId;

        @Builder
        public DeleteCategoryCommand(Long categoryId) {
            this.categoryId = categoryId;
            validateSelf();
        }
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class UpdateCategoryCommand extends SelfValidating<UpdateCategoryCommand> {
        @NotNull
        private final Long categoryId;

        @NotBlank
        @Size(max = 30)
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9\\s]*$")
        private final String name;

        @NotNull
        @Size(max = 50)
        private final String description;

        @Builder
        public UpdateCategoryCommand(Long categoryId, String name, String description) {
            this.categoryId = categoryId;
            this.name = name;
            this.description = description;
            validateSelf();
        }

    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class UpdateCategorySequencesCommand extends SelfValidating<UpdateCategorySequencesCommand> {
        @NotNull
        private final List<UpdateCategorySequenceCommand> updateCategorySequenceCommands;

        @Builder
        public UpdateCategorySequencesCommand(List<UpdateCategorySequenceCommand> updateCategorySequenceCommands) {
            this.updateCategorySequenceCommands = updateCategorySequenceCommands;
            validateSelf();
        }

        @Getter
        public static class UpdateCategorySequenceCommand extends SelfValidating<UpdateCategorySequenceCommand> {
            @NotNull
            private final Long categoryId;

            @Min(0)
            private final int sequence;

            @Builder
            public UpdateCategorySequenceCommand(Long categoryId, int sequence) {
                this.categoryId = categoryId;
                this.sequence = sequence;
                validateSelf();
            }
        }
    }

}
