./update_image.sh
helm dependency build ./helm
helm uninstall gateway
helm install gateway ./helm