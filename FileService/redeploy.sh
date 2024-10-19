./update_image.sh
helm dependency build ./helm
helm uninstall file-service
helm install file-service ./helm