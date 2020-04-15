## DataWareHouse implemented by using 3 kinds of database

## Description
A accident report website for users to visualize all accidents in US since 2016.  
Users can self-report accidents with or without user names.  
Admin will handle all reports.

## Function
### Main Page
1. Show numbers of accidents per state in the map.
2. Show numbers of accidents per county per state in the map
3. Searching numbers of accidents by road, city and state and show in google map.
4. Analysis and visualize number of accidents by humidity.
5. Analysis and visualize number of accidents by visibility.
6. Analysis and visualize number of accidents by weather-condition.

### User page
1. Register a new account with username, password, email, city, state, phone number.
2. Reset passwords if forget by username, email, phone number, city ,state.
3. Login and logout.
4. View my account page including username, city, state, email, phone number.
5. Update account page.
6. Reset password after logging in by enter the old password.
7. Update report settings weather by username or anonymous.
8. Self report by username, road, county, city, state, time and severity.
9. View all history report.
10. Withdraw pending report.

### Admin page
1. Login and Logout.
2. Handle all user reports, approve and deny by comments.
3. View all reports.
4. Update all reports.
5. Delete reports for untrue.
6. Delete users.

## Construction&Technology Stack

### Back-end Technology
1. Spring MVC
2. Spring Data JPA
3. Spring Security JWT for authentication and authorization
4. Docker

### DataBase
Neo4j, MongoDB, PostGre


### Front-end Technology 
Angular
1. Installing angular   
> npm i -g angular
2. Run angular 
> ng serve

### CSS style 
BootStrap

### Visualization
1. ECharts
2. Google Map API


### RESTFUL APIs
||Operation|API|Description|
|:---|---|---|---|
||GET|api/accidents/numbersByState|Select numbers of accidents per state|
||GET|api/accidents/numbersByCounty/:state|Select numbers of accidents per county by state|
||GET|api/accidents/accidentsByRoad/:state/:city/:road|Searching by road, city and state|
||GET|api/accidents/numbersByHumidity|Select numbers of accidents by humidity|
||GET|api/accidents/numbersByVisibility|Select numbers of accidents by visibility|
||GET|api/accidents/numbersByWeatherCondition|Select numbers of accidents by weather condition|
||POST|api/user/signup|User registration|
||GET|api/user/infoCheck|Check info is matched or not|
||PUT|api/user/updatePassword/:username|Reset password for user|
||POST|api/user/login|Check info is matched or not|
||GET|api/user/:username|Get all user info|
||PUT|api/user/updateAllInfo/:username|update new info for user|
||PUT|api/user/updateSettings/:username|Update report settings|
||POST|api/user/self-report/:username|Self-report accidents|
||GET|api/user/reports/:username|View all history reports|
||DELETE|api/user/:username/:reportID|Delete pending report|
||POST|api/admin/login|Check info is matched for admin|
||PUT|api/admin/:reportID|Update report details|
||DELETE|api/admin/:reportID|Delete user|
||DELETE|api/admin/:username|Delete user|
||GET|api/admin/allUser|Get all users|
||GET|api/admin/allAccidents|Get top 100 recent accidents|


