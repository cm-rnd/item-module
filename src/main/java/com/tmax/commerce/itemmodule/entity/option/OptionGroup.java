package com.tmax.commerce.itemmodule.entity.option;

import com.tmax.commerce.itemmodule.entity.base.DateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OptionGroup extends DateTimeEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private UUID shopId;

    private int min;

    private int max;

    private String name;

    private Boolean required = true;

    private Long version;
}
