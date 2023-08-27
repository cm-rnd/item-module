package com.tmax.commerce.itemmodule.service.query.shop.owner.info;

import com.tmax.commerce.itemmodule.entity.item.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OptionGroupInfo {
    private final Long optionGroupId;
    private final String name;
    private final Boolean required;
    private final int choiceCount;
    private final List<OptionInfo> options;
    private final List<SimpleItemInfo> items;

    public OptionGroupInfo(Long optionGroupId, String name, Boolean required, int choiceCount,
                           List<OptionInfo> options, List<SimpleItemInfo> items) {
        this.optionGroupId = optionGroupId;
        this.name = name;
        this.required = required;
        this.choiceCount = choiceCount;
        this.options = options != null ? options : new ArrayList<>();
        this.items = items != null ? items : new ArrayList<>();
    }

    @Getter
    @AllArgsConstructor
    public static class OptionInfo {
        private final Long optionId;
        private final String name;
        private final int price;
        private final ItemStatus itemStatus;
        private final long version;
        private final int sequence;
    }

    @Getter
    @AllArgsConstructor
    public static class SimpleItemInfo {
        private final Long itemGroupId;
        private final String name;
    }
}
