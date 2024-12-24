#!/bin/sh

mvn clean package
clear
java -jar target/task-tracker-1.0-SNAPSHOT.jar