/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.servlet;

import baoph.tblAccount.TblAccountDAO;
import baoph.tblAccount.TblAccountDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author DELL
 */
public class VerifyServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(VerifyServlet.class);
    private final String HOME_PAGE = "home.jsp";
    private final String ERROR_PAGE = "error.html";
    private final String LOGIN_PAGE = "login.html";
    private final String VERIFY_PAGE = "verify.jsp";

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
        logger.info("VerifyServlet is called");
        String url = ERROR_PAGE;
        try {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(false);
            if (session != null) {
                // get auth code create by server.
                String authCode = (String) session.getAttribute("Code");
                // get submit code client submit
                String submitCode = request.getParameter("txtCode");
                if (authCode.equals(submitCode)) { // check if auth code and submit code is equal
                    TblAccountDTO dto = (TblAccountDTO) session.getAttribute("UserVerify");
                    // set user attribute to session
                    session.setAttribute("User", dto);
                    session.removeAttribute("UserVerify");
                    session.removeAttribute("Code");
                    TblAccountDAO dao = new TblAccountDAO();
                    // activate account by DAO
                    boolean result = dao.activateAccount(dto);
                    if (result) { // activate account OK
                        url = HOME_PAGE;
                    }
                } else { // // check if auth code and submit code is not equal
                    request.setAttribute("ERROR_SUBMIT_CODE", "The submit code is not right !");
                    url = VERIFY_PAGE;
                }
            } else {
                url = LOGIN_PAGE;
            }

        } catch (NamingException ex) {
            logger.error("VerifyServlet_Naming : " + ex.getMessage());
        } catch (SQLException ex) {
            logger.error("VerifyServlet_SQL : " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("VerifyServlet_Exception : " + ex.getMessage());
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
