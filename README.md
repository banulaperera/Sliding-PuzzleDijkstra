***

# Sliding Puzzle Game

**Sliding Puzzle Game** is an interactive Java application built for pathfinding and shortest-route visualization in grid-based mazes. This project combines algorithmic logic with user-friendly CLI interaction, letting players find the shortest path from a starting point `S` to a finishing point `F`, while navigating obstacles and boundaries.

> **This project was developed as part of the coursework for the undergraduate Software Engineering program.**

***

## Features

- **Shortest Path Finding:**  
  Utilizes Dijkstra’s algorithm to efficiently compute the optimal path through a maze[1].
- **Clear CLI User Experience:**  
  Simple command-line prompts let players supply maze files and interact with game menus.
- **Visual Path Display:**  
  Shows the traveled path within the maze, helping players learn how the algorithm works.
- **Execution Time Reporting:**  
  Measures and prints out the time taken to solve each maze, offering insights into algorithm performance.
- **Robust Maze Validation:**  
  Ensures the maze format is correct and provides informative error messages for invalid inputs.
- **Reference Implementation:**  
  Coded with best practices for algorithms, file I/O, and object-oriented design.

***

## Screenshots

See examples of both the CLI and maze-solving output.  
Replace these with actual demo images as needed.

**CLI Main Menu**
<img width="1023" height="584" alt="Screenshot 2025-08-27 at 09 21 52" src="https://github.com/user-attachments/assets/7b4b7688-170a-4050-87c2-7f11f426be84" />

**Maze Solving Output**
<img width="825" height="365" alt="Screenshot 2025-08-27 at 09 22 00" src="https://github.com/user-attachments/assets/89ebb47d-69a2-43c9-b585-944ebbfc4b8a" />

***

## Getting Started

### Prerequisites

- Java SE 8 or higher
- Command-line terminal (Linux, macOS, or Windows)
- A valid maze file formatted as described below

### Maze File Format

- **Start point:** `S`
- **Finish point:** `F`
- **Empty cells:** `0`
- **Obstacles/rocks:** `.`
- Only use these characters per cell. Each line in the file represents a maze row.

Example:
```
S00.
.0F0
0000
```

### Run Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/sliding-puzzle-game-java.git
   cd sliding-puzzle-game-java
   ```

2. **Compile the source**
   ```bash
   javac Main.java
   ```

3. **Run the application**
   ```bash
   java Main
   ```
   Follow the prompts to enter your maze file path and watch the pathfinding in action.

***

## Coursework Statement

> This project was completed as part of the **Undergraduate Software Engineering coursework** at [Your University Name], demonstrating hands-on skills in algorithms, Java programming, and user interface design.

***

## References & Credits

Inspired by:
- [GeeksforGeeks: Dijkstra's Shortest Path](https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/)
- [Javatpoint: Shortest Path in Binary Maze](https://www.javatpoint.com/shortest-path-in-a-binary-maze-in-java#)
- [CS Princeton Style Guide](https://introcs.cs.princeton.edu/java/11style/)
- YouTube tutorials on Dijkstra’s algorithm

Developed by Banula Perera (2025)

***

## License

For educational use. See [LICENSE](LICENSE) if provided.

***

**Questions or suggestions? Fork, star, and share your feedback!**
