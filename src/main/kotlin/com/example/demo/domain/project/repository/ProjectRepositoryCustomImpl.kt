package com.example.demo.domain.project.repository

import com.example.demo.domain.project.domain.enums.ProjectState
import com.example.demo.domain.project.domain.Project
import org.bson.Document
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component

@Component
class ProjectRepositoryCustomImpl(
	private val mongoTemplate: MongoTemplate,
) : ProjectRepositoryCustom {

	override fun getSimilarProjects(
		addressId: String,
		excludeProjectId: String,
		style: String?,
	): List<Project> {
		val criteria = Criteria.where("fromMap.addressId").`is`(addressId)
			.and("_id").ne(excludeProjectId)
			.and("state").`is`(ProjectState.ACTIVATED.value)

		val query = Query(criteria)
		var projects = mongoTemplate.find(query, Project::class.java)

		if (!style.isNullOrBlank()) {
			val portfolioV2s = mongoTemplate.find(
				Query(Criteria.where("projectId").`in`(projects.map { it._id })
					.and("deletedAt").`is`(null)),
				Document::class.java,
				"portfoliosV2"
			)

			val projectIdsWithMatchingStyle = portfolioV2s.mapNotNull { portfolio ->
				val property = portfolio.get("property") as? Document
				val otherProperty = property?.get("otherProperty") as? Document
				val interiorStyle = otherProperty?.get("인테리어 스타일") as? Document
				val styleValues = (interiorStyle?.get("value") as? List<*>)
					?.filterIsInstance<Document>()

				if (styleValues?.any { styleTag ->
					val value = styleTag["value"] as? String
					value?.contains(style, ignoreCase = true) == true
				} == true) {
					portfolio["projectId"] as? String
				} else null
			}

			projects = projects.filter { it._id in projectIdsWithMatchingStyle }
		}

		return projects
	}

    override fun findRecentIds(limit: Int): List<String> {
        val query = Query().apply {
            fields().include("_id")
            with(Sort.by(Sort.Direction.DESC, "updatedAt"))
            limit(limit)
        }

        return mongoTemplate.find(query, Project::class.java, "projects")
            .mapNotNull { it._id }
    }
}
