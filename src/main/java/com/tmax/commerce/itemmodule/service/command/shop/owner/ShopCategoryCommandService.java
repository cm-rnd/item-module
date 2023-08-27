package com.tmax.commerce.itemmodule.service.command.shop.owner;


import com.tmax.commerce.itemmodule.common.exception.BusinessException;
import com.tmax.commerce.itemmodule.common.exception.BusinessErrorCode;
import com.tmax.commerce.itemmodule.common.utils.enums.CategoryColor;
import com.tmax.commerce.itemmodule.entity.category.Category;
import com.tmax.commerce.itemmodule.repository.CategoryRepository;
import com.tmax.commerce.itemmodule.service.external.ShopValidateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopCategoryCommandService {
    private final CategoryRepository categoryRepository;
    private final ShopValidateService shopValidateService; // 임의 구현

    public Long registerShopCategory(ShopCategoryCommand.RegisterCategoryCommand registerCategoryCommand) {
        // shopValidateService.isShopIdValid(shopId); TODO: shop 유효성 검증 필요
        validateUniqueShopCategoryName(registerCategoryCommand.getShopId(), registerCategoryCommand.getName());
        Integer maxSequence = categoryRepository.findMaxSequence(registerCategoryCommand.getShopId()).orElse(0);

        Category category = Category.builder()
                .uuid(UUID.randomUUID())
                .shopId(registerCategoryCommand.getShopId())
                .name(registerCategoryCommand.getName())
                .description(registerCategoryCommand.getDescription())
                .categoryColor(CategoryColor.NONE)
                .parentCategory(null)
                .sequence(maxSequence + 1)
                .build();

        return categoryRepository.save(category).getId();
    }

    private void validateUniqueShopCategoryName(UUID shopId, String name) {
        if (categoryRepository.existsByShopAndName(shopId, name)) {
            throw new BusinessException(BusinessErrorCode.DUPLICATE_NAME_SHOP_CATEGORY, List.of(name));
        }
    }

    public void deleteShopCategory(ShopCategoryCommand.DeleteCategoryCommand deleteCategoryCommand) {
        Category category = categoryRepository.findByIdOrElseThrow(deleteCategoryCommand.getCategoryId());
        categoryRepository.delete(category);
    }

    public void updateShopCategory(ShopCategoryCommand.UpdateCategoryCommand updateCategoryCommand) {
        Category category = categoryRepository.findByIdOrElseThrow(updateCategoryCommand.getCategoryId());
        UUID shopId = category.getShopId();

        if (!category.getName().equals(updateCategoryCommand.getName())) {
            validateUniqueShopCategoryName(shopId, updateCategoryCommand.getName());
        }

        category.updateName(updateCategoryCommand.getName());
        category.updateDescription(updateCategoryCommand.getDescription());
    }

    public void updateShopCategorySequence(ShopCategoryCommand.UpdateCategorySequencesCommand updateCategorySequencesCommand) {
        updateCategorySequencesCommand
                .getUpdateCategorySequenceCommands()
                .forEach(updateCategorySequenceCommand -> {
                    Category category = categoryRepository.findByIdOrElseThrow(updateCategorySequenceCommand.getCategoryId());
                    category.updateSequence(updateCategorySequenceCommand.getSequence());
                });
    }
}
