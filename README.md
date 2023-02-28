# IV1201 Job Application Project - BackEnd

This is the repository containing the source code for the Back End Server for the Job Application recruitment service.

This Readme file contains information related to initilization, installation and necessary frameworks needed to run the application.

## Setup

To run the application in chosen IDE the following frameworks, dev-tools and dependencies are required:

 * Java Development Kit: Version 17 (Coretto)
 * Spring Boot: Version 3.0 or later
 * Spring Secuirty: 6.0 or later
 * Maven Project Management Tool
 * JPA/Hibernate: 6.0 or later

### Database

The application uses PostGreSQL relational database as the internal database to manage data. 
The application.propeties should contain the necessary datasource information and env variables, together with the pom.xml database dependencies. For local testing run the __existing-database.sql__ script in the psql CLI. After creating the database, connect it to the application and it should be able to run Back End transactions.


### Deployment

The application is deployed on Heroku cloud platform and can be reached by the base url:

<https://backendjobbapp.herokuapp.com/>

Where REST endpoints can be tested using a API platform such a Postman.

The application uses Heroku-Postgres and can be configured in the application.properties to change the env variables if another database is required after deployment on Heroku.

## Security

Security of the application is handled in the __WebSecurityConfig__ class.

