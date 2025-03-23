# CARIN - Covid Antibody Response and Infection Neutralization

## About the Project
CARIN is a simulation game designed to model the interaction between Covid-19 antibodies and the virus. Players can explore various strategies to neutralize the virus using different antibodies and resources. The game aims to educate players about the importance of antibodies and how they can be used to combat viral infections.

## Techstacks
- **Java**: Used for game logic and parser implementation. Java provides a robust and platform-independent environment for developing the core functionalities of the game.
- **Spring Boot**: Serves as the backend framework. Spring Boot simplifies the development of RESTful web services and provides a solid foundation for the backend infrastructure.
- **React & Typescript**: Utilized for the game UI. React allows for the creation of dynamic and responsive user interfaces, enhancing the overall user experience.

## Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/ProjectOOP.git
    ```
2. Navigate to the project directory:
    ```sh
    cd ProjectOOP
    ```
3. Open the project in IntelliJ IDEA and build the project. Ensure that all dependencies are resolved and the project compiles successfully.

## Game Classes
- **Virus**: Represents the Covid-19 virus in the game. It contains attributes and methods to simulate the behavior and characteristics of the virus.
- **ATBD**: Stands for Antibody. This class models the different types of antibodies available in the game, each with unique properties and effects.
- **Shop**: Provides a mechanism for players to acquire resources and antibodies. The shop class handles transactions and inventory management.
- **Game**: The main class that orchestrates the game flow. It manages the game state, player interactions, and overall game logic.

## Parser Classes
### Evaluable Classes
- **Number**: Represents numerical values in the game. It is used in various calculations and expressions.
- **RandomValue**: Generates random values within a specified range. This class is used to introduce variability and unpredictability in the game.
- **Identifier**: Represents variable names and identifiers used in the game scripts.
- **SensorExpression**: Evaluates conditions based on the game state. It is used in decision-making processes within the game.
- **BinaryArith**: Handles binary arithmetic operations such as addition, subtraction, multiplication, and division.

### Executable Classes
- **BlockStatement**: Represents a block of statements to be executed sequentially. It is used to group multiple statements together.
- **IfStatement**: Implements conditional execution of statements based on evaluated conditions.
- **WhileStatement**: Represents a loop that executes statements repeatedly as long as a condition is true.
- **AssignmentStatement**: Handles the assignment of values to variables.
- **MoveCommand**: Represents a command to move a game entity from one location to another.
- **AttackCommand**: Represents a command to initiate an attack by a game entity on another entity.

