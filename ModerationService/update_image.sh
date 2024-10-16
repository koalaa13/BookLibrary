./mvnw clean install -DskipTests
docker build -t koalaa13/moderation-service:latest .
docker push koalaa13/moderation-service:latest