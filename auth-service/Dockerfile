FROM eclipse-temurin:19
RUN mkdir /opt/app
COPY build/libs/*.jar /opt/app/app.jar
CMD ["java", "-jar", "/opt/app/app.jar"]
