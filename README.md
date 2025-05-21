
# Train Ticket Booking API

A simple Spring Boot REST API to simulate booking a train ticket from **London to France**. The app is designed as a technical assessment and runs entirely in memory — no database or external dependencies.

---

## 🚀 Features

- Book a ticket from London to France for $20
- Assign a user a seat in section A or B
- Retrieve receipt by user email
- View all users in a train section
- Modify a user's seat
- Remove a user from the train

---

## 🔧 Technologies Used

- Java 17
- Spring Boot
- Maven
- In-memory storage (HashMaps)
- RESTful APIs

---

## 🛠 How to Run

### 1. Build the project
Using Intellij/Eclipse or any other ide, run the command in the terminal as :
mvn clean install

### 2. Run the Spring Boot app by typing in the terminal
java -jar target/train-ticket-app-1.0.0.jar


## 📌 API Endpoints

All endpoints are prefixed with: `/train/ticket`

---

### 1. 🎟️ Book a Ticket  
**Endpoint:** `POST /train/ticket/book`  
**Description:** Books a train ticket for a user. Automatically assigns a seat and sets the route from **London** to **France** at a fixed price of `$20`.  
**Request Body (JSON):**

```json
{
  "firstName": "Narendra",
  "lastName": "Modi",
  "email": "narendramodi@example.com"
}

**Response:**
{
  "from": "London",
  "to": "France",
  "user": {
    "firstName": "Alice",
    "lastName": "Smith",
    "email": "alice@example.com"
  },
  "price": 20.0,
  "seat": "A1"
}


### 2. 📄 Get User Ticket Receipt  
**Endpoint:** `GET /train/ticket/receipt`  
**Description:** Retrieves the ticket receipt for a user using their email address. Returns details like departure, destination, seat, and user info.

**Query Parameters:**
- `email` (required): The user's email address used during ticket booking

**Example Request:**

GET /train/ticket/receipt?email=narendramodi@example.com


**Example Response:**
```json
{
  "from": "London",
  "to": "France",
  "user": {
    "firstName": "Alice",
    "lastName": "Smith",
    "email": "alice@example.com"
  },
  "price": 20.0,
  "seat": "A1"
}
