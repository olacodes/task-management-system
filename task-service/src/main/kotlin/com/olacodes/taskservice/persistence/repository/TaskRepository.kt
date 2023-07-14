package com.olacodes.taskservice.persistence.repository

import com.olacodes.taskservice.persistence.entity.TaskEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

interface TaskRepository: CrudRepository<TaskEntity, UUID>{}
