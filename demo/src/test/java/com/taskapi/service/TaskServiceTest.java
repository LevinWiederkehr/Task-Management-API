package com.taskapi.service;

import com.taskapi.model.Task;
import com.taskapi.model.Task.Priority;
import com.taskapi.model.Task.TaskStatus;
import com.taskapi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * TDD Test Suite für TaskService
 *
 * Diese Tests werden ZUERST geschrieben (RED Phase),
 * dann wird der Code implementiert (GREEN Phase),
 * und danach refactored (REFACTOR Phase)
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("TaskService TDD Tests")
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task testTask;

    @BeforeEach
    void setUp() {
        testTask = new Task("Test Task");
        testTask.setId(1L);
    }

    // ==================== RED PHASE: Test 1 ====================
    // Test wird FEHLSCHLAGEN, da createTask() noch nicht existiert

    @Test
    @DisplayName("RED: Should create a new task")
    void whenCreateTask_thenTaskIsSaved() {
        // Arrange
        Task newTask = new Task("New Task");
        when(taskRepository.save(any(Task.class))).thenReturn(newTask);

        // Act
        Task createdTask = taskService.createTask(newTask);

        // Assert
        assertNotNull(createdTask, "Created task should not be null");
        assertEquals("New Task", createdTask.getTitle());
        verify(taskRepository, times(1)).save(newTask);
    }

    // ==================== RED PHASE: Test 2 ====================
    // Testen der Priority-Sortierung (wird fehlschlagen)

    @Test
    @DisplayName("RED: Should sort tasks by priority (HIGH first)")
    void whenSortByPriority_thenHighPriorityFirst() {
        // Arrange
        Task lowTask = new Task("Low Priority Task", Priority.LOW);
        Task mediumTask = new Task("Medium Priority Task", Priority.MEDIUM);
        Task highTask = new Task("High Priority Task", Priority.HIGH);

        List<Task> unsortedTasks = Arrays.asList(lowTask, mediumTask, highTask);

        // Act
        List<Task> sortedTasks = taskService.sortByPriority(unsortedTasks);

        // Assert
        assertEquals(3, sortedTasks.size());
        assertEquals(Priority.HIGH, sortedTasks.get(0).getPriority(),
                "First task should be HIGH priority");
        assertEquals(Priority.MEDIUM, sortedTasks.get(1).getPriority(),
                "Second task should be MEDIUM priority");
        assertEquals(Priority.LOW, sortedTasks.get(2).getPriority(),
                "Third task should be LOW priority");
    }

    // ==================== RED PHASE: Test 3 ====================
    // Testen von überfälligen Tasks

    @Test
    @DisplayName("RED: Should identify overdue tasks")
    void whenTaskHasPassedDeadline_thenIsOverdue() {
        // Arrange
        Task overdueTask = new Task("Overdue Task");
        overdueTask.setDeadline(LocalDateTime.now().minusDays(1)); // Gestern
        overdueTask.setStatus(TaskStatus.TODO);

        // Act
        boolean isOverdue = overdueTask.isOverdue();

        // Assert
        assertTrue(isOverdue, "Task with past deadline should be overdue");
    }

    @Test
    @DisplayName("RED: Should NOT mark completed tasks as overdue")
    void whenTaskIsDone_thenNotOverdue() {
        // Arrange
        Task completedTask = new Task("Completed Task");
        completedTask.setDeadline(LocalDateTime.now().minusDays(1)); // Gestern
        completedTask.setStatus(TaskStatus.DONE);

        // Act
        boolean isOverdue = completedTask.isOverdue();

        // Assert
        assertFalse(isOverdue, "Completed tasks should never be overdue");
    }

    // ==================== RED PHASE: Test 4 ====================
    // Testen der Status-Übergänge

    @Test
    @DisplayName("RED: Should update task status")
    void whenUpdateStatus_thenStatusIsChanged() {
        // Arrange
        testTask.setStatus(TaskStatus.TODO);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        // Act
        Task updatedTask = taskService.updateTaskStatus(1L, TaskStatus.IN_PROGRESS);

        // Assert
        assertNotNull(updatedTask);
        assertEquals(TaskStatus.IN_PROGRESS, updatedTask.getStatus());
        verify(taskRepository, times(1)).save(testTask);
    }

    // ==================== RED PHASE: Test 5 ====================
    // Testen der Validierung

    @Test
    @DisplayName("RED: Should throw exception when creating task with empty title")
    void whenCreateTaskWithEmptyTitle_thenThrowException() {
        // Arrange
        Task invalidTask = new Task("");

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> taskService.createTask(invalidTask),
                "Should throw exception for empty title");
    }

    // ==================== RED PHASE: Test 6 ====================
    // Testen des Findens aller Tasks

    @Test
    @DisplayName("RED: Should return all tasks")
    void whenGetAllTasks_thenReturnTaskList() {
        // Arrange
        List<Task> mockTasks = Arrays.asList(
                new Task("Task 1"),
                new Task("Task 2"),
                new Task("Task 3")
        );
        when(taskRepository.findAll()).thenReturn(mockTasks);

        // Act
        List<Task> tasks = taskService.getAllTasks();

        // Assert
        assertNotNull(tasks);
        assertEquals(3, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    // ==================== RED PHASE: Test 7 ====================
    // Testen des Löschens

    @Test
    @DisplayName("RED: Should delete task by ID")
    void whenDeleteTask_thenTaskIsRemoved() {
        // Arrange
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        // Act
        taskService.deleteTask(1L);

        // Assert
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("RED: Should throw exception when deleting non-existent task")
    void whenDeleteNonExistentTask_thenThrowException() {
        // Arrange
        when(taskRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> taskService.deleteTask(999L),
                "Should throw exception when task doesn't exist");
    }
}