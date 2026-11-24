**# Institute Management Platform**



**A Java-based Institute Management Platform designed to simplify and organize academic and administrative operations within a training institute.**



**The application is being developed as a modular full-stack system using Spring Boot for backend services, PostgreSQL for data persistence, and React for the planned frontend.**



**\*\*Development Status:\*\* Backend development in progress. Student Management and Course Management CRUD modules are implemented.**



**## Project Overview**



**The platform aims to centralize academic and administrative operations, including student registration, course management, trainer allocation, batch scheduling, enrollment, attendance, and fee tracking.**



**The project focuses on applying core software engineering principles through a practical business application, including REST API development, layered architecture, database design, validation, exception handling, and automated testing.**



**## Technology Stack**



**| Category | Technology |**

**|---|---|**

**| Programming Language | Java 17 |**

**| Backend Framework | Spring Boot 4 |**

**| Build Tool | Maven |**

**| Database | PostgreSQL |**

**| ORM | Spring Data JPA / Hibernate |**

**| API Architecture | RESTful APIs |**

**| Validation | Jakarta Bean Validation |**

**| Testing | JUnit, Mockito |**

**| Version Control | Git and GitHub |**

**| Frontend | React (planned) |**



**## Implemented Modules**



**### 1. Student Management**



**- Register new students.**

**- Retrieve all student records.**

**- Retrieve student details by ID.**

**- Update student information.**

**- Delete student records.**

**- Validate incoming request data.**

**- Handle missing student records and invalid requests.**



**### 2. Course Management**



**- Create and manage courses.**

**- Retrieve all available courses.**

**- Retrieve course details by ID.**

**- Update course information.**

**- Delete courses.**

**- Validate course name, code, duration, and fees.**

**- Prevent duplicate course codes.**

**- Manage course active status.**

**- Handle missing course records.**



**## Planned Modules**



**The following modules will be implemented incrementally:**



**- Trainer Management**

**- Batch Management**

**- Student Enrollment**

**- Attendance Management**

**- Fee Management**

**- Authentication and Role-Based Access Control**

**- Dashboard and Reports**

**- React-based frontend**



**## Project Architecture**



**The backend follows a layered architecture to separate responsibilities and improve maintainability.**



**```text**

**Client / API Request**

&#x20;       **|**

&#x20;       **v**

**Controller Layer**

&#x20;       **|**

&#x20;       **v**

**Service Layer**

&#x20;       **|**

&#x20;       **v**

**Repository Layer**

&#x20;       **|**

&#x20;       **v**

**PostgreSQL Database**

**```**



**### Package Structure**



**```text**

**com.peakprosys.institute**

**|**

**|-- controller**

**|-- dto**

**|   |-- request**

**|   |-- response**

**|-- entity**

**|-- exception**

**|-- repository**

**|-- service**

**|   |-- impl**

**|-- InstituteManagementApiApplication.java**

**```**



**The application uses DTOs to separate API request and response structures from database entities. Business logic is maintained in the service layer, while repositories handle database operations.**



**## Testing**



**Automated testing is included as part of the development process.**



**Current testing coverage includes:**



**- Course service unit tests using JUnit and Mockito.**

**- Successful course creation and retrieval.**

**- Duplicate course code validation.**

**- Course update and deletion scenarios.**

**- Handling of missing course records.**

**- Maven build and verification using `clean verify`.**



**Student and additional integration/API tests will be expanded as development progresses.**



**## Prerequisites**



**Install the following:**



**- Java Development Kit (JDK) 17**

**- PostgreSQL**

**- Git**



**Maven Wrapper is included in the repository, so a separate Maven installation is not required.**



**## Getting Started**



**### 1. Clone the repository**



**```bash**

**git clone https://github.com/hemangi-dev05/institute-management-platform.git**

**cd -institute-management-platform**

**```**



**### 2. Create the PostgreSQL database**



**Connect to PostgreSQL and execute:**



**```sql**

**CREATE DATABASE institute\_management;**

**```**



**### 3. Configure database credentials**



**The application reads the PostgreSQL password from the `DB\_PASSWORD` environment variable.**



**For Windows PowerShell:**



**```powershell**

**$env:DB\_PASSWORD = "your-local-postgresql-password"**

**```**



**Replace the placeholder with your local PostgreSQL password.**



**Do not commit actual database credentials to GitHub.**



**### 4. Run the application**



**On Windows:**



**```powershell**

**.\\mvnw.cmd spring-boot:run**

**```**



**The backend application starts at:**



**`http://localhost:8081`**



**### 5. Run tests and verify the build**



**```powershell**

**.\\mvnw.cmd clean verify**

**```**



**## API Documentation**



**Base URL:**



**`http://localhost:8081`**



**### Student Management API**



**| HTTP Method | Endpoint | Description |**

**|---|---|---|**

**| POST | `/api/students` | Register a student |**

**| GET | `/api/students` | Retrieve all students |**

**| GET | `/api/students/{id}` | Retrieve a student by ID |**

**| PUT | `/api/students/{id}` | Update student details |**

**| DELETE | `/api/students/{id}` | Delete a student |**



**### Course Management API**



**| HTTP Method | Endpoint | Description |**

**|---|---|---|**

**| POST | `/api/courses` | Create a course |**

**| GET | `/api/courses` | Retrieve all courses |**

**| GET | `/api/courses/{id}` | Retrieve a course by ID |**

**| PUT | `/api/courses/{id}` | Update course details |**

**| DELETE | `/api/courses/{id}` | Delete a course |**



**## Development Roadmap**



**| Module | Status |**

**|---|---|**

**| Project Setup | Completed |**

**| Student Management | Completed |**

**| Course Management | Completed |**

**| Trainer Management | Planned |**

**| Batch Management | Planned |**

**| Student Enrollment | Planned |**

**| Attendance Management | Planned |**

**| Fee Management | Planned |**

**| Authentication \& Authorization | Planned |**

**| Dashboard \& Reports | Planned |**

**| React Frontend | Planned |**



**## Engineering Goals**



**The project is being developed with emphasis on:**



**- Clean and maintainable code.**

**- Separation of concerns through layered architecture.**

**- REST API design and implementation.**

**- Database relationships and transaction management.**

**- Input validation and centralized exception handling.**

**- Unit and integration testing.**

**- Version control and incremental development.**



**## Repository**



**\*\*GitHub:\*\* https://github.com/hemangi-dev05/institute-management-platform**



**---**



**\*This project is actively under development. Features and documentation will be updated as new modules are implemented.\***

