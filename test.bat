@echo off
if not exist "bin\com\nashtech\employeemanagement\EmployeeServiceTest.class" (
    call build.bat
)

java -cp bin com.nashtech.employeemanagement.EmployeeServiceTest
pause
