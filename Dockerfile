FROM eclipse-temurin:21-jdk-alpine

# Diretório de trabalho
WORKDIR /app

# Copia o JAR (ajuste o nome conforme necessário)
COPY target/gymapp-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta usada pela aplicação
EXPOSE 8080 5000

# Comando de inicialização e debug
ENTRYPOINT ["sh", "-c", "\
    if [ \"$DEBUG\" = \"true\" ]; then \
      java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5000 -jar app.jar --spring.profiles.active=auth,postgres; \
    else \
      java -jar app.jar --spring.profiles.active=auth,postgres; \
    fi \
"]