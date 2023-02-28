# IV1201 Job Application Project - BackEnd

This is the repository containing the source code for the Back End Server for the Job Application recruitment service.

This Readme file contains information related to initilization, installation and general information on how to run the application.

## Setup

To run the application in chosen IDE the following frameworks, dev-tools and dependencies are required:

 * Java Development Kit: Version 17 (Coretto)
 * Spring Boot: Version 3.0 or later
 * Spring Security: 6.0 or later
 * Maven Project Management Tool
 * JPA/Hibernate: 6.0 or later
 * JSON WEB TOKEN (io.jsonwebtoken): Version 0.11.5

Adding new dependencies or updating existing ones is done by changing the content of the pom.xml file.

### Database

The application uses PostGreSQL relational database as the internal database to manage data. 
The __application.propeties__ should contain the necessary datasource information and env variables, together with the pom.xml database dependencies. For local testing run the __existing-database.sql__ script in the psql CLI. After creating the database, connect it to the application and it should be able to run Back End transactions.

The database is then handled in the application by Hibernate/JPA, which can be configured in the __application.properties__ file. An example is to view queries run by Hibernate in the console set: __spring.jpa.show-sql=true__ within that file. 

### Deployment

The application is deployed on Heroku cloud platform and can be reached by the base url:

<https://backendjobbapp.herokuapp.com/>

Where REST endpoints can be tested using a API platform such a Postman.

The application uses Heroku-Postgres and can be configured in the __application.properties__ to change the env variables if another database is required after deployment on Heroku.

## Security

Security of the application is handled in the __WebSecurityConfig__ class of the security package. Changes to permitted endpoints, request-methods and authorization is done in the __securityFilterChain__ Bean. Changes to CORS-configuration on allowed headers, methods and origin of requests is done in the __corsConfigurationSource__ Bean.

The application uses JWT-tokens to authorize and identify users by checking the Authorization header of every request done to this application. To change how these are handled one could modify the __JWTAuthenticateFilter__ class in the security package. Changing the expiration date or how tokens are generated is done in the __JWTService__ class.

## Execution

For localtesting first build the application and then run it using your IDE. 

Endpoints can then be tested by writing out the localhost URL in chosen browser or using an API platform like Postman.
