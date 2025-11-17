FROM gradle:8.14.0-jdk17
WORKDIR /opt/app
COPY ./build/libs/MyTunes-1.0-SNAPSHOT.jar ./
