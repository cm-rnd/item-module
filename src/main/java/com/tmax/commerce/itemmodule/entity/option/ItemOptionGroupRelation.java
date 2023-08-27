package com.tmax.commerce.itemmodule.entity.option;

import com.tmax.commerce.itemmodule.entity.base.DateTimeEntity;
import com.tmax.commerce.itemmodule.entity.item.ItemGroup;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Getter
@Entity
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemOptionGroupRelation extends DateTimeEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_group_id")
    private ItemGroup itemGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private OptionGroup optionGroup;

    @Builder
    public ItemOptionGroupRelation(ItemGroup itemGroup, OptionGroup optionGroup){
        this.uuid = UUID.randomUUID();
        this.itemGroup = itemGroup;
        this.optionGroup = optionGroup;
    }

    public void setItemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

    public void setOptionGroup(OptionGroup optionGroup) {
        this.optionGroup = optionGroup;
    }

    public ItemOptionGroupRelation(UUID uuid) {
        this.uuid = uuid;
    }
}
