Booking Service
===============

Overview
--------

The **Booking Service** is a RESTful API that allows for the management of bookings across various departments such as Sales, IT, and Support. The service provides endpoints to create, update, retrieve, and process bookings. It also includes features for fetching all unique currencies used in bookings and calculating the total sum of prices for bookings in a specific currency.

Installation
------------

1.  **Clone the repository:**

    bash

    Copy code

    `git clone https://github.com/yourusername/bookingservice.git
    cd bookingservice`

2.  **Build the project using Maven:**

    bash

    Copy code

    `mvn clean install`

3.  **Run the application:**

    bash

    Copy code

    `mvn spring-boot:run`

Running the Application
-----------------------

The application can be run using Maven or your preferred IDE (e.g., IntelliJ, Eclipse). Ensure that all dependencies are correctly installed and the project is properly built.

Once the application is running, the API will be accessible at `http://localhost:8080/bookingservice`.

API Endpoints
-------------

### Create a Booking

-   **URL:** `/bookingservice/bookings`

-   **Method:** `POST`

-   **Request Body:**

    json

    Copy code

    `{
      "bookingId": "1",
      "description": "Test Booking",
      "price": 100.0,
      "currency": "USD",
      "subscriptionStartDate": "2023-01-01",
      "email": "test@example.com",
      "department": "sales"
    }`

-   **Response:**

    -   **201 Created**
-   **cURL Example:**

    bash

    Copy code

    `curl -X POST http://localhost:8080/bookingservice/bookings   -H "Content-Type: application/json"   -d '{
          "bookingId": "1",
          "description": "Test Booking",
          "price": 100.0,
          "currency": "USD",
          "subscriptionStartDate": "2023-01-01",
          "email": "test@example.com",
          "department": "sales"
        }'`

### Update a Booking

-   **URL:** `/bookingservice/bookings/{bookingId}`

-   **Method:** `PUT`

-   **Request Body:** (same as above)

-   **Response:**

    -   **200 OK**
-   **cURL Example:**

    bash

    Copy code

    `curl -X PUT http://localhost:8080/bookingservice/bookings/1   -H "Content-Type: application/json"   -d '{
          "bookingId": "1",
          "description": "Updated Booking",
          "price": 150.0,
          "currency": "EUR",
          "subscriptionStartDate": "2023-02-01",
          "email": "updated@example.com",
          "department": "support"
        }'`

### Get a Booking by ID

-   **URL:** `/bookingservice/bookings/{bookingId}`

-   **Method:** `GET`

-   **Response:**

    -   **200 OK**
    -   **404 Not Found**
-   **cURL Example:**

    bash

    Copy code

    `curl -X GET http://localhost:8080/bookingservice/bookings/1`

### Get Bookings by Department

-   **URL:** `/bookingservice/bookings/department/{department}`

-   **Method:** `GET`

-   **Response:**

    -   **200 OK**
    -   **404 Not Found**
-   **cURL Example:**

    bash

    Copy code

    `curl -X GET http://localhost:8080/bookingservice/bookings/department/sales`

### Get All Currencies

-   **URL:** `/bookingservice/bookings/currencies`

-   **Method:** `GET`

-   **Response:**

    -   **200 OK**
-   **cURL Example:**

    bash

    Copy code

    `curl -X GET http://localhost:8080/bookingservice/bookings/currencies`

### Get Sum by Currency

-   **URL:** `/bookingservice/sum/{currency}`

-   **Method:** `GET`

-   **Response:**

    -   **200 OK**
-   **cURL Example:**

    bash

    Copy code

    `curl -X GET http://localhost:8080/bookingservice/sum/USD`

### Process a Booking

-   **URL:** `/bookingservice/bookings/dobusiness/{bookingId}`

-   **Method:** `GET`

-   **Response:**

    -   **200 OK**
    -   **400 Bad Request** (if department is unknown)
    -   **404 Not Found** (if booking is not found)
-   **cURL Example:**

    bash

    Copy code

    `curl -X GET http://localhost:8080/bookingservice/bookings/dobusiness/1`

Error Handling
--------------

The API uses centralized exception handling to provide consistent error responses. The following HTTP status codes are commonly returned:

-   **400 Bad Request:** Returned for validation errors or when a department is unknown.
-   **404 Not Found:** Returned when a requested booking or resource is not found.
-   **500 Internal Server Error:** Returned for any unexpected server errors.

Testing
-------

Unit tests are included in the project and can be run using Maven:

bash

Copy code

`mvn test`

The tests cover the service layer, repository layer, and controller endpoints.

Components Description
----------------------

### Controller Layer

-   **BookingController.java:** Handles HTTP requests and routes them to the appropriate service methods. This layer is responsible for interacting with the client, handling input validation, and returning appropriate HTTP responses.

### Service Layer

-   **BookingService.java:** Contains the business logic for handling bookings. It interacts with the repository layer to store, retrieve, and process bookings. It also includes the logic for delegating business operations to the correct department service.

-   **DepartmentService.java:** An interface defining the contract for department-specific business logic.

-   **ITDepartmentService.java, SalesDepartmentService.java, SupportDepartmentService.java:** Implementations of `DepartmentService` that provide specific business logic for each department.

### Repository Layer

-   **BookingRepository.java:** Manages the in-memory storage of bookings. This layer is responsible for adding, updating, retrieving, and deleting booking data.

### Exception Handling

-   **GlobalExceptionHandler.java:** A centralized exception handler that catches and processes exceptions thrown by the application. It ensures that consistent error responses are returned to the client for various error scenarios, such as validation errors, booking not found, or unexpected server errors.

-   **BookingNotFoundException.java, DepartmentNotFoundException.java:** Custom exceptions that are thrown when a booking or department is not found, respectively.

### Model Layer

-   **Booking.java:** Represents the booking entity with fields such as `bookingId`, `description`, `price`, `currency`, `subscriptionStartDate`, `email`, and `department`. This class also includes validation annotations to enforce business rules.