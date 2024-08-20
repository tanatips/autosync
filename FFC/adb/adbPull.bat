@echo off
cd ./FFC/adb
adb -s null pull /sdcard/Android/data/th.in.ffc/databases/mJHCIS.sdb ../Db_tmp/mJHCIS.sdb
echo success