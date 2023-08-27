package com.tmax.commerce.itemmodule.repository.option;

import com.tmax.commerce.itemmodule.entity.option.OptionGroup;
import com.tmax.commerce.itemmodule.repository.ExtendedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OptionGroupRepository extends ExtendedRepository<OptionGroup, Long> {

    Page<OptionGroup> findAllByShopId(UUID shopId, Pageable pageable);

    List<OptionGroup> findByShopId(UUID shopId);

    Optional<OptionGroup> findByUuid(UUID uuid);
}
