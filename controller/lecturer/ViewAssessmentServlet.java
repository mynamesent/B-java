/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bootcamp.controller.lecturer;

import bootcamp.model.database.Assessment;
import bootcamp.model.database.CourseInstance;
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
public class ViewAssessmentServlet extends HttpServlet {

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
        if(session == null){
            response.sendRedirect(getServletContext().getContextPath());
        }
        
        Long courseId = null;
        
        if(request.getParameter("courseId") != null){
            courseId = Long.parseLong(request.getParameter("courseId"));
        }else{
            response.sendRedirect("LecturerMainServlet");
            return;
        }
        
        Connection con = ConnectionBuilder.getConnection();
        
        CourseInstance courseInstance = CourseInstance.getCourseInstance(courseId, con);
        List<Assessment> assessments = Assessment.getAssessments(courseId, con);
        
        request.setAttribute("assessments", assessments);
        request.setAttribute("course", courseInstance);
        
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ViewAssessmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.getRequestDispatcher("/lecturer/viewassessment.jsp").forward(request, response);
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
