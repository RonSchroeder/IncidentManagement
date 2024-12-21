package com.example.incidentManagement.service;

import com.example.incidentManagement.entity.IncidentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IncidentService {
    IncidentEntity createIncident(IncidentEntity incident);

    Page<IncidentEntity> queryAllIncidents(Pageable pageable);

    IncidentEntity modifyIncident(Long id, IncidentEntity incident);

    void deleteIncident(List<Long> id);
}
