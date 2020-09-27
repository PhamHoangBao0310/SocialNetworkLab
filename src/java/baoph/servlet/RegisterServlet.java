/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.servlet;

import baoph.tblAccount.TblAccountDAO;
import baoph.tblAccount.TblAccountRegisterError;
import baoph.utils.SHAHelpers;
import baoph.utils.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author DELL
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(RegisterServlet.class);
    private final String ERROR_PAGE = "error.html";
    private final String LOGIN_PAGE = "login.html";
    private final String REGISTER_PAGE_JSP = "register.jsp";
    private final String REGISTER_PAGE = "register.html";

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
        logger.info("RegisterServlet is called");
        // Get parameter from request txtUserID, txtPassword , txtPasswordConfirm ,txtFullName.
        String txtUserID = request.getParameter("txtUserID");
        String txtPassword = request.getParameter("txtPassword");
        String txtPasswordConfirm = request.getParameter("txtPasswordConfirm");
        String txtFullName = request.getParameter("txtFullName");
        TblAccountRegisterError error = new TblAccountRegisterError();
        String url = ERROR_PAGE;
        boolean foundError = false;
        try {
            /* TODO output your page here. You may use following sample code. */
            // Email Validation
            if (!Validation.checkEmailInput(txtUserID)) {
                foundError = true;
                error.setEmailFormatError("Email is invalid");
            }
            // Password Validation
            if (txtPassword.trim().length() < 5 || txtPassword.trim().length() > 20) {
                foundError = true;
                error.setPasswordError("Password must be from 5 to 20");
            } else if (!txtPassword.trim().equals(txtPasswordConfirm.trim())) {
                foundError = true;
                error.setConfirmNotMatched("Cofirm not matched");
            }
            // Fullname Validation
            if (txtFullName.trim().length() < 5 || txtFullName.trim().length() > 20) {
                foundError = true;
                error.setFullnameLengthError("Full name must be from 5 to 20");
            }

            if (foundError) {
                // Create attribute ERROR to provide error message.
                request.setAttribute("ERROR", error);
                url = REGISTER_PAGE_JSP;
            } else {
                // No error found
                txtPassword = SHAHelpers.convertToSHAString(txtPassword);
                TblAccountDAO dao = new TblAccountDAO();
                // register user by DAO
                boolean register = dao.createAccount(txtUserID, txtPassword, txtFullName);
                if (register) {
                    url = LOGIN_PAGE;
                }
            }
        } catch (NoSuchAlgorithmException ex) {
            logger.error("RegisterServlet_NoSuchAlgorithm : " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("RegisterServlet_Naming" + ex.getMessage());
        } catch (SQLException ex) {
            // Duplication Validation
            String msg = ex.getMessage();
            logger.error("RegisterServlet_SQL: " + ex.getMessage());
            if (msg.contains("duplicate")) {
                error.setUsernameIsExisted("Email has registered");
                request.setAttribute("ERROR", error);
                url = REGISTER_PAGE_JSP;
            }
        } catch (Exception ex) {
            logger.error("RegisterServlet_Exception" + ex.getMessage());
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
