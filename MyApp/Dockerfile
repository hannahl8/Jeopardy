FROM gradle:jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle war -x test

FROM tomcat:10.1.24-jdk17-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/build/libs/*.war /usr/local/tomcat/webapps/MyApp.war
EXPOSE 8080
CMD ["catalina.sh", "run"]