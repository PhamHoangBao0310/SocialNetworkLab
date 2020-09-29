/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.servlet;

import baoph.tblAccount.TblAccountDAO;
import baoph.tblAccount.TblAccountDTO;
import baoph.utils.SHAHelpers;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
public class FirstTimeServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(FirstTimeServlet.class);
    private final String LOGIN_PAGE = "login.html";
    private final String GET_MY_POST_CONTROLLER = "GetMyPostsServlet";

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
        String url = LOGIN_PAGE;
        logger.info("FirstTimeServlet is called");
        try {
            /* TODO output your page here. You may use following sample code. */
            // get cookies from request.
            Cookie[] cookies = request.getCookies();
            // create user Email and password variable for auto login.
            String userEmail = "";
            String userPassword = "";
            // check cookies to get 2 cookies includes VieUserEmail(contains email) and VieUserPassword(contains password).
            if (cookies != null) {
                // Loop on cookies
                for (Cookie cookie : cookies) {
                    String tempName = cookie.getName();
                    String tempValue = cookie.getValue();
                    if (tempName.equals("VieUserEmail")) {
                        userEmail = tempValue;
                    }
                    if (tempName.equals("VieUserPassword")) {
                        userPassword = tempValue;
                    }
                }
                TblAccountDAO dao = new TblAccountDAO();
                // login by DAO
                TblAccountDTO dto = dao.login(userEmail, SHAHelpers.convertToSHAString(userPassword));
                // check login is success or not
                if (dto != null) { // Login OK 
                    if (dto.getStatus() == 1) {
                        HttpSession session = request.getSession();
                        // set attribute User to session
                        session.setAttribute("User", dto);
                        url = GET_MY_POST_CONTROLLER;
                        logger.debug("FirstTime - Has User");
                    } else {
                        logger.debug("User is not activated");
                    }
                }
            } else {
                logger.debug("No cookies");
            }
        } catch (NamingException ex) {
            logger.error("FisrtTimeSevlet_Naming : " + ex.getMessage());
        } catch (SQLException ex) {
            logger.error("FisrtTimeSevlet_SQL : " + ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            logger.error("FisrtTimeSevlet_NoSuchAlgorithm : " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("FisrtTimeSevlet_Exception : " + ex.getMessage());
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
