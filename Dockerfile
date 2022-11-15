FROM openjdk:14
EXPOSE 8080
ARG JAR_FILE=target/challenge-transaction.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]