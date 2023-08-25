package com.tmax.commerce.itemmodule.service.command.shop.owner;

import com.tmax.commerce.itemmodule.common.exception.SelfValidating;
import com.tmax.commerce.itemmodule.common.exception.InputErrorCode;
import com.tmax.commerce.itemmodule.common.exception.InputException;
import com.tmax.commerce.itemmodule.entity.item.ItemStatus;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

public class OptionGroupCommand {

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class RegisterOptionGroupCommand extends SelfValidating<RegisterOptionGroupCommand> {

        @NotNull
        private final UUID shopId;

        @NotBlank
        @Size(max = 30)
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9\\s]*$")
        private final String name;

        private final Boolean required;

        @Builder
        public RegisterOptionGroupCommand(UUID shopId, String name, Boolean required) {
            this.shopId = shopId;
            this.name = name;
            this.required = required;
            validateSelf();
        }
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class DeleteOptionGroupCommand extends SelfValidating<DeleteOptionGroupCommand> {

        @NotNull
        private final Long optionGroupId;

        @Builder
        public DeleteOptionGroupCommand(Long optionGroupId) {
            this.optionGroupId = optionGroupId;
            validateSelf();
        }
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class UpdateOptionGroupCommand extends SelfValidating<UpdateOptionGroupCommand> {

        @NotNull
        private final Long optionGroupId;

        @NotBlank
        @Size(max = 30)
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9\\s]*$")
        private final String name;

        private final Boolean required;

        @Builder
        public UpdateOptionGroupCommand(Long optionGroupId, String name, Boolean required) {
            this.optionGroupId = optionGroupId;
            this.name = name;
            this.required = required;
            validateSelf();
        }
    }
    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class UpdateChoiceCountCommand extends SelfValidating<UpdateChoiceCountCommand>{

        @NotNull
        private final Long optionGroupId;

        private final int choiceCount;

        @Builder
        public UpdateChoiceCountCommand(Long optionGroupId, int choiceCount){
            this.optionGroupId = optionGroupId;
            this.choiceCount = choiceCount;
            validateSelf();
        }
    }

    @Getter
    public static class ConnectOptionGroupToItemCommand extends SelfValidating<ConnectOptionGroupToItemCommand> {

        @NotNull
        private final Long optionGroupId;

        private final List<Long> itemGroupIds;

        @Builder
        public ConnectOptionGroupToItemCommand(Long optionGroupId, List<Long> itemGroupIds) {
            this.optionGroupId = optionGroupId;
            this.itemGroupIds = itemGroupIds;
            validateSelf();
        }
    }
}
