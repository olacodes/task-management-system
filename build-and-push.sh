#!/bin/bash

gradle build -x test # to omit test

docker build -t tag-name/image-name .

docker push tag-name/image-name