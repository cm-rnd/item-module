package com.tmax.commerce.itemmodule.service.command.shop.owner;

import com.tmax.commerce.itemmodule.entity.option.Option;
import com.tmax.commerce.itemmodule.entity.option.OptionGroup;
import com.tmax.commerce.itemmodule.repository.option.OptionGroupRepository;
import com.tmax.commerce.itemmodule.repository.option.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class OptionCommandService {
    private final OptionGroupRepository optionGroupRepository;
    private final OptionRepository optionRepository;

    public Long registerOption(OptionCommand.RegisterOptionCommand command) {
        Long optionGroupId = command.getOptionGroupId();
        OptionGroup optionGroup = optionGroupRepository.findByIdOrElseThrow(optionGroupId);

        List<Option> options = optionGroup.getOptions();
        int currentSequence = getCurrentSequence(options);

        Option option = Option.builder()
                .optionGroup(optionGroup)
                .name(command.getName())
                .price(command.getPrice())
                .itemStatus(command.getItemStatus())
                .sequence(currentSequence)
                .build();

        optionGroup.addOption(option);

        return optionGroupRepository.save(optionGroup).getId();
    }

    private int getCurrentSequence(List<Option> options) {
        int sequence = 1;

        if (!options.isEmpty()) {
            options.sort(Comparator.comparing(Option::getSequence).reversed());
            Option option = options.get(0);
            sequence = option.getSequence() + 1;
        }

        return sequence;
    }

    public void updateOption(OptionCommand.UpdateOptionCommand command) {
        Option option = optionRepository.findByIdOrElseThrow(command.getOptionId());

        option.changeName(command.getName());
        option.changePrice(command.getPrice());
        option.changeItemStatus(command.getItemStatus());
    }


    public void updateOptionSequences(OptionCommand.UpdateOptionSequencesCommand command) {
        command.getUpdateOptionSequenceCommands()
                .forEach(updateOptionSequenceCommand -> {
                    Long optionId = updateOptionSequenceCommand.getOptionId();
                    Option option = optionRepository.findByIdOrElseThrow(optionId);
                    option.changeSequence(updateOptionSequenceCommand.getSequence());
                });
    }

    public void deleteOption(OptionCommand.DeleteOptionCommand command) {
        Long optionGroupId = command.getOptionGroupId();
        OptionGroup optionGroup = optionGroupRepository.findByIdOrElseThrow(optionGroupId);

        Long optionId = command.getOptionId();
        Option option = optionRepository.findByIdOrElseThrow(optionId);

        optionGroup.removeOption(option);
    }
}
