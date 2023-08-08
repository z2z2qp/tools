FROM openjdk:17
LABEL author="庄佳彬"

VOLUME [ "/home/JRI" ]

RUN mkdir -p /home/JRI

EXPOSE 8080

WORKDIR /home/JRI

COPY build/libs/*.jar /home/JRI/

