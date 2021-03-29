FROM openjdk:14-alpine

EXPOSE 8080

COPY ./target/tdd-*.jar app.jar

ARG jvm_opts=""
ENV JDK_JAVA_OPTIONS="${jvm_opts} -Xmx350m"

ENTRYPOINT ["java", "-jar", "/app.jar"]