# Create a LoadBalancer for our project
resource "digitalocean_loadbalancer" "web" {
    name   = "node-balancer-v2"
    region = "nyc3"

    forwarding_rule {
        entry_port     = 80
        entry_protocol = "http"

        target_port     = 3000
        target_protocol = "http"
    }

    healthcheck {
        port     = 3000
        protocol = "http"
        path     = "/"
    }

    droplet_tag = "${digitalocean_tag.web.name}"
}


# Create a tag for our droplets
resource "digitalocean_tag" "web" {
    name = "website"
}


# Create a new Web Droplet in the nyc3 region
resource "digitalocean_droplet" "web" {
    count    = 2
    image    = "${var.image_id}"
    name     = "node-service-v2"
    region   = "nyc3"
    size     = "512mb"
    ssh_keys = [21998511]
    tags     = ["${digitalocean_tag.web.id}"]

    lifecycle {
        create_before_destroy = true
    }

    provisioner "local-exec" {
        command = "sleep 160 && curl ${self.ipv4_address}:3000"
    }

    user_data = <<EOF
#cloud-config
coreos:
    units:
        - name: "node-v2.service"
        command: "start"
        content: |
            [Unit]
            Description=Devops Demo
            After=docker.service

            [Service]
            ExecStart=/usr/bin/docker run -d -p 3000:3000 node-service
EOF
}