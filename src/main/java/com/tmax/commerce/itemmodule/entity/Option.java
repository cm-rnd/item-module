package com.tmax.commerce.itemmodule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "`option`")
public class Option extends BaseEntity {
    @Id
    private UUID id;

    private Long originId;

    private String name;

    private Long price;

    private int sequence;

    @ManyToOne
    @JoinColumn(name = "option_group_id")
    private OptionGroup optionGroup;

    private Long versionNumber;

    //TODO: Status 추가
    //TODO: Type 추가
}
