THIS DOCUMENT IS MADE SO THAT YOU CAN UNDERSTAND HOW THIS APPLICATION WILL TAKE THE DATA AND ONBOARD THE CUSTOMERS.

WHAT THIS WILL DO: Give you a guideline for backend as to what the request body should be, what the response body should be, and how the data gets seperated to different purpose-specific tables and then form as a template as to what data should frontend take, and what screens to have(React Native frontend) when all the flow is completed and tested. 

Before we start, remember to follow DRY principles, this project is built with SpringBoot and Maven build. As the app is not released yet, there is no issue of reformatting the database tables as long as it achieves the desired result and below written flow. Focus on feature and flow implementation rather than testing. I will test the flow myself on POSTMAN, once the apis are completed.  


Abbreviates - (r)- required field
(o)- Optional field

1. User Registration 

user enters email(r), mobile number(o), First Name(r), Last Name(o)

Call types- POST


2. Garage Registration(to take the garage data)

user enters garage name(r), address line 1(r), address line 2(o), city(r), country(r)(default-India), pincode(r), state(r), gst number(o), business registration number(o), has_branch(boolean)(to be a toggle switch)(just a toggle switch to store boolean, nothing to do with this data as this is an MVP), garage logo(o), garage website url(o), business hours(r)

Call types- POST, GET, PATCH

3. Payment Configurations(to set up the payment methods of the garage)


user sees a field with cards(different payment methods each with a toggle). For whatever the toggle switch is turned on, that is taken to the garage payment configurations and when he puts up an invoice he can select from the toggled options

Payment methods are predefined in @Enums.java so only these options should be available
if there is no paymentmethods table, then create it. this table will have a foreign key UUID(to garage UUID from the garage table), will have fields configured payment methods, created_at, updated_at only. 

Call types- POST, GET, PATCh

4. Staff Registration(to keep track of the staff roles)

user will register staff, by adding details like First Name(r), Last Name(o), Mobile Number(r), aadhar number(o), role(r). 
We have an enum for role types in @Enums.java so only those options should be available. 
For mechanic roles, their UUID will have a foreign key to job cards table so as to associate each jobcard with a mechanic.

Call types- POST, GET, PATCH


