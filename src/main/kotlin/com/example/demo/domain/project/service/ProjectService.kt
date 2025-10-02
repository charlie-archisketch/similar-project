package com.example.demo.domain.project.service;

import com.example.demo.domain.project.domain.Project;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProjectService {
    private final ProjectDomainService projectDomainService;

    public ProjectService(ProjectDomainService projectDomainService) {
        this.projectDomainService = projectDomainService;
    }

    public Project getByIdWithoutFloorplan(String id) throws NoSuchElementException {
        return projectDomainService.getWithoutFloorplan(id).orElseThrow(NoSuchElementException::new);
    }
}
