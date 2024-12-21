package com.example.incidentManagement.repository;

import com.example.incidentManagement.entity.IncidentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IncidentRepository extends JpaRepository<IncidentEntity, Long> {
}