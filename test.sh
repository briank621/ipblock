#!/bin/bash

GREEN='\033[0;32m'
NC='\033[0m' # No Color

javac ipblock.java

while read line; do
	echo -e "${GREEN}"$line"${NC}"
	java ipblock $line
done <testcases
