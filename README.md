# Library Management Application

A Java-based application that allows librarians to manage the lending of books and multimedia items. The system supports issuing, renewing, and returning items, with all data stored and retrieved from CSV files for persistence.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Classes and Object-Oriented Design](#classes-and-object-oriented-design)
- [Data Structures and Control Flow](#data-structures-and-control-flow)
- [Static Methods and Exception Handling](#static-methods-and-exception-handling)
- [Additional Technical Details](#additional-technical-details)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)

## Features

- Issue books and multimedia items to users.
- Renew existing loans with rules for books and multimedia.
- Record the return of items.
- View all items currently on loan.
- **Search for items on loan**: Quickly find details of items currently on loan by searching via barcode.
- **Generate reports**: Produce reports showing the total number of each type of loan and the percentage of items that have been renewed more than once.
- Data persistence through CSV files.

## Classes and Object-Oriented Design

The application is structured around five key object classes: `Item`, `Book`, `Multimedia`, `User`, and `Loan`. These classes encapsulate the data and behavior relevant to the library management system.

- **Item**: An abstract superclass representing common attributes and behaviors of all lendable items.
- **Book** and **Multimedia**: Subclasses that inherit from `Item`, implementing specific functionality like due date calculations and string representations.
- **User**: Represents the library users, including relevant attributes like user ID.
- **Loan**: Manages the details of each loan, including barcode, user ID, issue date, due date, and renewals.

The `Library_Manager` class acts as the controller, handling user interactions and managing the operations within the system.

### Inheritance and Polymorphism

- The `Item` class is abstract, allowing `Book` and `Multimedia` subclasses to inherit common functionality while customizing certain behaviors, like due date calculations, specific to each item type.
- Polymorphism is leveraged through the `toString()` method, which is overridden in each subclass to provide a detailed string representation of the items.

### Data Encapsulation and Access Control

- All instance variables are set as private to protect the internal state of objects. Controlled access is provided through getters and setters, ensuring data integrity.

## Data Structures and Control Flow

### Data Structures

- **ArrayLists**: Used to store collections of items, loans, and users. The dynamic sizing of ArrayLists was crucial for efficiently managing the varying number of objects in the system.

### Control Structures

- **do-while loops**: Used for user input prompts, ensuring the prompt appears at least once before conditions are checked.
- **for loops**: Utilized for iterating over ArrayLists, particularly for validating user inputs like barcodes and IDs.
- **if statements**: Primarily used for validation checks within loops to handle user input errors and provide feedback.
- **switch statements**: Employed in the menu system for readability and maintainability when handling multiple options.

## Static Methods and Exception Handling

### Static Methods

- The application includes static methods for reading and writing to CSV files. These methods do not depend on the state of any object and can be accessed directly, ensuring efficient file management.

### Exception Handling

- Exception handling is implemented throughout the application to manage unexpected inputs and file I/O operations.
  - **InputMismatchException**: Managed in the menu system to catch invalid user inputs.
  - **IOException**: Handled in CSV file operations to prevent issues related to file accessibility.

## Additional Technical Details

### Iteration and Concurrency

- The `return item` method includes iteration to safely remove objects from the `Loan` ArrayList, addressing potential concurrency issues that arise from modifying a collection during iteration.

### Extra Variables

- An additional `status` variable is included in the `Item` class to track the availability of items, preventing multiple loans of the same item within a single session.



