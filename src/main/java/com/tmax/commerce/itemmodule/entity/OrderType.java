package com.tmax.commerce.itemmodule.entity;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderType {
    SHIPPING("배송"),
    DELIVERY("배달"),
    PICKUP("포장"),
    RESERVATION("예약"),
    ONSITE("현장");

    private final String description;
}
