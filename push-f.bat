@echo off
git add .
git commit -m "update"
git push origin development --force
echo ============================
echo  Done!
echo ============================

pause