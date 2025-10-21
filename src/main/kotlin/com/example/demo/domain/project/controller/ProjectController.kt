package com.example.demo.domain.project.controller

import com.example.demo.domain.project.controller.dto.response.FloorResponse
import com.example.demo.domain.project.controller.dto.response.ProjectResponse
import com.example.demo.domain.project.controller.dto.response.RoomResponse
import com.example.demo.domain.project.service.ProjectService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/projects")
class ProjectController(
    private val projectService: ProjectService,
) {
    @GetMapping("/{projectId}")
    fun getById(
        @PathVariable projectId: String,
    ): ResponseEntity<ProjectResponse> {
        val response = projectService.getById(projectId)

        return ResponseEntity.ok(response)
    }

	@GetMapping("/{projectId}/similar-by-address")
	fun getSimilarProjectsByAddress(
        @PathVariable projectId: String,
        @RequestParam(required = false) style: String?
	): ResponseEntity<List<ProjectResponse>> {
		val response = projectService.getSimilarProjectsByAddress(
			projectId = projectId,
			style = style,
		)

		return ResponseEntity.ok(response)
	}

    @PostMapping("/{projectId}/structure")
    fun createStructure(@PathVariable projectId: String): ResponseEntity<Unit> {
        projectService.createStructure(projectId)

        return ResponseEntity.noContent().build()
    }

    @PostMapping("/structures")
    fun createRecent100Structures(): ResponseEntity<Unit> {
        projectService.createRecent100Structures()

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{floorId}/similar-floor")
    fun getSimilarFloors(
        @PathVariable floorId: String,
        @RequestParam(required = false) areaFrom: Int?,
        @RequestParam(required = false) areaTo: Int?,
    ): ResponseEntity<List<FloorResponse>> {
        val response = projectService.getSimilarFloors(
            floorId = floorId,
            areaFrom = areaFrom,
            areaTo = areaTo,
        )

        return ResponseEntity.ok(response)
    }

    @GetMapping("/{roomId}/similar-room")
    fun getSimilarRooms(
        @PathVariable roomId: String,
        @RequestParam(required = false) areaFrom: Int?,
        @RequestParam(required = false) areaTo: Int?,
    ): ResponseEntity<List<RoomResponse>> {
        val response = projectService.getSimilarRooms(
            roomId = roomId,
            areaFrom = areaFrom,
            areaTo = areaTo,
        )

        return ResponseEntity.ok(response)
    }
}