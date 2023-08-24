package com.tmax.commerce.itemmodule.entity.item;


import com.tmax.commerce.itemmodule.entity.item.subtyped.OnsiteItem;
import com.tmax.commerce.itemmodule.entity.item.subtyped.PickupItem;
import com.tmax.commerce.itemmodule.service.command.ItemCommand;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public enum OrderType {
    SHIPPING("배송", (registerItemCommand, uuid) -> null),
    DELIVERY("배달", (registerItemCommand, uuid) -> null),
    PICKUP("포장", (registerItemCommand, uuid) -> createPickupItem(registerItemCommand, uuid)),
    RESERVATION("예약", (registerItemCommand, uuid) -> null),
    ONSITE("현장", (registerItemCommand, uuid) -> createOnsiteItem(registerItemCommand, uuid))
    ;
    private final String description;
    private final ItemCreator itemCreator;
    public Item createItem(ItemCommand.RegisterItemCommand registerItemCommand, UUID uuid) {
        return itemCreator.createItem(registerItemCommand, uuid);
    }
    interface ItemCreator {
        Item createItem(ItemCommand.RegisterItemCommand registerItemCommand, UUID uuid);
    }

    private static PickupItem createPickupItem(ItemCommand.RegisterItemCommand registerItemCommand, UUID uuid) {
        return new PickupItem(
                uuid,
                registerItemCommand.getPrice(),
                registerItemCommand.getBrandNew(),
                registerItemCommand.getRecommended(),
                registerItemCommand.getItemStatus(),
                registerItemCommand.getName(),
                registerItemCommand.getDescription(),
                registerItemCommand.getItemImages(),
                registerItemCommand.getCombinable()
        );
    }

    private static OnsiteItem createOnsiteItem(ItemCommand.RegisterItemCommand registerItemCommand, UUID uuid) {
        return new OnsiteItem(
                uuid,
                registerItemCommand.getPrice(),
                registerItemCommand.getBrandNew(),
                registerItemCommand.getRecommended(),
                registerItemCommand.getItemStatus(),
                registerItemCommand.getName(),
                registerItemCommand.getDescription(),
                registerItemCommand.getItemImages(),
                registerItemCommand.getCombinable()
        );
    }
}
