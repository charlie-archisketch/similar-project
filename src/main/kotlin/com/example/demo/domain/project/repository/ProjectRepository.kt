package com.example.demo.domain.project.repository

import com.example.demo.domain.project.domain.Project
import org.springframework.data.mongodb.repository.MongoRepository

interface ProjectRepository : MongoRepository<Project, String>, ProjectRepositoryCustom
