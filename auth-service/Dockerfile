FROM java:8
COPY target/rpm-auth-service-0.0.1-SNAPSHOT.jar /tmp/rpm-auth-service-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/tmp/auth-service-0.0.1-SNAPSHOT.jar","--server.servlet.context-path=/auth", "--spring.profiles.active=dev", "&"]
#ADD .