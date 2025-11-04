import { render, screen, fireEvent } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import TaskCard from "./components/TaskCard";
import axios from "axios";
import { vi } from "vitest";

vi.mock("axios");

const mockTask = {
  id: 1,
  title: "Test Task",
  description: "Test Description",
  priority: "HIGH",
  status: "TODO",
};

describe("TaskCard Component", () => {
  test("zeigt Priority Badge korrekt an", () => {
    render(<TaskCard task={mockTask} />);
    const badge = screen.getByText("HIGH");
    expect(badge).toHaveStyle({ backgroundColor: "rgb(245, 101, 101)" }); // passt zur Komponente
  });

  test("Status ändern ruft onUpdate auf", async () => {
    const onUpdate = vi.fn();
    render(<TaskCard task={mockTask} onUpdate={onUpdate} />);

    const statusSelect = screen.getByDisplayValue("To Do");
    await userEvent.selectOptions(statusSelect, "DONE");

    expect(onUpdate).toHaveBeenCalled();
  });

  test("Edit und Delete Buttons funktionieren", () => {
    const onEdit = vi.fn();
    const onDelete = vi.fn();
    render(
      <TaskCard task={mockTask} onEdit={onEdit} onDelete={onDelete} />
    );

    const editBtn = screen.getByText("Bearbeiten");
    fireEvent.click(editBtn);
    expect(onEdit).toHaveBeenCalled();

    const deleteBtn = screen.getByText("Löschen");
    fireEvent.click(deleteBtn);
    expect(onDelete).toHaveBeenCalled();
  });
});