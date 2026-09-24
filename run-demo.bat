@echo off
if not exist "bin\com\nashtech\employeemanagement\EmployeeManagementDemo.class" (
    call build.bat
)

java -cp bin com.nashtech.employeemanagement.EmployeeManagementDemo
pause
