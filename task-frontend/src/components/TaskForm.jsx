import React, { useState, useEffect } from 'react';

export default function TaskForm({ onSubmit, editingTask, cancelEdit }) {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [priority, setPriority] = useState('MEDIUM');

  useEffect(() => {
    if (editingTask) {
      setTitle(editingTask.title);
      setDescription(editingTask.description);
      setPriority(editingTask.priority);
    } else {
      setTitle('');
      setDescription('');
      setPriority('MEDIUM');
    }
  }, [editingTask]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!title.trim()) return;
    onSubmit({
      id: editingTask?.id,
      title,
      description,
      priority,
      status: editingTask?.status || 'TODO',
    });
    setTitle('');
    setDescription('');
    setPriority('MEDIUM');
  };

  return (
    <form className="task-form" onSubmit={handleSubmit}>
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
      <button type="submit">{editingTask ? 'Speichern' : 'Erstellen'}</button>
      {editingTask && <button type="button" onClick={cancelEdit}>Abbrechen</button>}
    </form>
  );
}
