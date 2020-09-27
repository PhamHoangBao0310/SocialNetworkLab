/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.servlet;

import baoph.tblArticle.TblArticleDAO;
import baoph.tblArticle.TblArticleDTO;
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
public class SearchServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(SearchServlet.class);
    private final String ERROR_PAGE = "error.html";
    private final String HOME_PAGE = "home.jsp";
    private final String GET_MY_POST_CONTROLLER = "GetMyPostsServlet";
    private final int ELEMENT_IN_PAGE = 2;

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
        logger.info("SearchServlet is called");
        String txtSearch = request.getParameter("txtSearch");
        String indexString = request.getParameter("index");
        String url = ERROR_PAGE;
        try {
            /* TODO output your page here. You may use following sample code. */
            if (txtSearch.trim().isEmpty()) {
                url = GET_MY_POST_CONTROLLER;
            } else {
                logger.debug("Search Value : " + txtSearch);
                url = HOME_PAGE;
                TblArticleDAO dao = new TblArticleDAO();
                int index = Integer.parseInt(indexString);
                // get article list
                List<TblArticleDTO> list = dao.searchArticle(txtSearch, index);
                // get number of article
                int numOfPage = dao.countArticle(txtSearch);
                // return the number of paging
                if (numOfPage % ELEMENT_IN_PAGE == 0) {
                    numOfPage = numOfPage / ELEMENT_IN_PAGE;
                } else {
                    numOfPage = Math.round(numOfPage / ELEMENT_IN_PAGE) + 1;
                }

                request.setAttribute("NUM_OF_RESULT", numOfPage);
                request.setAttribute("LIST_RESULT", list);
            }
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
