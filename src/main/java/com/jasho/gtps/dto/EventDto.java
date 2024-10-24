package com.jasho.gtps.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jashobanta Patra
 * crated on 24-10-2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EventDto {
    private Long eventId;
    private String eventName;
    private String createdBy;
    private String createdDate;
    private String updatedBy;
    private String updatedDate;
}
