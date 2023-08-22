package com.tmax.commerce.itemmodule.entity.option;

import com.tmax.commerce.itemmodule.entity.base.PersistableDateTimeEntity;
import com.tmax.commerce.itemmodule.entity.item.ItemGroup;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Entity
@ToString
@Builder
@AllArgsConstructor
@Table(name = "option_group_relation")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionGroupRelation extends PersistableDateTimeEntity {

    @Id
    @Builder.Default
    @Column(name = "option_group_relation_id", columnDefinition = "BINARY(16)")
    private UUID id = UUID.randomUUID();

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_group_id")
    private ItemGroup itemGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_option_group_id")
    private OptionGroup productOptionGroup;
}
