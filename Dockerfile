FROM openjdk:11
EXPOSE 8082
ADD target/stockservice_local.jar stockservice_local.jar
ENTRYPOINT ["java", "-jar", "stockservice_local.jar"]