# couponProject
## Coupon System Project:
A school project based off of pre-made instructions of John Bryce College.
#### Made out of two layers:
1. ***Coupon System*** - the infrastracture on top of which the Web App sits.
2. ***Couplux*** -  A dynamic web project.
 
 ## Main Layers:

### COUPON SYSTEM

##### 3 User Types :  ADMIN, COMPANY, CUSTOMER.
	A. ADMIN : Can add, update, delete and see all the info of companies and customers.
	B. COMPANY : Can add, delete, update and see the info of all of its coupons.
	C. CUSTOMER : Can purchase and see all coupons.
    
##### 3 Main Levels:
	1. DAO -		The basic level that synchronizes with the database.
	2. FACADES -		The level that contains the buisness logic of the system.
	3. COUPON SYSTEM -	The main object through which you may connect to the system. 
 *Works with **Apache Derby** Database.*

### COUPLUX 
  
  The name of the coupon web app. 
  Includes a **Tomcat** server infrastracture, **Jersey Web Services**, Filters and a UI Design based on **Angular 5**.
  
   *Based on the COUPON SYSTEM jar file.*
  
 #### Demo Link: http://couplux.eu-west-1.elasticbeanstalk.com/
