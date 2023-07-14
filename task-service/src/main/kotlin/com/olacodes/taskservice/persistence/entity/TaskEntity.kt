package com.olacodes.taskservice.persistence.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.olacodes.taskservice.dto.TaskPriority
import com.olacodes.taskservice.dto.TaskStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.UUID

@JsonInclude(JsonInclude.Include.ALWAYS)
@Entity(name = "tasks")
data class TaskEntity(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    var id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var description: String,

    var dueDate: LocalDate? = null,

    @Column(nullable = false)
    var status: TaskStatus = TaskStatus.TODO,

    var priority: TaskPriority = TaskPriority.MEDIUM,

    var assignee: List<UUID> = emptyList(), // Reference to the User entity's id

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    var createdAt: ZonedDateTime? = null,

    @UpdateTimestamp
    @Column(nullable = false)
    var updatedAt: ZonedDateTime? = null
)
