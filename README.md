# couponProject
coupon project

Coupon System Project:
A school project based off of pre-made instructions of John Bryce College.
Made out of two layers:
 1. ***Coupon System*** - the infrastracture on top of which the Web App sits.
 2. ***Couplux*** -  A dynamic web project.
 
 ### MAIN LAYERS:

#### COUPON SYSTEM

3 main users :  ADMIN, COMPANY, CUSTOMER.
    A. ADMIN : Can add, update, delete and see all the info of companies and customers.
    B. COMPANY : Can add, delete, update and see the info of all of its coupons.
    C. CUSTOMER : Can purchase and see all coupons.
    
*Works with Apache Derby Database.

##### 3 Main Levels:
**DAO** - The basic level that synchronizes with the database.

**FACADES** - The level that contains the buisness logic of the system.

**COUPON SYSTEM** - The main object through which you may connect to the system. 

#### COUPLUX 
  
  The name of the coupon web app. 
  Includes a **Tomcat** server infrastracture, **Jersey Web Services**, Filters and a UI Design based on **Angular 5**.
  
  *Based on the COUPON SYSTEM jar file.
  
