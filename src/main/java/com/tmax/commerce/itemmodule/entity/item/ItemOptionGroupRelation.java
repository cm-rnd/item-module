package com.tmax.commerce.itemmodule.entity.item;

import com.tmax.commerce.itemmodule.entity.base.BaseEntity;
import com.tmax.commerce.itemmodule.entity.option.OptionGroup;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ItemOptionGroupRelation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "option_group_id")
    private OptionGroup optionGroup;
}
