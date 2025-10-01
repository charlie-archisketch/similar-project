package com.example.demo.domain.project.service;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.demo.domain.project.repository.ProjectRepository;
import com.example.demo.domain.project.domain.Project;
import com.example.demo.infra.s3.S3FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProjectDomainService {
    private final ProjectRepository projectRepository;
    private final S3FileManager s3FileManager;

    @Value("${aws.cdn.url}")
    private String cdnUrl;


    public Optional<Project> getWithoutFloorplan(String id) {
        Optional<Project> result = projectRepository.findById(id);
        result.ifPresent(this::updateDefaultCoverImage);
        return result;
    }

    public String getLatestDefaultCoverImageUrlInS3(Project project) {
        String projectId = project.get_id();
        String defaultCoverImagePrefix = "projects/" + projectId + "/images";
        List<String> keys = s3FileManager.listObjectsV2(defaultCoverImagePrefix).stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());

        String defaultCoverImageUrl = null;
        if (!keys.isEmpty()) {
            String key = keys.get(0);
            defaultCoverImageUrl = cdnUrl + "/" + key;
        }
        project.setDefaultCoverImage(defaultCoverImageUrl);
        return defaultCoverImageUrl;
    }

    private void updateDefaultCoverImage(Project project) {
        if (project.getDefaultCoverImage() == null) {
            getLatestDefaultCoverImageUrlInS3(project);
            if (project.getDefaultCoverImage() == null) {
                project.setDefaultCoverImage(project.getCoverImage());
            }
            save(project);
        }
    }

    private Project save(Project project) {
        return projectRepository.save(project);
    }
}
