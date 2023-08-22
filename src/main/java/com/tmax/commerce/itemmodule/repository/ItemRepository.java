package com.tmax.commerce.itemmodule.repository;

import com.tmax.commerce.itemmodule.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends ExtendedRepository<Item, UUID> {
}
