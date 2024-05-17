FROM adoptopenjdk:11
# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar file into the container
COPY build/libs/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]
