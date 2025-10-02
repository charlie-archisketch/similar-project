package com.example.demo.domain.project.service

import com.example.demo.domain.project.controller.dto.response.ProjectResponse
import com.example.demo.domain.project.domain.Structure
import com.example.demo.domain.project.domain.Project
import com.example.demo.domain.project.domain.child.floorplan_structure.BoundingBox
import com.example.demo.domain.project.repository.StructureRepository
import com.example.demo.domain.project.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val projectDomainService: ProjectDomainService,
    private val projectRepository: ProjectRepository,
    private val structureRepository: StructureRepository,
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

    fun getSimilarProjects(projectId: String): List<ProjectResponse> {
        val structure = structureRepository.findByProjectId(projectId)
            ?: throw NoSuchElementException()

        val top10FloorplanStructure = structureRepository.findTopKSimilarProjectIds(
            excludeProjectId = projectId,
            area = structure.area,
            floorCount = structure.floorCount,
            aspectRI = structure.boundingBox.aspectRI,
            k = 10,
        )

        val projects = projectDomainService.getAllWithoutFloorplan(top10FloorplanStructure.map {
            println(it.score)
            it.projectId
        })

        val order = top10FloorplanStructure.map { it.projectId }
        val sortedProjects = projects.sortedBy { project -> order.indexOf(project._id) }

        return sortedProjects.map {
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

    fun createFloorplanStructure(projectId: String) {
        val floorplans = projectDomainService.get(projectId).floorplans

        val structure = Structure(
            projectId = projectId,
            area = floorplans[0].area,
            floorCount = floorplans.count(),
            boundingBox = BoundingBox.fromFloorplan(floorplans[0]),
        )

        structureRepository.save(structure)
    }
}
