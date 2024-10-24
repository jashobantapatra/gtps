package com.jasho.gtps.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Jashobanta Patra
 * crated on 09-08-2024
 */
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE", nullable = false)
    private LocalDateTime updatedDate;

    @Column(name = "created_by")
    private long createdBy;

    @Column(name = "updated_by")
    private long updatedBy;

    @PrePersist
    protected void setCreateDate() {
        updatedDate = createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void setUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
