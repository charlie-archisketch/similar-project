package com.example.demo.domain.project.service

import com.example.demo.domain.project.controller.dto.response.ProjectResponse
import com.example.demo.domain.project.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectServiceV2(
    private val projectRepository: ProjectRepository,
    private val projectService: ProjectService,
) {
	fun getSimilarProjects(projectId: String, style: String?): List<ProjectResponse> {
		val project = projectService.getByIdWithoutFloorplan(projectId)
		val projects = projectRepository.getSimilarProjects(
			addressId = project.fromMap.addressId,
			excludeProjectId = projectId,
			style = style,
		)

		return projects.map {
			ProjectResponse(
				_id = it._id,
				userId = it.userId,
				name = it.name,
				enterpriseId = it.enterpriseId,
				directoryIds = it.directoryIds,
				teamDirectoryIds = it.teamDirectoryIds,
				coverImage = it.coverImage,
				defaultCoverImage = it.defaultCoverImage,
				state = it.state,
				createdAt = it.createdAt,
				updatedAt = it.updatedAt,
			)
		}
	}
}
