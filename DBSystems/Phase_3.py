## CS 4400 Heavyweight Project - Phase 3
## Group 70

from tkinter import *
from tkinter import messagebox

from pymysql import *
from MySQL_Interface import *
import os
import math

class Phase3:

    def __init__(self, rWin):

    ## initialize all windows ##

        self.database = MySQLInterface("cs4400_Team_70", "PbcACLt4")
        self.logged_in_user = NULL

        #login page
        self.loginWin = rWin
        self.loginWin.title('Login Page')
        self.loginPage()

        ### STUDENT PAGES ###

        #new student registration
        self.stuRegWin = Toplevel()
        self.stuRegWin.title('New Student Registration')
        self.stuRegWin.withdraw()

        #Stu Main page
        self.mainPageWin = Toplevel()
        self.mainPageWin.title('Main Page')
        self.mainPageWin.withdraw()

        #Student's Me page
        self.stuMeWin = Toplevel()
        self.stuMeWin.title('Me')
        self.stuMeWin.withdraw()

        #Edit Profile Page
        self.editProfileWin = Toplevel()
        self.editProfileWin.title('Edit Profile')
        self.editProfileWin.withdraw()

        #My Application Page
        self.myAppWin = Toplevel()
        self.myAppWin.title('My Application')
        self.myAppWin.withdraw()

        #View and Applyt to Project Page
        self.view_applyProjWin = Toplevel()
        self.view_applyProjWin.title('View and Applyt to Project')
        self.view_applyProjWin.withdraw()

        #View Course Page
        self.viewCourseWin = Toplevel()
        self.viewCourseWin.title('View Course')
        self.viewCourseWin.withdraw()

        ### ADMIN PAGES ###

        #Choose Funcationality Page
        self.chooseFuncWin = Toplevel()
        self.chooseFuncWin.title('Choose Functionality - This window only shown for admin')
        self.chooseFuncWin.withdraw()

        #View Application Page
        self.viewAppWin = Toplevel()
        self.viewAppWin.title('View Student Application')
        self.viewAppWin.withdraw()

        #View Popular Project Report Page
        self.popProjWin = Toplevel()
        self.popProjWin.title('View Popular Project - Report')
        self.popProjWin.withdraw()

        #View Application Report Page
        self.appReportWin = Toplevel()
        self.appReportWin.title('View Application - Report')
        self.appReportWin.withdraw()

        #Add Project Page
        self.addProjWin = Toplevel()
        self.addProjWin.title('Add Project')
        self.addProjWin.withdraw()

        #Add Course Page
        self.addCourseWin = Toplevel()
        self.addCourseWin.title('Add Course')
        self.addCourseWin.withdraw()

        #designation and category options for option menu --> used for stu and admin users

        self.category_OPTIONS = self.database.get_all_categories()
        self.designation_OPTIONS = self.database.get_designations()
        self.major_OPTIONS = self.database.get_all_majors()

        self.year_OPTIONS = ['Freshman', 'Sophomore', 'Junior', 'Senior', 'Super Senior']

        #tried to optimize windows but ran into problems - http://stackoverflow.com/questions/6181935/how-do-you-create-different-variable-names-while-in-a-loop-python
##        title = ['New User Registration', 'Main Page', 'Me', 'Edit Profile', 'My Application', 'View and Apply to Project', 'View Course', 'Choose Functionality', 'View Applications', 'View Popular Project Report', 'View Application Report', 'Add Project', 'Add Course']
##        name_variable = ['StuRegWin', 'main_page', 'stuMe_page', 'editProf_page', 'myApp', 'viewApplyProj_page', 'viewCourse_page', 'func_page', 'viewApps_page', 'viewPopProj_page', 'viewApp_page', 'addProj_page', 'addCourse_page']
##
##        for name in range(len(name_variable)):
##            globals()['%s'% name_variable[name]] = Toplevel()

        #

    ## Connect with Database
        # try:
        #     db = pymysql.connect(host = '', passwd = '', user = '', db = '')
        # except:
        #     messagebox.showerror('Database Error',  'There was an error connecting to the database, please check your internet connection')
        #


### Format Pages ###

