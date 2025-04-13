Project Overview
The Employee Management System is an Android application built with Kotlin and Jetpack Compose. It enables users to efficiently manage employee information, such as adding, editing, and deleting employee records. The app integrates with a backend API to retrieve and update employee data, offering a smooth user experience.

Key features include creating account, login, logout, viewing a list of employees, updating and deleting employee data, which also stores localy the login status. The user interface is entirely designed with Jetpack Compose, ensuring a modern and responsive UI experience.

Prerequisites
  1. Android Studio (latest stable version)
  2. Kotlin (latest stable version)
  3. Jetpack Compose (for UI)
  4. Retrofit (for API calls)
  
Steps to Run
  1. Clone the repository: git clone https://github.com/mohamedNilshad/employee_management_system.git
  2. Open the project in Android Studio.
  3. Ensure you have the necessary SDK versions installed
  4. In Android Studio, go to File > Sync Project with Gradle Files to ensure all dependencies are downloaded.
  5. Build the project
  6. Run the app on an emulator or a physical device by clicking the Run button in Android Studio.
  7. You can download the APK file from "APK FILE HEAR" Folder in this project

API Endpoints
The application uses the following API endpoints to interact with employee data and users data:
  1. GET /employees - Fetches a list of all employees.
  2. POST /employees - Adds a new employee.
  3. PUT /employees/{id} - Updates an existing employee by ID.
  4. DELETE /employees/{id} - Deletes an employee by ID.
  5. GET /employees/{id} - Fetches the details of a specific employee by ID.
These endpoints are used by the app via Retrofit to make network requests and interact with the backend API.

UI Technology Used
  1. The app's user interface was developed using Jetpack Compose, Android's modern UI toolkit. All screens, including the employee list, login functionality, and forms for adding and   editing employees, are implemented using Compose.
  
  2. API used MockAPI.io

End Note
  1. Dependency Injection (DI) is not yet implemented.
  2. Search and Filter features are incomplete and may not reflect real-time UI changes in all cases.

