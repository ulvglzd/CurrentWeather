# Current Weather API

This repository contains a REST API service that provides current weather data for requested city. Users can register, log in using JWT (JSON Web Tokens) authentication, upload profile images, and request current weather information for cities. The API fetches weather data from an external API using a Feign client, stores it in a database, and sends the weather information to the logged-in user's email address. Additionally, it manages data expiration: if data for requested city is present in database and not expired, it fetches data from database. Otherwise, it make an api call to get the fresh data from weatherstack.com, and refreshes the expired data in database. It also includes a scheduling service for data cleanup in the database once in a month. 

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Authentication](#authentication)


## Features

- User Registration and Login: Users can create an account and log in using their email and password. Authentication is handled using JWT tokens. There is a validation for credentials. 

- Profile Image Upload: Users have the option to upload a profile image to personalize their accounts.

- Current Weather Data: Users can request current weather data for a specified city.

- Data Retrieval: The API retrieves weather data from an external source via a Feign client and saves it in a database.

- Email Notification: Weather information is sent to the logged-in user's email address.

- Data Expiration: The system checks for the presence and expiration of weather data in the database. If data is expired, it is deleted and refreshed.
- Data Clean-up: A scheduling service ensures data cleanup in the database once in a month.

## Technologies

- Java 17
- Spring Boot 3.1.5
- Spring Security 6.1.5
- Spring Data JPA
- Database (MySQL)
- JWT (JSON Web Tokens)
- Feign Client
- Email Services (SMTP Gmail)
- Swagger
- Logging SLF4J
- External Weather API (Weatherstack.com)

## Installation

To set up the Current Weather API on your local machine, follow these steps:

1. **Clone this repository:**
   ```bash
   git clone https://github.com/ulvglzd/CurrentWeather
2. **Navigate to the project directory:**
   cd current-weather-api
3. **Configuration parameters:**

Sensitive data, such as database connection details, API keys, and email credentials, should not be stored directly in the `application.properties` file or pushed to GitHub for security reasons. Instead, create an `env.properties` file to store these values securely.

Here's an example of the data you should include in your `env.properties` file:

```properties
# Database Configuration
DB_DATABASE=your_db
DB_USER=your_username
DB_PASSWORD=yourpassword

# External API Key
API_KEY=your_api_key

# JWT Secret Key
SECRET_KEY=secret_key

# Email Credentials
MAIL_USERNAME=your_email
MAIL_PASSWORD=your_app_password_given_by_gmail
```
4. **Build and Run the Project:**
```
mvn clean install
mvn spring-boot:run
```
## Usage

To use the Current Weather API, you should interact with the available endpoints. Detailed documentation on the API endpoints and how to use them can be found in the [API Endpoints](#api-endpoints) section.

## API Endpoints

Here are some of the main API endpoints you can use:

- `POST /api/v1/auth/register`: Register a new user.
- `POST /api/v1/auth/login`: Log in and receive a JWT token.
- `POST /api/v1/account/upload`: Upload a profile image.
- `GET /api/v1/current-weather/{city}`: Get current weather data for the specified city.


## Authentication

Authentication in this API is based on JSON Web Tokens (JWT). When users log in, they receive a token that should be included in the headers of subsequent API requests for authentication. You can adjust the expiration dates of both token and refresh token in `application.properties`.








   
