import { useEffect, useState } from "react";
import axios from "axios";
import "./App.css";
import TaskForm from "./components/TaskForm";
import TaskCard from "./components/TaskCard";

const API_URL = "http://localhost:8080/api/tasks";

function App() {
  const [tasks, setTasks] = useState([]);
  const [editingTask, setEditingTask] = useState(null);

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

  const handleSubmit = async (taskData) => {
    try {
      if (taskData.id) {
        await axios.put(`${API_URL}/${taskData.id}`, taskData);
      } else {
        await axios.post(API_URL, taskData);
      }
      setEditingTask(null);
      fetchTasks();
    } catch (err) {
      console.error("Fehler beim Speichern:", err);
    }
  };

  const handleDelete = async (id) => {
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

      <TaskForm
        onSubmit={handleSubmit}
        editingTask={editingTask}
        cancelEdit={() => setEditingTask(null)}
      />

      <div className="task-list">
        {tasks.map((task) => (
          <TaskCard
            key={task.id}
            task={task}
            onEdit={() => setEditingTask(task)}
            onDelete={() => handleDelete(task.id)}
            onUpdate={fetchTasks}
          />
        ))}
      </div>
    </div>
  );
}

export default App;
