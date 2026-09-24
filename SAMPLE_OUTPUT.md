# Sample Execution Output

This document contains the verified console execution logs demonstrating the successful execution of the **Employee Management Console Application** developed for the NashTech Java Essentials Assignment.

---

## 1. Automated Execution Demo Output (`EmployeeManagementDemo`)

```text
===============================================================================
   EMPLOYEE MANAGEMENT CONSOLE APPLICATION - AUTOMATED EXECUTION DEMO          
===============================================================================

>>> STEP 1: Adding Initial Employees (Sample dataset from assignment specifications)
[SUCCESS] Added 4 initial employees successfully.

>>> STEP 2: Display All Employees
+------+----------------------+----------------------+-----------------+----------+
| ID   | Name                 | Department           | Salary          | Status   |
+------+----------------------+----------------------+-----------------+----------+
| 101  | Alex                 | Engineering          |        90000.00 | Active   |
| 102  | Sam                  | Engineering          |       125000.00 | Active   |
| 103  | John                 | Finance              |       140000.00 | Inactive |
| 104  | Priya                | Engineering          |       150000.00 | Active   |
+------+----------------------+----------------------+-----------------+----------+
Total records: 4

>>> STEP 3: Search Employee by ID (ID: 102 - Sam)
[SUCCESS] Found employee:
+-------------------------------------------------------+
|                   EMPLOYEE DETAILS                    |
+-------------------------------------------------------+
| ID          : 102                                     |
| Name        : Sam                                     |
| Department  : Engineering                             |
| Salary      : 125000.00                               |
| Status      : Active                                  |
+-------------------------------------------------------+

>>> STEP 4: Search Non-Existent Employee (ID: 999 - Triggers EmployeeNotFoundException)
[SUCCESS] Properly caught expected exception -> EmployeeNotFoundException: Employee not found with ID: 999

>>> STEP 5: Display Employees in Department: 'Engineering'
+------+----------------------+----------------------+-----------------+----------+
| ID   | Name                 | Department           | Salary          | Status   |
+------+----------------------+----------------------+-----------------+----------+
| 101  | Alex                 | Engineering          |        90000.00 | Active   |
| 102  | Sam                  | Engineering          |       125000.00 | Active   |
| 104  | Priya                | Engineering          |       150000.00 | Active   |
+------+----------------------+----------------------+-----------------+----------+
Total records: 3

>>> STEP 6: Display Employees in Department: 'Finance'
+------+----------------------+----------------------+-----------------+----------+
| ID   | Name                 | Department           | Salary          | Status   |
+------+----------------------+----------------------+-----------------+----------+
| 103  | John                 | Finance              |       140000.00 | Inactive |
+------+----------------------+----------------------+-----------------+----------+
Total records: 1

>>> STEP 7: Stream API Demo - Find active employees with salary > 100,000
Stream Pipeline:
  employees.stream()
           .filter(Employee::isActive)
           .filter(e -> e.getSalary() > 100000)
           .forEach(System.out::println);

+------+----------------------+----------------------+-----------------+----------+
| ID   | Name                 | Department           | Salary          | Status   |
+------+----------------------+----------------------+-----------------+----------+
| 102  | Sam                  | Engineering          |       125000.00 | Active   |
| 104  | Priya                | Engineering          |       150000.00 | Active   |
+------+----------------------+----------------------+-----------------+----------+
Total records: 2

Extracted Names of Matching Active Employees:
  * Sam
  * Priya

>>> STEP 8: Testing Duplicate Employee ID Exception
[SUCCESS] Properly caught expected exception -> DuplicateEmployeeException: An employee with ID 101 already exists in the system.

>>> STEP 9: Testing Invalid Employee Data Exception (Negative Salary)
[SUCCESS] Properly caught expected exception -> InvalidEmployeeDataException: Employee salary must be non-negative. Provided: -5000.0

>>> STEP 10: Modern Java Bonus Features (Record & Immutability)
Converted Employee to Java Record: EmployeeRecord[id=104, name=Priya, department=Engineering, salary=150000.0, active=true]
Record Component Accessors: ID=104, Name=Priya, Active=true

===============================================================================
               ALL VERIFICATION CHECKS PASSED SUCCESSFULLY!                   
===============================================================================
```

---

## 2. Interactive Console Application Output (`EmployeeManagementApp`)

```text
===============================================================================
         NASH TECH - JAVA ESSENTIALS: EMPLOYEE MANAGEMENT SYSTEM              
===============================================================================
[INFO] Sample employees preloaded (Alex [101], Sam [102], John [103], Priya [104])

-------------------------------------------------------------------------------
                             MAIN MENU                                         
-------------------------------------------------------------------------------
 1. Add Employee
 2. Display All Employees
 3. Search Employee by ID
 4. Display Employees by Department
 5. Display Active Employees with Salary > Threshold (Stream API Demo)
 6. View Employee as Modern Java Record (Bonus Demonstration)
 7. Reload / Reset Initial Sample Dataset
 8. Exit
-------------------------------------------------------------------------------
Enter your choice (1-8): 2

--- [2] All Registered Employees ---
+------+----------------------+----------------------+-----------------+----------+
| ID   | Name                 | Department           | Salary          | Status   |
+------+----------------------+----------------------+-----------------+----------+
| 101  | Alex                 | Engineering          |        90000.00 | Active   |
| 102  | Sam                  | Engineering          |       125000.00 | Active   |
| 103  | John                 | Finance              |       140000.00 | Inactive |
| 104  | Priya                | Engineering          |       150000.00 | Active   |
+------+----------------------+----------------------+-----------------+----------+
Total records: 4

Enter your choice (1-8): 5

--- [5] Filter Active Employees by Salary Threshold ---
Enter Minimum Salary Threshold (e.g. 100000): 100000

Active employees earning more than 100000.00:
+------+----------------------+----------------------+-----------------+----------+
| ID   | Name                 | Department           | Salary          | Status   |
+------+----------------------+----------------------+-----------------+----------+
| 102  | Sam                  | Engineering          |       125000.00 | Active   |
| 104  | Priya                | Engineering          |       150000.00 | Active   |
+------+----------------------+----------------------+-----------------+----------+
Total records: 2

Names of matching employees (Stream extraction):
 - Sam
 - Priya
```

---

## 3. Unit Test Execution Output (`EmployeeServiceTest`)

```text
===============================================================================
                 RUNNING EMPLOYEE SERVICE UNIT TESTS                           
===============================================================================
[PASS] testAddAndRetrieveEmployee
[PASS] testSearchNonExistentEmployeeThrowsException
[PASS] testDuplicateEmployeeThrowsException
[PASS] testFilterByDepartment
[PASS] testFilterActiveEmployeesWithSalaryThreshold
[PASS] testInvalidEmployeeDataValidations
-------------------------------------------------------------------------------
Test Summary: 6 Passed, 0 Failed
===============================================================================
```
