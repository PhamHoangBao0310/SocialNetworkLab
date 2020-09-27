/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.servlet;

import baoph.tblAccount.TblAccountDAO;
import baoph.tblAccount.TblAccountDTO;
import baoph.tblAccount.TblAccountLoginError;
import baoph.utils.SHAHelpers;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author DELL
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(LoginServlet.class);
    private final String LOGIN_PAGE_JSP = "login.jsp";
    private final String GET_MY_POSTS_CONTROLLER = "GetMyPostsServlet";
    private final String ERROR_PAGE = "error.html";
    private final int IS_ACTIVE = 1;
    private final String VERIFY_PAGE = "verify.jsp";
    private final String CODE_HANDLER_CONTROLLER = "CodeHandlerServlet";

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
        logger.info("LoginServlet is called");
        String txtUserID = request.getParameter("txtUserID");
        String txtPasssword = request.getParameter("txtPassswod");
        TblAccountLoginError error = new TblAccountLoginError();
        String url = ERROR_PAGE;
        boolean foundError = false;
        try {
            /* TODO output your page here. You may use following sample code. */
            TblAccountDAO dao = new TblAccountDAO();
            // check login user
            TblAccountDTO dto = dao.login(txtUserID, SHAHelpers.convertToSHAString(txtPasssword));
            if (dto == null) { // check if login is not success
                foundError = true;
                error.setUserNotFoundError("Invalid username or password");
            } else { // check if login OK 
                if (dto.getStatus() != IS_ACTIVE) { // check user is not active. If not active, redirect to send code servlet
                    request.setAttribute("User", dto);
                    url = CODE_HANDLER_CONTROLLER;
                } else { // check user is active .
                    url = GET_MY_POSTS_CONTROLLER;
                    HttpSession session = request.getSession();
                    // set Atrribute User in session
                    session.setAttribute("User", dto);
                    // set 2 cookies fro auto login VieUserEmail and VieUserPassword.
                    Cookie emailCookie = new Cookie("VieUserEmail", txtUserID);
                    Cookie passwordCookie = new Cookie("VieUserPassword", txtPasssword);
                    emailCookie.setMaxAge(10 * 60);
                    passwordCookie.setMaxAge(10 * 60);
                    response.addCookie(emailCookie);
                    response.addCookie(passwordCookie);
                }
            }
            if (foundError) {
                url = LOGIN_PAGE_JSP;
                request.setAttribute("ERROR", error);
            }
        } catch (NoSuchAlgorithmException ex) {
            logger.error("LoginServlet_NoSuchAlgorithm: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("LoginServlet_Naming: " + ex.getMessage());
        } catch (SQLException ex) {
            logger.error("LoginServlet_SQL: " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("LoginServlet_Exception: " + ex.getMessage());
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
