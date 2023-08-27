package com.tmax.commerce.itemmodule.service.query.shop.customer;

import com.tmax.commerce.itemmodule.entity.option.Option;
import com.tmax.commerce.itemmodule.entity.option.OptionGroup;
import com.tmax.commerce.itemmodule.service.query.shop.customer.info.CustomerOptionGroupInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerOptionGroupInfoMapper {
    CustomerOptionGroupInfoMapper INSTANCE = Mappers.getMapper(CustomerOptionGroupInfoMapper.class);

    @Mapping(target = "optionGroupId", source = "optionGroup.id")
    @Mapping(target = "options", source = "optionGroup.options")
    @Mapping(target = "choiceCount", source = "optionGroup.max")
    CustomerOptionGroupInfo of(OptionGroup optionGroup);

    @Mapping(target = "optionId", source = "option.id")
    CustomerOptionGroupInfo.CustomerOptionInfo of(Option option);
}