./mvnw clean install -DskipTests
docker build -t koalaa13/gateway:latest .
docker push koalaa13/gateway:latest