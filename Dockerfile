FROM maven:4.0.0-rc-5-eclipse-temurin-25 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

FROM eclipse-temurin:25-jre

COPY --from=build workspace/target/adguard-home-operator-0.1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]