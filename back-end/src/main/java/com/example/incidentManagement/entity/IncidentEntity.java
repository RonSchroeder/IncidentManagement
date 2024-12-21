package com.example.incidentManagement.entity;


import com.example.incidentManagement.validation.CreateGroup;
import com.example.incidentManagement.validation.DeleteGroup;
import com.example.incidentManagement.validation.UpdateGroup;
import com.example.incidentManagement.validation.ValidStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("title", title)
                .add("description", description)
                .add("status", status)
                .toString();
    }
}
