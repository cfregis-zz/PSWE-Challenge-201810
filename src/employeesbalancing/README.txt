This project is a Java 8 application, that run as a prompt command line interface program.

This project code was developed on eclipse IDE, 
Import project on eclipse, 
1. Run the class "IntegrationTests.java" as JUnit Test to validate the system, with the files used on the example scenario,
provided on the problem description. 

2. Run the class "CompanyEmployeesBalancingApp.java", to run the main app flow, and use the console prompt to interact with the app.

3. Export the project on eclipse as a runnable jar, from the execution of the class CompanyEmployeesBalancingApp. 
I save the file on the dist folder. But the email block the .jar file on the upload. 
So if want to run as a command line app outside eclipse, export as runnable jar for Java 8.
------------------------------------------------------------------------------------------------------------------------------------
Run without Eclipse:

Run, on the /dist folder the command:

java -jar CompanyEmployeesBalancingApp.jar 

The App will prompt:
>> Welcome!
These are the commands examples to use this app:
load "team.txt" "employees.txt" // load data from files to the system
allocate // allocate employees between teams accordling the Maturity specified
promote 2 // promote 2 employees, following promotion criterias
balance // redistribute employees between the teams, accordling the Maturity specified
Type quit, to exit the program.
>>

at this point you can use the commands as follow:

load "team.txt" "employees.txt"
allocate
promote 2
balance
promote 4
balance

------------------------------------------------------------------------------------------------------------------------------------