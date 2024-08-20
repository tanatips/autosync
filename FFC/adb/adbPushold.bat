@echo off
cd ./FFC/adb
adb -s R58M505K2WH push ../Db_tmp/uJHCIS.db /mnt/sdcard/Android/data/th.in.ffc/databases/uJHCIS.db
echo success