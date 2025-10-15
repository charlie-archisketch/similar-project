package com.example.demo.domain.project.repository

import com.example.demo.domain.project.domain.RoomStructure
import org.springframework.data.jpa.repository.JpaRepository

interface RoomStructureRepository : JpaRepository<RoomStructure, String>, RoomStructureRepositoryCustom