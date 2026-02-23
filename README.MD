## Bike Race
Keep logic working as it is, just use Executor Service.

## Maintenance App
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

## Stream API


## Student 2DBs App
Create a Student Web Page
 
RollNo., Name, Standard, School
 
* Should have INSERT & RESET buttons
* When clicked on INSERT button, it should enter the student record in 2 different database tables

## Student REST App
Create a Student database having the below structure and generate the given REST APIs
* Use PostGres Database
* Student: RegNo, RollNo, Name, Standard, School, Gender, Percentage
 
GET		/students									- Get all the students
 
GET 	/students/regNo							- Specific student details for the given Registration number
 
POST	/students									- Insert a student record
 
PUT		/students/regNo							- Update specific student record
 
PATCH 	/students/regNo							- Update with given attributes
 
DELETE	/students/regNo							- Remove the student record for the given Registration number
 
GET		/students/school?name=KV					- List all students belonging to that school
 
GET		/students/school/count?name=DPS				- Total strength in that school
 
GET		/students/school/standard/count?class=5			- Total number of students in 5th standard
 
GET 	/students/result?pass=true/false				- List the students in descending order of their percentage (40% and above is pass)
 
GET		/students/strength?gender=MALE&standard=5	- How many Male students in standard 5
 
