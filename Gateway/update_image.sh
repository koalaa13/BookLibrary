./mvnw clean install -DskipTests
docker build -t koalaa13/Gateway:latest .
docker push koalaa13/Gateway:latest