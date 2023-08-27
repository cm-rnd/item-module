package com.tmax.commerce.itemmodule.entity.category;

import com.tmax.commerce.itemmodule.common.utils.enums.CategoryColor;
import com.tmax.commerce.itemmodule.entity.base.DateTimeEntity;
import com.tmax.commerce.itemmodule.entity.item.ItemGroup;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@ToString
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@Builder
@Table(name = "Category")
public class Category extends DateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    private UUID shopId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private CategoryColor categoryColor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parentCategory;

    private int sequence;

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Category> subCategories = new ArrayList<>();

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<ItemGroup> items;

//    @OneToMany(mappedBy = "category")
//    @Builder.Default
//    private List<ShoppingColor> shoppingColors = new ArrayList<>();

    public void addSubCategory(Category subCategory) {
        subCategories.add(subCategory);
        subCategory.setParent(this);
    }

    protected void setParent(Category parent) {
        this.parentCategory = parent;
    }

    public boolean removeSubCategory(Category subCategory) {
        boolean removed = subCategories.remove(subCategory);
        if (removed) {
            subCategory.setParent(null);
        }
        return removed;
    }

    public void updateName(String name){
        if(name != null){
            this.name = name;
        }
    }

    public void updateDescription(String description){ this.description = description; }

    public void updateSequence(int sequence) { this.sequence = sequence; }
}
