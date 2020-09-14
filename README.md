# EmployeeLeaveManagementSystem
A system has been built by the system administrator where employee records can be created and employee leaves can be managed. **Spring Boot** is used as a development framework. 
Different types of employees have roles. In order to manage these roles and create a secure system, **Spring Security** has been used for authentication and authorization operations. REST Services in the system have been tested through Postman. There are user names and passwords of employees in the database. Passwords are kept encrypted in the database. The user names and passwords of the employees are chosen the same.
## Roles
### 1. Admin: 
- Admin can create, update and delete employee record.
- Admin can view employee record.
- The admin department can create and update the record.
- The admin can assign employees to departments and appoint managers to departments.
### 2. Manager:
- It accepts the leave drafts of the employees in the manager department if they comply with the conditions.
### 3. Employee:
- Employees can view their current leave entitlements.
- Employees create a draft to get permission.
- Employees can update or delete drafts when they are not approved or rejected by their manager.
- Employees can view accepted leave drafts.
