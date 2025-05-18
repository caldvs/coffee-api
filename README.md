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

## Environment Variables

This project requires an OpenAI API key to function. The key should be stored as a GitHub secret named `OPENAI_API_KEY`.

### Setting up the GitHub Secret

To set up the secret:

1. Go to your GitHub repository
2. Click on "Settings"
3. Click on "Secrets and variables" under Security
4. Click on "Actions"
5. Click "New repository secret"
6. Name: `OPENAI_API_KEY`
7. Value: Your OpenAI API key
8. Click "Add secret"

The application will automatically use this secret when deployed through GitHub Actions.

### Local Development

For local development, set the environment variable:
```bash
export OPENAI_API_KEY=your-api-key-here
```

Then run the application:
```bash
mvn spring-boot:run
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.
