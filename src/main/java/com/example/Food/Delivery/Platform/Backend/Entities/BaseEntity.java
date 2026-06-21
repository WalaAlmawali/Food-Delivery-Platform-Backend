package com.example.Food.Delivery.Platform.Backend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @Builder.Default
    private Boolean isActive = true;

    @PrePersist
    public void onCreate() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
