package com.moiseevTest.skbTest.events;

import com.moiseevTest.skbTest.dto.FormComplianceMessageDto;
import com.moiseevTest.skbTest.model.RegistrationRecordStatus;
import lombok.Getter;

/**
 Событие обновления статуса одобрения формы.
 */
public record FormStatusUpdatedEvent(@Getter FormComplianceMessageDto formComplianceMessageDto, @Getter RegistrationRecordStatus registrationRecordStatus) {
}
