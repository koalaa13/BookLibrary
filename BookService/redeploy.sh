./update_image.sh
helm dependency build ./helm
helm uninstall book-service
helm install book-service ./helm