@echo off
cd ./FFC/adb
adb -s R58M505K2WH pull /mnt/sdcard/Android/data/th.in.ffc/databases/mJHCIS.sdb ../Db_tmp/mJHCIS.sdb
echo success