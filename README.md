
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
	"firstName":"Rohit",
	"lastName":"Sharma",
	"email":"rsharma@example.com"
}

**Response:**
{
    "sourceCity": "London",
    "destinationCity": "France",
    "ticketPrice": 20,
    "user": {
        "firstName": "Rohit",
        "lastName": "Sharma",
        "email": "rsharma@example.com"
    },
    "section": "A",
    "seatNumber": "A1"
}


### 2. 📄 Get User Ticket Receipt  
**Endpoint:** `GET /train/ticket/receipt`  
**Description:** Retrieves the ticket receipt for a user using their email address. Returns details like departure, destination, seat, and user info.

**Query Parameters:**
- `email` (required): The user's email address used during ticket booking

**Example Request:**

GET /train/ticket/receipt?email=rsharma@example.com


**Example Response:**
```json
{
    "sourceCity": "London",
    "destinationCity": "France",
    "ticketPrice": 20,
    "user": {
        "firstName": "Rohit",
        "lastName": "Sharma",
        "email": "rsharma@example.com"
    },
    "section": "A",
    "seatNumber": "A1"
}

### 3. 📄 Get User Tickets By Section  
**Endpoint:** `GET /train/ticket/section`  
**Description:** Retrieves the list of user tickets belonging to a given section. Returns details like departure, destination, price, seat, and user info.

**Query Parameters:**
- `section` (required): The section to retrieve the list of user tickets

**Example Request:**

GET /train/ticket/section?section=A


**Example Response:**
```json
[
    {
        "sourceCity": "London",
        "destinationCity": "France",
        "ticketPrice": 20,
        "user": {
            "firstName": "Rohit",
            "lastName": "Sharma",
            "email": "rsharma@example.com"
        },
        "section": "A",
        "seatNumber": "A1"
    }
]

### 4. 📄 Remove a user  
**Endpoint:** `DELETE /train/ticket/remove`  
**Description:** Removes the user from the system based on the user's email.

**Query Parameters:**
- `email` (required): The user's email address used during ticket booking

**Example Request:**
DELETE /train/ticket/remove?email=rsharma@example.com

**Example Response:**

User removed successfully.


### 5. 📄 Modify the seat of the user  
**Endpoint:** `PUT /train/ticket/modify`  
**Description:** Updates the seat of the user as per the section provided. Returns details like departure, destination, price, seat, and user info.

**Query Parameters:**
- `email and section` (required): The user's email address and the section where the new seat is to be allocated

**Example Request:**
PUT /train/ticket/modify?email=rsharma@example.com&section=B

**Example Response:**
```json
{
    "sourceCity": "London",
    "destinationCity": "France",
    "ticketPrice": 20,
    "user": {
        "firstName": "Rohit",
        "lastName": "Sharma",
        "email": "rsharma@example.com"
    },
    "section": "B",
    "seatNumber": "B9"
}