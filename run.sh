#!/bin/bash
set -e

# docker build
echo "Step 1. docker build"
sudo docker build -t transfer:0.0.1 .

# docker run
echo "Step 1. docker run"
sudo docker run -d -p 9393:9393 transfer:0.0.1
