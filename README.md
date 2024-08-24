# Client Database Application

This is a comprehensive database application designed for service providers to manage clients and their services. The app allows users to store client details, keep track of services provided, and even attach photos to services for documentation.

## Features

- **Add New Clients**: Easily add clients to the database with their contact information.
- **Client List**: View a list of all clients stored in the application.
- **Client Details**: View detailed information about individual clients.
- **Edit Client Information**: Update client details such as name, phone number, email, and more.
- **Delete Clients**: Remove clients and their associated data from the database.
- **Add Services**: Record services provided to clients, including description, date, and price.
- **Photo Attachments to Services**: Add photos from the gallery to a service entry to keep visual records of the work done.
- **Cloud Backup with Firebase**: Securely backup and restore client data and service records to/from Firebase for safekeeping.
- **Service Photos Backup**: Automatically back up service-related photos to Firebase Storage, ensuring no data is lost.

## Technologies Used

- **Kotlin**: For the Android application development.
- **Jetpack Compose**: For UI development with modern, declarative components.
- **Firebase**: For cloud backup and restoring client data and service-related photos.
- **Room Database**: For local storage of client and service data.
- **Hilt**: For dependency injection to make the codebase modular and maintainable.

## Installation
1. Clone the repository to your local machine using the following command:
`git clone https://github.com/your-username/client-database-app.git`
2. Open the project in Android Studio
3. Build and run the application on an emulator or a physical device

## Usage
1. Open the application
2. Click the 'Add Client' button to add a new client to the database
3. Click on a client in the list to view their details
4. To edit a client's information, click the 'Edit' button on the client's details screen
5. To delete a client from the database, click the 'Delete' button on the client's details screen

## Credits
This application was designed and developed by Martin Szüč.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
