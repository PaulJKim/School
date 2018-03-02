CREATE SCHEMA SLS


CREATE TABLE User
(Username	VARCHAR(30)	 		NOT NULL,
Password	VARCHAR(30)	   		NOT NULL,
admin_student	INT				NOT NULL, 
PRIMARY KEY (Username),
UNIQUE(Username)
);


CREATE TABLE Admin
( Username VARCHAR(30)  				NOT NULL,
Password VARCHAR(30) 				NOT NULL,
PRIMARY KEY(Username)
);




CREATE TABLE Student(
GT_Email	VARCHAR(30)   t
	Year	VARCHAR(4)	   			NOT NULL,
App_Date	DATE		   			NOT NULL,
App_Status	STATUS	   			NOT NULL,
Major_Name	VARCHAR(30)    			NOT NULL,
Stu_Name	VARCHAR(30)    			NOT NULL,
Username	VARCHAR(30)    			NOT NULL,
Password	VARCHAR(30)    			NOT NULL,
PRIMARY KEY(GT_Email),
UNIQUE(GT_Email)
FOREIGN KEY(Major_Name) REFERENCES Major(Major_Name),
ON DELETE CASCADE		   ON UPDATE CASCADE
FOREIGN KEY(User_Name) REFERENCES User(Username),
ON DELETE CASCADE		   ON UPDATE CASCADE
FOREIGN KEY(PW) REFERENCES User(Password),
ON DELETE CASCADE		   ON UPDATE CASCADE
)


CREATE TABLE Major
(Major_Name  VARCHAR(30)   			NOT NULL,
Dept_Name	VARCHAR(30) 		 	NOT NULL,
PRIMARY KEY(Major_Name),
FOREIGN KEY(Dept_Name) REFERENCES Department(Dept_Name)
);


CREATE TABLE Department
(
Dept_Name	VARCHAR(30)			NOT NULL,
PRIMARY KEY(Dept_Name)
);


Paul
CREATE TABLE Project(
	Project_Name		VARCHAR(30)		NOT NULL,
	Estimate_Num_Stu	INT				NOT NULL,
	Advisor_Name	VARCHAR(30)		NOT NULL,
	Advisor_Email		VARCHAR(30)		NOT NULL,
	Category 		VARCHAR(30)		NOT NULL,
	Description		VARCHAR(MAX)		NOT NULL,
	Designation_name	VARCHAR(30)		NOT NULL,
	PRIMARY KEY(Project_Name),
	FOREIGN KEY(Category) REFERENCES Category(Category_Name),
	FOREIGN KEY(Designation_Name) REFERENCES Designation(Designation_Name)
	ON DELETE CASCADE				ON UPDATE CASCADE
);







CREATE TABLE Course
(
Course_Num VARCHAR(30) 				NOT NULL,
Course_Name VARCHAR(30) 			NOT NULL,
Instructor VARCHAR(30) 				NOT NULL,
Estimated_Num_Stu VARCHAR(30) 			NOT NULL,
Designation_Name VARCHAR(30)   			NOT NULL,
Category_Name VARCHAR(30)        		NOT NULL,
PRIMARY KEY(Course_Num, Course_Name),
FOREIGN KEY(Designation_Name) REFERENCES Designation(Designation_Name),
FOREIGN KEY(Category_Name) REFERENCES Designation(Category_Name),
);




CREATE TABLE Category
(
Category_Name	VARCHAR(30)		NOT NULL,
PRIMARY KEY (Category_Name)
);


CREATE TABLE Designation
(
	Designation_Name VARCHAR(30) 		NOT NULL,
	PRIMARY KEY (Designation_Name)
);




CREATE TABLE Apply_To
(
	GT_Email VARCHAR(30)			NOT NULL,
	Project_Name	VARCHAR(30)		NOT NULL,
	ApplyDate DATE				NOT NULL,
	ApplyStatus VARCHAR(30)			NOT NULL,
	PRIMARY KEY (GT_Email),
	FOREIGN KEY (GT_Email) REFERENCES Student(GT_Email)
	ON DELETE CASCADE 	ON UPDATE CASCADE,
	FOREIGN KEY (Project_Name) REFERENCES Project(Project_Name)
	ON DELETE CASCADE 	ON UPDATE CASCADE
);


/* Apply_To(GT_email (fk-->students), Proj_name (fk-->project), Date, Status)*/




CREATE TABLE Project_Is
(
Category_Name	VARCHAR(30)		NOT NULL,
Project_Name	VARCHAR(30) 			NOT NULL,
PRIMARY KEY (Category_Name, Project_Name),
FOREIGN KEY (Category_Name) REFERENCES Category(Category_Name)
ON DELETE CASCADE 	ON UPDATE CASCADE,
FOREIGN KEY (Project_Name) REFERENCES Project(Project_Name)
ON DELETE CASCADE 	ON UPDATE CASCADE
);




/* Project_Is(Category_Name (fk-->category), Project_Name (fk-->project))*/




CREATE TABLE Course_Is
(
	Category_Name	VARCHAR(30) 	NOT NULL,
	Course_Name VARCHAR(30) 		NOT NULL,
	Course_Num VARCHAR(30) 			NOT NULL,
	PRIMARY KEY (Category_Name, Course_Name, Course_Num),
	FOREIGN KEY (Category_Name) REFERENCES Category(Category_Name)
	ON DELETE CASCADE 	ON UPDATE CASCADE
	-- ,
	-- FOREIGN KEY (Course_Name) REFERENCES Course(Course_Name)
	-- ON DELETE CASCADE 	ON UPDATE CASCADE
);


CREATE TABLE Project_Requirement
(
	Project_Name	VARCHAR(30) 		NOT NULL,
	Requirement_Name VARCHAR(30) 		NOT NULL,
	PRIMARY KEY (Project_Name, Requirement_Name),
	FOREIGN KEY (Project_Name) REFERENCES Project(Proj_Name)
	ON DELETE CASCADE 	ON UPDATE CASCADE
);


Paul’s Version
CREATE TABLE Project_Requirements(
	Requirement_Value	VARCHAR(30)	NOT NULL DEFAULT 'No Requirement',
	Requirement_Type	VARCHAR(30)	NOT NULL,
	Project_Name		VARCHAR(30)	NOT NULL,
	PRIMARY KEY(Requirement_Type, Project_Name),
	FOREIGN KEY(Project_Name) REFERENCES Project(Project_Name)
);


/*Project_Requirement(Project_Name (fk-->project), Requirement_Name)
*/
