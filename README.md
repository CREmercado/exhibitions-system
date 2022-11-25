# Exhibitions System

## Description
This is a Spring Boot based application. It offers a catalogue of current exhibitions showing their themes, prices, exhibit halls, start and ending date, and also opening and closing time.

## Usage
There are different views within the application. The main page redirects either to a registration form or to a login page.  

Once the user is registered and logged in, then a proper view will be displayed based on his role.  

The system considers 3 different user roles.
- [ ] Administrator: this role has the ability to add new exhibits, and cancel or reactivate a exhibition.
- [ ] Authorized User: this role can list all active exhibits and also buy tickets.
- [ ] Normal User: this role can just list all active exhibitions.

## Ecosystem
There are different topics/elements that were applied during the development and implementation of the project.
- [ ] Java Naming Conventions.
- [ ] Connection to a Postgres Database.
- [ ] Dates implemented through java.time.
- [ ] Internationalization and Localization for English and Spanish language. 
- [ ] Thymeleaf implementation in order to create a functional GUI.
- [ ] PGR implementation ( protection against re-sending data to the server when refreshing the page).
- [ ] Authentication and authorization through Spring Security.
- [ ] Password encryption.
- [ ] Log4j library applied to log important events.
- [ ] Comments contained on controllers methods using Javadoc.
- [ ] Modular tests applied using Mockito and MockMvc.
- [ ] Data validation implemented.
- [ ] Bootstrap used within the GUI part.
- [ ] Git was used through Gitlab tool.
- [ ] Lombok annotations were used.
- [ ] Hibernate was employed for data access.
- [ ] Some level of Exception Handling was applied.
- [ ] The java version used was 11.

## Authors and acknowledgment
Main author: Christian Mercado - christian.erick.mf@gmail.com

## Project status
The project has been developed and implemented in order to complete a Java Training. In this sense, the 1.0 version of the code has been established with the current delivery.