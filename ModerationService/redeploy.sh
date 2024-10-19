./update_image.sh
helm dependency build ./helm
helm uninstall moderation-service
helm install moderation-service ./helm