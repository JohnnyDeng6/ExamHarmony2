# ExamHarmony

Project Overview 

The Exam Scheduling Web Application aims to streamline the process of scheduling exams for educational institutions. The application will facilitate the submission of exam slot requests by instructors, the creation and management of exam schedules by administrators, and the assignment and notification of invigilators. By automating and organizing these processes, the application will improve efficiency, reduce conflicts, and ensure effective communication among all parties involved.

Project Objectives

Simplify Exam Slot Requests: Allow instructors to request exam slots with necessary details and preferences.
Efficient Schedule Management: Enable administrators to create, edit, and finalize exam schedules seamlessly.
Effective Communication: Notify invigilators about their assignments and any schedule updates.
Conflict Avoidance: Implement checks to avoid scheduling conflicts between courses.
Requirements

Instructors

Request Exam Slots: Instructors can request exam slots by providing:
Course name
Exam duration
First, second, and third choice of dates
The system will check for conflicts (e.g., ensuring that CMPT120 and MATH101 do not have exams at the same time).
Edit Requests: Instructors can edit their submitted exam slot requests.
Delete Requests: Instructors can delete their submitted exam slot requests.
Admin

Create Exam Slots: Admins can create exam slots with the following details:
Course name and section
Start time
Duration
Number of rooms required
Assigned rooms
Number of invigilators needed
Edit Exams: Admins can edit the details of created exam slots.
Delete Exams: Admins can delete existing exam slots.
Finalize Exams: Once the exam schedule is finalized, the system will:
Send an email notification to all invigilators.
Update the list of pending assignments for invigilators.
Invigilators/Proctors

Specify Non-Available Dates: Invigilators can indicate dates they are not available to invigilate exams.
View Pending Assignments: Invigilators can see their pending assignments and can either agree to or reject these assignments.
Workflow

Instructors Submit Exam Slot Requests: At the beginning of the semester, instructors log in to the web application to submit their exam slot requests, specifying their course details and preferred dates.
Admin Reviews and Creates Exam Schedule: The admin is notified of new requests. Using the app’s interface, the admin reviews requests, checks for conflicts, and creates a preliminary exam schedule.
Finalize Exam Schedule: The admin finalizes the exam schedule. The system then sends email notifications to all invigilators, informing them of their assignments. The finalized schedule is also visible to the invigilators through the application.
Invigilators Manage Assignments: Invigilators log in to the application to view their assignments. They can confirm their availability or indicate if they are unable to invigilate on certain dates.

