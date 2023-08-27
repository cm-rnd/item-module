package com.tmax.commerce.itemmodule.service.query.shop.customer;
import com.tmax.commerce.itemmodule.common.exception.SelfValidating;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

public class CustomerOptionGroupQuery {

    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class RetrieveOptionGroupByItemGroupQuery extends SelfValidating<RetrieveOptionGroupByItemGroupQuery>{

        @NotNull
        private final Long itemGroupId;

        @Builder
        public RetrieveOptionGroupByItemGroupQuery(Long itemGroupId){
            this.itemGroupId = itemGroupId;
            validateSelf();
        }
    }
}

