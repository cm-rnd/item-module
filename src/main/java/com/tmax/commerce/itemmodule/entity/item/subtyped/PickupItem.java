package com.tmax.commerce.itemmodule.entity.item.subtyped;

import com.tmax.commerce.itemmodule.entity.item.Item;
import com.tmax.commerce.itemmodule.entity.item.ItemStatus;
import com.tmax.commerce.itemmodule.entity.item.OrderType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

@Getter
@Entity
@DiscriminatorValue("PICKUP")
@NoArgsConstructor
public class PickupItem extends Item {
    @Builder
    public PickupItem(int price, boolean brandNew, boolean recommended, ItemStatus itemStatus, String name, String description, List<File> productImages, Item.SuperStoreStatus connectedSuperStore, int sequence) {
        super(price, brandNew, recommended, itemStatus, name, description, productImages, connectedSuperStore, sequence, OrderType.PICKUP);
    }
}
