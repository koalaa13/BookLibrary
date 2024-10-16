./mvnw clean install -DskipTests
docker build -t koalaa13/file-service:latest .
docker push koalaa13/file-service:latest