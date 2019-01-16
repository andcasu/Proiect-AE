package com.endava.taskserver.controller;

import com.endava.taskserver.TaskStatus;
import com.endava.taskserver.exception.NotFoundException;
import com.endava.taskserver.model.Task;
import com.endava.taskserver.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping(value = "/api/tasks")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> createNewTask(@RequestBody Task task) throws URISyntaxException {
        taskService.createNewTask(task);
        URI uri = new URI("/api/tasks/" + task.getId());
        return ResponseEntity.created(uri).build();

    }

    @PutMapping(value = "/api/tasks/{id}")
    public ResponseEntity<Void> updateTicketWithId(@PathVariable("id") int id, @RequestBody Task task) {
        taskService.updateTaskWithId(id, task);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/api/tasks/{id}")
    public ResponseEntity<Void> deleteTicketWithId(@PathVariable("id") int id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/api/tasks")
    @ResponseBody
    public List<Task> getAllTasks() {
        return taskService.getAll();
    }

    @GetMapping(value = "/api/tasks/{id}")
    @ResponseBody
    public Task getTaskById(@PathVariable("id") int id) {
        return taskService.getTaskById(id);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Void> handleNotFound() {
        return ResponseEntity.notFound().build();
    }
}
