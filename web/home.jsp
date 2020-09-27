<%-- 
    Document   : home
    Created on : Sep 16, 2020, 3:00:34 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" crossorigin="anonymous">
        <title>Vie Home Page</title>
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


        <!-- Button trigger modal -->
        <button type="button" class="btn btn-outline-warning" data-toggle="modal" data-target="#ModalCenter">
            What is on your mind , ${user.name} ?
        </button>

        <!-- Post modal -->
        <div class="modal fade" id="ModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="form_group">
                        <form action="DispatchController" method="POST" enctype="multipart/form-data" >
                            <!--!Modal header-->
                            <div class="modal-header">
                                <input class="modal-title form-control" type="text" name="txtTitle" value="" placeholder="Your post's title" required="required" />
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <!--!Modal body-->
                            <div class="modal-body">
                                <textarea class="form-control" rows="10" placeholder="Tell me what you feel" name="txtContent" value=""></textarea>
                                <label for="imageFile">Add image</label>
                                <input  type="file" class="form-control-file" id="imageFile" accept="image/*" name="txtImage" multiple="multiple"
                                        onchange ="loadImage()">
                                <br />
                                <!--load image preview-->
                                <b>Preview</b>
                                <br />
                                <div id="dvPreview"></div>
                            </div>
                            <!--!Modal footer-->
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <input type="submit" class="btn btn-primary" name="btnAction" value="Save new post"/>
                            </div>
                        </form> 
                    </div>
                </div>
            </div>
        </div>
        <br>


        <!--Delete Post Confirmation Modal -->
        <div class="modal" id="DeleteConfirmationModal">
            <div class="modal-dialog">
                <div class="modal-content">

                    <!-- Modal Header -->
                    <div class="modal-header">
                        <h4 class="modal-title">Are you sure?</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <form action="DispatchController">
                        <div class="modal-body">
                            <input type="hidden" name="postID" value="" id="confirmPostID" />
                        </div>
                        <!-- Modal footer -->
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                            <input type="submit" class="btn btn-primary" name="btnAction" value="Delete this post"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        
        <!--get my post list-->
        <c:set var="listPost" value="${requestScope.LISTPOST}"/>
        <c:if test="${not empty listPost}">
            <h2>Your post</h2>
            <table border="1" class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col">Title</th>
                        <th scope="col">Date</th>
                        <th scope="col">View</th>
                        <th scope="col">Delete</th>
                        <th scope="col">Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="mypost" items="${listPost}" varStatus="counter">
                    <form action="">
                        <tr>
                            <td>
                                ${mypost.title}
                            </td>
                            <td>
                                ${mypost.date}
                            </td>
                            <td>
                                <c:url var="view" value="DispatchController">
                                    <c:param name="postId" value="${mypost.postId}"/>
                                    <c:param name="btnAction" value="View Post"/>
                                </c:url>
                                <a class="btn btn-secondary" href="${view}" style="text-decoration: none;" >View</a>
                            </td>


                            <c:if test="${mypost.status}">
                                <td>                                    
                                    <button type="button" class="btn btn-primary" onclick="getInfoToConFirm(${mypost.postId})" data-toggle="modal" data-target="#DeleteConfirmationModal">
                                        Delete
                                    </button>
                                </td>
                                <td>
                                    Active  
                                </td>

                            </c:if>
                            <c:if test="${not mypost.status}">
                                <td>

                                </td>
                                <td>
                                    Deleted  
                                </td>
                            </c:if>    
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <!--show search result-->
    <c:if test="${not empty param.txtSearch}">
        
        <c:set var="result" value="${requestScope.LIST_RESULT}"/>
        <c:set var="numOfResult" value="${requestScope.NUM_OF_RESULT}"/>
        
        <c:if test="${not empty result}">
            
            <c:forEach var="post" items="${result}" varStatus="counter">
                <c:url var="viewPost" value="DispatchController">
                    <c:param name="postId" value="${post.postId}"/>
                    <c:param name="btnAction" value="View Post"/>
                </c:url>
                ${counter.count} . <a href="${viewPost}">${post.title}</a> by ${post.email}  at ${post.date}<br> <br> <br>
            </c:forEach>

            <c:forEach var="count" begin="1" end="${numOfResult}">
                <c:url var="nextPage" value="DispatchController">
                    <c:param name="txtSearch" value="${param.txtSearch}"/>
                    <c:param name="index" value="${count}"/>
                    <c:param name="btnAction" value="Search Now!"/>
                </c:url>
                <a href="${nextPage}">${count} </a> &nbsp; 
            </c:forEach>

        </c:if>
        <c:if test="${empty result}">
            Sorry , We can not find any post you want
        </c:if>
    </c:if>

    <script>
        function getInfoToConFirm(postId) { //this function is used to add post ID to hidden field with ID: "confirmPostID"
            var confirmPostID = document.getElementById("confirmPostID");
            confirmPostID.value = postId;
            console.log("Confirm post ID : " + document.getElementById("confirmPostID").value);
        }

        function loadImage() { // This function is used to load new image to preview
            var dvPreview = document.getElementById("dvPreview");
            // remove all image loaded
            while (dvPreview.firstChild) {
                dvPreview.removeChild(dvPreview.lastChild);
            }
            // Load image path from image input id : "imageFile" 
            var imageFile = document.getElementById("imageFile");
            // Loop for load all image
            for (var i = 0; i < imageFile.files.length; i++) {
                var image = imageFile.files[i];
                var newImg = document.createElement("img");
                newImg.height = "100";
                newImg.width = "100";
                newImg.src = window.URL.createObjectURL(image);
                dvPreview.appendChild(newImg);
            }
        }
    </script>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"  crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"  crossorigin="anonymous"></script>
</body>
</html>
