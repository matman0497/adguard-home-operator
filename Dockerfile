FROM eclipse-temurin:25

COPY target/adguard-home-operator-0.1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]