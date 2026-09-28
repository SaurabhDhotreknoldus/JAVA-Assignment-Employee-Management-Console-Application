@echo off
echo ===============================================================================
echo Publishing Employee Library to Nexus Hosted Repository...
echo ===============================================================================

where mvn >nul 2>nul
if %ERRORLEVEL% equ 0 (
    echo Using Maven on PATH to deploy employee-library...
    cd employee-library
    call mvn clean deploy -s ..\maven-settings\settings.xml
    cd ..
) else (
    echo [NOTICE] 'mvn' command not found on system PATH.
    echo Packaging JAR locally using JDK tools...
    if not exist "employee-library\bin" mkdir "employee-library\bin"
    if not exist "employee-library\target" mkdir "employee-library\target"
    javac -d employee-library\bin employee-library\src\main\java\com\nashtech\library\model\*.java employee-library\src\main\java\com\nashtech\library\dto\*.java employee-library\src\main\java\com\nashtech\library\exception\*.java employee-library\src\main\java\com\nashtech\library\util\*.java
    jar cvf employee-library\target\employee-library-1.0.0.jar -C employee-library\bin .
    echo.
    echo [SUCCESS] Packaged employee-library-1.0.0.jar in employee-library\target\
    echo When Maven is available, deploy to Nexus using:
    echo   cd employee-library ^&^& mvn clean deploy -s ..\maven-settings\settings.xml
)

echo.
pause

