<%-- 
    Document   : viewPost
    Created on : Sep 17, 2020, 11:30:11 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vie Post Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" crossorigin="anonymous">
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    </head>
    <body>
        <c:set var="user" value="${sessionScope.User}" />

        <!--Navigation bar-->
        <nav class="navbar navbar-light bg-light">
            <p><a href="DispatchController?btnAction=Get my post" style="text-decoration: none;"  class="navbar-brand">Hi ${user.name}</a></p>
            <form class="form-inline my-2 my-lg-0" action="DispatchController" accept-charset="utf-8">
                <input class="form-control mr-sm-2" type="text" name="txtSearch" placeholder="Search" value="${param.txtSearch}" />
                <input type="hidden" name="index" value="1" />
                <input class="btn btn-outline-success  my-2 my-sm-0" type="submit" name="btnAction" value="Search Now!" />
            </form>
            <a class="navbar-brand" href="DispatchController?btnAction=View Notification" style="text-decoration: none;">Notification</a>
            <a class="navbar-brand" href="DispatchController?btnAction=Log out" style="text-decoration: none;">Log out</a>
        </nav>


        <!--Delete Comment Confirmation -->
        <div class="modal" id="DeleteConfirmationModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <!-- Modal Header -->
                    <div class="modal-header">
                        <h4 class="modal-title">Are you sure?</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <!-- Modal body -->
                    <div class="modal-body">
                        <input type="hidden" name="postID" id="postId" value="${param.postId}" />
                        <input type="hidden" name="commentID" value="" id="confirmCommentID" />
                    </div>
                    <!-- Modal footer -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                        <button class="btn btn-primary" id="delete-btn">Delete this comment</button>
                    </div>
                </div>
            </div>
        </div>


        <!--Show post-->
        <c:set var="post" value="${requestScope.POST}"/>
        <c:if test="${not empty post}">
            <div style="width: 90%; margin: 10% auto ;background: #f7f7f7; box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);">
                <h2>${post.title}</h2>
                <h6>by ${post.email}</h6>
                <input type="hidden" name="txtAuthor" value="${post.email}"  id="author"/>
                <h6>post on ${post.date}</h6>
                <br>
                <br>

                <p>${post.postContent}</p>

                <c:if test="${not empty post.image}">
                    <c:set var="listImage" value="${fn:split(post.image , ' ')}"/>
                    <c:forEach var="image" items="${listImage}" varStatus="counter">
                        <img src="${image}" alt="Image" style="width:40%; height: 50%"/>
                    </c:forEach>
                </c:if>

                <!--Show Like and Dislike-->
                <br>
                <br>
                <c:set var="user_reaction" value="${requestScope.USER_REACTION}"/>
                <c:set var="like" value="${requestScope.LIKE}"/>
                <c:set var="dislike" value="${requestScope.DISLIKE}"/>

                <button type="button" class="btn btn-outline-success" id="btnLike" style="background-color: white;">Like</button> &nbsp;
                <c:if test="${not empty like}">
                    <span id="likeCount">
                        ${like}  
                    </span>
                </c:if>
                <c:if test="${empty like}">
                    <span id="likeCount">
                        0  
                    </span>
                </c:if>
                &nbsp; &nbsp;
                <button type="button" class="btn btn-outline-success" id="btnDislike" style="background-color: white;">Dislike</button> &nbsp;
                <c:if test="${not empty dislike}">
                    <span id="dislikeCount">
                        ${dislike}  
                    </span>
                </c:if>
                <c:if test="${empty dislike}">
                    <span id="dislikeCount">
                        0 
                    </span>
                </c:if>
                <c:if test="${not empty user_reaction }">
                    <c:if test="${user_reaction.emmotion}">
                        <script>
                            var btnLike = document.getElementById("btnLike");
                            btnLike.style.backgroundColor = "blue";
                        </script>
                    </c:if>
                    <c:if test="${not user_reaction.emmotion}">
                        <script>
                            var btnDisLike = document.getElementById("btnDislike");
                            btnDisLike.style.backgroundColor = "blue";
                        </script>
                    </c:if>
                </c:if>



                <!--Comment box-->
                <br>
                <br>
                <div class="form-group" >
                    <label for="comment">Comment:</label>
                    <input type="hidden" name="postID" id="postID" value="${post.postId}" />
                    <textarea class="form-control" rows="2" id="comment" name="txtComment"></textarea>
                </div>
                <button class="btn btn-primary" id="btn-action">Create Comment</button>


                <!--Show Comments-->
                <br>
                <br>
                <br>
                <h3>Comments</h3>
                <br>
                <br>
                <c:set var="commentList" value="${requestScope.LIST_COMMENT}"/>
                <div id="CommentBox">
                    <c:if test="${not empty commentList}">
                        <c:forEach var="comment" items="${commentList}" varStatus="counter">
                            <div class="CommentElement" id="${comment.commentID}">
                                <h5>  ${comment.userEmail}  at ${comment.date}</h5>
                                <span>${comment.commentContent} </span>   &nbsp; &nbsp; 
                                <c:if test="${comment.userEmail eq user.email}">
                                    <button type="button" class="btn btn-primary" onclick="getCommentInfo(${comment.commentID})" data-toggle="modal" data-target="#DeleteConfirmationModal">
                                        Delete
                                    </button>
                                </c:if>
                                <br>
                                <br>
                            </div>
                        </c:forEach>
                    </c:if>
                    
                    <c:if test="${empty commentList}">
                        <div id="NoCommentBox">
                            No comment
                        </div>
                    </c:if>
                </div>
            </div>
        </c:if>
        
        <c:if test="${empty post}">
            <h2>Sorry we did not find the post you want.</h2>
        </c:if>


        <script>

            function sendRequestCreateComment() {
                if (document.getElementById("comment").value.trim() === "") {

                } else {
                    var postId = document.getElementById("postID").value;
                    var txtComment = document.getElementById("comment").value;
                    var txtAuthor = document.getElementById("author").value;
                    // ajax request
                    $.ajax({
                        url: "DispatchController",
                        method: 'POST',
                        data: {postID: postId, txtComment: txtComment, btnAction: "Create Comment", txtAuthor: txtAuthor},
                        success: function (data) {
                            console.log(data);
                            var items = data.split("/");
                            // Create div element
                            var new_comment_element = document.createElement('div');
                            new_comment_element.className = "CommentElement";
                            new_comment_element.id = items[0];
                            // Create h5 element
                            var h = document.createElement("H5");
                            var t = document.createTextNode(items[3] + " at " + items[1]);
                            h.appendChild(t);
                            new_comment_element.appendChild(h);
                            // Create span element
                            var span = document.createElement("SPAN");
                            var content = document.createTextNode(items[2]);
                            span.appendChild(content);
                            new_comment_element.appendChild(span);

                            // Create button delete element
                            var button = document.createElement("button");
                            var label = document.createTextNode("Delete");
                            button.setAttribute("class", "btn btn-primary");
                            button.setAttribute("data-toggle", "modal");
                            button.setAttribute("data-target", "#DeleteConfirmationModal");
                            button.setAttribute("onclick", "getCommentInfo(" + items[0] + ")");
                            button.type = "button";
                            button.appendChild(label);

                            new_comment_element.appendChild(button);

                            var br1 = document.createElement("BR");
                            var br2 = document.createElement("BR");
                            new_comment_element.appendChild(br1);
                            new_comment_element.appendChild(br2);

                            var commentElement = document.getElementsByClassName("CommentElement");
                            if (commentElement.length === 0) {
                                // check if post has any comment , if not exist remove no comment box.
                                var commentBox = document.getElementById("CommentBox");
                                commentBox.appendChild(new_comment_element);
                                var noCommentBox = document.getElementById("NoCommentBox");
                                commentBox.removeChild(noCommentBox);
                            } else {
                                // if exist comment insert above by insertbefore command.
                                let parentNode = document.getElementsByClassName("CommentElement")[0].parentNode;
                                parentNode.insertBefore(new_comment_element, document.getElementsByClassName("CommentElement")[0]);
                            }
                            document.getElementById("comment").value = "";
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            alert("Can't not send comment");
                        }
                    });
                }
            }

            $(document).on("click", "#btn-action", function () {
                console.log("aaaaaaaaaaaaaa");
                sendRequestCreateComment();
            });

            function getCommentInfo(commentId) {
                var confirmCommentID = document.getElementById("confirmCommentID");
                confirmCommentID.value = commentId;
                console.log(document.getElementById("confirmCommentID").value);
            }

            function sendRequestDeleteComment() {
                var commentId = document.getElementById("confirmCommentID").value;
                $.ajax({
                    url: "DispatchController",
                    method: "GET",
                    data: {commentID: commentId, btnAction: "Delete this comment"},
                    success: function (data) {
                        console.log("Delete comment id : " + data);
                        // Remove CommentElement div in CommentBox div . Each CommentElement has id is comment id in database.
                        var commentBox = document.getElementById("CommentBox");
                        var deletedChild = document.getElementById(data);
                        commentBox.removeChild(deletedChild);
                        // Check if not any comment exist, add "no comment" box .
                        var commentElementClass = document.getElementsByClassName("CommentElement");
                        if (commentElementClass.length === 0) {
                            var noCommentBox = document.createElement('div');
                            var text = document.createTextNode("No comment");
                            noCommentBox.id = "NoCommentBox";
                            noCommentBox.appendChild(text);
                            commentBox.appendChild(noCommentBox);
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert("Can't not delete comment");
                    }
                });
            }

            $(document).on("click", "#delete-btn", function () {
                console.log("bbbbbbbbbb");
                sendRequestDeleteComment();
                $("#DeleteConfirmationModal").modal("hide");
            });

            function resetBtn() {
                var btnLike = document.getElementById("btnLike");
                var btnDislike = document.getElementById("btnDislike");
                var likeCount = document.getElementById("likeCount");
                var dislikeCount = document.getElementById("dislikeCount");
                // Decrease like count and dislike count if this button is selected
                if (btnLike.style.backgroundColor === "blue") {
                    likeCount.innerHTML = parseInt(likeCount.innerHTML) - 1;
                }
                if (btnDislike.style.backgroundColor === "blue") {
                    dislikeCount.innerHTML = parseInt(dislikeCount.innerHTML) - 1;
                }
            }

            function sendEmotion(emotionType) {
                var postId = document.getElementById("postID").value;
                var txtAuthor = document.getElementById("author").value;
                $.ajax({
                    url: "DispatchController",
                    method: "GET",
                    data: {postID: postId, btnAction: "Send Emotion", emotionType: emotionType, txtAuthor: txtAuthor},
                    success: function (data) {
                        console.log("data :" + data);
                        var btnDislike = document.getElementById("btnDislike");
                        var btnLike = document.getElementById("btnLike");
                        var likeCount = document.getElementById("likeCount");
                        var dislikeCount = document.getElementById("dislikeCount");
                        // If emotion is like, change background of btnLike to blue and btnDislike to white, increase like count
                        if (emotionType === "like") {
                            likeCount.innerHTML = parseInt(likeCount.innerHTML) + 1;
                            btnLike.style.backgroundColor = "blue";
                            btnDislike.style.backgroundColor = "white";
                            console.log("like here");
                        }
                        // If emotion is dislike, change background of btnDislike to blue and btnLike to white, increase dislike count
                        if (emotionType === "dislike") {
                            dislikeCount.innerHTML = parseInt(dislikeCount.innerHTML) + 1;
                            btnDislike.style.backgroundColor = "blue";
                            btnLike.style.backgroundColor = "white";
                            console.log("dislike here");
                        }
                        // If emotion is unlike , change background color of btnLike to white
                        if (emotionType === "unlike") {
                            btnLike.style.backgroundColor = "white";
                            console.log("unlike here");
                        }
                        // If emotion is undislike , change background color of btnDislike to white
                        if (emotionType === "undislike") {
                            btnDislike.style.backgroundColor = "white";
                            console.log("undislike here");
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert("Can't not like or dislike");
                    },
                    cache: false
                });
            }
            $(document).on("click", "#btnLike", function () {
                var btnLike = document.getElementById("btnLike");
                resetBtn();
                // Verify this action is like or unlike by this background color
                if (btnLike.style.backgroundColor === "white") {
                    sendEmotion("like");
                } else {
                    sendEmotion("unlike");
                }
                console.log("Like");
            });

            $(document).on("click", "#btnDislike", function () {
                var btnDislike = document.getElementById("btnDislike");
                resetBtn();
                // Verify this action is dislike or undislike by this background color
                if (btnDislike.style.backgroundColor === "white") {
                    sendEmotion("dislike");
                } else {
                    sendEmotion("undislike");
                }
                console.log("Dislike");
            });
        </script>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"  crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