## STUDENT PAGES ##
    ## Login ##
    def loginPage(self):
        print('hello')

        #labels - shows the text on the GUI's
        lab1 = Label(self.loginWin, text='Login')
        lab2 = Label(self.loginWin, text='Username')
        lab3 = Label(self.loginWin, text='Password')
        lab1.grid(row=0, column=2, columnspan=1, pady=4, padx=4)  # using grid format
        lab2.grid(row=1, column=1, columnspan=1)
        lab3.grid(row=2, column=1, columnspan=1)

        #entry boxes
        self.userName_text = StringVar() #creates var for text entered in Entry Box
        self.passWord_text = StringVar()
        self.userName_entry = Entry(self.loginWin, textvariable = self.userName_text, width=30)
        self.passWord_entry = Entry(self.loginWin, textvariable = self.passWord_text, width=30)
        self.userName_entry.grid(row=1, column=2, columnspan=2)
        self.passWord_entry.grid(row=2, column=2, columnspan=2)

        #Buttons
        loginButt = Button(self.loginWin, text='Login', command=self.checkUserLogin)
        regButt = Button(self.loginWin, text='Register', command=self.newStuReg)
        loginButt.grid(row=3, column=2)
        regButt.grid(row=3, column=3)

    ## New Student Registration ##
    def newStuReg(self):
        self.loginWin.withdraw() #withdraw the previous (login) page
        self.stuRegWin.deiconify() #deiconify or pull up the current page (student registration)

        #labels
        lab1 = Label(self.stuRegWin, text = 'New Student Registration')
        lab2 = Label(self.stuRegWin, text='UserName')
        lab3 = Label(self.stuRegWin, text = 'GT Email Address')
        lab4 = Label(self.stuRegWin, text = 'Password')
        lab5 = Label(self.stuRegWin, text = 'Confirm Password')
        lab1.grid(row = 1, column = 1, columnspan=3)
        lab2.grid(row=2, column=1)
        lab3.grid(row=3, column=1)
        lab4.grid(row=4, column=1)
        lab5.grid(row=5, column=1)      ## gotta figure out how to make these all start from the left

        #entry
        self.newUserName_text = StringVar()
        self.GTemail_text = StringVar()
        self.newPassWord_text = StringVar()
        self.confirmNewPW_text = StringVar()

        self.newUserName_entry = Entry(self.stuRegWin, textvariable=self.newUserName_text, width=30)
        self.GTemail_entry = Entry(self.stuRegWin, textvariable=self.GTemail_text, width=30)
        self.newPassWord_entry = Entry(self.stuRegWin, textvariable=self.newPassWord_text, width=30)
        self.confirmNewPW_entry = Entry(self.stuRegWin, textvariable=self.confirmNewPW_text, width=30)

        self.newUserName_entry.grid(row=2, column=2)
        self.GTemail_entry.grid(row=3, column=2)
        self.newPassWord_entry.grid(row=4, column=2)
        self.confirmNewPW_entry.grid(row=5, column=2)

        #buttons
        createButt = Button(self.stuRegWin, command=self.createNewStu, text='Create User Profile')
        createButt.grid(row=6, column=2)

    ## mainPage
    def mainPage(self):
        #Labels

        lab1 = Label(self.mainPageWin, text = 'Main Page')
        lab2 = Label(self.mainPageWin, text = 'Title')
        lab3 = Label(self.mainPageWin, text = 'Category')
        lab4 = Label(self.mainPageWin, text = 'Designation')
        lab5 = Label(self.mainPageWin, text = 'Major')
        lab6 = Label(self.mainPageWin, text = 'Year')

        lab1.grid(row = 1, column = 2)
        lab2.grid(row = 2, column = 2)
        lab3.grid(row = 2, column = 4, padx=5)
        lab4.grid(row = 4, column = 2)
        lab5.grid(row = 5, column = 2)
        lab6.grid(row = 6, column = 2)

        #entry
        self.title_text  = StringVar()
        self.title_entry = Entry(self.mainPageWin, textvariable=self.title_text, width = 25)
        self.title_entry.grid(row=2, column=3)

        #option menu for category --> utilize OptionMenu tkinter method ==>!!!!!!!******* found in __init__
        #self.category_OPTIONS = ['Computing for good', 'Doing good for your neighborhood', 'Reciprocal teaching and learning', 'Urban development',
        #           'Adaptive learning', 'Technology for social good', 'Sustainable communities', 'Crowd-sourced', 'Collaborative action']
        #self.designation_OPTIONS = ['Sustainable Communities', 'Community']
        #self.major_OPTIONS = ['CS', 'IE', 'History FTW, actually rip', 'Bizness']  ### need to get actual majors from GT
        #self.year_OPTIONS = ['Freshman', 'Sophomore', 'Junior', 'Senior', 'Super Senior']

        self.categ_text_1 = StringVar()
        self.categ_text_2 = StringVar()
        self.desig_text = StringVar()
        self.major_text = StringVar()
        self.year_text = StringVar()

        self.categ_text_1.set(self.category_OPTIONS[0]) #set default category
        self.categ_text_2.set(self.category_OPTIONS[1])
        self.desig_text.set(self.designation_OPTIONS[0])
        self.major_text.set(self.major_OPTIONS[2])
        self.year_text.set(self.year_OPTIONS[4])

        categ_optionMenu_1 = OptionMenu(self.mainPageWin, self.categ_text_1, *self.category_OPTIONS, command=self.categOptionChanged_stu_1)
        categ_optionMenu_2 = OptionMenu(self.mainPageWin, self.categ_text_2, *self.category_OPTIONS, command=self.categOptionChanged_stu_2)
        desig_optionMenu = OptionMenu(self.mainPageWin, self.desig_text, *self.designation_OPTIONS, command=self.desigOptionChanged_stu)
        major_optionMenu = OptionMenu(self.mainPageWin, self.major_text, *self.major_OPTIONS, command=self.majorOptionChanged)
        year_optionMenu = OptionMenu(self.mainPageWin, self.year_text, *self.year_OPTIONS, command=self.yearOptionChanged)

        categ_optionMenu_1.grid(row=2, column=5)
        categ_optionMenu_2.grid(row=3, column=5)
        desig_optionMenu.grid(row=4, column=3)
        major_optionMenu.grid(row=5, column=3)
        year_optionMenu.grid(row=6, column=3)

        #radio buttons - use int variables to represent
        self.mainRBVar = IntVar()

        projRB = Radiobutton(self.mainPageWin, text = 'Project', variable=self.mainRBVar, value=1)
        courseRB = Radiobutton(self.mainPageWin, text = 'Course', variable=self.mainRBVar, value=2)
        bothRB = Radiobutton(self.mainPageWin, text = 'Both', variable=self.mainRBVar, value=3)
        projRB.grid(row=7, column = 2)
        courseRB.grid(row = 7, column = 3)
        bothRB.grid(row = 7, column=4)

        self.mainRBVar.set(3) #set default radioButton to both

        ## need to include in applyFilter method the sefl.mainRBvar.get() method to get which radio button clicked

        ## Buttons
        applyFilterButt = Button(self.mainPageWin, text='Apply Filter', command=self.applyFilter)
        resetFilterButt = Button(self.mainPageWin, text='Reset Filter', command=self.resetFilter)
        addCategButt = Button(self.mainPageWin, text='Add a category', command=self.addCategory_stu)

        applyFilterButt.grid(row=8, column=2)
        resetFilterButt.grid(row=8, column=3)
        addCategButt.grid(row=3, column=6)

        meButt = Button(self.mainPageWin, text='//ME', command=self.withdrawMain_me)
        meButt.grid(row=1, column=1)

        #pseudo project and class button
        psuedoProj_butt = Button(self.mainPageWin, text='pseudo project', command=self.withdrawMain_projApp)
        psuedoProj_butt.grid(row=9, column=2)

        psuedoCourse_butt = Button(self.mainPageWin, text='pseudo course', command=self.withdrawMain_viewCourse)
        psuedoCourse_butt.grid(row=9, column=3)

    ## stuMe
    def stuMe(self):
        lab1 = Label(self.stuMeWin, text='//ME')
        lab2 = Label(self.stuMeWin, text='Edit Profile')
        lab3 = Label(self.stuMeWin, text='My Application')
        #lab2.bind("<Button-1>", self.withdrawstuMe_edit) #needs to call withdraw function
        #lab3.bind("<Button-1>", self.myApp) #needs to to call withdraw function

        lab1.grid(row=1, column=1, padx = 30)
        lab2.grid(row=2, column=1, padx = 30)
        lab3.grid(row=3, column=1, padx = 30)

        # pseudo button to call edit page until figure out binding
        pseudoButt = Button(self.stuMeWin, text = 'Edit', command=self.withdrawstuMe_edit)
        pseudoButt.grid(row=5, column=1)
        pseudoButt_app = Button(self.stuMeWin, text = 'myApp', command=self.withdrawstuMe_app)
        pseudoButt_app.grid(row=6, column=1)

        #buttons
        backButt = Button(self.stuMeWin, text='Back', command=self.withdraw_back)
        backButt.grid(row=4, column=1, pady=15)

    ## editProfile
    def editProfile(self):
        #labels
        lab1 = Label(self.editProfileWin, text = 'Edit Profile')
        lab2 = Label(self.editProfileWin, text = 'Major')
        lab3 = Label(self.editProfileWin, text = 'Year')
        lab4 = Label(self.editProfileWin, text = 'Department')
        lab1.grid(row=1, column=2)
        lab2.grid(row=2, column=1)
        lab3.grid(row=3, column=1)
        lab4.grid(row=4, column=1)

        #drop down menu
        self.editMajor_text = StringVar()
        self.editYear_text = StringVar()

        self.editMajor_text.set(self.major_OPTIONS[0])
        self.editYear_text.set(self.year_OPTIONS[4])

        major_optionMenu = OptionMenu(self.editProfileWin, self.major_text, *self.major_OPTIONS)
        year_optionMenu = OptionMenu(self.editProfileWin,self.year_text, *self.year_OPTIONS)

        major_optionMenu.grid(row=2, column=2)
        year_optionMenu.grid(row=3, column=2)

        #display department based on major --> need to have list of majors and match to department

        self.collegeLabel_text = 'College of Computing'

        self.college_computing = ['CS', 'Computer Engineering', 'COMP Media']
        self.college_engineering = ['BME', 'AE', 'CE', 'IE']
        self.college_liberal = ['IA', 'Econ']

        if self.editMajor_text in self.college_computing:
            self.collegeLabel_text = 'College of Computing'
        elif self.editMajor_text in self.college_engineering:
            self.collegeLabel_text = 'College of Engineering'
        elif self.editMajor_text in self.college_liberal:
            self.collegeLabel_text = 'College of Liberal Arts'
        else:
            self.collegeLabel_text = 'IDK what college you in boi'

        self.collegeLabel = Label(self.editProfileWin, text = self.collegeLabel_text)
        self.collegeLabel.grid(row=4, column=2)

        #can't figure out how to do this get major from dropdown menu


        #back button
        backButt = Button(self.editProfileWin, text='Back', command=self.withdrawEdit_back)
        backButt.grid(row=5, column=1, columnspan=2)

    ## myApp
    def myApp(self):
        lab1 = Label(self.myAppWin, text='My Application')
        lab1.grid(row=1, column=1)

        # need to display student applications somehow...

        #button
        backButton = Button(self.myAppWin, text='Back', command=self.withdrawApp_back)
        backButton.grid(row=3, column=1)

    ## view_applyProj
    def view_applyProj(self):
        #need: get function to grab proj info from db
        #labels --> specific section (ie. 'Advisor:') + info from DB
        #**exception is 'Description:' --> add info after this label
        projLab_text = 'from the project that is clicked'

        #this label and info is different form other buttons that combine the section (i.e. 'Requirement:') and specific info from db
        #  --> description info will come below 'Description' label

        descripLab_text = 'from db'

        advisorLab_text = 'Advisor: ' + 'from db'
        desigLab_text = 'Designation: ' + 'from db'
        categLab_text = 'Category: ' + 'from db'
        reqLabel_text = 'Requirement: ' + 'from db'
        numStuLabel_text = 'Estimated Number of Students: ' + 'from db'

        projNameLabel = Label(self.view_applyProjWin, text=projLab_text)
        advisorLabel =Label(self.view_applyProjWin, text=advisorLab_text)
        projLabel = Label(self.view_applyProjWin, text='Description: ')
        projLabel_info = Label(self.view_applyProjWin, text=descripLab_text)
        desigLabel = Label(self.view_applyProjWin, text=desigLab_text)
        categLabel = Label(self.view_applyProjWin, text=categLab_text)
        reqLabel = Label(self.view_applyProjWin, text=reqLabel_text)
        numStuLabel = Label(self.view_applyProjWin, text=numStuLabel_text)

        projNameLabel.grid(row=1, column=1)
        advisorLabel.grid(row=2, column=1)
        projLabel.grid(row=3, column=1)
        desigLabel.grid(row=4, column=1)
        categLabel.grid(row=5, column=1)
        reqLabel.grid(row=6, column=1)
        numStuLabel.grid(row=7, column=1)

        #buttons
        backButt = Button(self.view_applyProjWin, text='Back', command=self.withdrawViewProj_main)
        applyButt = Button(self.view_applyProjWin, text='Apply', command=self.applyProj)
        backButt.grid(row=8, column=1)
        applyButt.grid(row=8, column=2)

    ## viewCourse
    def viewCourse(self):
        courseLab_text = 'from db'
        fromDB = 'from db' #this will need to be individual calls to DB
        courseName_text ='Course: ' + fromDB
        instruct_text = 'Instructor: ' + fromDB
        desig_text = 'Designation: ' + fromDB
        categ_text = 'Category: ' + fromDB
        estNumStu_text = 'Estimated number of students: ' + fromDB

        courseLab = Label(self.viewCourseWin, text=courseLab_text)
        courseNameLab = Label(self.viewCourseWin, text=courseName_text)
        instructLab = Label(self.viewCourseWin, text=instruct_text)
        desigLab = Label(self.viewCourseWin, text=desig_text)
        categLab = Label(self.viewCourseWin, text=categ_text)
        estNumStuLab = Label(self.viewCourseWin, text=estNumStu_text)

        courseLab.grid(row=1, column=1)
        courseNameLab.grid(row=2, column=1)
        instructLab.grid(row=3, column=1)
        desigLab.grid(row=4, column=1)
        categLab.grid(row=5, column=1)
        estNumStuLab.grid(row=6, column=1)

        #back button
        backButt = Button(self.viewCourseWin, text='Back', command=self.withdrawViewCourse_Main)
        backButt.grid(row=7, column=1)

