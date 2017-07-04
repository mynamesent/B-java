/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bootcamp.controller.lecturer;

import bootcamp.model.database.Assessment;
import bootcamp.model.utility.ConnectionBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
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
public class EditAssessmentServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        if(session == null){
            response.sendRedirect(getServletContext().getContextPath());
        }
        
        Long assessmentId = null;
        String courseId = null;
        
        if(request.getParameter("assessmentId") != null){
            assessmentId = Long.parseLong(request.getParameter("assessmentId"));
            courseId = request.getParameter("courseId");
        }else{
            response.sendRedirect("ViewAssessmentServlet");
            return ;
        }
        
        Connection connection = ConnectionBuilder.getConnection();
        Assessment assessment = Assessment.getAssessment(assessmentId, connection);
        
        request.setAttribute("courseId", courseId);
        request.setAttribute("assessment", assessment);
        
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditAssessmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.getServletContext().getRequestDispatcher("/lecturer/editassessment.jsp").forward(request, response);
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
        String assessmentName = null;
        Double maxScore = null;
        Boolean visible = false;
        String message = "";
        Boolean fail = false;
        
        if(request.getParameter("courseId") != null){
            courseId = Long.parseLong(request.getParameter("courseId"));
        }else{
            message += "courseid is not found.<br />";
            fail = true;
        }
        if(request.getParameter("assessmentId") != null){
            assessmentId = Long.parseLong(request.getParameter("assessmentId"));
        }else{
            message += "Assessment id is disappear.<br />";
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
            
            Assessment assessment = Assessment.getAssessment(assessmentId, con);
            assessment.setName(assessmentName);
            assessment.setScore(maxScore);
            assessment.setVisible(visible);

            if(Assessment.update(assessment, con)){
                message = "Success";
            }
        }
        
        Assessment assessment = Assessment.getAssessment(assessmentId, con);
        
        request.setAttribute("courseId", courseId);
        request.setAttribute("assessment", assessment);
        request.setAttribute("message", message);
        
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditAssessmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.getServletContext().getRequestDispatcher("/lecturer/editassessment.jsp").forward(request, response);
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
