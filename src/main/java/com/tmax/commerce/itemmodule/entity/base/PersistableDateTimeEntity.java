package com.tmax.commerce.itemmodule.entity.base;


import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class PersistableDateTimeEntity extends DateTimeEntity implements Persistable<UUID> {

    public boolean isNew() {
        return createdAt == null;
    }

    public abstract UUID getId();

}
