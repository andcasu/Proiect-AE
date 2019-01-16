package com.endava.taskserver.service;

import com.endava.taskserver.entity.TaskEntity;
import com.endava.taskserver.exception.NotFoundException;
import com.endava.taskserver.model.Task;
import com.endava.taskserver.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createNewTask(Task task ){

        TaskEntity taskEntity = this.getTaskEntityByTask(task);
        taskEntity = taskRepository.saveAndFlush(taskEntity);
        task.setId(taskEntity.getId());
        return task;
    }

    public boolean updateTaskWithId(int taskId, Task task) throws NotFoundException {
        TaskEntity taskEntity = taskRepository.getOne(taskId);
        if( taskEntity == null ) {
            throw new NotFoundException();
        }

        taskEntity.setDescription(task.getDescription());
        taskEntity.setName(task.getName());
        taskEntity.setStartDate(Date.from(LocalDateTime.parse(task.getStartDate(),  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toInstant(ZoneOffset.UTC)));
        taskEntity.setEndDate(Date.from(LocalDateTime.parse(task.getEndDate(),  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toInstant(ZoneOffset.UTC)));
        taskEntity.setStatus(task.getStatus());

        try {
            taskEntity = taskRepository.save(taskEntity);
        } catch (Throwable e ) {
          return false;
        }
        return true;
    }

    public boolean deleteTaskById(int taskId) {
        TaskEntity taskEntity = taskRepository.findOne(taskId);
        if( taskEntity == null ) {
            throw new NotFoundException();
        }
        try {
            taskRepository.delete(taskId);
        }catch (Throwable e ) {
            return false;
        }
        return true;
    }

    public Task getTaskById(int taskId ) {
        TaskEntity taskEntity = taskRepository.findOne(taskId);
        if( taskEntity == null ) {
            throw new NotFoundException();
        }

        return Task.builder().status(taskEntity.getStatus())
                .description(taskEntity.getDescription())
                .name(taskEntity.getName())
                .startDate(taskEntity.getStartDate().toString())
                .endDate(taskEntity.getEndDate().toString())
                .build();
    }

    public List<Task> getAll() {
        List<TaskEntity> taskEntityList = taskRepository.findAll();
        return taskEntityList.stream().map(e -> Task.builder()
                .description(e.getDescription())
                .name(e.getName())
                .id(e.getId())
                .startDate(e.getStartDate().toString())
                .endDate(e.getEndDate().toString())
                .status(e.getStatus())
                .build()).collect(Collectors.toList());
    }


    private TaskEntity getTaskEntityByTask(Task task) {
        TaskEntity taskEntity = TaskEntity.builder()
                .description(task.getDescription())
                .name(task.getName())
                .startDate(Date.from(LocalDateTime.parse(task.getStartDate(),  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toInstant(ZoneOffset.UTC)))
                .endDate(Date.from(LocalDateTime.parse(task.getEndDate(),  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toInstant(ZoneOffset.UTC)))
                .status(task.getStatus())
                .build();
        return taskEntity;
    }
}
