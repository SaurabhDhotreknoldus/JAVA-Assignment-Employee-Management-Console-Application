@echo off
echo Stopping Nexus and Redis containers...
docker compose -f docker\docker-compose.yml down
echo Containers stopped.
pause
