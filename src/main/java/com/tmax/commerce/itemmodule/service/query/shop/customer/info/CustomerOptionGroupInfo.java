package com.tmax.commerce.itemmodule.service.query.shop.customer.info;

import com.tmax.commerce.itemmodule.entity.item.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CustomerOptionGroupInfo {
    private final Long optionGroupId;
    private final String name;
    private final Boolean required;
    private final int choiceCount;
    private final List<CustomerOptionInfo> options;

    @Getter
    @AllArgsConstructor
    public static class CustomerOptionInfo {
        private final Long optionId;
        private final String name;
        private final int price;
        private final ItemStatus itemStatus;
        private final long version;
        private final int sequence;
    }
}