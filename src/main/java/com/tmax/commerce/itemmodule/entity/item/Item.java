package com.tmax.commerce.itemmodule.entity.item;

import com.tmax.commerce.itemmodule.entity.base.DateTimeEntity;
import com.tmax.commerce.itemmodule.entity.category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@AllArgsConstructor
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@Table(name = "item")
public abstract class Item extends DateTimeEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;

    private String description;

    private String itemCode;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<File> itemImages = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private SuperStoreStatus connectedSuperStore;

    private long version = 0;

    @Column(name = "sequences")
    private int sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_group_id")
    private ItemGroup itemGroup;

    private int price;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    private boolean brandNew;

    private boolean recommended;

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    protected OrderType type;

    public Item(int price, boolean brandNew, boolean recommended, ItemStatus ItemStatus, String name, String description, List<File> ItemImages, SuperStoreStatus connectedSuperStore, int sequence, OrderType orderType) {
        this.name = name;
        this.price = price;
        this.brandNew = brandNew;
        this.itemStatus = ItemStatus;
        this.recommended = recommended;
        this.description = description;
        this.itemImages = ItemImages;
        this.connectedSuperStore = connectedSuperStore;
        this.sequence = sequence;
        this.type = orderType;
    }

    public boolean getAvailabilityByType(int i) {
        boolean result = false;
        switch (i) {
            case 0:
                result = itemGroup.getAvailabilityByType(OrderType.PICKUP);
                break;
            case 1:
                result = itemGroup.getAvailabilityByType(OrderType.ONSITE);
                break;
            case 2:
                result = itemGroup.getAvailabilityByType(OrderType.RESERVATION);
                break;
        }
        return result;
    }

    public void setItemGroup(ItemGroup ItemGroup) {
        this.itemGroup = ItemGroup;
    }

    public Category getItemCategory() {
        return itemGroup.getItemCategory();
    }

    public Object getOrderType() {
        return type;
    }

    public List<File> getImages() {
        return itemImages;
    }


    public void changeItemStatus(ItemStatus ItemStatus) {
        if (this.itemStatus.equals(ItemStatus))
            return;
        this.itemStatus = ItemStatus;
        updateVersion();
    }

    public void changePrice(int price) {
        if (this.price == price)
            return;
        this.price = price;
        updateVersion();
    }

    protected void updateVersion() {
        version++;
    }

    public void changeBrandNew(boolean brandNew) {
        this.brandNew = brandNew;
    }

    public void changeRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public boolean isLatest(long version) {
        return this.version == version;
    }

    public void changeItemImages(List<File> files) {
        this.itemImages = files;
        updateVersion();
    }


    public void wasInitialized() {
        this.version = 0;
    }


    public enum SuperStoreStatus {
        BEFORE, INPROGRESS, COMPLETED
    }

    public void changeSequence(int sequence) {
        this.sequence = sequence;
    }

    public void changeName(String name) {
        if (this.name != null & this.name.equals(name))
            return;

        this.name = name;
        updateVersion();
    }

    public void changeDescription(String description) {
        if (this.description != null && this.description.equals(description))
            return;

        this.description = description;
        updateVersion();
    }

    public void changeConnectedSuperStore(SuperStoreStatus connectedSuperStore) {
        this.connectedSuperStore = connectedSuperStore;
    }
}
