package com.example.demo.domain.project.service

import com.example.demo.domain.project.controller.dto.response.FloorResponse
import com.example.demo.domain.project.controller.dto.response.ProjectResponse
import com.example.demo.domain.project.controller.dto.response.RoomResponse
import com.example.demo.domain.project.domain.FloorStructure
import com.example.demo.domain.project.domain.Project
import com.example.demo.domain.project.domain.RoomStructure
import com.example.demo.domain.project.domain.child.structure.BoundingBox
import com.example.demo.domain.project.repository.FloorStructureRepository
import com.example.demo.domain.project.repository.ProjectRepository
import com.example.demo.domain.project.repository.RoomStructureRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val projectDomainService: ProjectDomainService,
    private val projectRepository: ProjectRepository,
    private val floorStructureRepository: FloorStructureRepository,
    private val roomStructureRepository: RoomStructureRepository,
) {
    fun getById(id: String): ProjectResponse {
        val project = projectDomainService.get(id)

        return ProjectResponse.from(project)
    }

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

		return projects.map { ProjectResponse.from(it) }
	}

    fun getSimilarFloors(floorId: String): List<FloorResponse> {
        val floorStructure = floorStructureRepository.findById(floorId)
            .orElseThrow { NoSuchElementException() }

        val top10FloorStructure = floorStructureRepository.findTopKSimilarFloors(
            excludeProjectId = floorStructure.projectId,
            area = floorStructure.area,
            aspectRI = floorStructure.boundingBox.aspectRI,
            rectangularity = floorStructure.rectangularity,
            k = 10,
        )

        val projects = projectDomainService.getAllWithoutFloorplan(top10FloorStructure.map { it.projectId })
        val projectMap = projects.associateBy { it._id }

        return top10FloorStructure.mapNotNull {
            val project = projectMap[it.projectId] ?: return@mapNotNull null
            FloorResponse.of(
                project = project,
                _id = it.id,
                title = it.title
            )
        }
    }

    fun getSimilarRooms(
        roomId: String,
        areaFrom: Int?,
        areaTo: Int?,
    ): List<RoomResponse> {
        val roomStructure = roomStructureRepository.findById(roomId)
            .orElseThrow { NoSuchElementException() }

        val top10RoomStructure = if (roomStructure.type != 0) {
            roomStructureRepository.findTopKSimilarRoomsByType(
                excludeProjectId = roomStructure.projectId,
                area = roomStructure.area,
                areaFrom = areaFrom?.let { (it * 1000).toDouble() } ?: (roomStructure.area * 0.85),
                areaTo = areaTo?.let { (it * 1000).toDouble() } ?: (roomStructure.area * 1.15),
                rectangularity = roomStructure.rectangularity,
                aspectRI = roomStructure.boundingBox.aspectRI,
                type = roomStructure.type,
                k = 10,
            )
        } else {
            roomStructureRepository.findTopKSimilarRooms(
                excludeProjectId = roomStructure.projectId,
                area = roomStructure.area,
                areaFrom = areaFrom?.let { (it * 1000).toDouble() } ?: (roomStructure.area * 0.85),
                areaTo = areaTo?.let { (it * 1000).toDouble() } ?: (roomStructure.area * 1.15),
                rectangularity = roomStructure.rectangularity,
                aspectRI = roomStructure.boundingBox.aspectRI,
                k = 10,
            )
        }

        val projects = projectDomainService.getAllWithoutFloorplan(top10RoomStructure.map { it.projectId })
        val projectMap = projects.associateBy { it._id }

        return top10RoomStructure.mapNotNull {
            val project = projectMap[it.projectId] ?: return@mapNotNull null
            println(project.name)
            println(it.score)
            println()
            RoomResponse.of(
                project = project,
                _id = it.id,
            )
        }
    }

    fun createStructure(projectId: String) {
        val floorplans = projectDomainService.get(projectId).floorplans

        val floorStructures = floorplans.map {
            val boundingBox = BoundingBox.fromFloorplan(it)
            FloorStructure(
                id = it.id,
                title = it.title,
                projectId = projectId,
                area = it.area,
                boundingBox = boundingBox,
                rectangularity = if (boundingBox.area > 0.0) it.area * 1_000_000 / boundingBox.area else 0.0,
            )
        }

        val roomStructures = floorplans.flatMap { floorplan ->
            floorplan.rooms.map { room ->
                val boundingBox = BoundingBox.fromRoom(floorplan, room)
                RoomStructure(
                    id = room.archiId,
                    projectId = projectId,
                    type = room.type,
                    area = room.area,
                    boundingBox = boundingBox,
                    rectangularity = if (boundingBox.area > 0.0) room.area / boundingBox.area else 0.0,
                )
            }
        }

        floorStructureRepository.saveAll(floorStructures)
        roomStructureRepository.saveAll(roomStructures)
    }

    fun createRecent100Structures() {
        val ids = projectRepository.findRecentIds(100)

        val projects = projectDomainService.getAll(ids)

        val floorStructures = projects.flatMap { project ->
            project.floorplans.map { floorplan ->
                val boundingBox = BoundingBox.fromFloorplan(floorplan)
                FloorStructure(
                    id = floorplan.id,
                    title = floorplan.title,
                    projectId = project._id,
                    area = floorplan.area,
                    boundingBox = boundingBox,
                    rectangularity = if (boundingBox.area > 0.0) floorplan.area * 1_000_000 / boundingBox.area else 0.0,
                )
            }
        }

        val roomStructures = projects.flatMap { project ->
            project.floorplans.flatMap { floorplan ->
                floorplan.rooms.map { room ->
                    val boundingBox = BoundingBox.fromRoom(floorplan, room)
                    RoomStructure(
                        id = room.archiId,
                        projectId = project._id,
                        type = room.type,
                        area = room.area,
                        boundingBox = boundingBox,
                        rectangularity = if (boundingBox.area > 0.0) room.area / boundingBox.area else 0.0,
                    )
                }
            }
        }

         floorStructureRepository.saveAll(floorStructures)
         roomStructureRepository.saveAll(roomStructures)
    }

}
