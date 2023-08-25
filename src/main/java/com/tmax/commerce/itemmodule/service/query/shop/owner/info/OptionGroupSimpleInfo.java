package com.tmax.commerce.itemmodule.service.query.shop.owner.info;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OptionGroupSimpleInfo {
    private final Long optionGroupId;
    private final String name;
    private final Boolean required;
    private final int optionCount;
    private final List<OptionInfo> options;

    @Getter
    @AllArgsConstructor
    public static class OptionInfo {
        private final Long optionId;
        private final String name;
        private final int sequence;
    }
}
