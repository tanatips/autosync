@echo off
cd ./FFC/adb
adb -s R58T80HZ3GF push ../FFC-Xml/FFC-Stat-population.xml /sdcard/Android/data/th.in.ffc/files/FFC-Stat-population.xml
echo success