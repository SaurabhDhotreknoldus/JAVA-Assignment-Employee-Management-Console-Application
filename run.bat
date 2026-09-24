@echo off
if not exist "bin\com\nashtech\employeemanagement\EmployeeManagementApp.class" (
    call build.bat
)

java -cp bin com.nashtech.employeemanagement.EmployeeManagementApp
pause