## ADMIN PAGES ##

    ## chooseFunc
    def chooseFunc(self):
        chooseFuncLab = Label(self.chooseFuncWin, text='Choose Functionality')
        chooseFuncLab.grid(row=1, column=1)

        #buttons to choose admin functionality
        viewAppsButt = Button(self.chooseFuncWin, text='View Applications', command=self.withdrawChooseFunc_viewApp)
        popProjButt = Button(self.chooseFuncWin, text='View popular project report', command=self.withdrawChooseFunc_popProj)
        appReportButt = Button(self.chooseFuncWin, text='View Application report', command=self.withdrawChooseFunc_appReport)
        addProjButt = Button(self.chooseFuncWin, text='Add a Project', command=self.withdrawLogin_addProj)
        addCourseButt = Button(self.chooseFuncWin, text='Add a Course', command=self.withdrawLogin_addCourse)
        logOutButt = Button(self.chooseFuncWin, text='Log out', command=self.withdrawLogin_logOut)


        viewAppsButt.grid(row=2, column=1)
        popProjButt.grid(row=3, column=1)
        appReportButt.grid(row=4,column=1)
        addProjButt.grid(row=5, column=1)
        addCourseButt.grid(row=6, column=1)
        logOutButt.grid(row=7, column=2)

    ## viewApp
    def viewApp(self):
        appLab = Label(self.viewAppWin, text='Student Applications')
        appLab.grid(row=1, column=2)

        #table with applications --> radio button if pending


        #buttons
        backButt = Button(self.viewAppWin, text='Back', command=self.withdrawViewApp_choose)
        acceptButt = Button(self.viewAppWin, text='Accept', command=self.acceptApp)
        rejectButt = Button(self.viewAppWin , text='Reject', command=self.rejectApp)

        backButt.grid(row=3, column=2)
        acceptButt.grid(row=3, column=4)
        rejectButt.grid(row=3, column=5)

    ## popProj
    def popProj(self):
        #window head label
        popLabel = Label(self.popProjWin, text='Popular Projects')
        popLabel.grid(row=1, column=1)

        #scrollable table

        #back button
        backButt = Button(self.popProjWin, text='Back', command=self.withdrawPopProj_choose)
        backButt.grid(row=3, column=1)

    ## appReport
    def appReport(self):
        #labels
        appLab = Label(self.appReportWin, text='Application Report')
        appLab.grid(row=1, column=1)

        totApps_text = 'Total Applicants: ' + 'from db' # need to have get function to db
        acceptApps_text = 'Accepted Applicants: ' + 'from db'
        totAppsLab = Label(self.appReportWin, text=totApps_text)
        acceptAppsLab = Label(self.appReportWin, text=acceptApps_text)

        totAppsLab.grid(row=2, column=1)
        acceptAppsLab.grid(row=3, column=1)

        # table

        #back button
        backButt = Button(self.popProjWin, text='Back', command=self.withdrawPopProj_choose)
        backButt.grid(row=3, column=1)

    ## addProj
    def addProj(self):
        #labels
        projLab = ['Add a Project', 'Project Name: ', 'Advisor: ', 'Advisor Email: ', 'Description: ', 'Category: ']
        for i in range(len(projLab)):
            Label(self.addProjWin, text=projLab[i]).grid(row=i, column=1)
            print(i)

        desigLab = Label(self.addProjWin, text='Designation: ')
        estNumStuLab = Label(self.addProjWin, text='Estimated # of students: ')
        majorReqLab = Label(self.addProjWin, text='Major Requirement: ')
        yearReqLab = Label(self.addProjWin, text ='Year Requirement: ')
        deptReqLab = Label(self.addProjWin, text='Department Requirement: ')

        desigLab.grid(row=7, column=1)
        estNumStuLab.grid(row=8, column=1)
        majorReqLab.grid(row=9, column=1)
        yearReqLab.grid(row=10, column=1)
        deptReqLab.grid(row=11, column=1)

        #entry boxes
        self.projEntry_text = StringVar()
        self.advisorEntry_text = StringVar()
        self.emailEntry_text = StringVar()
        self.descriptEntry_text = StringVar()
        stringVarList = [self.projEntry_text, self.advisorEntry_text, self.emailEntry_text, self.descriptEntry_text]

        for i in range(4):
            Entry(self.addProjWin, textvariable=stringVarList[i], width=30).grid(row=i+1, column=2)

        self.estNumStu_text = StringVar()
        self.estNumStu = Entry(self.addProjWin, textvariable=self.estNumStu_text, width=30)
        self.estNumStu.grid(row=8, column=2)

        #drop down menus
        self.categText_adminProj_1 = StringVar()
        self.categText_adminProj_2 = StringVar()
        self.desigText_adminProj = StringVar()
        self.majorReq_adminProj = StringVar()
        self.yearReq_adminProj = StringVar()
        self.deptReq_adminProj = StringVar()

        self.majorReq_OPTIONS = self.database.get_major_requirement_list()
        self.yearReq_OPTIONS = ['None', 'Only Senior Students', 'Only Junior Students', 'Only Sophomore Students', 'Only Freshman Students']
        self.deptReq_OPTIONS = self.database.get_departments_requirements()

        self.categText_adminProj_1.set(self.category_OPTIONS[0] ) #set default category
        self.categText_adminProj_2.set(self.category_OPTIONS[2])
        self.desigText_adminProj.set(self.designation_OPTIONS[0])
        self.majorReq_adminProj.set(self.majorReq_OPTIONS[0])
        self.yearReq_adminProj.set(self.yearReq_OPTIONS[0])
        self.deptReq_adminProj.set(self.deptReq_OPTIONS[0])

        categ_optionMenu_1 = OptionMenu(self.addProjWin, self.categText_adminProj_1, *self.category_OPTIONS, command=self.categOptionChanged_proj_1)
        categ_optionMenu_2 = OptionMenu(self.addProjWin, self.categText_adminProj_2, *self.category_OPTIONS, command=self.categOptionChanged_proj_2)
        desig_optionMenu = OptionMenu(self.addProjWin, self.desigText_adminProj, *self.designation_OPTIONS, command=self.desigOptionChanged_proj)
        majorReq_optionMenu = OptionMenu(self.addProjWin, self.majorReq_adminProj, *self.majorReq_OPTIONS, command=self.majorReqOptionChanged_proj)
        yearReq_optionMenu = OptionMenu(self.addProjWin, self.yearReq_adminProj, *self.yearReq_OPTIONS, command=self.yearReqOptionChanged_proj)
        deptReq_optionMenu = OptionMenu(self.addProjWin, self.deptReq_adminProj, *self.deptReq_OPTIONS, command=self.majorReqOptionChanged_proj)

        categ_optionMenu_1.grid(row=5, column=2)
        categ_optionMenu_2.grid(row=6, column=2)
        desig_optionMenu.grid(row=7, column=2)
        majorReq_optionMenu.grid(row=9, column=2)
        yearReq_optionMenu.grid(row=10, column=2)
        deptReq_optionMenu.grid(row=11, column=2)

        #buttons
        addCategButt = Button(self.addProjWin, text='Add a new category', command=self.addCateg_proj)
        backButt = Button(self.addProjWin, text='Back', command=self.withdrawAddProj_choose)
        submitButt = Button(self.addProjWin, text='Submit', command=self.submitProj)

        addCategButt.grid(row=6 ,column=3)
        backButt.grid(row=12, column=1)
        submitButt.grid(row=12, column=2)

    ## addCourse
    def addCourse(self):
        #labels
        addCourseLab = Label(self.addCourseWin, text='Add a Course:')
        courseNumLab = Label(self.addCourseWin, text='Course Number:')
        courseNameLab = Label(self.addCourseWin, text='Course Name:')
        instructLab = Label(self.addCourseWin, text='Instructor:')
        desigLab = Label(self.addCourseWin, text='Designation:')
        categLab = Label(self.addCourseWin, text='Category:')
        estStuLab = Label(self.addCourseWin, text='Estimated # of students:')

        addCourseLab.grid(row=1, column=1)
        courseNumLab.grid(row=2, column=1)
        courseNameLab.grid(row=3, column=1)
        instructLab.grid(row=4, column=1)
        desigLab.grid(row=6, column=1)
        categLab.grid(row=5, column=1)
        estStuLab.grid(row=7, column=1)

        #entry boxes
        self.courseNumEntry_text = StringVar()
        self.courseNameEntry_text = StringVar()
        self.instructEntry_text = StringVar()
        self.estStuEntry_text = StringVar()

        self.courseNumEntry = Entry(self.addCourseWin, textvariable=self.courseNumEntry_text, width=25)
        self.courseNameEntry = Entry(self.addCourseWin, textvariable=self.courseNameEntry_text, width=25)
        self.instructEntry = Entry(self.addCourseWin, textvariable=self.instructEntry_text, width=25)
        self.estStuEntry = Entry(self.addCourseWin, textvariable=self.estStuEntry_text, width=25)

        self.courseNumEntry.grid(row=2, column=2)
        self.courseNameEntry.grid(row=3, column=2)
        self.instructEntry.grid(row=4, column=2)
        self.estStuEntry.grid(row=7, column=2)

        #dropdown menus
        self.categText_adminCourse = StringVar()
        self.desigText_adminCourse = StringVar()

        self.categText_adminCourse.set(self.category_OPTIONS[0] ) #set default category
        self.desigText_adminCourse.set(self.designation_OPTIONS[0])

        categ_optionMenu = OptionMenu(self.addCourseWin, self.categText_adminCourse, *self.category_OPTIONS, command=self.categOptionChanged_course)
        desig_optionMenu = OptionMenu(self.addCourseWin, self.desigText_adminCourse, *self.designation_OPTIONS, command=self.desigOptionChanged_course)

        categ_optionMenu.grid(row=5, column=2)
        desig_optionMenu.grid(row=6, column=2)

        #buttons
        addNewCategButt = Button(self.addCourseWin, text='Add a new category', command=self.addNewCateg)
        backButt = Button(self.addCourseWin, text='Back', command=self.withdrawAddCourse_choose)
        submitButt = Button(self.addCourseWin, text='Submit', command=self.submitCourse)

        addNewCategButt.grid(row=6, column=3)
        backButt.grid(row=8, column=1)
        submitButt.grid(row=8, column=2)

    ### GET METHODS from interface ###

    def checkUserLogin(self):
        print('checkUserLogin')
        userName = self.userName_text.get() # gets the username text entered in the entry box
        passWord = self.passWord_text.get()

        ## query database to check if username and password match --> if does make call to mainPage()
        ## else: return dialgogn based --> messagebox.showwarning('sorry', 'Usename and password don't match existing')

        #test case userName - need to replace with get function that queries db

        logged_in_user = self.database.login_User(userName, passWord)
        if self.database.login_User(userName, passWord) is not False and not None:
            self.logged_in_user = logged_in_user
            print(self.logged_in_user.getGT_Email())
            if logged_in_user.getIs_Admin():
                self.withdrawLogin_admin()
            else:
                self.withdrawLogin()
        else:
            messagebox.showerror("Incorrect Login Information", "The Username and Password do not match, please try again or register a new account!")

    def createNewStu(self):

        new_UserName = self.newUserName_text.get()
        new_GTemail = self.GTemail_text.get()
        new_passWord = self.newPassWord_text.get()
        confirmPassword = self.confirmNewPW_text.get()

        if new_UserName is "":
            messagebox.showerror("Registration Error", "Username field is null.")
            self.newStuReg()
        elif(new_GTemail is ""):
            messagebox.showerror("Registration Error", "GT-Email field is null.")
            self.newStuReg()
        elif new_passWord is "":
            messagebox.showerror("Registration Error", "Password field is null.")
            self.newStuReg()
        elif confirmPassword is "":
            messagebox.showerror("Registration Error", "Please confirm password.")
            self.newStuReg()
        elif new_passWord != confirmPassword:
            print("passwords don't match")
            messagebox.showerror("Passwords don't match", "The passwords you entered do not match, try again!")
            self.newStuReg()
        else:
            user_added = self.database.add_User(new_GTemail, new_UserName, new_passWord, 0, "Not_Selected", 0)
            if user_added is "Database Error":
                messagebox.showerror("Registration error", "There was an error with the database. Make sure your information is correct")
            elif user_added is False:
                messagebox.showerror("Registration error", "Either GT-Email or username already exists.")
            elif user_added is True:
                messagebox.showinfo("Registration Success", "New user was successfully added.")
            self.withdrawStuReg()

    # these methods will manipulate options in dropdown menus from main page
    def categOptionChanged_stu_1(self, theString):
        categoryOption = self.categ_text_1.get()
        print('categoryOption', categoryOption)
        print('passed string', theString)

    def categOptionChanged_stu_2(self, theString):
        categoryOption = self.categ_text_2.get()
        print('categoryOption', categoryOption)
        print('passed string', theString)

    def desigOptionChanged_stu(self, theString):
        desigOption = self.desig_text

    def majorOptionChanged(self, theString):
        majorOption = self.major_text

    def yearOptionChanged(self, theString):
        yearOptions = self.year_text

    # add category for main page of student
    def addCategory_stu(self):
        print('add category')
        ## call categOptionChanged_stu_2 for category in dropdown menu

    # filter button functions
    def applyFilter(self):
        print('apply filter button clicked')

    def resetFilter(self):
        print('reset filter button clicked')

    def applyProj(self):
        print('apply proj')
        #needs to make call to withdraw function back to main

    def acceptApp(self):
        print('accept App')

    def rejectApp(self):
        print('reject App')

    def submitCourse(self):
        print('submitCourse')
        bad_data = False

        course_num = self.courseNumEntry_text.get()
        course_name = self.courseNameEntry_text.get()
        instructor = self.instructEntry_text.get()
        est_stu = self.estStuEntry_text.get()
        if est_stu is not "":
            try:
                est_stu = int(est_stu)
            except:
                try:
                    est_stu = float(est_stu)
                    est_stu = math.ceil(est_stu)
                    est_stu = int(est_stu)
                except:
                    bad_data = True

        designation = self.desigText_adminCourse.get()
        category = self.categText_adminCourse.get()

        if bad_data is True :
            messagebox.showerror("Add Course Error", "There was a problem with the estimated number of students")
        if course_num is "":
            messagebox.showerror("Add Course Error", "Course number is null")
        elif course_name is "":
            messagebox.showerror("Add Course Error", "Course name is null.")
        elif instructor is "":
            messagebox.showerror("Add Course Error", "Instructor name is null.")
        elif est_stu is "":
            messagebox.showerror("Add Course Error", "Estimated students is null.")
        else:
            if self.database.add_new_course(course_num, course_name, instructor, est_stu, designation) == "Database Error":
                messagebox.showerror("Add Course Error", "Database Error Occured")
            else:
                if self.database.add_course_is(category, course_name, course_num) is "Database Error" :
                    messagebox.showerror("Add Course Error", "Database Error Occured")
                else:
                    messagebox.showinfo("Add Course Success", "Course was successfully added.")
                    self.withdrawAddCourse_choose()

    def addNewCateg(self):
        print('add a new category')

    #dropdown menus methods for project func
    def categOptionChanged_proj_1(self, passedStr):
        categoryOption_1 = self.categText_adminProj_1.get()
        print('passed string:', passedStr)
        print('the value:', categoryOption_1)

    def categOptionChanged_proj_2(self, passedStr):
        categoryOption_2 = self.categText_adminProj_2.get()
        print('passed string:', passedStr)
        print('the value:', categoryOption_2)

    def desigOptionChanged_proj(self, passedStr):
        desigOption = self.desigText_adminProj.get()
        print('passed string:', passedStr)
        print('the value:', desigOption)

    def majorReqOptionChanged_proj(self, passedStr):
        majorReqOption = self.majorReq_adminProj
        print('major requirement: ', majorReqOption)

    def yearReqOptionChanged_proj(self, passedStr):
        yearReqOption = self.yearReq_adminProj
        print('year req: ', yearReqOption)

    def deptReqOptionChanged_proj(self, passedStr):
        deptReqOption = self.deptReq_adminProj
        print('dept req: ', deptReqOption)

    def addCateg_proj(self):
        print('addCateg_proj')

    def submitProj(self):
        print('submit new project')
        bad_data = False

        project_name = self.projEntry_text.get()
        est_stu = self.estNumStu_text.get()
        advisor_name = self.advisorEntry_text.get()
        advisor_email = self.emailEntry_text.get()
        description = self.descriptEntry_text.get()
        designation = self.desigText_adminProj.get()
        category = self.categText_adminProj_1.get()

        major_req = self.majorReq_adminProj.get()
        year_req = self.yearReq_adminProj.get()
        dept_req = self.deptReq_adminProj.get()

        req_list = [major_req, year_req, dept_req]


        if est_stu is not "":
            try:
                est_stu = int(est_stu)
            except:
                try:
                    est_stu = float(est_stu)
                    est_stu = math.ceil(est_stu)
                    est_stu = int(est_stu)
                except:
                    bad_data = True

        if bad_data is True:
            messagebox.showerror("Add Project Error", "There was a problem with the estimated number of students")
        elif project_name is "":
            messagebox.showerror("Add Project Error", "Project Name is null")
        elif advisor_name is "":
            messagebox.showerror("Add Project Error", "Advisor name is null.")
        elif advisor_email is "":
            messagebox.showerror("Add Project Error", "Advisor Email is null.")
        elif est_stu is "":
            messagebox.showerror("Add Project Error", "Estimated Number of Students is null.")
        else:
            if self.database.add_new_project(project_name, est_stu, advisor_name, advisor_email, description, designation) == "Database Error":
                messagebox.showerror("Add Project Error", "Database Error Occured")
            else:
                project_is_result = self.database.add_project_is(category, project_name)

                for requirement in req_list:
                    if requirement is not "None":
                        self.database.add_project_requirements(requirement, project_name)

                if project_is_result == "Database Error":
                    messagebox.showerror("Add Course Error", "Database Error Occured")
                else:
                    messagebox.showinfo("Add Project Success", "Course was successfully added.")
                    self.withdrawAddProj_choose()




    #dropdown menu methods for course function
    def categOptionChanged_course(self, passedStr):
        categoryOption_course = self.categText_adminCourse.get()
        print('passed string:', passedStr)
        print('the value:', categoryOption_course)

    def desigOptionChanged_course(self, passedStr):
        desigOption_course = self.desigText_adminCourse.get()
        print('passed string:', passedStr)
        print('the value:', desigOption_course)



