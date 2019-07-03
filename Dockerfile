FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/cat_demo-1.0.0-SNAPSHOT.jar app.jar
RUN mkdir -p /data/appdata/cat
ADD target/classes/cat/client.xml /data/appdata/cat
RUN echo "Asia/Shanghai" > /etc/timezone
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Djava.library.path=/app","-jar","/app.jar"]