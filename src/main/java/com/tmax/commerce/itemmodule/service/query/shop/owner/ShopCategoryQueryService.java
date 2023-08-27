package com.tmax.commerce.itemmodule.service.query.shop.owner;

import com.tmax.commerce.itemmodule.repository.CategoryRepository;
import com.tmax.commerce.itemmodule.service.query.shop.owner.info.CategoryInfo;
import com.tmax.commerce.itemmodule.service.query.shop.owner.info.CategoryItemInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopCategoryQueryService {

    private final CategoryRepository categoryRepository;

    public Slice<CategoryInfo> retrieveShopCategories(ShopCategoryQuery.RetrieveCategoriesQuery retrieveCategoriesQuery) {
        return categoryRepository
                .findByShopId(retrieveCategoriesQuery.getShopId(), retrieveCategoriesQuery.getPageable())
                .map(CategoryInfoMapper.INSTANCE::of);
    }

    public Slice<CategoryItemInfo> retrieveShopCategoryAndItem(ShopCategoryQuery.RetrieveCategoriesQuery retrieveCategoriesQuery) {
        return categoryRepository.findByShopId(retrieveCategoriesQuery.getShopId(), retrieveCategoriesQuery.getPageable())
                .map(CategoryInfoMapper.INSTANCE::itemOf);
    }
}
