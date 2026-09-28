@echo off
echo ===============================================================================
echo Starting Employee Redis Spring Boot Application...
echo ===============================================================================

where mvn >nul 2>nul
if %ERRORLEVEL% equ 0 (
    cd employee-redis-app
    call mvn spring-boot:run -s ..\maven-settings\settings.xml
    cd ..
) else (
    echo [NOTICE] Maven is not detected on your command line PATH.
    echo To run the Spring Boot application:
    echo  1. Open IntelliJ IDEA or VS Code.
    echo  2. Open the project root or 'employee-redis-app' directory.
    echo  3. Run the main class: com.nashtech.redisapp.EmployeeRedisApplication
    echo.
    echo Or install Maven and run:
    echo  cd employee-redis-app ^&^& mvn spring-boot:run -s ..\maven-settings\settings.xml
)

echo.
pause

