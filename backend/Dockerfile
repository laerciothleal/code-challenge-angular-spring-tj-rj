FROM maven:3.8.6-amazoncorretto-17 AS build

COPY src /backend/src

COPY pom.xml /backend

RUN mvn -f /backend/pom.xml clean package

FROM amazoncorretto:17

COPY --from=build /backend/target/spring-boot-back-end.jar /usr/local/lib/spring-boot-back-end.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/usr/local/lib/spring-boot-back-end.jar"]
