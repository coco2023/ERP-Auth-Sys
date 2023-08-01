FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} erp-product-service.jar

ENTRYPOINT ["java", "-jar", "/erp-product-service.jar"]

EXPOSE 9001