package com.example.incidentManagement.entity;


import com.example.incidentManagement.validation.CreateGroup;
import com.example.incidentManagement.validation.DeleteGroup;
import com.example.incidentManagement.validation.UpdateGroup;
import com.example.incidentManagement.validation.ValidStatus;
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidents", indexes = {
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_title", columnList = "title")
})
public class IncidentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "ID must not be null", groups = {UpdateGroup.class, DeleteGroup.class})
    private Long id;

    @Column(nullable = false, length = 100)
    @NotEmpty(message = "Title must not be empty", groups = {CreateGroup.class, UpdateGroup.class})
    @Size(max = 100, message = "Title must not exceed 100 characters", groups = {CreateGroup.class, UpdateGroup.class})
    private String title;

    @Column(length = 500)
    @Size(max = 500, message = "Description must not exceed 500 characters", groups = {CreateGroup.class, UpdateGroup.class})
    private String description;

    @Column(nullable = false, length = 20)
    @NotNull(message = "Status must not be null", groups = {CreateGroup.class, UpdateGroup.class})
    @ValidStatus(groups = {CreateGroup.class, UpdateGroup.class})
    private String status;

    @Column(name = "created_by", length = 50, nullable = false)
    private String createdBy;

    // 创建时间，自动设置当前时间
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_by", length = 50, nullable = true)
    private String updatedBy;

    // 更新时间，自动更新为当前时间
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("title", title)
                .add("description", description)
                .add("status", status)
                .add("createdBy", createdBy)
                .add("createdAt", createdAt)
                .add("updatedBy", updatedBy)
                .add("updatedAt", updatedAt)
                .toString();
    }
}
