package com.jasho.gtps.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jashobanta Patra
 * crated on 04-11-2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EventDto {
    private String eventName;
    private String eventDescription;
    private String eventDate;
}
