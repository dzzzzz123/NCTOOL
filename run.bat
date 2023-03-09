@echo off

cd bin
start run.bat

cd ../ruoyi-ui
start cmd /k "npm run dev"

echo over