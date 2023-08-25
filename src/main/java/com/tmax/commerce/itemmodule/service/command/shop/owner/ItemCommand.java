package com.tmax.commerce.itemmodule.service.command.shop.owner;


import com.tmax.commerce.itemmodule.common.exception.InputErrorCode;
import com.tmax.commerce.itemmodule.common.exception.InputException;
import com.tmax.commerce.itemmodule.common.exception.SelfValidating;
import com.tmax.commerce.itemmodule.entity.file.FileDetail;
import com.tmax.commerce.itemmodule.entity.item.Item;
import com.tmax.commerce.itemmodule.entity.item.ItemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

public class ItemCommand {

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class RegisterItemCommand extends SelfValidating<RegisterItemCommand> {
        @NotNull
        private final UUID itemCategoryId;

        @NotBlank
        @Size(max = 10)
        private final String name;

        @NotNull
        @Size(max = 10)
        private final String description;

        private final int price;

        private final int onsitePrice;

        @NotNull
        private final ItemStatus itemStatus;

        private final boolean brandNew;

        private final boolean recommended;

        private final boolean onsiteOrder;

        private final boolean pickupOrder;

        @Size(max = 10)
        private final List<FileDetail> productImages;

        private final List<UUID> productOptionGroupIds;

        @Builder
        public RegisterItemCommand(UUID itemCategoryId, String name, String description, int price, int onsitePrice, ItemStatus itemStatus,
                                   boolean brandNew, boolean recommended, boolean onsiteOrder, boolean pickupOrder,
                                   List<FileDetail> productImages, List<UUID> productOptionGroupIds) {

            validateCheckPickupOrderAndOnsiteOrder(onsiteOrder, pickupOrder);
            this.itemCategoryId = itemCategoryId;
            this.name = name;
            this.description = description;
            this.price = validateProductPrice(price);
            this.onsitePrice = validateProductPrice(onsitePrice);
            this.itemStatus = itemStatus;
            this.brandNew = brandNew;
            this.recommended = recommended;
            this.onsiteOrder = onsiteOrder;
            this.pickupOrder = pickupOrder;
            this.productImages = productImages;
            this.productOptionGroupIds = productOptionGroupIds;
            validateSelf();
        }
    }

    private static int validateProductPrice(int price) {
        if (price % 10 != 0 || price <= 0) {
            throw new InputException(InputErrorCode.INVALID_INPUT_PRICE);
        }
        return price;
    }

    private static void validateCheckPickupOrderAndOnsiteOrder(boolean isPickupOrder, boolean isOnsiteOrder) {
        if (!isPickupOrder && !isOnsiteOrder) {
            throw new InputException(InputErrorCode.INVALID_PARAMETER_PICKUP_ORDER_AND_ONSITE_ORDER);
        }
    }


    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class UpdateProductCommand extends SelfValidating<UpdateProductCommand> {
        @NotNull
        private final UUID productGroupId;

        @NotBlank
        @Size(max = 10)
        private final String name;

        @NotNull
        @Size(max = 10)
        private final String description;

        private final int price;

        private final int onsitePrice;

        @NotNull
        private final ItemStatus itemStatus;

        private final boolean brandNew;

        private final boolean recommended;

        private final boolean onsiteOrder;

        private final boolean pickupOrder;

        @NotNull
        private final List<FileDetail> itemImages;

        @NotNull
        private final List<UUID> productOptionGroupIds;

        @Builder
        public UpdateProductCommand(UUID productGroupId, String name, String description, int price, int onsitePrice, ItemStatus itemStatus,
                                    boolean brandNew, boolean recommended, boolean onsiteOrder, boolean pickupOrder,
                                    List<FileDetail> itemImages, List<UUID> productOptionGroupIds) {
            validateCheckPickupOrderAndOnsiteOrder(onsiteOrder, pickupOrder);
            this.productGroupId = productGroupId;
            this.name = name;
            this.description = description;
            this.price = validateProductPrice(price);
            this.onsitePrice = validateProductPrice(onsitePrice);
            this.itemStatus = itemStatus;
            this.brandNew = brandNew;
            this.recommended = recommended;
            this.onsiteOrder = onsiteOrder;
            this.pickupOrder = pickupOrder;
            this.itemImages = itemImages;
            this.productOptionGroupIds = productOptionGroupIds;
            validateSelf();
        }
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class ChangeProductSequencesCommand extends SelfValidating<ChangeProductSequencesCommand> {
        @NotNull
        private final List<ChangeProductSequenceCommand> changeProductSequenceCommands;

        @Builder
        public ChangeProductSequencesCommand(List<ChangeProductSequenceCommand> changeProductSequenceCommands) {
            this.changeProductSequenceCommands = changeProductSequenceCommands;
            validateSelf();
        }
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class ChangeProductSequenceCommand extends SelfValidating<ChangeProductSequenceCommand> {
        @NotNull
        private final UUID productId;

        private final int sequence;

        @Builder
        public ChangeProductSequenceCommand(UUID productId, int sequence) {
            this.productId = productId;
            this.sequence = sequence;
            validateSelf();
        }
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class ConnectSuperStoreProductsCommand extends SelfValidating<ConnectSuperStoreProductsCommand> {
        @NotNull
        private final List<ConnectSuperStoreProductCommand> connectSuperStoreProductCommands;

        @Builder
        public ConnectSuperStoreProductsCommand(List<ConnectSuperStoreProductCommand> connectSuperStoreProductCommands) {
            this.connectSuperStoreProductCommands = connectSuperStoreProductCommands;
            validateSelf();
        }
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class ConnectSuperStoreProductCommand extends SelfValidating<ConnectSuperStoreProductCommand> {
        @NotNull
        private final UUID productGroupId;

        private final Item.SuperStoreStatus connectedSuperStore;

        @Builder
        public ConnectSuperStoreProductCommand(UUID productGroupId, Item.SuperStoreStatus connectedSuperStore) {
            this.productGroupId = productGroupId;
            this.connectedSuperStore = connectedSuperStore;
            validateSelf();
        }

    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class DeleteProductsCommand extends SelfValidating<DeleteProductsCommand> {
        @NotNull
        private final List<UUID> productGroupIds;

        @Builder
        public DeleteProductsCommand(List<UUID> productGroupIds) {
            this.productGroupIds = productGroupIds;
            validateSelf();
        }
    }
}
