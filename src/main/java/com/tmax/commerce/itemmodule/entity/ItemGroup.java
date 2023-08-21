package com.tmax.commerce.itemmodule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "item_group",
        uniqueConstraints = {
                @UniqueConstraint(name = "ITEM_NAME_UNIQUE", columnNames = {"item_category_id", "name"})
        })
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class ItemGroup extends PersistableDateTimeEntity {

    @Id
    @Builder.Default
    @EqualsAndHashCode.Include
    @Column(name = "item_group_id", columnDefinition = "BINARY(16)")
    private UUID id = UUID.randomUUID();

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;

    @Column(name = "sequences")
    private int sequence;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "itemGroup", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<OptionGroupRelation> itemOptionGroupRelations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "itemGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    private long version;

    public long getVersion() {
        return version;
    }


    public Item getitemByOrderType(OrderType orderType) {
        return items.stream().filter(item -> item.getOrderType() == orderType).findFirst().orElse(null);
    }

    public Category getItemCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

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

    @Override
    public UUID getId() {
        return id;
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
        //this.sequence = sequence;
        items.forEach(item -> item.changeSequence(sequence));
    }

    public void changeBrandNew(boolean brandNew) {
        items.forEach(item -> item.changeBrandNew(brandNew));
    }

    public void changeRecommended(boolean recommended) {
        items.forEach(item -> item.changeRecommended(recommended));
    }

    public void changeitemImages(List<File> files) {
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

    public void changeitemStatus(ItemStatus itemStatus) {
        for (Item item : items) {
            item.changeItemStatus(itemStatus);
        }
        updateVersion();
    }

    public void changeitemStatusByOrderType(OrderType orderType, ItemStatus itemStatus) {
        for (Item item : items) {
            if (item.getOrderType() == orderType)
                item.changeItemStatus(itemStatus);
        }
        updateVersion();
    }


    public void additemOptionGroupRelation(OptionGroupRelation optionGroupRelation) {
        this.itemOptionGroupRelations.add(optionGroupRelation);
    }

    public void removeitemOptionGroupRelation(OptionGroupRelation optionGroupRelation) {
        this.itemOptionGroupRelations.remove(optionGroupRelation);
        updateVersion();
    }


    protected void updateVersion() {
        version++;
        items.forEach(item -> item.updateVersion());
    }

    public void wasInitialized() {
        this.version = 0;
        items.forEach(item -> item.wasInitialized());
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
