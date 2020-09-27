<%-- 
    Document   : notification
    Created on : Sep 25, 2020, 3:35:08 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" crossorigin="anonymous">
        <title>Vie Notification Page</title>
    </head>
    <body>
        <c:set var="user" value="${sessionScope.User}" />
        <nav class="navbar navbar-light bg-light">
            <a href="DispatchController?btnAction=Get my post" style="text-decoration: none;" class="navbar-brand">Hi ${user.name}</a>
            <form class="form-inline my-2 my-lg-0" action="DispatchController" accept-charset="UTF-8">
                <input class="form-control mr-sm-2" type="text" name="txtSearch" placeholder="Search" value="${param.txtSearch}" />
                <input type="hidden" name="index" value="1" />
                <input class="btn btn-outline-success  my-2 my-sm-0" type="submit" name="btnAction" value="Search Now!" />
            </form>
            <a class="navbar-brand" href="DispatchController?btnAction=View Notification" style="text-decoration: none;">Notification</a>
            <a class="navbar-brand"href="DispatchController?btnAction=Log out" style="text-decoration: none;"> Log out</a>
        </nav>

        <c:set var="notifications" value="${requestScope.NOTIFICATION_LIST}" />

        <c:if test="${empty notifications}">
            <h2>No notification found!</h2>
        </c:if>

        <c:if test="${not empty notifications }">
            <h2>Notification</h2>
            
            <table border="1" class="table table-striped">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Content</th>
                        <th>View</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="notification" items="${notifications}" varStatus="counter">
                        <tr>
                            <td>
                                ${counter.count}
                            </td>
                            <td>
                                ${notification.notificationContent}
                            </td>
                            <td>
                                <c:url var="ViewPost" value="DispatchController">
                                    <c:param name="postId" value="${notification.postId}"/>
                                    <c:param name="btnAction" value="View Post"/>
                                </c:url>
                                <a class="btn btn-secondary" href="${ViewPost}" style="text-decoration: none;" >View</a>
                            </td>
                            <td>
                                ${notification.date}
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </c:if>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"  crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"  crossorigin="anonymous"></script>
    </body>
</html>
