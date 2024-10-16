./mvnw clean install -DskipTests
docker build -t koalaa13/subscription-service:latest .
docker push koalaa13/subscription-service:latest