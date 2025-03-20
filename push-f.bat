@echo off
git add .
git commit -m "update"
git push -f origin development
echo ============================
echo  Done!
echo ============================

pause