# Get an image of jdk from docker hub
FROM openjdk:8-jdk-alpine

# COPY ./target/Devops-Test-1.0.jar /usr/app/
COPY target/Gym-Center-App-1.0.jar Gym-Center-App-1.0.jar
#WORKDIR /usr/app

# Expose the application port
EXPOSE 8089

# Create the .jar file in target folder
#ADD target/Devops-Test.jar Devops-Test.jar

# RUN sh -c 'touch Devops-Test-1.0.jar'

# Get the .jar file from target and put it into the docker image
ENTRYPOINT ["java","-jar","Gym-Center-App-1.0.jar"]
