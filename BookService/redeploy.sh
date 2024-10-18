./update_image.sh
helm uninstall book-service
helm install book-service ./helm/book-service