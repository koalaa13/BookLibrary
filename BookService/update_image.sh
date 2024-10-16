./mvnw clean install -DskipTests
docker build -t koalaa13/BookService:latest .
docker push koalaa13/BookService:latest