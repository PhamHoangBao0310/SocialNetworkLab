/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.servlet;

import baoph.tblAccount.TblAccountDTO;
import baoph.tblArticle.TblArticleDAO;
import baoph.tblArticle.TblArticleDTO;
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
public class ViewPostServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(ViewPostServlet.class);
    private final String ERROR_PAGE = "error.html";
    private final String GET_COMMENT_CONTROLLER = "GetCommentServlet";
    private final String VIEW_POST_PAGE = "viewPost.jsp";

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
        logger.info("ViewPostServlet is called");
        String url = ERROR_PAGE;
        String postId = request.getParameter("postId");
        try {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(false);
            TblAccountDTO user = (TblAccountDTO) session.getAttribute("User");
            TblArticleDAO dao = new TblArticleDAO();
            // get article by DAO
            TblArticleDTO dto = dao.viewPost(Integer.parseInt(postId));
            url = GET_COMMENT_CONTROLLER;
            if (dto != null) { // check if article exist in DB
                if (!dto.isStatus()) { // check is article is deleted
                    if (!user.getEmail().equals(dto.getEmail())) { // check if this article is not belong to this client
                        // cause this article is not belong to client so set dto is null
                        dto = null;
                        url = VIEW_POST_PAGE;
                    }
                }
            } else { // check if article is not exist in DB
                url = VIEW_POST_PAGE;
            }
            //set attribute POST in request scope
            request.setAttribute("POST", dto);
        } catch (SQLException ex) {
            logger.error("SearchServlet_SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("SearchServlet_Naming: " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("SearchServlet_Exception: " + ex.getMessage());
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
