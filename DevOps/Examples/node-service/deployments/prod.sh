wget -O /tmp/packer.zip https://releases.hashicorp.com/packer/1.2.1/packer_1.2.1_linux_amd64.zip?_ga=2.172604963.1433869664.1522083868-384145405.1522083868
wget -O /tmp/terraform.zip https://releases.hashicorp.com/terraform/0.11.5/terraform_0.11.5_linux_amd64.zip?_ga=2.42370918.2146529047.1522086131-369822613.1522086131
sudo unzip /tmp/packer.zip -d /bin
sudo unzip /tmp/terraform.zip -d /bin

packer validate deployments/template.json &&
packer build deployments/template.json &&

export TF_VAR_image_id=$(curl -X GET -H"Content-Type: application/json" -H"Authorization: Bearer $DIGITALOCEAN_API_TOKEN""https://api.digitalocean.com/v2/images?private=true" | jq ."images[] | select(.name == \"node-service-$CIRCLE_BUILD_NUM\") | .id") &&

echo "Got the image id of the new digital ocean image" &&
echo $TF_VAR_image_id &&

cd infra && 
terraform init -input=false && 
terraform apply -input=false -auto-approve &&  cd .. &&
git config --global user.email "circleci@support.com" &&
git config --global user.name "Circle CI Script" &&
git add infra && git commit -m 'Deployed $CIRCLE_BUILD NUM [skip ci]' &&
git push origin master &&
echo "Deployed and saved!" &&
echo "Now deleting the image previously created" &&
curl -X DELETE -H "Content-Type: application/json" -H "Authorization: Bearer $DIGITALOCEAN_API_TOKEN" "https://api.digitalocean.com/v2/images/$TF_VAR_image_id" &&
echo "Image deleted successfuly" &&
echo "Done!"