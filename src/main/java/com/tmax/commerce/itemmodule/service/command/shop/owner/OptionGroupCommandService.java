package com.tmax.commerce.itemmodule.service.command.shop.owner;

import com.tmax.commerce.itemmodule.common.exception.BusinessErrorCode;
import com.tmax.commerce.itemmodule.common.exception.BusinessException;
import com.tmax.commerce.itemmodule.entity.item.ItemGroup;
import com.tmax.commerce.itemmodule.entity.option.ItemOptionGroupRelation;
import com.tmax.commerce.itemmodule.entity.option.OptionGroup;
import com.tmax.commerce.itemmodule.repository.item.ItemGroupRepository;
import com.tmax.commerce.itemmodule.repository.option.OptionGroupRepository;
import com.tmax.commerce.itemmodule.repository.option.ItemOptionGroupRelationRepository;
import com.tmax.commerce.itemmodule.service.external.ShopValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OptionGroupCommandService {
    private final ItemOptionGroupRelationRepository itemOptionGroupRelationRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final ItemGroupRepository itemGroupRepository;

    public Long registerOptionGroup(OptionGroupCommand.RegisterOptionGroupCommand command) {
        UUID shopId = command.getShopId();
        // shopValidateService.isShopIdValid(shopId); TODO: shop 유효성 검증 필요

        OptionGroup optionGroup = OptionGroup.builder()
                .shopId(shopId)
                .name(command.getName())
                .required(command.getRequired())
                .build();

        return optionGroupRepository.save(optionGroup).getId();
    }

    public void deleteOptionGroup(OptionGroupCommand.DeleteOptionGroupCommand command) {
        Long optionGroupId = command.getOptionGroupId();
        itemOptionGroupRelationRepository.deleteByOptionGroupId(optionGroupId);
        OptionGroup optionGroup = optionGroupRepository.findByIdOrElseThrow(optionGroupId);
        optionGroupRepository.delete(optionGroup);
    }

    public void updateOptionGroup(OptionGroupCommand.UpdateOptionGroupCommand command) {
        Long optionGroupId = command.getOptionGroupId();
        OptionGroup optionGroup = optionGroupRepository.findByIdOrElseThrow(optionGroupId);

        optionGroup.changeName(command.getName());
        optionGroup.changeRequired(command.getRequired());
    }

    public void updateChoiceCount(OptionGroupCommand.UpdateChoiceCountCommand command) {
        Long optionGroupId = command.getOptionGroupId();
        OptionGroup optionGroup = optionGroupRepository.findByIdOrElseThrow(optionGroupId);

        int minCount = optionGroup.getRequired() ? 1 : 0;
        if (minCount > command.getChoiceCount() || command.getChoiceCount() > optionGroup.getOptions().size()){
            throw new BusinessException(BusinessErrorCode.INVALID_PARAMETER_CHOICE_COUNT);
        }

        optionGroup.setMin(command.getChoiceCount());
        optionGroup.setMax(optionGroup.getOptions().size());
    }

    public Long connectOptionGroupToItem(OptionGroupCommand.ConnectOptionGroupToItemCommand command) {
        Long optionGroupId = command.getOptionGroupId();
        OptionGroup optionGroup = optionGroupRepository.findByIdOrElseThrow(optionGroupId);

        List<ItemOptionGroupRelation> relationsToDelete = itemOptionGroupRelationRepository.findAllByOptionGroup(optionGroup);
        itemOptionGroupRelationRepository.deleteAll(relationsToDelete);

        List<Long> itemGroupIds = command.getItemGroupIds();
        if (itemGroupIds != null && !itemGroupIds.isEmpty()) {
            for (Long itemGroupId : itemGroupIds) {
                if (itemGroupId != null) {
                    ItemGroup itemGroup = itemGroupRepository.findByIdOrElseThrow(itemGroupId);

                    ItemOptionGroupRelation itemOptionGroupRelation = ItemOptionGroupRelation.builder()
                            .itemGroup(itemGroup)
                            .optionGroup(optionGroup)
                            .build();

                    itemGroup.addItemOptionGroupRelation(itemOptionGroupRelationRepository.save(itemOptionGroupRelation));
                }
            }
        }
        return optionGroupRepository.save(optionGroup).getId();
    }
}
