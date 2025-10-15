package com.example.demo.domain.project.service;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.demo.domain.project.domain.child.Floorplan;
import com.example.demo.domain.project.repository.ProjectRepository;
import com.example.demo.domain.project.domain.Project;
import com.example.demo.infra.s3.S3FileManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProjectDomainService {
    private final ProjectRepository projectRepository;
    private final S3FileManager s3FileManager;

    @Value("${aws.cdn.url}")
    private String cdnUrl;

    public Project get(String id) throws Exception {
        Optional<Project> op = projectRepository.findById(id);
        if(op.isEmpty())
            throw new Exception("Project [" + id + "] not found.");
        Project project = op.get();
        String floorplanPath = project.getFloorplanPath();
        String key = "projects/"+ id + "/floorplans.json";
        if (!Strings.isNullOrEmpty(floorplanPath)) {
            if (s3FileManager.isObjExist(key)) {
                String floorplans;
                try (HttpClient client = HttpClient.newHttpClient()) {
                    floorplans = client.send(
                            HttpRequest.newBuilder(URI.create("https://dev-resources.archisketch.com/" + key))
                                    .GET()
                                    .build(),
                            HttpResponse.BodyHandlers.ofString()
                    )
                            .body();
                }

                ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.registerModule(new KotlinModule.Builder().nullIsSameAsDefault(true).build());
                mapper.registerModule(new JavaTimeModule());

                List<Floorplan> floorplanList = mapper.readValue(floorplans, new TypeReference<>() {});
                project.setFloorplans(floorplanList);
            }
        }

        updateDefaultCoverImage(project);
        return project;
    }

    public List<Project> getAll(List<String> ids) throws Exception {
        List<Project> projects = projectRepository.findAllById(ids);

        for (Project project: projects) {
            System.out.println(project.get_id());
            String floorplanPath = project.getFloorplanPath();
            String key = "projects/"+ project.get_id() + "/floorplans.json";
            if (!Strings.isNullOrEmpty(floorplanPath)) {
                if (s3FileManager.isObjExist(key)) {
                    String floorplans;
                    try (HttpClient client = HttpClient.newHttpClient()) {
                        floorplans = client.send(
                                        HttpRequest.newBuilder(URI.create("https://dev-resources.archisketch.com/" + key))
                                                .GET()
                                                .build(),
                                        HttpResponse.BodyHandlers.ofString()
                                )
                                .body();
                    }

                    ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    mapper.registerModule(new KotlinModule.Builder().nullIsSameAsDefault(true).build());
                    mapper.registerModule(new JavaTimeModule());

                    List<Floorplan> floorplanList = mapper.readValue(floorplans, new TypeReference<>() {});
                    project.setFloorplans(floorplanList);
                }
            }

            updateDefaultCoverImage(project);
        }
        return projects;
    }

    public Optional<Project> getWithoutFloorplan(String id) {
        Optional<Project> result = projectRepository.findById(id);
        result.ifPresent(this::updateDefaultCoverImage);
        return result;
    }

    public List<Project> getAllWithoutFloorplan(List<String> ids) {
        List<Project> result = projectRepository.findAllById(ids);
        result.forEach(this::updateDefaultCoverImage);
        return result;
    }

    public String getLatestDefaultCoverImageUrlInS3(Project project) {
        String projectId = project.get_id();
        String defaultCoverImagePrefix = "projects/" + projectId + "/images";
        List<String> keys = s3FileManager.listObjectsV2(defaultCoverImagePrefix).stream()
                .map(S3ObjectSummary::getKey)
                .toList();

        String defaultCoverImageUrl = null;
        if (!keys.isEmpty()) {
            String key = keys.getFirst();
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