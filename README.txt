Connor Taylor 
SE 480 
HW4 Part 2

Included in this zip:
	src			-	the source code
	resources		-	the text files 
	META-INF		-	manifest file for building jars
	HW4-Part2.jar		-	the executable jar file
	CTaylor HW4.docx 	-	a document with answers to the part 2 questions
	README.txt		-	this file

To run:
	navigate a terminal to the folder containing the jar file
	enter in the console 
		java -jar HW4-Part2.jar
	enter a number when prompted by the program 

Built using Java 1.8

NOTES:
	This program will continue to run until it is manually stopped (ctrl-c on windows), this is due to 
	  threads not being stopped within the program
	This program can also be used to see the operation time for the previous version. To do this:
		open the project in an IDE
		navigate to the main() in Driver.java
		uncomment the line driver.begin()
		comment the line driver.beginImproved()
		run the program from the IDE



