package com.example.incidentManagement.service;

import com.example.incidentManagement.entity.IncidentEntity;

import java.util.List;

public interface IncidentService {
    IncidentEntity createIncident(IncidentEntity incident);

    List<IncidentEntity> queryAllIncidents();

    IncidentEntity modifyIncident(Long id, IncidentEntity incident);

    void deleteIncident(List<Long> id);
}
