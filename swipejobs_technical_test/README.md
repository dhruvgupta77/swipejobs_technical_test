# Technical test for Swipejobs
Simple matching engine that presents Workers with appropriate Jobs

# Getting Started
Make sure that you have Java and Maven installed on your computer.
run below command from project directory. 
mvn clean install
java -jar target\swipejobs_technical_test-0.0.1-SNAPSHOT.jar

# Integrations
This service currently depends on below APIs to get the list of available Workers and Jobs
Workers API : http://test.swipejobs.com/api/workers
Jobs API    : http://test.swipejobs.com/api/jobs

# API Docs
GET /jobmatcher/{workerId}
This REST API will return a list of matching jobs for the given worker based on on skills, certifications, location preference and Driver Licence requirement

# Testing
Run the below Maven command for running the tests
mvn test

# Improvements
Further test cases can be added but due to time constraint couldn't be added
Job searching algorithm can be further improved