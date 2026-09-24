@echo off
echo ===============================================================================
echo Compiling Employee Management Console Application...
echo ===============================================================================

if not exist "bin" mkdir bin

javac -d bin -sourcepath src\main\java src\main\java\com\nashtech\employeemanagement\*.java src\main\java\com\nashtech\employeemanagement\model\*.java src\main\java\com\nashtech\employeemanagement\repository\*.java src\main\java\com\nashtech\employeemanagement\service\*.java src\main\java\com\nashtech\employeemanagement\exception\*.java src\main\java\com\nashtech\employeemanagement\util\*.java

if %ERRORLEVEL% equ 0 (
    echo [SUCCESS] Compilation completed successfully into bin/ directory.
) else (
    echo [ERROR] Compilation failed. Please check errors above.
)
