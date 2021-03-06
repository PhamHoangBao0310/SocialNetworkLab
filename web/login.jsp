<%-- 
    Document   : login
    Created on : Sep 16, 2020, 2:53:35 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vie Login Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" crossorigin="anonymous">
        <style>
            .login-form {
                width: 340px;
                margin: 50px auto;
                font-size: 15px;
            }
            .login-form form {
                margin-bottom: 15px;
                background: #f7f7f7;
                box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
                padding: 30px;
            }
            .login-form h2 {
                margin: 0 0 15px;
            }
            .form-control, .btn {
                min-height: 38px;
                border-radius: 2px;
            }
            .btn {        
                font-size: 15px;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div  class="login-form">
            <form action="DispatchController" method="POST">
                <c:set var="errors" value="${requestScope.ERROR}"/>
                <h2 class="text-center">Log in</h2> 
                <div class="form-group">
                    <input type="text" class="form-control"  placeholder="Email" name="txtUserID" value="" />  
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="Password" name="txtPassswod" value="" /> <br>
                </div>
                <c:if test="${not empty errors.userNotFoundError}">
                    <div class="alert alert-warning">
                        <strong>Hey!</strong> ${errors.userNotFoundError}
                    </div>
                </c:if> 
                <div class="form-group">
                    <input type="submit" class="btn btn-primary btn-block" name="btnAction" value="Login now!" /> <br>
                </div>             
            </form>
        </div>
        <p class="text-center"><a href="DispatchController?btnAction=register page">Create an Account</a></p>


        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"  crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
