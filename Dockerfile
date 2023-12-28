FROM openjdk:17-jdk
WORKDIR /build
LABEL authors="sin-yechan"
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
ADD ${JAR_FILE} auth-service.jar
ENV	USE_PROFILE deploy
ENTRYPOINT ["java","-Dskip.tests","-Dspring.profiles.active=${USE_PROFILE}", "-Djava.security.egd=file:/dev/./urandom","-jar","auth-service.jar"]
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","auth-service.jar"]