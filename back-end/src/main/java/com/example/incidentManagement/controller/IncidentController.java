package com.example.incidentManagement.controller;

import com.example.incidentManagement.common.Result;
import com.example.incidentManagement.entity.IncidentEntity;
import com.example.incidentManagement.service.IncidentService;
import com.example.incidentManagement.validation.CreateGroup;
import com.example.incidentManagement.validation.DeleteGroup;
import com.example.incidentManagement.validation.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/incidents")
@CrossOrigin(origins = "http://localhost:8081")
public class IncidentController {
    @Autowired
    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    // Create an incident
    @PostMapping("/createIncident")
    public Result<IncidentEntity> createIncident(@Validated(CreateGroup.class) @RequestBody IncidentEntity incident) {
        return Result.success(incidentService.createIncident(incident));
    }

    // Retrieve all incidents
    @PostMapping("/queryAllIncidents")
    public Result<Page<IncidentEntity>> queryAllIncidents(Pageable pageable) {
        return Result.success(incidentService.queryAllIncidents(pageable));
    }

    // Modify an incident
    @PostMapping("/modifyIncident")
    public Result<IncidentEntity> modifyIncident(@Validated(UpdateGroup.class) @RequestBody IncidentEntity incident) {
        return Result.success(incidentService.modifyIncident(incident.getId(), incident));
    }

    // Delete an incident
    @PostMapping("/deleteIncident")
    public Result<Void> deleteIncident(@Validated(DeleteGroup.class) @RequestBody List<IncidentEntity> incident) {
        incidentService.deleteIncident(incident.stream().map(IncidentEntity::getId).collect(Collectors.toList()));
        return Result.success();
    }
}
