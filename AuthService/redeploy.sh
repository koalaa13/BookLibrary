./update_image.sh
helm uninstall auth-service
helm install auth-service ./helm