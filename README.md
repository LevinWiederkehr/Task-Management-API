# 🧪 Testkonzept & Projektdokumentation – Task Management App

**Autor:** Levin Wiederkehr & Albaraa Alasaad  
**Modul:** M450 – Applikationen testen

---

## 1. Projektübersicht

Die **Task Management App** ist eine Fullstack-Anwendung zur Verwaltung von Aufgaben (Tasks).  
Sie besteht aus einem **Spring Boot Backend (Java)** und einem **React Frontend (Vite)**.  

Ziel war die Entwicklung einer funktionierenden Anwendung mit **automatisierten Tests**, **Code Coverage Reports**  
und einer **CI/CD-Pipeline**, umgesetzt nach dem Prinzip des **Test Driven Development (TDD)**.

---

## 2. Kurze Planung (ohne Gantt)

| Phase | Inhalt |
|--------|---------|
| **1** | Setup von Backend (Spring Boot) & Frontend (Vite) |
| **2** | Implementierung der Business-Logik im TaskService |
| **3** | JUnit Tests mit Mockito für Backend |
| **4** | React Component Tests mit Vitest |
| **5** | CI/CD Pipeline & SonarCloud-Integration |
| **6** | Abschlussdokumentation & Review |

---

## 3. Architekturübersicht

```
┌──────────────────────────────────────────┐
│                  Frontend                │
│ React (Vite)                             │
│ Components: TaskCard, TaskForm, App.jsx  │
│ Axios → REST API Calls                   │
└───────────────┬──────────────────────────┘
                │
                ▼
┌──────────────────────────────────────────┐
│                  Backend                 │
│ Spring Boot REST API (Java, Maven)       │
│ Controller → Service → Repository Layer  │
│ Tests: JUnit 5 + Mockito                 │
└───────────────┬──────────────────────────┘
                │
                ▼
        ┌────────────────┐
        │ In-Memory DB   │
        │ (Mock Repository) │
        └────────────────┘
```

---

## 4. Testkonzept (nach IEEE 829)

### **Introduction**
Dieses Testkonzept beschreibt die Strategie für das Testen der Task Management App.  
Ziel ist es, die Funktionsfähigkeit und Stabilität des Systems durch automatisierte Tests sicherzustellen.

### **Test Items**
Zu testende Komponenten:
- **Backend:** `TaskService`, `TaskController`, `Task`, `TaskRepository`, `TaskManagementAPIApplication`  
- **Frontend:** `TaskCard`, `TaskForm`, `App.jsx`  
- **Schnittstellen:** REST-Endpunkte `/api/tasks` und `/api/tasks/{id}`

### **Features to be tested**
- Neue Tasks erstellen  
- Tasks nach Priorität sortieren  
- Overdue Tasks erkennen  
- Abgeschlossene Tasks nicht als Overdue anzeigen  
- Task-Status ändern  
- Exception bei leerem Titel  
- Task löschen  
- Exception bei Löschen einer nicht existierenden Task  

### **Features not to be tested**
- Performance & Lasttests  
- UI-Styling & Animationen  
- Nicht-funktionale Aspekte (Security, Accessibility)

### **Approach**
- **Backend:** JUnit 5 & Mockito (Unit Tests)  
- **Frontend:** Vitest + React Testing Library (Component Tests)  
- **Methodik:** Test Driven Development (TDD)  
  - Red → Green → Refactor  

### **Item Pass / Fail Criteria**
| Kriterium | Beschreibung |
|------------|--------------|
| **Pass** | Test läuft erfolgreich durch und liefert erwartetes Ergebnis |
| **Fail** | Test bricht mit Exception oder falschem Ergebnis ab |

**Fehlerkategorien:**
- *Geringfügig:* UI- oder Layoutfehler  
- *Mittel:* Falsche Business-Logik  
- *Schwer:* API-Fehler, Absturz, unhandled Exception  

### **Test Deliverables**
- JUnit-Tests (Backend)  
- Vitest-Tests (Frontend)  
- CI/CD-Testlogs (GitHub Actions)  
- SonarCloud Coverage Report  
- Dieses Testkonzept (Dokumentation)

### **Testing Tasks**
| Teststufe | Beschreibung |
|------------|--------------|
| **Unit Tests** | Einzelne Backend-Funktionen |
| **Integration Tests** | Interaktion zwischen API und Repository |
| **Component Tests** | UI-Komponenten in Isolation |
| **Pipeline Tests** | Automatisierter Durchlauf bei jedem Push |

### **Environmental Needs**
| Bereich | Umgebung |
|----------|-----------|
| Backend | Java 21, Spring Boot, Maven |
| Frontend | Node.js 20, React (Vite) |
| CI/CD | GitHub Actions (ubuntu-latest) |
| Reporting | SonarCloud |

### **Schedule**
| Zeitpunkt | Testaktivität |
|------------|----------------|
| Während der Entwicklung | TDD Unit Tests |
| Nach Push | CI/CD Testlauf automatisch |

---

## 5. Code Coverage & Reports

- **Tool:** SonarCloud  
- **Abdeckung:** ca. 30%  
- **Fehler / Code Smells:** Keine kritischen  
- **Integration:** GitHub Actions + SonarCloud  
- **Reports:**  
  - Testlogs aus CI/CD  
  - Coverage Dashboard in SonarCloud  

---

## 6. Reflexion – Test Driven Development (TDD)

**Vorgehen:**
1. Test schreiben (rot)  
2. Minimalen Code implementieren (grün)  
3. Code verbessern (refactor)

**Erkenntnisse:**
- TDD hilft, sauberen und wartbaren Code zu schreiben  
- Tests dienen gleichzeitig als lebende Dokumentation  
- Aufwand anfangs höher, aber langfristig effizienter  

---

## 7. Reflexion – Code Reviews

- Einen Pull Requests mit Kommentar erstellt  
- Fokus auf Lesbarkeit, Struktur und Testqualität  
- Verbesserte Codequalität durch Peer Review  
- Konstruktives Feedback und Austausch im Team  

---

## 8. Fazit

Die Task Management App erfüllt die geforderten Kriterien:  
- Vollständige CI/CD-Pipeline mit automatisierten Tests  
- Saubere Teststrategie nach TDD  
- Hohe Codequalität mit SonarCloud-Überwachung  
- Nachvollziehbare Entwicklungsschritte durch Reviews  

Durch automatisierte Tests und CI/CD konnte ein stabiler, erweiterbarer Code geschaffen werden.  
Das Projekt zeigt, wie Test Driven Development und moderne Tools wie SonarCloud und GitHub Actions  
zu professioneller Softwarequalität führen.

---