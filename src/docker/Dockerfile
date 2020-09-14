FROM maven:latest AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package spring-boot:repackage

FROM openjdk:13-alpine
COPY --from=build /home/app/target/codex-trial-1.0.jar /usr/local/lib/codex-trial.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/codex-trial.jar"]