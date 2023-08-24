package com.tmax.commerce.itemmodule.service.query.shop.owner;
import com.tmax.commerce.itemmodule.entity.item.ItemGroup;
import com.tmax.commerce.itemmodule.entity.option.ItemOptionGroupRelation;
import com.tmax.commerce.itemmodule.entity.option.OptionGroup;
import com.tmax.commerce.itemmodule.repository.option.ItemOptionGroupRelationRepository;
import com.tmax.commerce.itemmodule.repository.option.OptionGroupRepository;
import com.tmax.commerce.itemmodule.service.query.shop.owner.info.OptionGroupInfo;
import com.tmax.commerce.itemmodule.service.query.shop.owner.info.OptionGroupSimpleInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OptionGroupQueryService {
    private final OptionGroupRepository optionGroupRepository;
    private final ItemOptionGroupRelationRepository itemOptionGroupRelationRepository;

    public Page<OptionGroupInfo> retrieveOptionGroups(OptionGroupQuery.RetrieveOptionGroupsQuery query) {
        UUID shopId = query.getShopId();
        Pageable pageable = query.getPageable();

        return findOptionGroupById(shopId, pageable)
                .map(optionGroup -> {
                    List<ItemGroup> items = findItemsByOptionGroupId(optionGroup.getId());
                    return OptionGroupInfoMapper.INSTANCE.of(optionGroup, items);
                });
    }

    private Page<OptionGroup> findOptionGroupById(UUID shopId, Pageable pageable) {
        return optionGroupRepository.findAllByShopId(shopId, pageable);
    }

    private List<ItemGroup> findItemsByOptionGroupId(Long optionGroupId) {
        return itemOptionGroupRelationRepository
                .findAllByOptionGroupId(optionGroupId)
                .stream()
                .map(ItemOptionGroupRelation::getItemGroup)
                .collect(Collectors.toList());
    }
    public OptionGroupInfo retrieveOptionGroup(OptionGroupQuery.RetrieveOptionGroupQuery query) {
        Long optionGroupId = query.getOptionGroupId();
        OptionGroup optionGroup = optionGroupRepository.findByIdOrElseThrow(optionGroupId);

        List<ItemGroup> items = findItemsByOptionGroupId(optionGroupId);
        return OptionGroupInfoMapper.INSTANCE.of(optionGroup, items);
    }

    public List<OptionGroupSimpleInfo> retrieveOptionGroupsByShopId(UUID shopId) {
        return optionGroupRepository.findByShopId(shopId)
                .stream()
                .map(OptionGroupInfoMapper.INSTANCE::simpleOf)
                .collect(Collectors.toList());
    }
}
