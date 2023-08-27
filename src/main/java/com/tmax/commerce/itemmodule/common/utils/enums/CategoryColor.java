package com.tmax.commerce.itemmodule.common.utils.enums;


import lombok.Getter;

@Getter
public enum CategoryColor {
    NAVY("#001D85"),
    PURPLE("#5812A1"),
    BROWN("#54393C"),
    RED("#FF0000"),
    YELLOW("#FAE312"),
    PINK("#FFA7FB"),
    BLUE("#006AC0"),
    GREEN("#00986E"),
    GRAY("#8B8B8B"),
    MINT("#28DCB9"),
    WHITE("#FFFFFF"),
    ORANGE("#FA7002"),
    NONE("NONE");

    private final String colorCode;

    CategoryColor(String colorCode){
        this.colorCode = colorCode;
    }
}
