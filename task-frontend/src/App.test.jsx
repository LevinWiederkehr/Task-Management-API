import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import axios from "axios";
import App from "./App";

vi.mock("axios");

describe("App Component", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("lädt Tasks beim Start", async () => {
    axios.get.mockResolvedValueOnce({
      data: [{ id: 1, title: "Test Task", description: "Desc", priority: "HIGH", status: "TODO" }],
    });

    render(<App />);

    await waitFor(() => {
      expect(screen.getByText("Test Task")).toBeInTheDocument();
    });
  });

  it("erstellt neue Task", async () => {
    axios.get.mockResolvedValue({ data: [] });
    axios.post.mockResolvedValue({});

    render(<App />);

    fireEvent.change(screen.getByPlaceholderText("Titel"), { target: { value: "Neue Aufgabe" } });
    fireEvent.change(screen.getByPlaceholderText("Beschreibung"), { target: { value: "Beschreibung" } });

    fireEvent.click(screen.getByText("Erstellen"));

      await waitFor(() => {
        expect(axios.post).toHaveBeenCalledWith(
          "http://localhost:8080/api/tasks",
          expect.objectContaining({
            title: "Neue Aufgabe",
            description: "Beschreibung",
            priority: "MEDIUM",
          })
        );
      });
    });

  it("löscht Task beim Klicken auf Löschen-Button", async () => {
    axios.get.mockResolvedValueOnce({
      data: [{ id: 1, title: "Test Task", description: "Desc", priority: "HIGH", status: "TODO" }],
    });
    axios.delete.mockResolvedValue({});
    axios.get.mockResolvedValueOnce({ data: [] }); // für fetchTasks nach Löschung

    render(<App />);

    // Warte bis Task geladen ist
    await waitFor(() => {
      expect(screen.getByText("Test Task")).toBeInTheDocument();
    });

    const deleteBtn = screen.getByText("Löschen");
    fireEvent.click(deleteBtn);

    await waitFor(() => {
      expect(axios.delete).toHaveBeenCalledWith("http://localhost:8080/api/tasks/1");
    });
  });
});
