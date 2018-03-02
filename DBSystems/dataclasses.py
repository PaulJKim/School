

class UserModel :

    def __init__(self, GT_Email, Username, Password, Year, Major_Name, Is_Admin ):
        self.GT_Email = GT_Email
        self.Username = Username
        self.Password = Password
        self.Year = Year
        self.Major_Name = Major_Name
        self.Is_Admin = Is_Admin

    def getGT_Email(self):
        return self.GT_Email
    def setGT_Email(self, GT_Email):
        self.GT_Email = GT_Email

    def getUsername(self):
        return self.Username
    def setUsername(self, username):
        self.Username = username

    def getPassword(self):
        return self.Password
    def setPassword(self, password):
        self.Password = password

    def getYear(self):
        return self.Year
    def setYear(self, year):
        self.setYear = year

    def getMajor_Name(self):
        return self.Major_Name
    def setMajor_Name(self, major_name):
        self.Major_Name = major_name

    def getIs_Admin(self):
        return self.Is_Admin
    def setIs_Admin(self, is_admin):
        self.Is_Admin = is_admin



class ProjectModel :
    def __init__(self,Project_Name, Estimate_Num_Stu,Advisor_Name,Advisor_Email,Description,Designation_name ):
        self.Project_Name = Project_Name
        self.Estimate_Num_Stu = Estimate_Num_Stu
        self.Advisor_Name = Advisor_Name
        self.Advisor_Email = Advisor_Email
        self.Description = Description
        self.Designation_name = Designation_name

    def getProject_Name(self):
        return self.Project_Name

    def getEstimate_Num_Stu(self):
        return self.Estimate_Num_Stu

    def getAdvisor_Name(self):
        return self.Advisor_Name

    def getAdvisor_Email(self):
        return self.Advisor_Email

    def getDescription(self):
        return self.Description

    def getDesignation_name(self):
        return self.Designation_name


    def setProject_Name(self, project_name):
        self.Project_Name = project_name

    def setEstimate_Num_Stu(self, estimate_num_stu):
        self.Estimate_Num_Stu = estimate_num_stu

    def setAdvisor_Name(self, advisor_name):
        self.Advisor_Name = advisor_name

    def Advisor_Email(self, advisor_email):
        self.Advisor_Email = advisor_email

    def Description(self, description):
        self.Description = description

    def Designation_name(self,designation_name):
        self.Designation_name = designation_name

class CourseModel :
    def __init__(self, Course_Num, Course_Name, Instructor, Estimated_Num_Stu, Designation_Name):
        self.Course_Num = Course_Num
        self.Course_Name = Course_Name
        self.Instructor = Instructor
        self.Estimated_Num_Stu = Estimated_Num_Stu
        self.Designation_Name = Designation_Name

    def getCourse_Num(self):
        return self.Course_Num
    def getCourse_Name(self):
        return self.Course_Name
    def getInstructor(self):
        return self.Instructor
    def Estimated_Num_Stu(self):
        return self.Estimated_Num_Stu
    def  Designation_Name(self):
        return self.Designation_Name

    def setCourse_Num(self, course_num):
        self.Course_Num = course_num
    def setCourse_Name(self, course_name):
        self.Course_Name = course_name
    def setInstructor(self, instructor):
        self.Instructor = instructor
    def setEstimated_Num_Stu(self, estimated_num_stu):
        self.Estimated_Num_Stu = estimated_num_stu
    def setDesignation_Name(self, designation_name):
        self.Designation_Name = designation_name
