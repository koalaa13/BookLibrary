./mvnw clean install -DskipTests
docker build -t koalaa13/ModerationService:latest .
docker push koalaa13/ModerationService:latest