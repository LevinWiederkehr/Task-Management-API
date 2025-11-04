import React, { useState } from "react";
import axios from "axios";

export default function TaskCard({ task, onEdit, onDelete, onUpdate }) {
  const [status, setStatus] = useState(task.status);

  const priorityColors = {
    LOW: "#48bb78",
    MEDIUM: "#ed8936",
    HIGH: "#f56565",
  };

  const handleStatusChange = async (e) => {
    const newStatus = e.target.value;
    setStatus(newStatus);
    try {
      await axios.put(`http://localhost:8080/api/tasks/${task.id}`, {
        ...task,
        status: newStatus,
      });
      onUpdate();
    } catch (err) {
      console.error("Fehler beim Statuswechsel:", err);
    }
  };

  return (
    <div className="task-card" data-priority={task.priority}>
      <div className="task-card-header">
        <h2>{task.title}</h2>
        <span
          className="priority-badge"
          style={{ backgroundColor: priorityColors[task.priority] }}
        >
          {task.priority}
        </span>
      </div>
      <p>{task.description}</p>

      <div className="task-footer">
        <div>
          <label>Status:</label>
          <select value={status || "TODO"} onChange={handleStatusChange}>
            <option value="TODO">To Do</option>
            <option value="IN_PROGRESS">In Progress</option>
            <option value="DONE">Done</option>
          </select>
        </div>

        <div className="task-buttons">
          <button onClick={onEdit}>Bearbeiten</button>
          <button onClick={onDelete}>Löschen</button>
        </div>
      </div>
    </div>
  );
}
