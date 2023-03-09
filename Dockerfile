FROM openjdk:17
LABEL author="jri"

VOLUME [ "/home/JRI" ]

RUN mkdir -p /home/JRI

WORKDIR /home/JRI

COPY target/tools.jar /home/JRI/app.jar

ENTRYPOINT [ "java","-jar","app.jar" ]