@echo off
echo ============================
echo  Running Django migrations...
echo ============================

:: Kích hoạt môi trường ảo nếu cần
:: call venv\Scripts\activate

:: Chạy makemigrations
echo Making migrations...
python manage.py makemigrations stories

:: Chạy migrate
echo Applying migrations...
python manage.py migrate

echo ============================
echo  Done!
echo ============================

pause
