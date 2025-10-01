package com.example.demo.domain.project.controller

import com.example.demo.domain.project.controller.dto.response.ProjectResponse
import com.example.demo.domain.project.service.ProjectServiceV2
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/projects")
class ProjectControllerV2(
    private val projectServiceV2: ProjectServiceV2,
) {
	@GetMapping("/{projectId}/similar")
	fun getSimilarProjects(
        @PathVariable projectId: String,
        @RequestParam(required = false) style: String?
	): ResponseEntity<List<ProjectResponse>> {
		val response = projectServiceV2.getSimilarProjects(
			projectId = projectId,
			style = style,
		)

		return ResponseEntity.ok(response)
	}
}