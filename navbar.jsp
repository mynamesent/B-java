<%-- 
    Document   : navbar
    Created on : May 28, 2016, 1:07:57 PM
    Author     : oFNo's PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="index.html">SIT Student Portal</a>
    </div>
    <ul class="nav navbar-top-links navbar-right">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i> ${student.name}${officer.name} (${id})  <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
<!--                <li><a href="UpdateProfileServlet"><i class="fa fa-user fa-fw"></i> User Profile</a>
                </li>
                <li class="divider"></li>-->
                <li><a href="LogoutServlet"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                </li>
            </ul>
        </li>
    </ul>
    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <li class="sidebar-search titlemenu">
                    Main Menu
                </li>
                <%
                       String role = (String) session.getAttribute("role");
                       if (role.equalsIgnoreCase("student")) {   %>
                <li>
                    <a href="StudentScoreView"><i class="fa fa-bar-chart" aria-hidden="true"></i> Score</a>
                </li>
                <%
                    }
                    if (role.equalsIgnoreCase("Lecturer")) {%>
                <li>
                    <a href="LecturerMainServlet"><i class="fa fa-list-alt" aria-hidden="true"></i> Assessment Overview</a>
                </li>
                <li>
                    <a href="AddAssessmentServlet"><i class="fa fa-plus" aria-hidden="true"></i> Add Assessment</a>
                </li>
                <%}%>


            </ul>
        </div>
    </div>
</nav>
