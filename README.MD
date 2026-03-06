# Our Bank Website - 2 Mar
Create a website for 'Our Bank' as Single Page Application using Plain HTML, CSS, Plain JS.

### *Also added CSS styling to website using BootStrap - 4 Mar
### *Also added Loan EMI Calculator to website - 2 Mar

---
---

# Docker DB & Java App Containers - 25 Feb
* Create an application which can insert a record into a table (wrapped inside a container). Also the compilation should be done in the container itself.
* The database need to be in other container (Use any sql DB, preferably PostGres)
* Just make these 2 containers to interact with each other.
* Build = docker compose up --build
* Observe in Postgres = docker exec -it postgres_db psql -U postgres -d testdb <br> SELECT * FROM users;

---
---

# Jenkins Trigger GitHub Job - 24 Feb
Calculator.java = add(), sub(), mul(), div()
 
CalculatorMain.java = main()
 
* Both of these programs should be pushed into GIT/GitHub
* As soon as the code is pushed to GIT/GitHub, immediately take the code, compile and execute
* As soon as the code is pushed to GIT/GitHub, immediately take the code, compile, test, build and execute

### See /GitCalculatorJob/README.MD for more info.

---
---

# Deploy on Maven Central Repositoy - 20 Feb
Create an application jar file and deploy it in mvnrepository.com

Link for Repo: [Visit](https://mvnrepository.com/artifact/io.github.yashd15/idValidator)

```xml
<!-- Source: https://mvnrepository.com/artifact/io.github.yashd15/idValidator -->
<dependency>
    <groupId>io.github.yashd15</groupId>
    <artifactId>idValidator</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

---
---

# Student REST App - 17 Feb
### *Included Response Entity - 18 Feb
### *Included Spring Boot Testing - 24 Feb
* Test for an service call in Spring Boot.
* Return a proper status code if resource is not found.

Create a Student database having the below structure and generate the given REST APIs
* Use PostGres Database
* Student: RegNo, RollNo, Name, Standard, School, Gender, Percentage
 
GET		/students				    - Get all the students

GET 	/students/regNo			    - Specific student details for the given Registration number

POST	/students					- Insert a student record

PUT		/students/regNo				- Update specific student record

PATCH 	/students/regNo				- Update with given attributes

DELETE	/students/regNo				- Remove the student record for the given Registration number

GET		/students/school?name=KV	- List all students belonging to that school

GET		/students/school/count?name=DPS		        - Total strength in that school

GET		/students/school/standard/count?class=5		- Total number of students in 5th standard

GET 	/students/result?pass=true/false			- List the students in descending order of their percentage (40% and above is pass)

GET		/students/strength?gender=MALE&standard=5	- How many Male students in standard 5

---
---

# Student 2DBs App - 17 Feb
Create a Student Web Page
 
RollNo., Name, Standard, School
 
* Should have INSERT & RESET buttons
* When clicked on INSERT button, it should enter the student record in 2 different database tables

---
---

# Stream API - 9 Feb
* Find the highest salary paid employee
* Find how many male & female employees working in company (numbers)
* Total expense for the company department wise
* Who is the top 5 senior employees in the company
* Find only the names who all are managers
* Hike the salary by 20% for everyone except manager
* Find the total number of employees

---
---

# Maintenance App - 3 Feb
Layout Maintenance Application:

Types: Villa, Apartment, Independent House, Open Site

Total Sites – 35 Sites
First 10 sites: 40 x 60 ft, 
Next 10 sites: 30 x 50 ft, 
Last 15 sites: 30 x 40 ft

Maintenance Charges: 
Open sites: ₹6 per sqft, 
Occupied sites: ₹9 per sqft

Admin Functions: 
Add / Edit / Remove Owner Details, 
Add / Edit / Remove Site Details, 
Collect maintenance and update payment status, 
View pending maintenance: All sites, Specific site, 
Approve / Reject update requests made by site owners

Site Owner Functions:
View details of their own site, 
Request updates to their site details, 
(Changes will be applied only after Admin approval)

---
---

# Bike Race - 10 Feb
Keep logic working as it is, just use Executor Service.

---
---