FROM openjdk:14-alpine
COPY target/planetary-weather-forecast-*.jar planetary-weather-forecast.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "planetary-weather-forecast.jar"]