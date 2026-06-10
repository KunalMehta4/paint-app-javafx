Paint App (JavaFX)
A desktop paint application built using JavaFX that allows users to create, customize, edit, and 
save drawings through an intuitive graphical interface. The application supports multiple geometric shapes,
configurable drawing attributes, clipboard functionality, undo/redo operations, and persistent save files. 
The project was developed using object-oriented design principles and incorporates several software engineering 
patterns, including MVC, Observer, Command, and Prototype, to create a modular and maintainable architecture.

Features
Users can draw circles, rectangles, squares, triangles, ovals, polylines, and freehand squiggles while customizing 
stroke width, stroke colour, and fill colour in real time. The application also includes editing functionality such 
as copy, cut, paste, undo, and redo, enabling efficient modification of drawings. Projects can be saved and reloaded 
through serialization, preserving shapes, drawing attributes, and application state between sessions. A status bar provides
live feedback about the active tool and recently performed actions, while keyboard shortcuts streamline common editing operations.

Technologies
This project was developed using Java 22, JavaFX, and Maven. The architecture follows the Model-View-Controller (MVC) pattern, 
with the Observer Pattern used for UI synchronization and the Command Pattern used to encapsulate user actions such as copy,
paste, save, and undo/redo. Serialization is used to support the persistent storage of drawings and application state.

Project Structure
Assignment2/
├── paint/
│   ├── Paint.java
│   ├── PaintModel.java
│   ├── View.java
│   ├── PaintPanel.java
│   ├── Mode.java
│   ├── AttributesModel.java
│   ├── command/
│   └── shape/

Running the Application
Clone the repository, navigate to the project directory, and run the application using Maven:
git clone https://github.com/KunalMehta4/paint-app-javafx.git
cd paint-app-javafx/Assignment2
mvn clean javafx:run

Design Highlights
The application separates user interface components, business logic, and data management through MVC, making the codebase 
easier to maintain and extend. Shapes are created through a prototype-based system that allows new drawing tools to be added with 
minimal changes to existing code. User actions are encapsulated as commands, enabling reusable functionality for editing operations 
and keyboard shortcuts. Observers automatically synchronize the canvas, status bar, and attribute panels whenever the underlying model changes.

Learning Outcomes
This project demonstrates proficiency in JavaFX GUI development, object-oriented programming, event-driven systems, software design patterns, 
serialization, and interactive desktop application development.
