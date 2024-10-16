./mvnw clean install -DskipTests
docker build -t koalaa13/BankService:latest .
docker push koalaa13/BankService:latest