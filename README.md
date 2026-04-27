# Heroes vs Monsters: Dungeon Crawler

A turn-based, CLI-driven RPG dungeon crawler implemented in Java. Players select a hero from various classes—each with unique progression and special abilities—to navigate through levels, engage in tactical combat with monsters, and avoid deadly traps.

## Project Overview
The game is built on a grid-based system where every element (Player, Monster, Wall, Trap) is a specialized `Tile`. The core logic focuses on extensible object-oriented design, allowing for complex interactions between different units through polymorphic behavior and robust game-state management.

### Key Features
* **Hero Classes:** Choose from **Warriors** (high health/defense), **Mages** (mana-based spellcasting), **Rogues** (energy-based strikes), or **Hunters** (ranged arrow mechanics).
* **Dynamic Enemy AI:** Monsters employ a vision-range logic to track and pursue players, while traps utilize a "tick" system to toggle visibility and ambush the player.
* **Combat System:** A randomized "roll-based" combat engine that factors in attack and defense stats for both units.
* **RPG Progression:** Experience (XP) system with leveling mechanics that dynamically increase health, attack, and defense pools.
* **Level Management:** Automated level loading from external `.txt` files, supporting multi-level campaigns.

---

## Technical Architecture
* **The Visitor Pattern:** Implemented via `visit(Tile)` and `accept(Unit)` methods to handle complex interactions between different unit types without resorting to `instanceof` checks.
* **Deep Inheritance Hierarchy:** Utilizes an abstract base `Tile` class, extending into `Unit` (for living entities) and eventually into specialized `Player` and `Enemy` subclasses.
* **Grid Logic:** A `GameBoard` manager that handles coordinate-based positioning and board rendering.
* **Testing Suite:** Comprehensive unit testing using **JUnit 5**, covering combat mechanics, mana regeneration, movement constraints, and experience gain.

---

## Skills & Concepts Implemented
* **Object-Oriented Programming (OOP):** Heavy use of polymorphism, abstract classes, and encapsulation to manage game entities.
* **Design Patterns:** Implementation of the **Visitor Pattern** for interaction logic and **Template Method** for unit-specific level-up routines.
* **File I/O:** Dynamic parsing of level layouts and directory scanning for game assets.
* **Game Loop Design:** Managing turn-based input, "game ticks," and state transitions (Winner/Game Over).
* **Software Quality Assurance:** Developed using Test-Driven Development (TDD) principles with extensive JUnit coverage.

---

## Controls
| Key | Action |
| :--- | :--- |
| `w`, `a`, `s`, `d` | Move Up, Left, Down, Right |
| `e` | Cast Class-Specific Special Ability |
| `q` | Wait / Skip Turn |

---

## Project Structure
```text
src/
├── Enemies/    # Monster and Trap logic
├── Game/       # GameRunner and GameBoard (Core Engine)
├── Players/    # Hero classes (Warrior, Mage, Rogue, Hunter)
├── Tiles/      # Base Tile, Unit, Position, and Static Tiles (Wall/Empty)
├── Tests/      # JUnit 5 test suites
└── Main.java   # Application entry point