/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bootcamp.controller.student;

import bootcamp.model.database.ApplicationData;
import bootcamp.model.database.CourseInstance;
import bootcamp.model.database.Officer;
import bootcamp.model.database.view.JoinView;
import bootcamp.model.utility.ConnectionBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
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
 * @author oFNo's PC
 */
public class StudentMainServlet extends HttpServlet {

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
           response.sendRedirect("/");
        }
        
        String id = (String) session.getAttribute("id");
        String startYear = id.substring(0, 2);
        startYear = "25"+startYear;
        startYear = (Integer.parseInt(startYear)-543) + "";
        
        Connection con = ConnectionBuilder.getConnection();
        
        List<ApplicationData> applicationDatas = ApplicationData.getApplicationData(con);
        ApplicationData currentAD = null;
        if(applicationDatas.size() > 0){
            currentAD = applicationDatas.get(0);
        }
        
        Integer currentYear = 0;
        String currentSemester = "";
        
        if(currentAD != null){
            currentYear = currentAD.getCurrentAcademicYear().getYear() + 1900;
            currentSemester = ApplicationData.getSemesterEnum(currentAD.getCurrentSemester());
        }
        
        session.setAttribute("currentSemester", Integer.parseInt(currentSemester));
        session.setAttribute("currentYear", new Date(currentYear-1900,0,1));
        
        Integer selectSemester = null;
        if((String)request.getParameter("semester") != null)
            selectSemester = Integer.parseInt((String)request.getParameter("semester"));
        String selectYear = (String)request.getParameter("year");
        if(selectSemester == null){
            selectSemester = Integer.parseInt(currentSemester);
        }
        if(selectYear == null){
            selectYear = currentYear+"";
        }
        
        List<CourseInstance> courseInstances = JoinView.getEnrollCourse(Long.parseLong(id), selectSemester, selectYear, con);
        
        request.setAttribute("selectSemester", selectSemester);
        request.setAttribute("selectYear", selectYear);
        request.setAttribute("courseInstances", courseInstances);
        request.setAttribute("currentYear", currentYear);
        request.setAttribute("currentSemester", currentSemester);
        request.setAttribute("startYear", startYear);
        
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentMainServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.getServletContext().getRequestDispatcher("/student/stdhomepage.jsp").forward(request, response);
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
