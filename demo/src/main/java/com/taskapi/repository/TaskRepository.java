package com.taskapi.repository;

import com.taskapi.model.Task;
import com.taskapi.model.Task.Priority;
import com.taskapi.model.Task.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository für Task-Entitäten
 * Spring Data JPA generiert automatisch die Implementierung
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Findet alle Tasks mit einem bestimmten Status
     */
    List<Task> findByStatus(TaskStatus status);

    /**
     * Findet alle Tasks mit einer bestimmten Priorität
     */
    List<Task> findByPriority(Priority priority);

    /**
     * Findet Tasks die vor einem bestimmten Datum als Deadline haben
     */
    List<Task> findByDeadlineBefore(LocalDateTime dateTime);

    /**
     * Findet überfällige Tasks (Deadline in der Vergangenheit und Status nicht DONE)
     */
    @Query("SELECT t FROM Task t WHERE t.deadline < :now AND t.status != 'DONE'")
    List<Task> findOverdueTasks(@Param("now") LocalDateTime now);

    /**
     * Findet Tasks nach Titel (Case-insensitive)
     */
    List<Task> findByTitleContainingIgnoreCase(String title);

    /**
     * Zählt Tasks nach Status
     */
    long countByStatus(TaskStatus status);

    /**
     * Findet alle Tasks sortiert nach Priorität (HIGH zuerst)
     */
    @Query("SELECT t FROM Task t ORDER BY t.priority DESC")
    List<Task> findAllOrderedByPriority();
}