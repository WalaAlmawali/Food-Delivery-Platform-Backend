package com.example.Food.Delivery.Platform.Backend.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

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
