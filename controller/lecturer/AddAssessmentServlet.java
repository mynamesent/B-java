/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bootcamp.controller.lecturer;

import bootcamp.model.database.Assessment;
import bootcamp.model.database.CourseInstance;
import bootcamp.model.database.CourseInstanceOfficer;
import bootcamp.model.utility.ConnectionBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
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
public class AddAssessmentServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddAssessmentServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddAssessmentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        
        Connection con = ConnectionBuilder.getConnection();
        
        List<CourseInstance> courseInstances = CourseInstanceOfficer.getCourseInstanceOfficerByOfficerId((Long)session.getAttribute("id"), con);
        
        request.setAttribute("courseinstances", courseInstances);
        
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AddAssessmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher("/lecturer/addassessment.jsp").forward(request, response);
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
        String assessmentName = null;
        Double maxScore = null;
        Boolean visible = false;
        String message = "";
        Boolean fail = false;
        
        if(request.getParameter("courseid") != null){
            courseId = Long.parseLong(request.getParameter("courseid"));
        }else{
            message += "courseid is not found.<br />";
            fail = true;
        }
        if(request.getParameter("assessmentName") != null){
            assessmentName = request.getParameter("assessmentName");
        }else{
            message += "Assessment name is not found.<br />";
            fail = true;
        }
        if(request.getParameter("maxScore") != null){
            maxScore = Double.parseDouble(request.getParameter("maxScore"));
        }else{
            message += "Max score is not found.<br />";
            fail = true;
        }
        if(request.getParameter("visible") != null){
            visible = true;
        }
        
        Connection con = ConnectionBuilder.getConnection();
        
        if(!fail)
        {
            
            Assessment assessment = new Assessment();
            assessment.setCourseInstanceId(courseId);
            assessment.setName(assessmentName);
            assessment.setScore(maxScore);
            assessment.setVisible(visible);

            if(Assessment.insert(assessment, con)){
                message = "Success";
            }
        }
        
        List<CourseInstance> courseInstances = CourseInstanceOfficer.getCourseInstanceOfficerByOfficerId((Long)session.getAttribute("id"), con);
        
        request.setAttribute("courseinstances", courseInstances);
        request.setAttribute("message", message);
        
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AddAssessmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.getServletContext().getRequestDispatcher("/lecturer/addassessment.jsp").forward(request, response);
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
