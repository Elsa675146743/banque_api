# On commence avec une image Maven pour compiler le code
FROM maven:3.8.4-openjdk-17 AS build

# On copie tout notre projet dans l'image
COPY . .

# On compile l'application (on saute les tests pour éviter les erreurs)
RUN mvn clean package -DskipTests

# On crée une nouvelle image plus légère avec seulement le JDK pour exécuter l'application
FROM openjdk:17-jdk-slim

# On copie le fichier JAR généré depuis l'étape de build
COPY --from=build /target/*.jar app.jar

# On expose le port sur lequel l'application tourne
EXPOSE 8080

# Commande pour lancer l'application
ENTRYPOINT ["java", "-jar", "/app.jar"]