# Pollmanager

The project consist in a test to simulate a poll manager

# Frameworks used

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Initializr](https://start.spring.io/)
* [Thymeleaf](https://www.thymeleaf.org/)
* Maven
* JPA
* [Bootstrap](https://getbootstrap.com/)
* [Chart.js](https://github.com/chartjs/Chart.js)
* [H2](https://www.h2database.com/)

# Work

This version dont acomplished topics about create poll or edit response use without be logged.

I choose Spring Boot because is a simple framework to generated a webserver without need to install Tomcat, JBoss or Wildfly.

The project are using H2 to persiste data. So, every time you shutdown the server all data will be lost. If its necessary use a MySQL, just configure commented lines in application.properties file in resources dir

# Structure

The project use a common MVC pattern, separating views (resorces/templates), controllers, models and i like to use business classes to manager business rules. Its ease to create test for each rule and can be reused if necessary. And two more packages: exception and helper, their names are auto explainable

In business classes, i put some comments where can be added some rules if are requisited by the 'client'

# Build

To build project, you will need maven installed and clone the repository. After run the command:
```
mvn package
```
a jar called pollmanager-0.0.1-SNAPSHOT.jar will be generated in the target folder

If you dont have the maven, in bin folder has a copy of jar

# Run

To run you will need the java configured and run the command:

```
java -jar target/pollmanager-0.0.1-SNAPSHOT.jar
```

Make sure the port 8080 are free

Open your favorite browser and access the [link](htt://localhost:8080)