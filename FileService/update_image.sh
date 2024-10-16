./mvnw clean install -DskipTests
docker build -t koalaa13/FileService:latest .
docker push koalaa13/FileService:latest