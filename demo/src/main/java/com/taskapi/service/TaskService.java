package com.taskapi.service;

import com.taskapi.model.Task;
import com.taskapi.model.Task.Priority;
import com.taskapi.model.Task.TaskStatus;
import com.taskapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TaskService - Business Logic für Task-Management
 *
 * GREEN PHASE: Implementierung, um alle Tests zu erfüllen
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    // Comparator für Prioritäts-Sortierung (HIGH zuerst)
    private static final Comparator<Task> PRIORITY_COMPARATOR =
            Comparator.comparing(Task::getPriority, Comparator.reverseOrder());

    /**
     * Erstellt eine neue Task
     * (Test 1, 5)
     */
    public Task createTask(Task task) {
        log.info("Creating new task: {}", task.getTitle());

        // Validierung
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }

        // Default-Werte setzen
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.TODO);
        }
        if (task.getPriority() == null) {
            task.setPriority(Priority.MEDIUM);
        }

        Task savedTask = taskRepository.save(task);
        log.info("Task created successfully with ID: {}", savedTask.getId());
        return savedTask;
    }

    /**
     * Sortiert Tasks nach Priorität
     * (Test 2)
     */
    @Transactional(readOnly = true)
    public List<Task> sortByPriority(List<Task> tasks) {
        log.info("Sorting tasks by priority");
        return tasks.stream()
                .sorted(PRIORITY_COMPARATOR)
                .collect(Collectors.toList());
    }

    /**
     * Holt alle Tasks
     * (Test 6)
     */
    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        log.info("Fetching all tasks");
        return taskRepository.findAll();
    }

    /**
     * Holt eine Task per ID (optional für interne Nutzung)
     */
    @Transactional(readOnly = true)
    public Task getTask(Long id) {
        log.info("Fetching task with ID: {}", id);
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));
    }

    /**
     * Aktualisiert den Status einer Task
     * (Test 4)
     */
    public Task updateTaskStatus(Long id, TaskStatus newStatus) {
        log.info("Updating status for task ID: {} to {}", id, newStatus);
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));

        existingTask.setStatus(newStatus);
        return taskRepository.save(existingTask);
    }

    /**
     * Löscht eine Task per ID
     * (Test 7)
     */
    public void deleteTask(Long id) {
        log.info("Deleting task with ID: {}", id);
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot delete non-existent task with ID: " + id);
        }
        taskRepository.deleteById(id);
    }
}
