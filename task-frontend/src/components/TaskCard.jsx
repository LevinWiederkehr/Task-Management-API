import React, { useState } from 'react';

export default function TaskCard({ task, onEdit, onDelete, onStatusChange }) {
  const priorityColors = {
    LOW: 'green',
    MEDIUM: 'orange',
    HIGH: 'red',
  };

  const [status, setStatus] = useState(task.status);

  const handleStatusChange = (e) => {
    const newStatus = e.target.value;
    setStatus(newStatus);
    if (onStatusChange) onStatusChange({ ...task, status: newStatus });
  };

  return (
    <div className="task-card">
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
          Status:
          <select value={status} onChange={handleStatusChange}>
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
