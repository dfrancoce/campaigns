# Campaigns
This project populates an embedded mongodb database with advertising campaigns stored in the data.json file stored in the resources folder. Each campaign is composed of different platforms (Google AdWords, Facebook, Instagram). It exposes a simple API endpoint to return this data. Also, there are some images stored that are used in these campaigns. These images are exposed in another endpoint so when a client consumes this data she is able to complete the campaign information with the respective image.

## Getting started

These instructions will get you a copy of the project up and running on a local machine for testing purposes.

### Prerequisites

This is a Spring Boot project which is built using gradle.

### Starting the app

To run the app just execute the following command in the root directory 
```
gradle bootRun
```
To execute tests or others gradle tasks you can type the following command to get more information
```
gradle tasks
```
Once everything is up and running, You should be able to see the list of campaigns and access to the detail of each one clicking in the rows. 
```
localhost:8080
```
An UI with the endpoints provided by the API can be seen at 
```
localhost:8080/swagger-ui.html
```
