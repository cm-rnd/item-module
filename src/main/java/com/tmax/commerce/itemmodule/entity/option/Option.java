package com.tmax.commerce.itemmodule.entity.option;

import com.tmax.commerce.itemmodule.entity.base.PersistableDateTimeEntity;
import com.tmax.commerce.itemmodule.entity.item.ItemStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "`option`")
public class Option extends PersistableDateTimeEntity {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private Long originId;

    private String name;

    private Long price;

    private int sequence;

    @ManyToOne
    @JoinColumn(name = "option_group_id")
    private OptionGroup optionGroup;

    private Long versionNumber;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;
}
