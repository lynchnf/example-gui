@echo off
if "%OS%" == "Windows_NT" setlocal
set CURRENT_DIR=%cd%
cd /d %0\..
start "Example GUI" /B java -jar ..\lib\example-gui-${pom.version}.jar
cd %CURRENT_DIR%