### PAGE WITHDRAW methods ### --> these methods are used to withdraw a page and return a different one

    def withdrawStuReg(self):
        self.loginWin.deiconify()
        self.stuRegWin.withdraw()
        self.loginPage()

    def withdrawLogin(self):
        self.mainPageWin.deiconify()
        self.loginWin.withdraw()
        self.mainPage()

    def withdrawMain_me(self):
        self.stuMeWin.deiconify()
        self.mainPageWin.withdraw()
        self.stuMe()

    def withdrawstuMe_edit(self):
        self.editProfileWin.deiconify()
        self.stuMeWin.withdraw()
        self.editProfile()

    def withdrawstuMe_app(self):
        self.myAppWin.deiconify()
        self.stuMeWin.withdraw()
        self.myApp()

    def withdraw_back(self):
        self.mainPageWin.deiconify()
        self.stuMeWin.withdraw()
        self.mainPage()

    def withdrawEdit_back(self):
        self.stuMeWin.deiconify()
        self.editProfileWin.withdraw()
        self.stuMe()

    def withdrawApp_back(self):
        self.stuMeWin.deiconify()
        self.myAppWin.withdraw()
        self.stuMe()

    def withdrawMain_projApp(self):
        self.view_applyProjWin.deiconify()
        self.mainPageWin.withdraw()
        self.view_applyProj()

    def withdrawViewProj_main(self):
        self.mainPageWin.deiconify()
        self.view_applyProjWin.withdraw()
        self.mainPage()

    def withdrawMain_viewCourse(self):
        self.viewCourseWin.deiconify()
        self.mainPageWin.withdraw()
        self.viewCourse()

    def withdrawViewCourse_Main(self):
        self.mainPageWin.deiconify()
        self.viewCourseWin.withdraw()
        self.mainPage()

    def withdrawLogin_admin(self):
        self.chooseFuncWin.deiconify()
        self.loginWin.withdraw()
        self.chooseFunc()

    def withdrawChooseFunc_viewApp(self):
        self.viewAppWin.deiconify()
        self.chooseFuncWin.withdraw()
        self.viewApp()

    def withdrawChooseFunc_popProj(self):
        self.popProjWin.deiconify()
        self.chooseFuncWin.withdraw()
        self.popProj()

    def withdrawChooseFunc_appReport(self):
        self.viewAppWin.deiconify()
        self.chooseFuncWin.withdraw()
        self.appReport()

    def withdrawLogin_addProj(self):
        self.addProjWin.deiconify()
        self.chooseFuncWin.withdraw()
        self.addProj()

    def withdrawLogin_addCourse(self):
        self.addCourseWin.deiconify()
        self.chooseFuncWin.withdraw()
        self.addCourse()

    def withdrawLogin_logOut(self):
        self.loginWin.deiconify()
        self.chooseFuncWin.withdraw()
        self.loginPage()

    def withdrawViewApp_choose(self):
        self.chooseFuncWin.deiconify()
        self.viewAppWin.withdraw()
        self.chooseFunc()

    def withdrawPopProj_choose(self):
        self.chooseFuncWin.deiconify()
        self.popProjWin.withdraw()
        self.chooseFunc()

    def withdrawAddProj_choose(self):
        self.chooseFuncWin.deiconify()
        self.addProjWin.withdraw()
        self.chooseFunc()

    def withdrawAddCourse_choose(self):
        self.chooseFuncWin.deiconify()
        self.addCourseWin.withdraw()
        self.chooseFunc()

#### Helper functions - random ###

rWin = Tk()
app = Phase3(rWin)
rWin.mainloop()
