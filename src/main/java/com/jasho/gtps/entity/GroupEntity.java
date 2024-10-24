package com.jasho.gtps.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jashobanta Patra
 * crated on 13-08-2024
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_groups")
public class GroupEntity extends BaseEntity {
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "user_id")
    private String userId;
}
