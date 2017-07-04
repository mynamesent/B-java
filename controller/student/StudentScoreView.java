/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bootcamp.controller.student;

import bootcamp.model.database.ApplicationData;
import bootcamp.model.database.Assessment;
import bootcamp.model.database.CourseInstance;
import bootcamp.model.database.Enrollment;
import bootcamp.model.database.Score;
import bootcamp.model.utility.ConnectionBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author oFNo's PC
 */
public class StudentScoreView extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        if(session == null && session.getAttribute("type") == null){
           response.sendRedirect("/");
        }
        
        
        //Initialize Value
        Connection con = ConnectionBuilder.getConnection();
        Long id = Long.parseLong((String) session.getAttribute("id"));
        Long courseInstanceId = null;
        Integer semester = 0;
        Date year = new Date();
        CourseInstance selectedCourse = null;
        List<CourseInstance> courseinstances = null;
        List<Assessment> assessments = null;
        List<Score> scores = null;
        Map<Long, Score> scoreList = new TreeMap<Long, Score>();
        List<Assessment> showAssessment = new ArrayList<Assessment>();
        List<CourseInstance> showCourseInstance = new ArrayList<CourseInstance>();
        
        if(request.getParameter("courseid") != null)
        {
            courseInstanceId = Long.parseLong((String) request.getParameter("courseid"));
            selectedCourse = CourseInstance.getCourseInstance(courseInstanceId, con);
            year = selectedCourse.getYear();
            semester = selectedCourse.getSemester();
        }
        else
        {
            List<ApplicationData> applicationDatas = ApplicationData.getApplicationData(con);
            ApplicationData currentAD = null;
            if(applicationDatas.size() > 0){
                currentAD = applicationDatas.get(0);
            }

            Integer currentYear = new Date().getYear();
            String currentSemester = "Error";

            if(currentAD != null){
                currentYear = currentAD.getCurrentAcademicYear().getYear() + 1900;
                currentSemester = ApplicationData.getSemesterEnum(currentAD.getCurrentSemester());
            }

            year.setYear(currentYear-1900);
            semester = ApplicationData.getSemesterNumber(currentSemester);
        }
        
//        Long id = 58130500013l;
//        Long courseInstanceId = 12l;
        

        //Get course in semester
        List<CourseInstance> courseInstances = CourseInstance.getCourseInstanceBySemester(semester, year.getYear()+1900+"", con);
        List<Enrollment> enrollments = Enrollment.getEnrollmentByStudent(id, con);
       
        List<CourseInstance> enrollCourse = new ArrayList<CourseInstance>();
        
        for (Iterator<Enrollment> iterator = enrollments.iterator(); iterator.hasNext();) {
            Enrollment next = iterator.next();
            for (Iterator<CourseInstance> iterator1 = courseInstances.iterator(); iterator1.hasNext();) {
                CourseInstance next1 = iterator1.next();
                if(next.getCourseInstanceId() == next1.getCourseId()){
                    enrollCourse.add(next1);
                }
            }
        }
       
        if(courseInstanceId != null){
            //Get Score in course instance
            assessments = Assessment.getAssessments(courseInstanceId, con);
            scores = Score.getScoreByStudentId(id, con);

            for (Iterator<Score> iterator = scores.iterator(); iterator.hasNext();) {
                Score next = iterator.next();
                for (Iterator<Assessment> iterator1 = assessments.iterator(); iterator1.hasNext();) {
                    Assessment next1 = iterator1.next();
                    if(next1.getVisible() && next1.getAssessmentId() == next.getAssessmentId()){
                        scoreList.put(next1.getAssessmentId(), next);
                        showAssessment.add(next1);
                    }
                }
            }
        }else{
            courseinstances = CourseInstance.getAllCourseInstance(con);
            assessments = Assessment.getAssessment(con);
            scores = Score.getLatestScore(id, con);

            for (Iterator<Score> iterator = scores.iterator(); iterator.hasNext();) {
                Score next = iterator.next();
                for (Iterator<Assessment> iterator1 = assessments.iterator(); iterator1.hasNext();) {
                    Assessment next1 = iterator1.next();
                    for (Iterator<CourseInstance> iterator2 = courseinstances.iterator(); iterator2.hasNext();){
                        CourseInstance next2 = iterator2.next();
                        if(next1.getVisible() && next1.getAssessmentId() == next.getAssessmentId() && next2.getCourseId() == next1.getCourseInstanceId()){
                            scoreList.put(next1.getAssessmentId(), next);
                            showAssessment.add(next1);
                            showCourseInstance.add(next2);
                        }
                    }
                }
            }
        }
        
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentScoreView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("semester", semester);
        request.setAttribute("year", year);
        request.setAttribute("scoreList", scoreList);
        request.setAttribute("showAssessment", showAssessment);
        request.setAttribute("showCourseInstance", showCourseInstance);
        request.setAttribute("selectedCourse", selectedCourse);
        request.setAttribute("enrollCourses", enrollCourse);
        
        request.getServletContext().getRequestDispatcher("/student/stdscore.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
