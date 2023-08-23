package com.tmax.commerce.itemmodule.common.utils.comparator;

import com.tmax.commerce.itemmodule.service.dto.category.CategoryDto;

import java.util.Comparator;

public class GetAllCategoryComp implements Comparator<CategoryDto> {
    private final String NEW_CATEGORY_NAME = "새 카테고리";
    @Override
    public int compare(CategoryDto c1, CategoryDto c2) {
        // 나중에 추가한 카테고리가 상위에 오도록
        String c1Name = c1.getName();
        String c2Name = c2.getName();
        if(c1Name.startsWith(NEW_CATEGORY_NAME) && c2Name.startsWith(NEW_CATEGORY_NAME)){
            return c1.getCreatedAt().isAfter(c2.getCreatedAt()) ? -1 : 1;
        }
        if(c1Name.startsWith(NEW_CATEGORY_NAME) && !c2Name.startsWith(NEW_CATEGORY_NAME)){
            return -1;
        }
        if(!c1Name.startsWith(NEW_CATEGORY_NAME) && c2Name.startsWith(NEW_CATEGORY_NAME)){
            return 1;
        }
        return (int)(c1.getId() - c2.getId());
    }
}
