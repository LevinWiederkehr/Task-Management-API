import { useEffect, useState } from "react";
import axios from "axios";
import "./App.css";

const API_URL = "http://localhost:8080/api/tasks";

function App() {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [priority, setPriority] = useState("MEDIUM");

  useEffect(() => {
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    try {
      const res = await axios.get(API_URL);
      setTasks(res.data);
    } catch (err) {
      console.error("Fehler beim Laden der Tasks:", err);
    }
  };

  const createTask = async () => {
    if (!title) return;
    try {
      await axios.post(API_URL, { title, description, priority });
      setTitle("");
      setDescription("");
      setPriority("MEDIUM");
      fetchTasks();
    } catch (err) {
      console.error("Fehler beim Erstellen:", err);
    }
  };

  const updateTask = async (task, field, value) => {
    try {
      const updatedTask = { ...task, [field]: value };
      await axios.put(`${API_URL}/${task.id}`, updatedTask);
      fetchTasks();
    } catch (err) {
      console.error("Fehler beim Aktualisieren:", err);
    }
  };

  const deleteTask = async (id) => {
    try {
      await axios.delete(`${API_URL}/${id}`);
      fetchTasks();
    } catch (err) {
      console.error("Fehler beim Löschen:", err);
    }
  };

  return (
    <div className="container">
      <h1>Task Management</h1>

      <div className="task-form">
        <input
          type="text"
          placeholder="Titel"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
        <input
          type="text"
          placeholder="Beschreibung"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />
        <select value={priority} onChange={(e) => setPriority(e.target.value)}>
          <option value="LOW">Niedrig</option>
          <option value="MEDIUM">Mittel</option>
          <option value="HIGH">Hoch</option>
        </select>
        <button onClick={createTask}>Erstellen</button>
      </div>

      <div className="task-list">
        {tasks.map((task) => (
          <div className="task-card" key={task.id}>
            <input
              className="task-title"
              value={task.title}
              onChange={(e) => updateTask(task, "title", e.target.value)}
            />
            <textarea
              className="task-desc"
              value={task.description || ""}
              onChange={(e) => updateTask(task, "description", e.target.value)}
            />
            <div className="task-meta">
              <select
                value={task.priority}
                onChange={(e) => updateTask(task, "priority", e.target.value)}
              >
                <option value="LOW">Niedrig</option>
                <option value="MEDIUM">Mittel</option>
                <option value="HIGH">Hoch</option>
              </select>
              <select
                value={task.status}
                onChange={(e) => updateTask(task, "status", e.target.value)}
              >
                <option value="TODO">To Do</option>
                <option value="IN_PROGRESS">In Progress</option>
                <option value="DONE">Done</option>
              </select>
            </div>
            <button className="delete-btn" onClick={() => deleteTask(task.id)}>
              Löschen
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;
