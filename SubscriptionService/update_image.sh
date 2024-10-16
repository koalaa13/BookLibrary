./mvnw clean install -DskipTests
docker build -t koalaa13/SubscriptionService:latest .
docker push koalaa13/SubscriptionService:latest