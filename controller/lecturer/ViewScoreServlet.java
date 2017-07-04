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
 * @author Aratto
 */
public class ViewScoreServlet extends HttpServlet {

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
            out.println("<title>Servlet ViewScoreServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewScoreServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        if(session == null){
            response.sendRedirect(getServletContext().getContextPath());
        }
        
        Long assessmentId = null;
        Long courseId = null;
        
        if(request.getParameter("assessmentId") != null){
            assessmentId = Long.parseLong(request.getParameter("assessmentId"));
        }else{
            response.sendRedirect(request.getHeader("Referer"));
            return ;
        }
        
        if(request.getParameter("courseId") != null){
            courseId = Long.parseLong(request.getParameter("courseId"));
        }else{
            response.sendRedirect(request.getHeader("Referer"));
            return ;
        }
        
        Connection con = ConnectionBuilder.getConnection();
        Map<Long, String> students = new TreeMap<Long, String>();
        List<Score> scores = Score.getScoreByAssessment(assessmentId, con);
        for (Iterator<Score> it = scores.iterator(); it.hasNext();) {
            Score score = it.next();
            Student std = Student.getStudent(score.getStudentId(), con);
            students.put(std.getStudentId(), std.getName());
        }
        
        Assessment assessment = Assessment.getAssessment(assessmentId, con);
        CourseInstance courseInstance = CourseInstance.getCourseInstance(courseId, con);
        
        request.setAttribute("scores", scores);
        request.setAttribute("assessment", assessment);
        request.setAttribute("course", courseInstance);
        request.setAttribute("students", students);
        
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ViewScoreServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.getServletContext().getRequestDispatcher("/lecturer/viewscore.jsp").forward(request, response);
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
