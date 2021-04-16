#!/bin/bash
set -e
echo Downloading spigot-1.13.2.jar.

curl -o spigot1.13.2/spigot-1.13.2.jar https://cdn.getbukkit.org/spigot/spigot-1.13.2.jar

echo Compiling plugin.
mvn package

echo Next steps: Now follow the README to add your custom world to spigot1.13.2/
