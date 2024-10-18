./update_image.sh
helm uninstall gateway
helm install gateway ./helm/gateway