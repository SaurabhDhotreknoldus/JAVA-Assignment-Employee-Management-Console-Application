@echo off
echo ===============================================================================
echo Starting Nexus 3 and Redis containers via Docker Compose...
echo ===============================================================================

docker compose -f docker\docker-compose.yml up -d

if %ERRORLEVEL% equ 0 (
    echo.
    echo [SUCCESS] Containers started successfully!
    echo - Nexus Repository: http://localhost:8081
    echo - Redis Server:     localhost:6379
    echo.
    echo Note: Nexus may take 60-90 seconds to initialize. Check logs with:
    echo   docker logs -f employee-nexus
) else (
    echo [ERROR] Failed to start Docker containers. Ensure Docker Desktop is running.
)
pause

