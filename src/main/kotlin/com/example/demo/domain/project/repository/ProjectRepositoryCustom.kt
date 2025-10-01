package com.example.demo.domain.project.repository

import com.example.demo.domain.project.domain.Project

interface ProjectRepositoryCustom {
	fun getSimilarProjects(addressId: String, excludeProjectId: String, style: String?): List<Project>
}
