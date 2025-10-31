package com.taskapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Task Entity - Repräsentiert eine Aufgabe im System
 */
@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title darf nicht leer sein")
    @Size(min = 3, max = 100, message = "Title muss zwischen 3 und 100 Zeichen lang sein")
    @Column(nullable = false, length = 100)
    private String title;

    @Size(max = 500, message = "Description darf maximal 500 Zeichen lang sein")
    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.TODO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority = Priority.MEDIUM;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Constructor für schnelles Erstellen von Tasks in Tests
     */
    public Task(String title) {
        this.title = title;
        this.status = TaskStatus.TODO;
        this.priority = Priority.MEDIUM;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Constructor mit Priorität
     */
    public Task(String title, Priority priority) {
        this.title = title;
        this.priority = priority;
        this.status = TaskStatus.TODO;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Prüft ob die Task überfällig ist
     */
    public boolean isOverdue() {
        if (deadline == null || status == TaskStatus.DONE) {
            return false;
        }
        return LocalDateTime.now().isAfter(deadline);
    }

    /**
     * Enum für Task Status
     */
    public enum TaskStatus {
        TODO("To Do"),
        IN_PROGRESS("In Progress"),
        DONE("Done");

        private final String displayName;

        TaskStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    /**
     * Enum für Prioritäten
     * Höherer Ordinal = Höhere Priorität
     */
    public enum Priority {
        LOW(1, "Niedrig"),
        MEDIUM(2, "Mittel"),
        HIGH(3, "Hoch");

        private final int value;
        private final String displayName;

        Priority(int value, String displayName) {
            this.value = value;
            this.displayName = displayName;
        }

        public int getValue() {
            return value;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}