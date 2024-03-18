# Use a JDK 21 base image

FROM apache/solr-nightly:10.0.0-SNAPSHOT-java21 AS build

# Set the working directory in the container
#WORKDIR /app

# Copy the packaged jar file into the container
#COPY target/shortxurl-0.0.1-SNAPSHOT.jar /app/
COPY target/*.jar app.jar

# Expose the port your application runs on
EXPOSE 8080

# Command to run your application
#CMD [java, -jar, shortxurl-0.0.1-SNAPSHOT.jar]
ENTRYPOINT ["java", "-jar", "/app.jar"]

# sudo docker build -t shortxurl-image .
# sudo docker run -p 8080:8080 shortxurl-image
