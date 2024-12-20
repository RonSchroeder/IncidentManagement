package com.example.incidentManagement.service.impl;

import com.example.incidentManagement.entity.IncidentEntity;
import com.example.incidentManagement.exception.CustomException;
import com.example.incidentManagement.service.IncidentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class IncidentServiceImpl implements IncidentService {
    private final List<IncidentEntity> incidents = new CopyOnWriteArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public IncidentEntity createIncident(IncidentEntity incident) {
        incident.setId(idCounter.getAndIncrement());
        incidents.add(incident);
        return incident;
    }

    @Override
    public List<IncidentEntity> queryAllIncidents() {
        return incidents;
    }

    @Override
    public IncidentEntity modifyIncident(Long id, IncidentEntity incident) {
        for (IncidentEntity currentIncident : incidents) {
            if (Objects.equals(currentIncident.getId(), id)) {
                BeanUtils.copyProperties(incident, currentIncident, "id");
                return incident;
            }
        }
        throw new CustomException("IncidentEntity not found");
    }

    @Override
    public void deleteIncident(List<Long> ids) {
        for (Long id : ids) {
            if (!incidents.removeIf(incident -> Objects.equals(id, incident.getId()))) {
                throw new CustomException("IncidentEntity not found");
            }
        }
    }
}
