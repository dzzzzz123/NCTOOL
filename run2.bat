@echo off

cd bin
psexec -u DZ -p dangzhang "E:\桌面\RuoYi-Vue-master\bin\run.bat"

cd ../ruoyi-ui
start cmd /k "npm run dev"

echo over