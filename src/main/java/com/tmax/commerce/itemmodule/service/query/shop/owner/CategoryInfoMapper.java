package com.tmax.commerce.itemmodule.service.query.shop.owner;

import com.tmax.commerce.itemmodule.entity.category.Category;
import com.tmax.commerce.itemmodule.entity.item.ItemGroup;
import com.tmax.commerce.itemmodule.service.query.shop.owner.info.CategoryInfo;
import com.tmax.commerce.itemmodule.service.query.shop.owner.info.CategoryItemInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryInfoMapper {
    CategoryInfoMapper INSTANCE = Mappers.getMapper(CategoryInfoMapper.class);

    @Mapping(target = "categoryId", source = "id")
    @Mapping(target = "itemCount", expression = "java(Category.getItems() != null ? Category.getItems().size() : 0)")
    CategoryInfo of(Category Category);

    @Mapping(target = "categoryId", source = "id")
    @Mapping(target = "itemCount", expression = "java(Category.getItems().size())")
    CategoryItemInfo itemOf(Category Category);

    @Mapping(target = "itemGroupId", source = "id")
    @Mapping(target = "description", expression = "java(itemGroup.getItems().get(0).getDescription())")
    @Mapping(target = "sequence", expression = "java(itemGroup.getItems().get(0).getSequence())")
    CategoryItemInfo.SimpleItemInfo itemOf(ItemGroup itemGroup);

}
