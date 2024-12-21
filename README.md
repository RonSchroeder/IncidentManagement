# Incident Management

## Project Overview
Incident Management is a full-stack web application designed to manage incidents effectively. The application supports CRUD (Create, Read, Update, Delete) operations on incidents, enabling efficient tracking and management of incident records. It is built using **Java Spring Boot** for the backend and **Vue.js** for the frontend, ensuring a seamless and responsive user experience.

---

## Features
The following features are implemented in the application:
- **Create Incidents**: Add new incidents to the system with relevant details.
- **Read Incidents**: Retrieve a list of all incidents with details, or view individual incident data.
- **Update Incidents**: Modify existing incident records.
- **Delete Incidents**: Remove one or more incidents from the system.

Each incident contains the following attributes:
- `id`: A unique identifier for the incident.
- `title`: A brief title summarizing the incident.
- `description`: A detailed description of the incident.
- `status`: The current status of the incident (`OPEN`, `IN_PROGRESS`, or `CLOSED`).
- `createdBy`: The user who created the incident.
- `createdAt`: The timestamp of when the incident was created.
- `updatedBy`: The user who last updated the incident.
- `updatedAt`: The timestamp of the last update to the incident.

---

## Technologies Used
- **Backend**: Java, Spring Boot
- **Frontend**: Vue.js
- **Database**: MySQL
- **Build Tool**: Maven
- **Testing**: JUnit, Mockito
- **API Design**: RESTful APIs
- **Dependency Management**: Maven

---

## Prerequisites
Before running this application, ensure that you have the following installed:
- **Java JDK** (version 17 or higher)
- **Maven** (latest version)
- **MySQL** (version 8.0 or higher)
- **Node.js and npm** (for running the Vue.js frontend)
- **Postman** (for testing APIs)

---

## Clone the Repository
To set up the project, clone the repository:
```bash
git clone https://github.com/RonSchroeder/IncidentManagement.git
cd IncidentManagement
```

---

## Installation and Setup

### Backend Setup
1. **Navigate to the backend directory**:
   ```bash
   cd back-end
   ```

2. **Set up the MySQL database**:
   - Create a database named `incident_management`.
   - Update the database credentials in `src/main/resources/application.properties`:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/incident_management
     spring.datasource.username=your_username(your own username of mysql)
     spring.datasource.password=your_password(your own password of mysql)
     ```

3. **Build the backend project**:
   ```bash
   mvn clean install
   ```

4. **Run the backend**:
   ```bash
   mvn spring-boot:run
   ```

The backend will be available at `http://localhost:8080`.

---

### Frontend Setup
1. **Navigate to the frontend directory**:
   ```bash
   cd ../front-end
   ```

2. **Install dependencies**:
   ```bash
   npm install
   ```

3. **Run the development server**:
   ```bash
   npm run serve
   ```

The frontend will be available at `http://localhost:8081`.

---

## API Endpoints

### 1. **Create Incident**
```bash
POST /api/incidents/createIncident
```
**Description**: Creates a new incident.

**Request Body**:
```json
{
  "title": "Sample Incident",
  "description": "This is a sample description",
  "status": "OPEN",
  "createdBy": "User1"
}
```

**Response**:
```json
{
  "errorCode": 0,
  "message": "Success",
  "data": {
    "id": 1,
    "title": "Sample Incident",
    "description": "This is a sample description",
    "status": "OPEN",
    "createdBy": "User1",
    "createdAt": "2024-12-21T10:00:00",
    "updatedBy": User1,
    "updatedAt": "2024-12-21T10:00:00"
  }
}
```

---

### 2. **Retrieve All Incidents**
```bash
POST /api/incidents/queryAllIncidents
```
**Description**: Retrieves all incidents with pagination support.

**Request Parameters**:
- `page`: (Optional) The page number, default is `0`.
- `size`: (Optional) The number of records per page, default is `10`.
- `sort`: (Optional) Sorting order, e.g., `createdAt,desc`.

**Response**:
```json
{
  "errorCode": 0,
  "message": "Success",
  "data": {
    "content": [
      {
        "id": 1,
        "title": "Sample Incident",
        "description": "This is a sample description",
        "status": "OPEN",
        "createdBy": "User1",
        "createdAt": "2024-12-21T10:00:00",
        "updatedBy": null,
        "updatedAt": null
      }
    ],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 10,
      "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
      }
    },
    "last": true,
    "totalElements": 1,
    "totalPages": 1
  }
}
```

---

### 3. **Modify Incident**
```bash
POST /api/incidents/modifyIncident
```
**Description**: Updates an existing incident.

**Request Body**:
```json
{
  "id": 1,
  "title": "Updated Incident",
  "description": "This is an updated description",
  "status": "IN_PROGRESS",
  "updatedBy": "User2"
}
```

**Response**:
```json
{
  "errorCode": 0,
  "message": "Success",
  "data": {
    "id": 1,
    "title": "Updated Incident",
    "description": "This is an updated description",
    "status": "IN_PROGRESS",
    "createdBy": "User1",
    "createdAt": "2024-12-21T10:00:00",
    "updatedBy": "User2",
    "updatedAt": "2024-12-21T11:00:00"
  }
}
```

---

### 4. **Delete Incident(s)**
```bash
POST /api/incidents/deleteIncident
```
**Description**: Deletes one or more incidents.

**Request Body**:
```json
[
  {
    "id": 1
  },
  {
    "id": 2
  }
]
```

**Response**:
```json
{
  "errorCode": 0,
  "message": "Success"
}
```

