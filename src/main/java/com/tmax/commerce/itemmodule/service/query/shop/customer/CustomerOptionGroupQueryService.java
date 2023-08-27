package com.tmax.commerce.itemmodule.service.query.shop.customer;

import com.tmax.commerce.itemmodule.entity.option.ItemOptionGroupRelation;
import com.tmax.commerce.itemmodule.repository.option.ItemOptionGroupRelationRepository;
import com.tmax.commerce.itemmodule.service.query.shop.customer.info.CustomerOptionGroupInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerOptionGroupQueryService {
    private final ItemOptionGroupRelationRepository itemOptionGroupRelationRepository;

    public List<CustomerOptionGroupInfo> retrieveOptionGroupsByItemGroupId(Long itemGroupId) {
        List<ItemOptionGroupRelation> relations = itemOptionGroupRelationRepository.findAllByItemGroupId(itemGroupId);

        return relations.stream()
                .map(relation -> CustomerOptionGroupInfoMapper.INSTANCE.of(relation.getOptionGroup()))
                .collect(Collectors.toList());
    }
}
