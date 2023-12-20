FROM openjdk:21
LABEL author="庄佳彬"

VOLUME [ "/home/JRI" ]

RUN mkdir -p /home/JRI

EXPOSE 8080

WORKDIR /home/JRI

COPY build/libs/tools-*-SNAPSHOT.jar /home/JRI/app.jar

ENTRYPOINT [ "java","-jar","app.jar" ]