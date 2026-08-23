FROM maven:4.0.0-rc-5-eclipse-temurin-25 AS build
WORKDIR /workspace
COPY operator/pom.xml ./operator/pom.xml
COPY adguardclient/pom.xml ./adguardclient/pom.xml
COPY operator/src ./operator/src
COPY adguardclient/src ./adguardclient/src

COPY pom.xml .

RUN --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml mvn -DskipTests package -X

FROM eclipse-temurin:25-jre

COPY --from=build workspace/operator/target/adguard-home-operator-reconcile-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]