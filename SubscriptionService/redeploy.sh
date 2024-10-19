./update_image.sh
helm dependency build ./helm
helm uninstall subscription-service
helm install subscription-service ./helm