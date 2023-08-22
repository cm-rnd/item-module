package com.tmax.commerce.itemmodule.entity.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

@Getter
@Entity
@DiscriminatorValue("ONSITE")
@NoArgsConstructor
public class OnsiteItem extends Item {
    @Builder
    public OnsiteItem(int price, boolean brandNew, boolean recommended, ItemStatus itemStatus, String name, String description, List<File> itemImages, SuperStoreStatus connectedSuperStore, int sequence) {
        super(price, brandNew, recommended, itemStatus, name, description, itemImages, connectedSuperStore, sequence, OrderType.ONSITE);
    }
}
