/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author DELL
 */
@MultipartConfig
public class DispatchController extends HttpServlet {

    static Logger logger = Logger.getLogger(DispatchController.class);
    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String ERROR_PAGE = "error.html";
    private final String LOGIN_PAGE = "login.html";
    private final String REGISTER_PAGE = "register.html";
    private final String INVALID_PAGE = "invalid.html";
    private final String REGISTER_CONTROLLER = "RegisterServlet";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    private final String FIRST_TIME_CONTROLLER = "FirstTimeServlet";
    private final String SERACH_CONTROLLER = "SearchServlet";
    private final String VIEW_POST_CONTROLLER = "ViewPostServlet";
    private final String GET_MY_POSTS_CONTROLLER = "GetMyPostsServlet";
    private final String CREATE_NEW_ARTICLE_CONTROLLER = "CreateArticleServlet";
    private final String CREATE_COMMENT_CONTROLLER = "CreateCommentServlet";
    private final String DELETE_POST_CONTROLLER = "DeletePostServlet";
    private final String DELETE_COMMENT_CONTROLLER = "DeleteCommentServlet";
    private final String CREATE_EMOTION_CONTROLLER = "CreateEmotionServlet";
    private final String SHOW_NOTIFICATION_CONTROLLER = "ShowNotificationServlet";
    private final String VERIFY_CONTROLLER = "VerifyServlet";

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
        PrintWriter out = response.getWriter();
        String url = ERROR_PAGE;
        logger.info("DispatchController is called");
        try {
            /* TODO output your page here. You may use following sample code. */
            String button = request.getParameter("btnAction");
            logger.info("button in dispatchController :" + button);
            if (button == null) {
                url = FIRST_TIME_CONTROLLER;
            } else if (button.equals("Create account now !")) {
                url = REGISTER_CONTROLLER;
            } else if (button.equals("Login now!")) {
                url = LOGIN_CONTROLLER;
            } else if (button.equals("Log out")) {
                url = LOGOUT_CONTROLLER;
            } else if (button.equals("Search Now!")) {
                url = SERACH_CONTROLLER;
            } else if (button.equals("View Post")) {
                url = VIEW_POST_CONTROLLER;
            } else if (button.equals("Get my post")) {
                url = GET_MY_POSTS_CONTROLLER;
            } else if (button.equals("login page")) {
                url = LOGIN_PAGE;
            } else if (button.equals("register page")) {
                url = REGISTER_PAGE;
            } else if (button.equals("Save new post")) {
                url = CREATE_NEW_ARTICLE_CONTROLLER;
            } else if (button.equals("Create Comment")) {
                url = CREATE_COMMENT_CONTROLLER;
            } else if (button.equals("Delete this post")) {
                url = DELETE_POST_CONTROLLER;
            } else if (button.equals("Delete this comment")){
                url = DELETE_COMMENT_CONTROLLER;
            } else if (button.equals("Send Emotion")) {
                url = CREATE_EMOTION_CONTROLLER;
            } else if (button.equals("View Notification")){
                url = SHOW_NOTIFICATION_CONTROLLER;
            } else if (button.equals("Check code")){
                url = VERIFY_CONTROLLER;
            }
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
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
