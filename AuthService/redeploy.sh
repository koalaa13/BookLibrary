./update_image.sh
helm dependency build ./helm
helm uninstall auth-service
helm install auth-service ./helm