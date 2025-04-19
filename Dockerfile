# Étape de build
FROM maven:3.8.4-openjdk-17 AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier pom.xml et récupérer les dépendances
COPY pom.xml .
#RUN mvn dependency:go-offline

# Copier les sources de l'application
COPY src ./src

# Compiler et empaqueter l'application (en ignorant les tests)
RUN mvn clean package -DskipTests

# Étape d'exécution avec une image plus légère
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copier le JAR généré depuis l'étape de build
COPY --from=build /app/target/article-0.0.1-SNAPSHOT.jar .

# Exposer le port utilisé par l'application
EXPOSE 6060

# Lancer l'application
ENTRYPOINT ["java", "-jar", "/app/article-0.0.1-SNAPSHOT.jar"]
