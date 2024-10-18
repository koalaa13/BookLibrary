./update_image.sh
helm uninstall moderation-service
helm install moderation-service ./helm/moderation-service