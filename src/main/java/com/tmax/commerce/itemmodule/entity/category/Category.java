package com.tmax.commerce.itemmodule.entity.category;

import jakarta.persistence.*;
import com.tmax.commerce.itemmodule.common.utils.enums.CategoryColor;
import com.tmax.commerce.itemmodule.entity.base.DateTimeEntity;
import com.tmax.commerce.itemmodule.entity.item.ItemGroup;
import lombok.*;

import java.util.UUID;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@ToString
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@Table(name = "category")
@Builder
public class Category extends DateTimeEntity {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryColor categoryColor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Category> subCategories = new ArrayList<>();

    @OneToMany(mappedBy = "category")
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

    public void updateName(String name) {
        if (name != null) {
            this.name = name;
        }
    }
}
