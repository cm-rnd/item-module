package com.tmax.commerce.itemmodule.service.query.shop.owner;

import com.tmax.commerce.itemmodule.common.exception.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class OptionGroupQuery {

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class RetrieveOptionGroupsQuery extends SelfValidating<RetrieveOptionGroupsQuery> {

        @NotNull
        private final UUID shopId;

        @NotNull
        private final Pageable pageable;

        @Builder
        public RetrieveOptionGroupsQuery(UUID shopId, Pageable pageable) {
            this.shopId = shopId;
            this.pageable = pageable;
            validateSelf();
        }
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class RetrieveOptionGroupQuery extends SelfValidating<RetrieveOptionGroupQuery> {

        @NotNull
        private final Long optionGroupId;

        @Builder
        public RetrieveOptionGroupQuery(Long optionGroupId) {
            this.optionGroupId = optionGroupId;
            validateSelf();
        }
    }
}