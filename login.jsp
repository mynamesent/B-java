<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>BootCamp Programming</title>
        <%@include file="header.jsp" %>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: black;
                background: url(img/bg.jpg) no-repeat center center fixed; 
                -webkit-background-size: cover;
                -moz-background-size: cover;    
                -o-background-size: cover;
                background-size: cover;
            }
        </style>
    </head>

    <body class="visible-phone">
    <div> 
        <a href="wrangle/index.html">
          <button   type="button" class="btn btn-warning btn-lg" data-toggle="modal" data-target="#myModal">Report Developer</button>
          </a>
    </div>
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="login-panel panel panel-default">
                        <div class="panel-heading ">
                            <h3 class="panel-title">Student Portal</h3>
                        </div>
                        <div class="">
                            <center>
                            <img src="img/sit_logo.jpg" class="logo img-circle">
                            </center>
                            <form role="form" action="LoginServlet" method="post" class="form-horizontal">
                                <fieldset>
                                    <div class="form-group">
                                        <div class="col-xs-4">
                                        <label for="in putID" class="control-label">Students ID :</label>
                                        </div>
                                        <div class="col-xs-8" style="margin:0 -10px">
                                            <input type="" class="form-control" id="inputEmail3" placeholder="Student Id">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="inputPassword3" class="col-xs-4 control-label">Password</label>
                                        <div class="col-xs-8"  style="margin:0 -10px">
                                            <input type="password" class="form-control" id="inputPassword3" placeholder="Password"  name="password" type="password" value="">
                                        </div>
                                    </div>
                                    <c:if test="${message != null}">
                                        <p class="errorm    essage">${message}</p>
                                    </c:if>
                                    <input type="submit" class="btn btn-lg btn-info btn-block" value="Login"> 
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
    <!-- jQuery -->
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/metisMenu.min.js"></script>
    <script src="js/raphael-min.js"></script>
    <script src="js/morris.min.js"></script>
    <script src="js/morris-data.js"></script>
    <script src="js/sb-admin-2.js"></script>
</html>
