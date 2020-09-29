/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.servlet;

import baoph.tblComment.TblCommentDAO;
import baoph.tblComment.TblCommentDTO;
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
import org.apache.log4j.Logger;

/**
 *
 * @author DELL
 */
public class GetCommentServlet extends HttpServlet {
    
    static Logger logger = Logger.getLogger(GetCommentServlet.class);
    private final String ERROR_PAGE = "error.html";
    private final String GET_EMOTION_CONTROLLER = "GetEmotionServlet";
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
        logger.info("GetCommentServlet is called");
        String postId = request.getParameter("postId");
        String url = ERROR_PAGE;
        try {
            /* TODO output your page here. You may use following sample code. */
            TblCommentDAO dao = new TblCommentDAO();
            // get comment list in post from post id by DAO
            List<TblCommentDTO> list = dao.getListCommentInPost(Integer.parseInt(postId));
            request.setAttribute("LIST_COMMENT", list);
            url = GET_EMOTION_CONTROLLER;

        } catch (NamingException ex) {
            logger.error("GetCommentServlet_Naming : " + ex.getMessage());
        } catch (SQLException ex) {
            logger.error("GetCommentServlet_SQL : " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("GetCommentServlet_Exception : " + ex.getMessage());
        } finally {
            RequestDispatcher rd =request.getRequestDispatcher(url);
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
