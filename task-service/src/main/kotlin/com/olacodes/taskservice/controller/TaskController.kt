package com.olacodes.taskservice.controller

import com.olacodes.taskservice.dto.TaskRequestDto
import com.olacodes.taskservice.persistence.entity.TaskEntity
import com.olacodes.taskservice.service.TaskService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class TaskController(
    val taskService: TaskService
) {

    @PostMapping(
        "/tasks",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createTask(@RequestBody(required = true) taskDto: TaskRequestDto): TaskEntity {
        return taskService.createTask(taskDto)
    }

    @GetMapping("/tasks", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getTasks(): Iterable<TaskEntity> {
        return taskService.getAllTasks()
    }

    @GetMapping("/tasks/{taskUuid}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getTask(@PathVariable("taskUuid") taskUuid: UUID): TaskEntity {
        return taskService.getTask(taskUuid)
    }

//    @PutMapping(
//        "/task/{taskUuid}", produces = [MediaType.APPLICATION_JSON_VALUE],
//        consumes = [MediaType.APPLICATION_JSON_VALUE]
//    )
//    fun updateTask(
//        @RequestBody taskDto: TaskRequestDto, @PathVariable("taskUuid") taskUuid: UUID
//    ): TaskRequestDto {
//        return taskService.updateTask(taskDto, taskUuid)
//    }

    @DeleteMapping("/tasks/{taskUuid}")
    fun deleteTask(@PathVariable("taskUuid") taskUuid: UUID) {
        return taskService.deleteTask(taskUuid)
    }


}