package com.tmax.commerce.itemmodule.entity.option;

import com.tmax.commerce.itemmodule.entity.base.DateTimeEntity;
import com.tmax.commerce.itemmodule.entity.item.ItemStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "`option`")
public class Option extends DateTimeEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    private String itemCode; // nullable가능함

    private OptionType type;// TODO: Bang 추후 테스트 코드 작성

    private String name;

    private int price;

    private int sequence;

    @ManyToOne
    @JoinColumn(name = "option_group_id")
    private OptionGroup optionGroup;

    private long version = 0;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    private void updateVersion() {
        version++;
    }

    @Builder
    public Option(OptionGroup optionGroup, String name, int price, ItemStatus itemStatus, int sequence) {
        this.optionGroup = optionGroup;
        this.name = name;
        this.price = price;
        this.itemStatus = itemStatus;
        this.sequence = sequence;
    }

    public void changeName(String name) {
        if (!this.name.equals(name)) {
            this.name = name;
            updateVersion();
        }
    }

    public void changePrice(int price) {
        if (this.price != price) {
            this.price = price;
            updateVersion();
        }
    }
    public void changeItemStatus(ItemStatus itemStatus) {
        if (!this.itemStatus.equals(itemStatus)) {
            this.itemStatus = itemStatus;
            updateVersion();
        }
    }

    public void changeSequence(int sequence) {
        this.sequence = sequence;
    }

}
