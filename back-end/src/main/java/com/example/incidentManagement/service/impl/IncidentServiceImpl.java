package com.example.incidentManagement.service.impl;

import com.example.incidentManagement.entity.IncidentEntity;
import com.example.incidentManagement.exception.CustomException;
import com.example.incidentManagement.repository.IncidentRepository;
import com.example.incidentManagement.service.IncidentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentServiceImpl implements IncidentService {
    @Autowired
    private IncidentRepository incidentRepository;

    @Override
    public IncidentEntity createIncident(IncidentEntity incident) {
        return incidentRepository.save(incident);
    }

    @Override
    public Page<IncidentEntity> queryAllIncidents(Pageable pageable) {
        return incidentRepository.findAll(pageable);
    }

    @Override
    public IncidentEntity modifyIncident(Long id, IncidentEntity incident) {
        return incidentRepository.findById(id).map(currentIncident -> {
            BeanUtils.copyProperties(incident, currentIncident, "id");
            return incidentRepository.save(incident);
        }).orElseThrow(() -> new CustomException("Incident not found"));
    }

    @Override
    public void deleteIncident(List<Long> ids) {
        for (Long id : ids) {
            if (!incidentRepository.existsById(id)) {
                throw new CustomException("Incident not found");
            }
            incidentRepository.deleteById(id);
        }
    }
}
