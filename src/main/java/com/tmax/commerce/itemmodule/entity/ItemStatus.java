package com.tmax.commerce.itemmodule.entity;

public enum ItemStatus {
    TODAY_SOLD_OUT("오늘 품절"),

    ON_SALE("판매중"),

    CONTINUE_SOLD_OUT("계속 품절"),

    UNUSED("사용안함");

    private final String itemStatus;

    ItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

}
