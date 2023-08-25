package com.tmax.commerce.itemmodule.service.command.shop.owner;

import com.tmax.commerce.itemmodule.common.exception.InputErrorCode;
import com.tmax.commerce.itemmodule.common.exception.InputException;
import com.tmax.commerce.itemmodule.common.exception.SelfValidating;
import com.tmax.commerce.itemmodule.entity.item.ItemStatus;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

public class OptionCommand {

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class RegisterOptionCommand extends SelfValidating<RegisterOptionCommand> {

        @NotNull
        private final Long optionGroupId;

        @NotBlank
        @Size(max = 30)
        private final String name;

        @Min(0)
        @Max(999990)
        private final int price;

        private final ItemStatus itemStatus;

        @Builder
        public RegisterOptionCommand(Long optionGroupId, String name, int price, ItemStatus itemStatus) {
            this.optionGroupId = optionGroupId;
            this.name = name;
            this.price = validateOptionPrice(price);
            this.itemStatus = itemStatus;
            validateSelf();
        }
    }

    private static int validateOptionPrice(int price) {
        if (price % 10 != 0 || price <= 0) {
            throw new InputException(InputErrorCode.INVALID_INPUT_PRICE);
        }
        return price;
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class UpdateOptionCommand extends SelfValidating<UpdateOptionCommand> {

        @NotNull
        private final Long optionId;

        @NotBlank
        @Size(max = 30)
        private final String name;

        @Min(0)
        @Max(999990)
        private final int price;

        private final ItemStatus itemStatus;


        @Builder
        public UpdateOptionCommand(Long optionId, String name, int price, ItemStatus itemStatus) {
            this.optionId = optionId;
            this.name = name;
            this.price = validateOptionPrice(price);
            this.itemStatus = itemStatus;
            validateSelf();
        }
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class DeleteOptionCommand extends SelfValidating<DeleteOptionCommand> {

        @NotNull
        private final Long optionGroupId;

        @NotNull
        private final Long optionId;

        @Builder
        public DeleteOptionCommand(Long optionGroupId, Long optionId) {
            this.optionGroupId = optionGroupId;
            this.optionId = optionId;
            validateSelf();
        }
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class UpdateOptionSequencesCommand extends SelfValidating<UpdateOptionSequencesCommand> {

        @NotNull
        private final Long optionGroupId;

        @NotNull
        private final List<UpdateOptionSequenceCommand> updateOptionSequenceCommands;

        @Builder
        public UpdateOptionSequencesCommand(Long optionGroupId, List<UpdateOptionSequenceCommand> updateOptionSequenceCommands) {
            this.optionGroupId = optionGroupId;
            this.updateOptionSequenceCommands = updateOptionSequenceCommands;
            validateSelf();
        }
    }

    @Getter
    public static class UpdateOptionSequenceCommand extends SelfValidating<UpdateOptionSequenceCommand> {

        @NotNull
        private final Long optionId;

        @Min(0)
        private final int sequence;

        @Builder
        public UpdateOptionSequenceCommand(Long optionId, int sequence) {
            this.optionId = optionId;
            this.sequence = sequence;
            validateSelf();
        }
    }

}
