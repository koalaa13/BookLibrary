./mvnw clean install -DskipTests
docker build -t koalaa13/book-service:latest .
docker push koalaa13/book-service:latest