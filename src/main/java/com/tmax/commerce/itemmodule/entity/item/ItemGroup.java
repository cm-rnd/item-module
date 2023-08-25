package com.tmax.commerce.itemmodule.entity.item;

import com.tmax.commerce.itemmodule.entity.base.DateTimeEntity;
import com.tmax.commerce.itemmodule.entity.category.Category;
import com.tmax.commerce.itemmodule.entity.option.ItemOptionGroupRelation;
import jakarta.persistence.*;
import lombok.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "item_group",
        uniqueConstraints = {
                @UniqueConstraint(name = "ITEM_NAME_UNIQUE", columnNames = {"category_id", "name"})
        })
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ItemGroup extends DateTimeEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;

    @Column(name = "sequences")
    private int sequence;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "itemGroup", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ItemOptionGroupRelation> itemOptionGroupRelations = new ArrayList<>();

    @OneToMany(mappedBy = "itemGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    private long version;

    public Item getItemByOrderType(OrderType orderType) {
        return items.stream().filter(item -> item.getOrderType() == orderType).findFirst().orElse(null);
    }

    public Category getItemCategory() {
        return category;
    }

    public String getName() { return name; }

    public long getPriceByType(OrderType orderType) {
        for (Item item : items) {
            if (item.getOrderType().equals(orderType)) {
                return item.getPrice();
            }
        }
        return 0;
    }

    public boolean getAvailabilityByType(OrderType orderType) {
        for (Item item : items) {
            if (item.getOrderType() == orderType) {
                return !item.getItemStatus().equals(ItemStatus.UNUSED);
            }
        }
        return false;
    }

    public boolean isLatest(long version) {
        return this.version == version;
    }

    public void changeName(String name) {
        if (this.name.equals(name))
            return;
        for (Item item : items) {
            item.changeName(name);
        }
        this.name = name;
        updateVersion();
    }

    public void changeSequence(int sequence) {
        items.forEach(item -> item.changeSequence(sequence));
    }

    public void changeBrandNew(boolean brandNew) {
        items.forEach(item -> item.changeBrandNew(brandNew));
    }

    public void changeRecommended(boolean recommended) {
        items.forEach(item -> item.changeRecommended(recommended));
    }

    public void changeItemImages(List<File> files) {
        items.forEach(item -> item.changeItemImages(files));
        updateVersion();
    }

    public void changeDescription(String description) {
        items.forEach(item -> item.changeDescription(description));
    }

    public void changePriceByOrderType(OrderType orderType, int price) {
        for (Item item : items) {
            if (item.getOrderType() == orderType) {
                item.changePrice(price);
            }
        }
        updateVersion();
    }

    public void changePrice(int price) {
        for (Item item : items) {
            item.changePrice(price);
        }
        updateVersion();
    }

    public void changeItemStatus(ItemStatus itemStatus) {
        for (Item item : items) {
            item.changeItemStatus(itemStatus);
        }
        updateVersion();
    }

    public void changeItemStatusByOrderType(OrderType orderType, ItemStatus itemStatus) {
        for (Item item : items) {
            if (item.getOrderType() == orderType)
                item.changeItemStatus(itemStatus);
        }
        updateVersion();
    }


    public void addItemOptionGroupRelation(ItemOptionGroupRelation itemoptionGroupRelation) {
        this.itemOptionGroupRelations.add(itemoptionGroupRelation);
    }

    public void removeItemOptionGroupRelation(ItemOptionGroupRelation itemoptionGroupRelation) {
        this.itemOptionGroupRelations.remove(itemoptionGroupRelation);
        updateVersion();
    }


    protected void updateVersion() {
        version++;
        items.forEach(Item::updateVersion);
    }

    public void wasInitialized() {
        this.version = 0;
        items.forEach(Item::wasInitialized);
    }

    @Builder
    public ItemGroup(Category category, String name) {
        this.category = category;
        this.name = name;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        this.items.add(item);
        item.setItemGroup(this);
    }

    public void changeConnectedSuperStore(Item.SuperStoreStatus connectedSuperStore) {
        items.forEach(item -> item.changeConnectedSuperStore(connectedSuperStore));
    }
}
