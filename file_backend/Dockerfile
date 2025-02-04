# Usar la imagen base de Java 8
FROM openjdk:8-jdk-alpine

# Crear el directorio de trabajo
WORKDIR /usr/src/archivosCasavieja
# eliminar todo lo que se encuentra adentro de archivosCasaVieja
RUN rm -rf ./
# Copiar todo lo que se encuentra adentro de archivosCasaVieja
COPY ./file_backend/archivosCasaVieja/ ./

# Copiar el jar de la aplicacion ROOT.jar que se encuentra en target
COPY ../target/ROOT.jar app.jar

# Exponer el puerto de la aplicación
EXPOSE 8080
#ejecutamos el jar
ENTRYPOINT ["java", "-jar", "app.jar"]