#!/bin/bash
#
# Use this shell script to compile (if necessary) your code and then execute it. Below is an example of what might be found in this file if your program was written in Python
#
#python ./src/sessionization.py ./input/log.csv ./input/inactivity_period.txt ./output/sessionization.txt

javac src/main/nyu/xiao/*.java
java -cp src/ main.nyu.xiao.Main ./input/log.csv ./input/inactivity_period.txt ./output/sessionization.txt
rm src/main/nyu/xiao/*.class