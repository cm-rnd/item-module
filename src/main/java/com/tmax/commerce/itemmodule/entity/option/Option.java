package com.tmax.commerce.itemmodule.entity.option;

import com.tmax.commerce.itemmodule.entity.base.DateTimeEntity;
import com.tmax.commerce.itemmodule.entity.item.ItemStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "`option`")
public class Option extends DateTimeEntity {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String itemCode; // nullable가능함

    private OptionType type;// TODO: Bang 추후 테스트 코드 작성

    private String name;

    private int price;

    private int sequence;

    @ManyToOne
    @JoinColumn(name = "option_group_id")
    private OptionGroup optionGroup;

    private Long version;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;
}
