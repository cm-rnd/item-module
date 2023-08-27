package com.tmax.commerce.itemmodule.entity.item.subtyped;

import com.tmax.commerce.itemmodule.entity.file.FileDetail;
import com.tmax.commerce.itemmodule.entity.item.Item;
import com.tmax.commerce.itemmodule.entity.item.ItemStatus;
import com.tmax.commerce.itemmodule.entity.item.OrderType;
import com.tmax.commerce.itemmodule.entity.file.File;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@Entity
@DiscriminatorValue("ONSITE")
@NoArgsConstructor
public class OnsiteItem extends Item {
    @Builder
    public OnsiteItem(int price, boolean brandNew, boolean recommended, ItemStatus itemStatus, String name, String description, List<File> itemImages, SuperStoreStatus connectedSuperStore, int sequence) {
        super(price, brandNew, recommended, itemStatus, name, description, itemImages, connectedSuperStore, sequence, OrderType.ONSITE);
    }

    public OnsiteItem(UUID uuid, int price, boolean brandNew, boolean recommended, ItemStatus itemStatus, String name, String description, List<FileDetail> itemImages, Boolean combinable) {
        super(uuid, price, brandNew, recommended, itemStatus, name, description, itemImages, OrderType.ONSITE, combinable);
    }
}
