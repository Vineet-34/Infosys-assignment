# Reward Points Calculation Service

## Prerequisites for Running the Project

Before running this project, ensure that the following prerequisites are met:

### 1. **Java Development Kit (JDK)**

- **Version**: Java 23
- **Installation**:
    - Download and install JDK from [Oracle's website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or [OpenJDK](https://openjdk.java.net/).
    - Set up the `JAVA_HOME` environment variable and add it to your system `PATH`.

  **Verify Installation:**
  ```bash
  java -version
  ```

### 2. **Maven**

- **Version**: Apache Maven 3.6 or higher
- **Installation**:
    - Download and install Maven from the [official Apache website](https://maven.apache.org/download.cgi).
    - Set up the `MAVEN_HOME` environment variable and add it to your system `PATH`.

  **Verify Installation:**
  ```bash
  mvn -v
  ```

### 3. **MySQL Database**

- **Version**: MySQL 5.7 or higher
- **Installation**:
    - Download and install MySQL from the [official MySQL website](https://dev.mysql.com/downloads/).
    - Make sure MySQL is running and accessible on your local machine or remote server.
    - Create a database for the project. For example, `reward_points_db`.

  **Verify Connection**:
  ```bash
  mysql -u root -p
  ```

### 4. **Configuration in `application.properties`**

- **Database Connection**:
  Update the `application.properties` file with your MySQL database connection details:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/reward_points_db
  spring.datasource.username=root
  spring.datasource.password=yourpassword
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  ```

- **Important**: Replace `yourpassword` with the actual password for the MySQL `root` user (or any other MySQL user you've created).

### 5. **IDE (Integrated Development Environment)**

- **Recommended**: IntelliJ IDEA, Eclipse, or Visual Studio Code (with Java and Spring Boot plugins).
- **Importing the Project**:
    - Clone the repository to your local machine.
    - Open the project in your IDE as a **Maven Project**.

### 6. **Postman or cURL (For API Testing)**

- To test the API endpoints, you can use **Postman** (download from [here](https://www.postman.com/downloads/)) or **cURL**.
- Sample API endpoints can be accessed after the Spring Boot application is running.

### 7. **Running the Application**

- **Step 1**: Clone the repository or download the project to your local machine.
- **Step 2**: Build the project using Maven:
  ```bash
  mvn clean install
  ```
- **Step 3**: Run the application:
  ```bash
  mvn spring-boot:run
  ```

- **Application Running**:
    - By default, the Spring Boot application will run on `http://localhost:8080`.

### 8. **API Endpoints**

After running the application, you can access the following REST API endpoints:

- **Calculate Rewards**:
    - **URL**: `POST /calculate/{customerId}`
    - **Request Params**: `startDate`, `endDate` (in `yyyy-MM-dd` format)
    - **Response**: JSON containing reward points information.

  Example:
  ```bash
  POST http://localhost:8080/calculate/customer123?startDate=2024-01-01&endDate=2024-03-31
  ```

### 9. **Testing the Service**

The project includes unit and integration tests. To run the tests, use the following Maven command:

   ```bash
   mvn test
