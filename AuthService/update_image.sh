./mvnw clean install -DskipTests
docker build -t koalaa13/auth-service:latest .
docker push koalaa13/auth-service:latest