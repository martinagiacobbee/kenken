# 🎮 KenKen Game - Java Implementation

Welcome to the **KenKen Game**! This project implements the famous KenKen puzzle game using design patterns such as **Observer**, **Factory Method**, **Template Method**, and **Mediator**. The game allows users to enjoy a dynamic and challenging experience while showcasing best practices in object-oriented design.

## 🧩 About the KenKen Game

KenKen is a mathematical puzzle game where players need to fill a grid with digits without repeating numbers in any row or column. Each puzzle comes with various constraints (like sums, products, etc.) that make solving the puzzle both fun and intellectually stimulating!

This Java implementation includes both a **Graphical User Interface (GUI)** and a **solver** based on the **Backtracking** algorithm.

## 🚀 Key Features

- **Observer Pattern**: Keeps the grid UI updated as players interact with it and make changes.
- **Factory Method Pattern**: Used for creating various types of constraints (e.g., sum, product, etc.).
- **Template Method Pattern**: Defines the structure for checking the constraints in the grid, allowing flexibility for different rules.
- **Mediator Pattern**: Coordinates interactions between different components like the grid, solver, and UI without direct dependencies.

## 🛠️ Design Patterns in Use

### 1. **Observer Pattern**
   - The grid is an observable subject, and the UI acts as an observer. Any changes made by the player (or by the solver) immediately reflect in the UI.

### 2. **Factory Method Pattern**
   - Used to create constraint objects (like sum, product, difference, etc.). This allows for easily adding new constraint types without modifying the core logic.

### 3. **Template Method Pattern**
   - Defines the steps to check whether a player's solution is valid, with subclasses implementing specific rule checks (like summing to a certain number).

### 4. **Mediator Pattern**
   - Ensures loose coupling between UI, the solver, and grid. The mediator coordinates actions without these components needing to know about each other directly.

### Prerequisites
Java 8 or later versions

## 🚦 How to Run the Project

The project can be easily started using the **GridView** class, which includes a `main` method with all the necessary initial configurations.

```java
public class GridView {
    public static void main(String[] args) {
        // Initial configurations and launch of the KenKen game
    }
}

