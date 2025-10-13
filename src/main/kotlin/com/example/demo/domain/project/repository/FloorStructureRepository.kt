package com.example.demo.domain.project.repository

import com.example.demo.domain.project.domain.FloorStructure
import org.springframework.data.jpa.repository.JpaRepository

interface FloorStructureRepository : JpaRepository<FloorStructure, String>, FloorStructureRepositoryCustom