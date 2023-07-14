package com.olacodes.taskservice.dto

import com.olacodes.taskservice.dto.TaskPriority
import com.olacodes.taskservice.dto.TaskStatus
import com.olacodes.taskservice.persistence.entity.TaskEntity
import java.time.LocalDate
import java.util.*

data class TaskRequestDto(
    val title: String,
    val description: String,
    val dueDate: LocalDate? = null,
    val status: TaskStatus = TaskStatus.TODO,
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val assignee: List<UUID> = emptyList()
) {

    fun toTaskEntity(taskDto: TaskRequestDto): TaskEntity {
        return TaskEntity(
            title = taskDto.title,
            description = taskDto.description,
            dueDate = taskDto.dueDate,
            status = taskDto.status,
            priority = taskDto.priority,
            assignee = taskDto.assignee
        )
    }
}


