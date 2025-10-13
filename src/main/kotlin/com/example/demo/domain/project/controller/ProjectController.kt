package com.example.demo.domain.project.controller

import com.example.demo.domain.project.controller.dto.response.FloorResponse
import com.example.demo.domain.project.controller.dto.response.ProjectResponse
import com.example.demo.domain.project.service.ProjectService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/projects")
class ProjectController(
    private val projectService: ProjectService,
) {
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
        projectService.createFloorplanStructure(projectId)

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{floorId}/similar")
    fun getSimilarProjects(
        @PathVariable floorId: String,
    ): ResponseEntity<List<FloorResponse>> {
        val response = projectService.getSimilarProjects(floorId)

        return ResponseEntity.ok(response)
    }
}