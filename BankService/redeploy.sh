./update_image.sh
helm dependency build ./helm
helm uninstall bank-service
helm install bank-service ./helm