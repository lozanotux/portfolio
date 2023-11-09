#!/bin/bash

# Option 1: with slash /
EXAMPLE_TEXT="John Doe"
RESULT=$(echo $EXAMPLE_TEXT | sed "s/Doe/Travolta/g")
echo "Case 1 --> $RESULT"

# Option 2: with At @ 
# avoid difficult to interpret characters
# and use, regular expressions.
EXAMPLE_TEXT="image: docker.io/project/app:tag"
RESULT=$(echo $EXAMPLE_TEXT | sed "s@/app:.*@/app:new-value@g")
echo "Case 2 --> $RESULT"