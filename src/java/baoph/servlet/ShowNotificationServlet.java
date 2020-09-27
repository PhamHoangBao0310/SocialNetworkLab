/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.servlet;

import baoph.tblAccount.TblAccountDTO;
import baoph.tblNotification.TblNotificationDAO;
import baoph.tblNotification.TblNotificationDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
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
public class ShowNotificationServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(ShowNotificationServlet.class);
    private final String ERROR_PAGE = "error.html";
    private final String NOTIFICATION_PAGE = "notification.jsp";

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
        logger.info("ShowNotificationServlet is called");
        PrintWriter out = response.getWriter();
        String url = ERROR_PAGE;
        try {
            /* TODO output your page here. You may use following sample code. */
            TblNotificationDAO dao = new TblNotificationDAO();
            HttpSession session = request.getSession(false);
            // get user email
            String useremail = "";
            if (session != null) {
                TblAccountDTO user = (TblAccountDTO) session.getAttribute("User");
                useremail = user.getEmail();
            }
            // get notification list
            List<TblNotificationDTO> list = dao.getNotification(useremail);
            request.setAttribute("NOTIFICATION_LIST", list);
            url = NOTIFICATION_PAGE;
        } catch (NamingException ex) {
            logger.error(("ShowNotificationServlet_Naming : " + ex.getMessage()));
        } catch (SQLException ex) {
            logger.error(("ShowNotificationServlet_SQL : " + ex.getMessage()));
        } catch (Exception ex) {
            logger.error(("ShowNotificationServlet_Exception : " + ex.getMessage()));
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
