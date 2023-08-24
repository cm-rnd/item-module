package com.tmax.commerce.itemmodule.repository.option;

import com.tmax.commerce.itemmodule.entity.option.ItemOptionGroupRelation;
import com.tmax.commerce.itemmodule.entity.option.OptionGroup;
import com.tmax.commerce.itemmodule.repository.ExtendedRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ItemOptionGroupRelationRepository extends ExtendedRepository<ItemOptionGroupRelation, Long> {

    @EntityGraph(attributePaths = {"itemGroup"})
    List<ItemOptionGroupRelation> findAllByOptionGroupId(Long optionGroupId);

    @Query("SELECT r FROM ItemOptionGroupRelation r WHERE r.optionGroup = :optionGroup")
    List<ItemOptionGroupRelation> findAllByOptionGroup(@Param("optionGroup") OptionGroup optionGroup);

    void deleteByOptionGroupId(Long optionGroupId);

    @EntityGraph(attributePaths = {"itemGroup"})
    List<ItemOptionGroupRelation> findAllByItemGroupId(Long itemGroupId);
}
