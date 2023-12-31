<br />
<div align="center">
  <h2>Restaurant Microservice</h2>
  <p align="center">
   Backend of a system that centralizes the services and orders of a restaurant chain that has different branches in the city.
  </p>
</div>

### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
* ![MongoDB](https://img.shields.io/badge/MongoDB-47A248.svg?style=for-the-badge&logo=MongoDB&logoColor=white)
* ![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
* ![OpenAPI](https://img.shields.io/badge/OpenAPI-<COLOR>?style=for-the-badge&logo=OpenAPI%20Initiative&logoColor=white)


<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these steps.

### Prerequisites

* JDK 17 [https://jdk.java.net/java-se-ri/17](https://jdk.java.net/java-se-ri/17)
* Gradle [https://gradle.org/install/](https://gradle.org/install/)
* MySQL [https://dev.mysql.com/downloads/installer/](https://dev.mysql.com/downloads/installer/)

### Recommended Tools
* IntelliJ Community [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
* Postman [https://www.postman.com/downloads/](https://www.postman.com/downloads/)

### Installation

1. Clone the repository
2. Change directory
   ```sh
   cd power-up-v4-restaurants-v1
   ```
3. Create a new database in MySQL called plazoleta
4. Update the database connection settings
   ```yml
   # src/main/resources/application-dev.yml
   spring:
      datasource:
          url: jdbc:mysql://<your-url-database>:<your-MySQL-port>/plazoleta
          username: root
          password: <your-password>
   ```
5. Create a new database in MongoDB called TraceabilityDB
6. Update the database connection settings
   ```yml
   # src/main/resources/application-dev.yml
   spring:
      data:
        mongodb:
          uri: mongodb://<your-uri-Mongo>
   ```
6. Add the microservices connection settings
   ```yml
   # src/main/resources/application-dev.yml
   app:
      urls:
        urlToUserMicroService: "http://<your-url-userMicroservice>:<your-port>/"
        urlToMessagingMicroService: "http://<your-url-messagingMicroservice>:<your-port>/"
   ```
7. After the tables are created execute src/main/resources/data.sql content to populate the database
8. Open Swagger UI and search the /auth/login endpoint and login with userDni: 123, password: 1234

<!-- USAGE -->
## Usage

1. Right-click the class RestaurantMicroserviceApplication and choose Run
2. You must run the User Microservice project
3. Open [http://localhost:8091/swagger-ui/index.html](http://localhost:8091/swagger-ui/index.html) in your web browser

<!-- ROADMAP -->
## Tests

- Right-click the test folder and choose Run tests with coverage
