# Train Ticket Booking API

A simple Spring Boot REST API to simulate booking a train ticket from **London to France**.  
The app runs entirely in memory, no database or external dependencies.

---

## Features

- Book a ticket from London to France for $20
- Automatically assign a seat in section A or B
- Retrieve a ticket receipt using user email
- View all users in a specific train section
- Modify a user's seat section
- Remove a user from the train

---

## Technologies Used

- Java 17  
- Spring Boot  
- Maven  
- RESTful APIs  
- In-memory storage (HashMaps)

---

## How to Build, Run, and Test

Follow these steps:

```bash
# Clone the project (if needed)
git clone https://github.com/your-username/train-ticket-api.git
cd train-ticket-api

# Step 1: Build the project
mvn clean install

# Step 2: Run the application
java -jar target/train-ticket-app-1.0.0.jar


API Endpoints
All endpoints are prefixed with: /train/ticket
Note: Port used is 9001 (Refer application.properties)

1. Book a Ticket
Endpoint: POST /train/ticket/book
Description: Books a train ticket. Automatically assigns a seat.

Request Body:

{
  "firstName": "Rohit",
  "lastName": "Sharma",
  "email": "rsharma@example.com"
}

Response:

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

2. Get Ticket Receipt
Endpoint: GET /train/ticket/receipt
Query Param: email (required)
Example: /train/ticket/receipt?email=rsharma@example.com

Response:
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

3. Get Users by Section
Endpoint: GET /train/ticket/section
Query Param: section (required)
Example: /train/ticket/section?section=A

Response:
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

4. Remove a User
Endpoint: DELETE /train/ticket/remove
Query Param: email (required)
Example: /train/ticket/remove?email=rsharma@example.com

Response:
User removed successfully.

5. Modify User's Seat Section
Endpoint: PUT /train/ticket/modify
Query Params: email, section (both required)
Example: /train/ticket/modify?email=rsharma@example.com&section=B
Response:
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
