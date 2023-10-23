FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

RUN apk --no-cache add maven

COPY backend /app

RUN mvn package -DskipTests

EXPOSE 8080
ENTRYPOINT ["java","-jar","target/openapi-spring-1.0.jar"]