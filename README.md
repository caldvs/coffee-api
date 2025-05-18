# Coffee API

A Spring Boot REST API for managing coffee products with AI-powered validation. This API allows you to create, read, update, and delete coffee products while ensuring that the coffee names are valid using AI validation.

## Features

- CRUD operations for coffee products
- AI-powered validation for coffee drink names
- PostgreSQL database integration
- OpenAPI/Swagger documentation
- Reactive endpoints using Project Reactor
- Comprehensive error handling

## Technologies

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Project Reactor
- OpenAPI/Swagger
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- PostgreSQL
- Maven

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/caldvs/coffee-api.git
   ```

2. Create PostgreSQL database:
   ```sql
   CREATE DATABASE apidb;
   ```

3. Update `application.properties` with your database configuration if needed:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/apidb
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```

### API Documentation

Once the application is running, you can access the Swagger UI documentation at:
```
http://localhost:8080/docs
```

## API Endpoints

### Products

- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get a product by ID
- `POST /api/products` - Create a new product
- `PUT /api/products/{id}` - Update a product
- `DELETE /api/products/{id}` - Delete a product

### Request/Response Examples

#### Create Product
```json
POST /api/products
{
    "name": "Cappuccino",
    "description": "Classic Italian coffee with steamed milk foam",
    "price": 3.99
}
```

#### Success Response
```json
{
    "id": 1,
    "name": "Cappuccino",
    "description": "Classic Italian coffee with steamed milk foam",
    "price": 3.99,
    "verified": true
}
```

## Error Handling

The API includes comprehensive error handling:

- 400 Bad Request - Invalid input or validation errors
- 404 Not Found - Resource not found
- 500 Internal Server Error - Server-side errors

Error responses include detailed messages and, where applicable, validation details.

## Development

The project uses standard Maven project structure:

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           ├── controller/
│   │           ├── model/
│   │           ├── repository/
│   │           └── service/
│   └── resources/
└── test/
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

