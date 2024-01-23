FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Añade el script de inicialización de la base de datos
COPY sisaBD.sql /docker-entrypoint-initdb.d/

# Instala wait-for-it
RUN apt-get update && apt-get install -y wait-for-it

ENTRYPOINT ["wait-for-it", "postgres:5432", "--", "java", "-jar", "/app.jar"]