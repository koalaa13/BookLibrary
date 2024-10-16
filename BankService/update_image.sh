./mvnw clean install -DskipTests
docker build -t koalaa13/bank-service:latest .
docker push koalaa13/bank-service:latest