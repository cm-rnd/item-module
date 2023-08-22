package com.tmax.commerce.itemmodule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@ToString
@AllArgsConstructor
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@Table(name = "item")
public abstract class Item extends PersistableDateTimeEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "item_id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;

    private String description;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<File> ItemImages = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private SuperStoreStatus connectedSuperStore;

    private long version = 0;

    @Column(name = "sequences")
    private int sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_group_id")
    private ItemGroup ItemGroup;

    private int price;

    @Enumerated(EnumType.STRING)
    private ItemStatus ItemStatus;

    private boolean brandNew;

    private boolean recommended;

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    protected OrderType type;

    public Item(int price, boolean brandNew, boolean recommended, ItemStatus ItemStatus, String name, String description, List<File> ItemImages, SuperStoreStatus connectedSuperStore, int sequence, OrderType orderType) {
        this.name = name;
        this.price = price;
        this.brandNew = brandNew;
        this.ItemStatus = ItemStatus;
        this.recommended = recommended;
        this.description = description;
        this.ItemImages = ItemImages;
        this.connectedSuperStore = connectedSuperStore;
        this.sequence = sequence;
        this.type = orderType;
    }

    public boolean getAvailabilityByType(int i) {
        boolean result = false;
        switch (i) {
            case 0:
                result = ItemGroup.getAvailabilityByType(OrderType.PICKUP);
                break;
            case 1:
                result = ItemGroup.getAvailabilityByType(OrderType.ONSITE);
                break;
            case 2:
                result = ItemGroup.getAvailabilityByType(OrderType.RESERVATION);
                break;
        }
        return result;
    }

    public void setItemGroup(ItemGroup ItemGroup) {
        this.ItemGroup = ItemGroup;
    }

    public Category getItemCategory() {
        return ItemGroup.getItemCategory();
    }

    public Object getOrderType() {
        return type;
    }

    public List<File> getImages() {
        return ItemImages;
    }


    public void changeItemStatus(ItemStatus ItemStatus) {
        if (this.ItemStatus.equals(ItemStatus))
            return;
        this.ItemStatus = ItemStatus;
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
        this.ItemImages = files;
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
