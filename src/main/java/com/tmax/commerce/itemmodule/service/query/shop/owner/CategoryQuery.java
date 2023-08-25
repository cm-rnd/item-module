package com.tmax.commerce.itemmodule.service.query.shop.owner;

import com.tmax.commerce.itemmodule.common.exception.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class CategoryQuery {
    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class RetrieveCategoriesQuery extends SelfValidating<RetrieveCategoriesQuery> {

        @NotNull
        private UUID shopId;

        @NotNull
        private Pageable pageable;

        @Builder
        public RetrieveCategoriesQuery(UUID shopId, Pageable pageable) {
            this.shopId = shopId;
            this.pageable = pageable;
            validateSelf();
        }
    }
}
