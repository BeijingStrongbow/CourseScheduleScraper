#!/bin/sh
rsync -auv "$2" "$1"
rm "$2"
java -jar "$1"