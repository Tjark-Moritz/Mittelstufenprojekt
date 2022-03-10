FROM adoptopenjdk:11-jre-openj9
MAINTAINER ??? <???@schule.bremen.de>

ARG JAR_FILE
COPY target/${JAR_FILE} /lf8_project.jar

ARG SPRING_BOOT_PROFILE

ENV WEB_IP "$WEB_ADDRESS_INT"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/lf8_project.jar"]