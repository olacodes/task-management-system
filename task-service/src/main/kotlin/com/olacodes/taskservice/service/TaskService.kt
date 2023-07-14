package com.olacodes.taskservice.service

import com.olacodes.taskservice.dto.TaskRequestDto
import com.olacodes.taskservice.exception.APIException
import com.olacodes.taskservice.persistence.entity.TaskEntity
import org.springframework.stereotype.Service
import com.olacodes.taskservice.persistence.repository.TaskRepository
import jakarta.validation.Valid
import jakarta.validation.executable.ValidateOnExecution
import org.modelmapper.ModelMapper
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import java.util.UUID

@ValidateOnExecution
@Service
class TaskService(val taskRepository: TaskRepository) {
    //    Create Task
    fun createTask(@Valid taskRequestDto: TaskRequestDto): TaskEntity {
        val taskEntity = taskRequestDto.toTaskEntity(taskRequestDto)
        return taskRepository.save(taskEntity)
        //        val taskEntity = modelMapper.map(taskRequestDto, TaskEntity::class.java)
    }
    fun getAllTasks(): Iterable<TaskEntity> {
        return  taskRepository.findAll()
    }

    fun getTask(taskId: UUID): TaskEntity {
        return taskRepository.findById(taskId).orElseThrow{ APIException(400,"Task with taskId $taskId not found")}
    }

    fun deleteTask(taskId: UUID) {
        return taskRepository.deleteById(taskId)
    }

}