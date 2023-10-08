🚀 User Authentication Service
🎯 Overview
This User Authentication Service is a dynamic and efficient service developed using the mighty 💪 Spring Boot for the backend, and MySQL for robust database management. The service handles all user-related operations like registration 📝, login 🔒, profile update 🔄, and more with utmost efficiency!

Emphasizing the need for testing, we use JUnit to conduct comprehensive unit tests to ensure the reliability and stability of our service.

🌐 Firebase Integration
To secure our application, we have incorporated Firebase Authentication. The front-end generates a Firebase token that we validate on the server-side by matching it with our user database. If the token matches a user, we generate and return our own JWT for further secured interactions.

✉️ Email Service
Email communication is crucial in an Authentication Service. To handle this, we've integrated Mailjet OAuth for swift and reliable email delivery, making sure users receive timely notifications for actions like password resets or account confirmations.

🐦 Flyway Integration
Database management and versioning are key in any application. That's why we use Flyway for our database migration needs. It enables us to apply version control to our database, making it seamless and easy to track.

🏡 Running Locally
To get the service running in your local environment, you need to have a MySQL server running. Simply clone this repository and hit run! The service will spring up on http://localhost:8080.

📚 API Documentation
For an interactive way to engage with our API, you have two options:

📮 Postman
Import our detailed Postman collection using this link:

[🔗 Postman Collection](https://elements.getpostman.com/redirect?entityId=18057863-2c37171a-009c-40b2-93ce-b6e15cd17098&entityType=collection)

Our Postman collection provides examples of all available requests, complete with required headers and example body content.

📖 Swagger
If you prefer Swagger for API documentation, we have that too! Run the service locally, and access the Swagger UI here:

[🔗 Swagger Documentation](http://localhost:8080/swagger-ui/index.html)

Swagger UI provides a visually engaging way to interact with our API, allowing you to see all available endpoints, required parameters, and even try out the requests directly from your browser.