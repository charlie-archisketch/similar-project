package com.example.demo.domain.project.service

import com.example.demo.domain.project.controller.dto.response.FloorResponse
import com.example.demo.domain.project.controller.dto.response.FloorResponse.Companion.toResponse
import com.example.demo.domain.project.controller.dto.response.ProjectResponse
import com.example.demo.domain.project.domain.FloorStructure
import com.example.demo.domain.project.domain.Project
import com.example.demo.domain.project.domain.child.structure.BoundingBox
import com.example.demo.domain.project.repository.FloorStructureRepository
import com.example.demo.domain.project.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val projectDomainService: ProjectDomainService,
    private val projectRepository: ProjectRepository,
    private val floorStructureRepository: FloorStructureRepository,
) {
    fun getByIdWithoutFloorplan(id: String): Project {
        return projectDomainService.getWithoutFloorplan(id)
            .orElseThrow{ NoSuchElementException() }
    }

	fun getSimilarProjectsByAddress(projectId: String, style: String?): List<ProjectResponse> {
		val project = getByIdWithoutFloorplan(projectId)
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

    fun getSimilarProjects(floorId: String): List<FloorResponse> {
        val floorStructure = floorStructureRepository.findById(floorId)
            .orElseThrow { NoSuchElementException() }

        val top10FloorStructure = floorStructureRepository.findTopKSimilarFloors(
            excludeProjectId = floorStructure.projectId,
            area = floorStructure.area,
            aspectRI = floorStructure.boundingBox.aspectRI,
            k = 10,
        )

        val projects = projectDomainService.getAllWithoutFloorplan(top10FloorStructure.map { it.projectId })
        val projectMap = projects.associateBy { it._id }

        return top10FloorStructure.mapNotNull {
            val project = projectMap[it.projectId] ?: return@mapNotNull null
            toResponse(
                project = project,
                floorId = it.id,
                floorName = it.title
            )
        }
    }

    fun createFloorplanStructure(projectId: String) {
        val floorplans = projectDomainService.get(projectId).floorplans

        val floorStructures = floorplans.map {
            FloorStructure(
                id = it.id,
                title = it.title,
                projectId = projectId,
                area = it.area,
                boundingBox = BoundingBox.fromFloorplan(it)
            )
        }

        floorStructureRepository.saveAll(floorStructures)
    }
}
