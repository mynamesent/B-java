/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bootcamp.controller;

import bootcamp.model.database.Officer;
import bootcamp.model.database.Student;
import bootcamp.model.utility.ConnectionBuilder;
import bootcamp.model.utility.LdapConnector;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author oFNo's PC
 */
public class LoginServlet extends HttpServlet {

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
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        HttpSession session = (HttpSession)request.getSession(true);
        Connection con = ConnectionBuilder.getConnection();
        Boolean success = false;
        
        Pattern usernamePattern = Pattern.compile("\\d{8,20}");
        Matcher usernameMatcher = usernamePattern.matcher(email);
       
        //By Pass Login
//        session.setAttribute("role", "student");
//        Student std = Student.getStudent(Long.parseLong("58130500013"), con);
//        session.setAttribute("id", std.getStudentId()+"");
//        session.setAttribute("name", std.getName());
//        session.setAttribute("student", std);
//        response.sendRedirect("StudentScoreView");
//        return;
        
        if(usernameMatcher.matches()){
            Map<String, Object> loginResult = LdapConnector.getInfo(email, password, "st");
            if((boolean)loginResult.get("result")){
             
                session.setAttribute("role", "student");
                Student std = Student.getStudent(Long.parseLong(email), con);
                if(std != null)
                {
                    session.setAttribute("id", std.getStudentId()+"");
                    session.setAttribute("name", std.getName());
                    session.setAttribute("student", std);
                    response.sendRedirect("StudentScoreView");
                    return;
                }else{
                    request.setAttribute("message", "There are no this student in database.");
                }
                
            }else{
                request.setAttribute("message", loginResult.get("message"));
            }
        }else{
            Map<String, Object> loginResult = LdapConnector.getInfo(email, password, "staff");
            if((boolean)loginResult.get("result")){
                Officer officer = Officer.getOfficerByUsername(email, con);
                if(officer != null){
                    session.setAttribute("id", officer.getOfficerId());
                    session.setAttribute("name", officer.getName());
                    session.setAttribute("officer", officer);
                    if(officer.getRole() == Officer.ROLE_LECTURER){
                        session.setAttribute("role", "lecturer");
                        response.sendRedirect("LecturerMainServlet");
                        return;
                    }else if(officer.getRole() == Officer.ROLE_ADMIN){
                        session.setAttribute("role", "admin");
                        response.setContentType("text/html;charset=UTF-8");
                        try (PrintWriter out = response.getWriter()) {
                            /* TODO output your page here. You may use following sample code. */
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>User Admin</title>");
                            out.println("</head>");
                            out.println("<body>");
                            out.println("This is user admin.");
                            out.println("</body>");
                            out.println("</html>");
                        }
                    }else if(officer.getRole() == Officer.ROLE_AA){
                        session.setAttribute("role", "aa");
                        response.setContentType("text/html;charset=UTF-8");
                        try (PrintWriter out = response.getWriter()) {
                            /* TODO output your page here. You may use following sample code. */
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>User AA</title>");
                            out.println("</head>");
                            out.println("<body>");
                            out.println("This is user aa.");
                            out.println("</body>");
                            out.println("</html>");
                        }
                    }
                }else{
                    request.setAttribute("message", "There are no this officer in database.");
                }
            }else{
                request.setAttribute("message", loginResult.get("message"));
            }
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
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
