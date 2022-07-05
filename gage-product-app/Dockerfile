FROM openjdk:11.0.15

EXPOSE 8080

ADD ./target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
