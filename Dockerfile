FROM maven:3.9.1-amazoncorretto-19 AS build
COPY . /app
WORKDIR /app

RUN mvn clean install

FROM amazoncorretto:19

COPY --from=build ./app/target /app
WORKDIR /app

EXPOSE 3030

ENTRYPOINT ["java", "-jar", "ninamo_service-jar-with-dependencies.jar"]