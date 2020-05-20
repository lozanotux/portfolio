FROM node:12.16.3

# Add metadata
LABEL name="node-service"

# Enable the port that the container listens
EXPOSE 3000

# Docker moves you to that directory
WORKDIR /app

# Allows me to copy files from my local machine to the container
ADD package.json /app/

# Run arbitrary commands and install declared dependencies
RUN npm install

ADD . /app/

# Only one CMD goes by Dockerfile and sets a root id process to keep container alive
CMD ["node", "server.js"]