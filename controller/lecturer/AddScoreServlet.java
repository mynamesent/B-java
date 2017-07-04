/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bootcamp.controller.lecturer;

import bootcamp.model.database.Assessment;
import bootcamp.model.database.CourseInstance;
import bootcamp.model.database.Score;
import bootcamp.model.database.Student;
import bootcamp.model.utility.ConnectionBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Aratto
 */
public class AddScoreServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession  session = request.getSession();
        if(session == null){
            response.sendRedirect(getServletContext().getContextPath());
            return ;
        }
        
        Long courseId = null;
        Long assessmentId = null;
        
        if(request.getParameter("courseId") != null){
            courseId = Long.parseLong(request.getParameter("courseId"));
            assessmentId = Long.parseLong(request.getParameter("assessmentId"));
        }else{
            response.sendRedirect(request.getHeader("Referer"));
            return ;
        }
        
        Connection con = ConnectionBuilder.getConnection();
        
        List<Student> students = Student.getStudentByCourseInstance(courseId, con);
        CourseInstance courseInstance = CourseInstance.getCourseInstance(courseId, con);
        Assessment assessment = Assessment.getAssessment(assessmentId, con);
             
        request.setAttribute("assessment", assessment);
        request.setAttribute("students", students);
        request.setAttribute("course", courseInstance);
        
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AddScoreServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.getServletContext().getRequestDispatcher("/lecturer/addscore.jsp").forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession  session = request.getSession();
        if(session == null){
            response.sendRedirect(getServletContext().getContextPath());
            return ;
        }
        
        Long courseId = null;
        Long assessmentId = null;
        Long studentId = null;
        String score = null;
        String message = "";
        
        if(request.getParameter("courseId") != null){
            courseId = Long.parseLong(request.getParameter("courseId"));
            assessmentId = Long.parseLong(request.getParameter("assessmentId"));
        }else{
            message = "Course id is invalid.<br/>";
            return ;
        }
        
        if(request.getParameter("studentId") != null){
            studentId = Long.parseLong(request.getParameter("studentId"));
        }else{
            message = "student id is invalid.<br/>";
            return ;
        }
        
        if(request.getParameter("score") != null){
            score = request.getParameter("score");
        }else{
            message = "score is invalid.<br/>";
            return ;
        }
        
        Connection con = ConnectionBuilder.getConnection();
        
        Score newScore = new Score();
        newScore.setAssessmentId(assessmentId);
        newScore.setStudentId(studentId);
        try{
            newScore.setScore(Double.parseDouble(score));
        }catch(NumberFormatException ex){
            newScore.setGrade(score);
        }
        
        boolean haveScore = false;
        
        List<Score> scoreList = Score.getScoreByAssessment(assessmentId, con);
        for (Iterator<Score> it = scoreList.iterator(); it.hasNext();) {
            Score score1 = it.next();
            if(score1.getStudentId().compareTo(studentId) == 0){
                haveScore = true;
            }
        }
        
        if(!haveScore)
        {
            if(Score.insert(newScore, con)){
                message = "Success";
            }else{
                message += "Something went wrong.";
            }
        }else{
            message += "This student have score in this course.";
        }
        
        List<Student> students = Student.getStudentByCourseInstance(courseId, con);
        CourseInstance courseInstance = CourseInstance.getCourseInstance(courseId, con);
        Assessment assessment = Assessment.getAssessment(assessmentId, con);
             
        request.setAttribute("assessment", assessment);
        request.setAttribute("students", students);
        request.setAttribute("course", courseInstance);
        request.setAttribute("message", message);
        
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AddScoreServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.getServletContext().getRequestDispatcher("/lecturer/addscore.jsp").forward(request, response);
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
