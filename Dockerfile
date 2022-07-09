FROM maven:3.8.6-openjdk-18 AS build
EXPOSE 8080
WORKDIR .
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker", "target/food-manager-0.0.1-SNAPSHOT.jar"]