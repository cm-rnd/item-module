package com.tmax.commerce.itemmodule.service.query.shop.owner.info;

import com.tmax.commerce.itemmodule.entity.item.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OptionGroupInfo {
    private final Long optionGroupId;
    private final String name;
    private final Boolean required;
    private final int choiceCount;
    private final List<OptionInfo> options;
    private final List<SimpleItemInfo> items;

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
