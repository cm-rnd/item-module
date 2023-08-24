package com.tmax.commerce.itemmodule.repository;

import com.tmax.commerce.itemmodule.entity.option.OptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long> {
    Optional<OptionGroup> findByUuid(UUID uuid);
}
