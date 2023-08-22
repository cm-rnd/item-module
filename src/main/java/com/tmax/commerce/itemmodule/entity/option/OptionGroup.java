package com.tmax.commerce.itemmodule.entity.option;

import com.tmax.commerce.itemmodule.entity.base.PersistableDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OptionGroup extends PersistableDateTimeEntity {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private int min;

    private int max;

    private String name;

    @Column
    private Boolean required = false;
}
