#!/usr/bin/python3

import pymysql
from dataclasses import *

class MySQLInterface:

    def __init__(self, user, password):
        try:
            self.db = pymysql.connect(host="academic-mysql.cc.gatech.edu",
                                 user=user,
                                 passwd=password,
                                 db="cs4400_Team_70")
            self.cursor = self.db.cursor()
            self.cursor.execute("SELECT VERSION()")
            data = self.cursor.fetchone()
            print ("Database version : %s " % data)
        except:
            print("Connection to Database Failed!")

    #Gets all usernames from database
    def get_Usernames(self):
        sql = "SELECT Username FROM User;"
        results = None
        
        try:
           # Execute the SQL command
           self.cursor.execute(sql)
           results = self.cursor.fetchall()
        except pymysql.Error as e:
            try:
                print (e)
            except IndexError:
                print (e)
            print("error")
            
        if(results is not None):
            print(results)

    #Returns true if username and password match
    def login_User(self, username, password):
        sql = "SELECT Password FROM User WHERE Username=" + "\"" + username + "\"" + ";"
        results = None
        
        try:
           # Execute the SQL command
           self.cursor.execute(sql)
           results = self.cursor.fetchall()
        except:
            print("error")
            
        if len(results) == 1:
            if results[0][0] == password:
                print("Login Success")
                user = self.get_User_Username(username)
                logged_in_user = UserModel(user[0][0], user[0][1], user[0][2], user[0][3], user[0][4], user[0][5])
                return logged_in_user
            else:
                print("Login Failed")
                return False
        else:
            return False


    #Adds a User to the user table if Major_Name exists in the major table and if user does not yet exist in database
    def add_User(self, GT_Email, username, password, year, major_name, is_admin):
        
        ### TODO ### Complete Data Validation here
        if(year is None):
            year = None
        if(is_admin is None):
            is_admin = 0
        if(major_name is None):
            major_name = "Default"

        result = False

        if(self.check_User_Exists(GT_Email, username)):
            result = False
            print("User already exists")
        else:
            sql = "INSERT INTO User(GT_Email, Username, Password, Year, Major_Name, Is_Admin) VALUES(" + "'%s'" % GT_Email + ", '%s'" % username + ", '%s'" % password + ", '%i'" % year + ", '%s'" % major_name + ", '%i'" % is_admin + ");"
            print(sql)

            try:
               # Execute the SQL command
               self.cursor.execute(sql)
               # Commit your changes in the database
               self.db.commit()
               print("Student Successfully Added")
               result = True
            except pymysql.Error as e:
               # Rollback in case there is any error
               print("Student was not added.")
               try:
                   print (e)
               except IndexError:
                   print (e)
               return "Database Error"
               self.db.rollback()

        return result

    # Gets full user object using GT-Email
    def get_User(self, GT_Email):
        sql = "SELECT * FROM User WHERE GT_Email=\"" + GT_Email + "\";"
        results = None

        try:
            # Execute the SQL command
            self.cursor.execute(sql)
            results = self.cursor.fetchall()
        except:
            raise Exception("There was an error in returning the user from database.")

        return results

    def get_User_Username(self, username):
        sql = "SELECT * FROM User WHERE Username=\"" + username + "\";"
        results = None

        try:
            # Execute the SQL command
            self.cursor.execute(sql)
            results = self.cursor.fetchall()
        except:
            raise Exception("There was an error in returning the user from database.")

        return results

    # Helper method to check if uesr already exists before adding a new user to the database.
    # Returns True if username or e-mail already exists, Returns false if both do not exist
    def check_User_Exists(self, GT_Email, username):
        email_Exists = False
        email_Results = self.get_User(GT_Email)

        if len(email_Results) != 0:
            email_Exists = True

        username_Exists = False
        username_Results = None
        sql = "SELECT * FROM User WHERE Username=\"" + username + "\";"

        try:
            # Execute the SQL command
            self.cursor.execute(sql)
            username_Results = self.cursor.fetchall()
        except:
            raise Exception("There was an error in returning the user from database.")

        if len(username_Results) != 0:
            username_Exists = True

        if username_Exists is True or email_Exists is True:
            return True
        else:
            return False


    def get_all_categories(self):
        sql = "SELECT * FROM Category"
        results = None

        try:
           # Execute the SQL command
           self.cursor.execute(sql)
           results = self.cursor.fetchall()
        except pymysql.Error as e:
            try:
                print (e)
            except IndexError:
                print (e)
            print("error")

        categories_list = list()
        for item in results:
            categories_list.append(item[0])

        return categories_list

    def get_all_majors(self):
        sql = "SELECT Major_Name FROM Major"
        results = None

        try:
            #executes SQL command
            self.cursor.execute(sql)
            results = self.cursor.fetchall()
        except pymysql.Error as e:
            try:
                print ("%s") % e.args[1]
            except IndexError:
                print ("MySQL Error: %s") % str(e)
            print("error")
        majors_list = list()
        for item in results:
            majors_list.append(item[0])
        return majors_list

    def get_major_requirement_list(self):
        sql = "SELECT Major_Name FROM Major"
        results = None

        try:
            # executes SQL command
            self.cursor.execute(sql)
            results = self.cursor.fetchall()
        except pymysql.Error as e:
            try:
                print ("%s") % e.args[1]
            except IndexError:
                print ("MySQL Error: %s") % str(e)
            print("error")
        majors_list = list()
        for item in results:
            if item[0] == "Not_Selected":
                majors_list.insert(0, "None")
            majors_list.append("Only " + item[0] + " Students")
        return majors_list

    def get_designations(self):
        sql = "SELECT * FROM Designation"
        results = None
        try:
            #executes SQL command
            self.cursor.execute(sql)
            results = self.cursor.fetchall()
        except pymysql.Error as e:
            try:
                print ("%s") % e.args[1]
            except IndexError:
                print ("MySQL Error: %s") % str(e)
            print("error")

        designations_list = list()
        for item in results:
            designations_list.append(item[0])
        return designations_list

    def get_departments_requirements(self):
        sql = "SELECT * FROM Department"
        results = None
        try:
            # executes SQL command
            self.cursor.execute(sql)
            results = self.cursor.fetchall()
        except pymysql.Error as e:
            try:
                print ("%s") % e.args[1]
            except IndexError:
                print ("MySQL Error: %s") % str(e)
            print("error")

        departments_list = list()
        for item in results:
            if (item[0] == "None"):
                departments_list.insert(0, "None")
            else:
                departments_list.append(item[0] + " Students Only")
        return departments_list

    def add_new_course(self, course_num, course_name, instructor, estimated_num_stu, designation_name):
        sql = "INSERT INTO Course(Course_Num, Course_Name, Instructor, Estimated_Num_Stu, Designation_Name) VALUES(" + "'%s'" % course_num + ", '%s'" % course_name + ", '%s'" % instructor + ", '%i'" % estimated_num_stu + ", '%s'" % designation_name + ");"

        try:
            # Execute the SQL command
            self.cursor.execute(sql)
            # Commit your changes in the database
            self.db.commit()
            print("course Successfully Added")
            return True
        except pymysql.Error as e:
            # Rollback in case there is any error
            print("Course was not added.")
            try:
                print (e)
            except IndexError:
                print ("MySQL Error: %s") % str(e)
            self.db.rollback()
            return "Database Error"

    def add_course_is(self, category_name, course_name, course_num):
        sql = "INSERT INTO Course_Is(Category_Name, Course_Name, Course_Num) VALUES(" + "'%s'" % category_name + ", '%s'" % course_name + ", '%s'" % course_num + ");"

        try:
            # Execute the SQL command
            self.cursor.execute(sql)
            # Commit your changes in the database
            self.db.commit()
            print("Course Category Successfully Added")
            return True
        except pymysql.Error as e:
            # Rollback in case there is any error
            print("Course category was not added.")
            try:
                print (e)
            except IndexError:
                print ("MySQL Error: %s") % str(e)
            self.db.rollback()
            return "Database Error"

    def add_new_project(self, project_name, estimated_num_stu, advisor_name, advisor_email, description, designation_name):
        sql = "INSERT INTO Project(Project_Name, Estimate_Num_Stu, Advisor_Name, Advisor_Email, Description, Designation_Name) VALUES(" + "'%s'" % project_name + ", '%i'" % estimated_num_stu + ", '%s'" % advisor_name + ", '%s'" % advisor_email + ", '%s'" % description + ", '%s'" % designation_name + ");"

        try:
            # Execute the SQL command
            self.cursor.execute(sql)
            # Commit your changes in the database
            self.db.commit()
            print("Project Successfully Added")
            return True
        except pymysql.Error as e:
            # Rollback in case there is any error
            print("Project was not added.")
            try:
                print (e)
            except IndexError:
                print ("MySQL Error: %s") % str(e)
            self.db.rollback()
            return "Database Error"

    def add_project_is(self, category_name, project_name):
        sql = "INSERT INTO Project_Is(Category_Name, Project_Name) VALUES(" + "'%s'" % category_name + ", '%s'" % project_name + ");"

        try:
            # Execute the SQL command
            self.cursor.execute(sql)
            # Commit your changes in the database
            self.db.commit()
            print("Project Category Successfully Added")
            return True
        except pymysql.Error as e:
            # Rollback in case there is any error
            print("Project category was not added.")
            try:
                print (e)
            except IndexError:
                print ("MySQL Error: %s") % str(e)
            self.db.rollback()
            return "Database Error"

    def add_project_requirements(self, requirement_type, project_name):
        sql = "INSERT INTO Project_Requirements(Requirement_Type, Project_Name) VALUES(" + "'%s'" % requirement_type + ", '%s'" % project_name + ");"

        try:
            # Execute the SQL command
            self.cursor.execute(sql)
            # Commit your changes in the database
            self.db.commit()
            print("Project Requirement Successfully Added")
            return True
        except pymysql.Error as e:
            # Rollback in case there is any error
            print("Project Requirement was not added.")
            try:
                print (e)
            except IndexError:
                print ("MySQL Error: %s") % str(e)
            self.db.rollback()
            return "Database Error"
# testing
#database = MySQLInterface("cs4400_Team_70", "PbcACLt4")
#database.get_Usernames()
#database.login_User("pkim", "test123")
#database.login_User("pkim", "test12")
#database.add_User("test", "test")
#database.add_User("test@gatech.edu", "test", "123", 4, "Computer Science", 0)
#database.db.close()
