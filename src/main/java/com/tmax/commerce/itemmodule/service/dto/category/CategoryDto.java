package com.tmax.commerce.itemmodule.service.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CategoryDto {
    private final Long id;
    private final String name;
    private final Long parentId;
    private final String colorCode;
    private final List<CategoryDto> subCategories;
    @JsonIgnore
    private LocalDateTime createdAt;

    public CategoryDto(Long id, String name, Long parentId, String colorCode, List<CategoryDto> subCategories, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.colorCode = colorCode;
        this.subCategories = subCategories;
        this.createdAt = createdAt;
    }
}
