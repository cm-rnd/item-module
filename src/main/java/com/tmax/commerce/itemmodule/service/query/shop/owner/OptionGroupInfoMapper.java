package com.tmax.commerce.itemmodule.service.query.shop.owner;

import com.tmax.commerce.itemmodule.entity.item.ItemGroup;
import com.tmax.commerce.itemmodule.entity.option.Option;
import com.tmax.commerce.itemmodule.entity.option.OptionGroup;
import com.tmax.commerce.itemmodule.service.query.shop.owner.info.OptionGroupInfo;
import com.tmax.commerce.itemmodule.service.query.shop.owner.info.OptionGroupSimpleInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface OptionGroupInfoMapper {
    OptionGroupInfoMapper INSTANCE = Mappers.getMapper(OptionGroupInfoMapper.class);

    @Mapping(target = "optionGroupId", source = "optionGroup.id")
    @Mapping(target = "options", source = "optionGroup.options")
    @Mapping(target = "choiceCount", source = "optionGroup.max")
    OptionGroupInfo of(OptionGroup optionGroup, List<ItemGroup> itemsGroup);

    @Mapping(target = "optionId", source = "option.id")
    OptionGroupInfo.OptionInfo of(Option option);

    @Mapping(target = "itemGroupId", source = "id")
    OptionGroupInfo.SimpleItemInfo of(ItemGroup itemGroup);

    @Mapping(target = "optionGroupId", source = "optionGroup.id")
    @Mapping(target = "options", source = "optionGroup.options")
    @Mapping(target = "optionCount", expression = "java(optionGroup.getOptions().size())")
    OptionGroupSimpleInfo simpleOf(OptionGroup optionGroup);

    @Mapping(target = "optionId", source = "option.id")
    OptionGroupSimpleInfo.OptionInfo simpleOf(Option option);
}
