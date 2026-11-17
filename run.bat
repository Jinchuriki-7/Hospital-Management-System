@echo off
title Hospital Management System (Console)
echo ===================================================
echo     Compiling Hospital Management System...
echo ===================================================
if not exist "bin" mkdir bin
javac -d bin src\com\hospital\model\enums\*.java src\com\hospital\model\*.java src\com\hospital\util\*.java src\com\hospital\service\*.java src\com\hospital\Main.java
if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed!
    pause
    exit /b %errorlevel%
)
echo [SUCCESS] Compilation complete. Launching application...
echo.
java -cp bin com.hospital.Main
pause
