<%-- 
    Document   : verify
    Created on : Sep 25, 2020, 4:53:36 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vie Verify Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" crossorigin="anonymous">
        <style>
            .login-form {
                width: 500px;
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
        <c:set var="User" value="${sessionScope.UserVerify}" />
        <c:set var="SubmitError" value="${requestScope.ERROR_SUBMIT_CODE}"/>
        <div class="login-form">
            <h2>Hi ${User.name} . Your account is not activated . We just send you a confirmation code. Please check your mail and enter the code now!</h2>
            <form action="DispatchController" method="POST">
                <div class="form-group">
                    <input type="text" class="form-control"  placeholder="Enter your code" name="txtCode" value="" />  
                </div>
                <c:if test="${not empty SubmitError}">
                    <div class="alert alert-warning">
                        <strong>Hey!</strong> ${SubmitError}
                    </div>
                </c:if> 
                <div class="form-group">
                    <input type="submit" class="btn btn-primary btn-block" name="btnAction" value="Check code" /> <br>
                </div>
            </form>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"  crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
