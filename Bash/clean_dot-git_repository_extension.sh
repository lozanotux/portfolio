#!/bin/bash

GIT_REPO="https://github.com/user/repository.git"
# Extract last 3 characters
EXT=${GIT_REPO: -3}

# And compare with git
if [ "$EXT" == "git" ]
then
  # If true, remove the ".git" extension from URL
  GIT_REPO=${GIT_REPO%.git}
fi

# And return the URL without ".git" extension
echo $GIT_REPO