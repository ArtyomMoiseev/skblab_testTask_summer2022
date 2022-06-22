package com.moiseevTest.skbTest.events;

import com.moiseevTest.skbTest.dto.FormComplianceMessageDto;
import lombok.Getter;

/**
 Событие создания новой формы.
 */
public record FormCreatedEvent(@Getter FormComplianceMessageDto formComplianceMessageDto) {
}
