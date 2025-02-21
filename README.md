# Deutsche Bank HW

This service provides an API for managing NaceDetails entities. It allows uploading NaceDetails from a CSV file, retrieving NaceDetails by ID.
Service is built using Spring Boot and uses an in-memory H2 database to store the NaceDetails.
NaceDetailsService.java is the service class that provides the business logic for managing NaceDetails. It build with more
CRUD operations like for extending the functionality in future which is not exposed in the controller.

## Prerequisites

- Java 22
- Maven 3.9.2 or higher

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/your-repo/deutsche-bank-hw.git
    cd deutsche-bank-hw
    ```

2. Install the Maven Wrapper:
    ```sh
    mvn -N io.takari:maven:wrapper
    ```

## Running the Application

To run the application using Spring Boot, use the following command:
```sh
./mvnw spring-boot:run

# Or, if you have Maven installed:
```sh
mvn spring-boot:run
```
##Generating the Executable JAR
To generate the executable JAR file, use the following command:
```sh
./mvnw clean package
```
The generated JAR file will be located in the target directory.

##Usage
Once the application is running, you can access the API at http://localhost:8080/v1/api/nace
Or check the swagger documentation at http://localhost:8080/swagger-ui.html

#Endpoints
POST /v1/api/nace/putNaceDetails/csv - Upload NaceDetails from a CSV file.
GET /v1/api/nace/{id} - Retrieve NaceDetails by ID.
GET /v1/api/heathCheck - Check the health of the application.
