FROM openjdk:11
VOLUME /tmp
EXPOSE 8082
RUN mkdir -p /app/
ADD build/libs/CarCatalogAPI.jar /app/CarCatalogAPI.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/app/CarCatalogAPI.jar"]