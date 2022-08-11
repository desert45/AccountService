FROM openjdk:8

#Carpeta de Trabajo
WORKDIR /app

COPY ./target/account-service-0.0.1-SNAPSHOT.jar /app

#EXPOSE 8001

ENTRYPOINT ["java","-jar","account-service-0.0.1-SNAPSHOT.jar"]