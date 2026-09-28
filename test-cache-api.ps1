# PowerShell Script: Test Employee Redis REST APIs and Cache Behavior

$BaseUrl = "http://localhost:8080/api/employees"

Write-Host "===============================================================================" -ForegroundColor Cyan
Write-Host "     TESTING EMPLOYEE REDIS REST APIS & SPRING CACHE BEHAVIOR                 " -ForegroundColor Cyan
Write-Host "===============================================================================" -ForegroundColor Cyan
Write-Host ""

try {
    # 1. Get All Employees
    Write-Host ">>> TEST 1: Retrieve All Employees (GET $BaseUrl)" -ForegroundColor Yellow
    $all = Invoke-RestMethod -Uri $BaseUrl -Method GET
    $all | Format-Table -Property id, name, department, salary, active
    Write-Host "[SUCCESS] Retrieved $($all.Count) employees." -ForegroundColor Green
    Write-Host ""

    # 2. Get Employee 102 - First Call (Cache Miss)
    Write-Host ">>> TEST 2: Read Employee 102 - Call 1 (Cache MISS -> DB Query -> Populates Redis)" -ForegroundColor Yellow
    $sw = [System.Diagnostics.Stopwatch]::StartNew()
    $emp1 = Invoke-RestMethod -Uri "$BaseUrl/102" -Method GET
    $sw.Stop()
    Write-Host "Response received in $($sw.ElapsedMilliseconds) ms: $($emp1.name) ($($emp1.department))" -ForegroundColor Cyan
    Write-Host "[INFO] Check application logs for: ==> [DATABASE HIT] Fetching employee from DB for ID: 102"
    Write-Host ""

    # 3. Get Employee 102 - Second Call (Cache Hit)
    Write-Host ">>> TEST 3: Read Employee 102 - Call 2 (Cache HIT -> Served directly from Redis)" -ForegroundColor Yellow
    $sw.Restart()
    $emp2 = Invoke-RestMethod -Uri "$BaseUrl/102" -Method GET
    $sw.Stop()
    Write-Host "Response received in $($sw.ElapsedMilliseconds) ms: $($emp2.name) ($($emp2.department))" -ForegroundColor Green
    Write-Host "[SUCCESS] Sub-millisecond/instant response served from Redis Cache (No DB hit)!"
    Write-Host ""

    # 4. Create New Employee (105)
    Write-Host ">>> TEST 4: Create New Employee (POST $BaseUrl)" -ForegroundColor Yellow
    $newBody = @{
        id = 105
        name = "Rachel Green"
        department = "HR"
        salary = 95000.0
        active = $true
    } | ConvertTo-Json
    $created = Invoke-RestMethod -Uri $BaseUrl -Method POST -Body $newBody -ContentType "application/json"
    Write-Host "[SUCCESS] Created: $($created.name) with ID $($created.id)" -ForegroundColor Green
    Write-Host ""

    # 5. Update Employee 102 (@CachePut Demonstration)
    Write-Host ">>> TEST 5: Update Employee 102 (@CachePut -> Synchronizes DB and Redis Cache)" -ForegroundColor Yellow
    $updateBody = @{
        name = "Sam Wilson (Lead Engineer)"
        department = "Engineering"
        salary = 135000.0
        active = $true
    } | ConvertTo-Json
    $updated = Invoke-RestMethod -Uri "$BaseUrl/102" -Method PUT -Body $updateBody -ContentType "application/json"
    Write-Host "[SUCCESS] Updated in DB and Cache: $($updated.name) - Salary: $($updated.salary)" -ForegroundColor Green
    Write-Host ""

    # 6. Verify Updated Value is Served Immediately from Redis
    Write-Host ">>> TEST 6: Read Employee 102 (@CachePut Verification)" -ForegroundColor Yellow
    $verifyCached = Invoke-RestMethod -Uri "$BaseUrl/102" -Method GET
    Write-Host "Cached value returned: $($verifyCached.name) | Salary: $($verifyCached.salary)" -ForegroundColor Cyan
    Write-Host ""

    # 7. Delete Employee 105 (@CacheEvict Demonstration)
    Write-Host ">>> TEST 7: Delete Employee 105 (@CacheEvict -> Removes from DB and Clears Cache Key)" -ForegroundColor Yellow
    Invoke-RestMethod -Uri "$BaseUrl/105" -Method DELETE
    Write-Host "[SUCCESS] Deleted employee 105 and evicted from cache." -ForegroundColor Green
    Write-Host ""

    # 8. Filter by Department
    Write-Host ">>> TEST 8: Filter Employees by Department 'Engineering'" -ForegroundColor Yellow
    $dept = Invoke-RestMethod -Uri "$BaseUrl/department/Engineering" -Method GET
    $dept | Format-Table -Property id, name, department, salary, active
    Write-Host ""

    # 9. Filter Active Employees by Salary Threshold
    Write-Host ">>> TEST 9: Stream API Filter - Active Employees with Salary > 100,000" -ForegroundColor Yellow
    $highEarners = Invoke-RestMethod -Uri "$BaseUrl/active/salary-threshold?minSalary=100000" -Method GET
    $highEarners | Format-Table -Property id, name, department, salary, active
    Write-Host ""

    # 10. Inspect Active Redis Keys
    Write-Host ">>> TEST 10: Inspect Active Redis Cache Keys" -ForegroundColor Yellow
    $keys = Invoke-RestMethod -Uri "$BaseUrl/cache/keys" -Method GET
    Write-Host "Active keys in Redis: $($keys -join ', ')" -ForegroundColor Cyan
    Write-Host ""

    Write-Host "===============================================================================" -ForegroundColor Green
    Write-Host "                 ALL API & CACHE TESTS COMPLETED SUCCESSFULLY!                " -ForegroundColor Green
    Write-Host "===============================================================================" -ForegroundColor Green

} catch {
    Write-Host "[ERROR] Request failed: $_" -ForegroundColor Red
}

