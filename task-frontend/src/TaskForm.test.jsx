import { render, screen, fireEvent } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import TaskForm from "./components/TaskForm";

describe("TaskForm Component", () => {
  const mockSubmit = vi.fn();
  const cancelEdit = vi.fn();

  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("erstellt neue Task", async () => {
    render(<TaskForm onSubmit={mockSubmit} />);

    fireEvent.change(screen.getByPlaceholderText("Titel"), { target: { value: "Neue Task" } });
    fireEvent.change(screen.getByPlaceholderText("Beschreibung"), { target: { value: "Desc" } });

    fireEvent.click(screen.getByText("Erstellen"));

    expect(mockSubmit).toHaveBeenCalledWith(
      expect.objectContaining({
        title: "Neue Task",
        description: "Desc",
        priority: "MEDIUM",
        status: "TODO",
      })
    );
  });

  it("Edit Mode zeigt Save/Cancel Buttons", async () => {
    const editingTask = { id: 1, title: "Edit Task", description: "Edit Desc", priority: "HIGH" };
    render(<TaskForm onSubmit={mockSubmit} editingTask={editingTask} cancelEdit={cancelEdit} />);

    expect(screen.getByText("Speichern")).toBeInTheDocument();
    expect(screen.getByText("Abbrechen")).toBeInTheDocument();
  });
});
