@echo off
REM Compiles and runs the University Student & Campus Management System.
REM Double-click this file, or type "run" in a terminal opened in this folder.

cd /d "%~dp0"

echo Compiling...
javac -d out -sourcepath src src\Main.java
if errorlevel 1 (
    echo.
    echo Compilation failed. Make sure the JDK is installed and "javac" is on your PATH.
    pause
    exit /b 1
)

echo Starting program...
echo.
java -cp out Main
pause
