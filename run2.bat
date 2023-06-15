@echo off

cd bin
start "psexec" psexec -u campltservice -p Mima4camxitongfuwuzhanghao "E:\test\nctool-master\bin\run.bat"

cd ../ruoyi-ui
start cmd /k "npm run dev"

echo over