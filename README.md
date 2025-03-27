# Secret Santa Game

## Overview

The **Secret Santa Game** is an application that automates the Secret Santa event for a company or group. Employees are randomly assigned a "secret child" (the person they will give a gift to). This solution includes a back-end built with **Spring Boot** for processing and managing assignments and a front-end built with **React** for user interaction.

## Features

- Randomly assigns a Secret Child to each employee.
- Ensures that no one is assigned to themselves.
- Prevents assigning the same Secret Child as in the previous year (if applicable).
- Allows employees to upload a CSV file with employee details.
- Generates and displays the assignments.

## Architecture

The project is split into two main parts:

### 1. **Back-end (Spring Boot)**
- **API Endpoints**: Exposes REST APIs for processing and fetching Secret Santa assignments.
- **CSV Parsing**: Handles reading from CSV files containing employee data and previous year’s assignments.
- **Random Assignment Logic**: Ensures each employee is assigned to a different person while respecting the constraints.

### 2. **Front-end (React)**
- **UI Components**: Allows users to upload a CSV file with employee details.
- **Fetch API**: Makes HTTP requests to the Spring Boot API for generating assignments.
- **Assignment Display**: Downloads CSV files as output.

---

## Requirements

### 1. **Back-end**:
- Java 17
- Spring Boot 3.4.4
- Maven for dependency management and building the application

### 2. **Front-end**:
- Node.js 22.14.0
- npm 10.9.2

### 3. **Libraries & Tools**:
- Spring Boot for the back-end
- React for the front-end
- Apache POI (for handling Excel files)
- `axios` for API calls in React

---

## Getting Started

Follow these steps to get the project up and running on your local machine.

### 1. **Back-end (Spring Boot)**

#### Step 1: Clone the repository

```bash
git clone https://github.com/swapnabs/Secret-Santa-Game.git
cd Secret-Santa-Game
```

#### Step 2: Set up the Back-end

Go to the back-end directory:

```bash
cd backend
```

#### Step 3: Build and Run the Back-end

```bash
mvn spring-boot:run
```

This will start the back-end server, which will be available at `http://localhost:8080`.

### 2. **Front-end (React)**

#### Step 1: Set up the Front-end

Go to the front-end directory:

```bash
cd frontend
```

#### Step 2: Install Dependencies

Run the following command to install all necessary dependencies:

```bash
npm install
```

#### Step 3: Run the Front-end

Start the React application:

```bash
npm start
```

The front-end will be running at `http://localhost:3000`.

---

## Using the Application

1. **Upload Employee Data**:
    - The front-end allows users to upload a CSV file containing employee names and email addresses.
    - The file should follow the format:
```csv
Employee_Name	Employee_EmailID
Hamish Murray	hamish.murray@acme.com
Layla Graham	layla.graham@acme.com
```

2. **Submit the CSV**:
    - Once the file is uploaded, the React app will send the data to the back-end via an API request.

3. **View Assignments**:
    - After the back-end processes the data and assigns Secret Children, the assignments are returned to the front-end and displayed to the user.

---

## Example CSV File Format

#### Employees Data (`EmployeeList.xlsx`):
```csv
Employee_Name	Employee_EmailID
Hamish Murray	hamish.murray@acme.com
Layla Graham	layla.graham@acme.com
```

#### Previous Year Assignments (`Secret-Santa-Game-2023.xlsx`):
```csv
Employee_Name	Employee_EmailID	Secret_Child_Name	Secret_Child_EmailID
Hamish Murray	hamish.murray@acme.com	Benjamin Collins	benjamin.collins@acme.com
Layla Graham	layla.graham@acme.com	Piper Stewart	piper.stewart@acme.com
```

---

## API Endpoints

### 1. **POST /api/secretsanta/assign**
This endpoint takes in the uploaded CSV file, processes the employee data, and assigns a Secret Child to each employee, ensuring no one is assigned to themselves and preventing assignments from the previous year.

**Request Body**: 
- `employeesFile`: The CSV file containing employee information.
- `previousAssignmentsFile`: The CSV file containing previous year’s assignments.

**Response**:
- A list of employees with their assigned Secret Child’s name and email.
---

## Development

To contribute to this project or make changes:

### 1. **Fork the repository** and clone it to your local machine:
```bash
git clone https://github.com/swapnabs/Secret-Santa-Game.git
```

### 2. **Make your changes**, commit them, and push to your fork:
```bash
git add .
git commit -m "Implementing new feature"
git push origin main
```
