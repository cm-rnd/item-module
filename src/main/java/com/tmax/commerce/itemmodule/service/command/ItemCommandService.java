package com.tmax.commerce.itemmodule.service.command;

import com.tmax.commerce.itemmodule.common.UuidGenerator;
import com.tmax.commerce.itemmodule.common.exception.BusinessErrorCode;
import com.tmax.commerce.itemmodule.common.exception.BusinessException;
import com.tmax.commerce.itemmodule.entity.item.ItemGroup;
import com.tmax.commerce.itemmodule.entity.item.OrderType;
import com.tmax.commerce.itemmodule.entity.option.ItemOptionGroupRelation;
import com.tmax.commerce.itemmodule.repository.CategoryRepository;
import com.tmax.commerce.itemmodule.repository.ItemGroupRepository;
import com.tmax.commerce.itemmodule.repository.OptionGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemCommandService {
    private final CategoryRepository categoryRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final UuidGenerator uuidGenerator;
    private final ItemGroupRepository itemGroupRepository;

    @Transactional
    public void registerItem(ItemCommand.RegisterItemCommand registerItemCommand) {
        ItemGroup itemGroup = createItemGroup(registerItemCommand);
        addItemOptionGroupRelation(itemGroup, registerItemCommand);
        addItem(itemGroup, registerItemCommand);
        itemGroupRepository.save(itemGroup);
    }


    private ItemGroup createItemGroup(ItemCommand.RegisterItemCommand registerItemCommand) {
        ItemGroup itemGroup = ItemGroup.builder()
                .uuid(uuidGenerator.generate())
                .name(registerItemCommand.getName())
                .category(categoryRepository
                        .findByUuid(registerItemCommand.getItemCategoryId())
                        .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_ITEM_CATEGORY)))
                .build();

        return itemGroup;
    }

    private void addItemOptionGroupRelation(ItemGroup itemGroup, ItemCommand.RegisterItemCommand registerItemCommand) {
        List<UUID> optionGroupIds = registerItemCommand.getOptionGroupIds();
        for (UUID optionGroupId : optionGroupIds) {
            ItemOptionGroupRelation itemOptionGroupRelation = new ItemOptionGroupRelation(uuidGenerator.generate());
            itemOptionGroupRelation.setOptionGroup(optionGroupRepository.findByUuid(optionGroupId)
                    .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_OPTION_GROUP)));
            itemGroup.addItemOptionGroupRelation(itemOptionGroupRelation);
        }
    }

    private void addItem(ItemGroup itemGroup, ItemCommand.RegisterItemCommand registerItemCommand) {
        for (OrderType orderType : registerItemCommand.getOrderTypes()) {
            itemGroup.addItem(orderType.createItem(registerItemCommand, uuidGenerator.generate()));
        }
    }
}
