# Use a JDK 21 base image

# FROM apache/solr-nightly:10.0.0-SNAPSHOT-java21 AS build

# Set the working directory in the container
# WORKDIR /app

# Copy the packaged jar file into the container
# COPY target/shortxurl-0.0.1-SNAPSHOT.jar /app/

# Expose the port your application runs on
# EXPOSE 8080

# Command to run your application
# CMD ["java", "-jar", "shortxurl-0.0.1-SNAPSHOT.jar"]


# sudo docker build -t shortxurl-image .
# sudo docker run -p 8080:8080 shortxurl-image


# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Stage 2: Run the application
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/target/shortxurl-0.0.1-SNAPSHOT.jar ./shortxurl-0.0.1-SNAPSHOT.jar

# Expose the port your application runs on
EXPOSE 8080

# Command to run your application
CMD ["java", "-jar", "shortxurl-0.0.1-SNAPSHOT.jar"]
