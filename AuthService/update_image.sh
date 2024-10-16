./mvnw clean install -DskipTests
docker build -t koalaa13/AuthService:latest .
docker push koalaa13/AuthService:latest