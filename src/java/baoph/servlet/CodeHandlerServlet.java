/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.servlet;

import baoph.tblAccount.TblAccountDTO;
import baoph.utils.SendEmail;
import java.io.IOException;
import java.io.PrintWriter;
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
public class CodeHandlerServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(CodeHandlerServlet.class);
    private final String VERIFY_PAGE = "verify.jsp";
    private final String ERROR_PAGE ="error.html";
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
        logger.info("CodeHandlerServlet is called");
        String url = ERROR_PAGE;
        try  {
            /* TODO output your page here. You may use following sample code. */
           //feth form value
            TblAccountDTO dto = (TblAccountDTO) request.getAttribute("User");

            //create instance object of the SendEmail Class
            SendEmail sm = new SendEmail();
            //get the 6-digit code
            String code = sm.getRandom();

            //call the send email method
            boolean test = sm.sendEmail(dto , code);

            //check if the email send successfully
            if (test) {
                HttpSession session = request.getSession();
                session.setAttribute("Code", code);
                session.setAttribute("UserVerify", dto);
                url = VERIFY_PAGE;
            } else {
                logger.debug("Error in verification");
            }
        } catch (Exception ex) {
            logger.error("CodeHandlerServlet_Exception : " + ex.getMessage());
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
