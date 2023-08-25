package com.tmax.commerce.itemmodule.entity.option;

import com.tmax.commerce.itemmodule.entity.base.DateTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OptionGroup extends DateTimeEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    private UUID shopId;

    private String name;

    private Boolean required = true;

    private int min;

    private int max;

    private long version;

    @OrderBy("sequence asc")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "optionGroup")
    private List<Option> options =new ArrayList<>();

    @Builder
    public OptionGroup(UUID shopId, String name, Boolean required){
        this.uuid = UUID.randomUUID();
        this.shopId = shopId;
        this.name = name;
        this.required = required;
        this.min = required ? 1 : 0;
        this.max = required ? 1 : 0;
    }

    private void updateVersion() {
        version++;
    }

    public void changeName(String name) {
        if (!this.name.equals(name)) {
            this.name = name;
            updateVersion();
        }
    }

    public void changeRequired(Boolean required) {
        this.required = required;
    }

    public void setMin(int min) { this.min = min; }

    public void setMax(int max) { this.max = max; }

    public void addOption(Option option) {
        this.options.add(option);
    }

    public void removeOption(Option option) {
        this.options.remove(option);
        updateVersion();
    }
}
