# Sudoku JavaFX

An application created as part of the *Component Programming* course – a Sudoku game built in Java using **JavaFX**.  
The project demonstrates fundamental object-oriented programming techniques in Java, GUI handling, and the implementation of game logic.

---

## 🎮 Features
- Interactive Sudoku gameplay.
- Validation of entered moves.
- Option to reset the board and start a new game.
- Clear graphical interface built with JavaFX.

---

## 🛠️ Technologies and Concepts
The project is written in **Java** and makes use of:
- **JavaFX** – GUI (grid layout, text fields, buttons, event handling).
- **Object-Oriented Programming (OOP)** – classes representing game logic and application structure.
- **MVC / JavaFX controllers** – separation of game logic from the interface.
- **2D arrays** – representation of the Sudoku board.
- **Bactracking algorithm** - recursive algorithm that's checking if the number is allowed in given place and if it's not, go back and try another one.
- **Validation algorithms** – checking correctness of rows, columns, and 3x3 blocks.
- **Exception handling** – e.g., validation of invalid user input.
- **Database** - saving boards to file or database (SQLite)
