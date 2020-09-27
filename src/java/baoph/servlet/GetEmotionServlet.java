/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.servlet;

import baoph.tblAccount.TblAccountDTO;
import baoph.tblEmotion.TblEmotionDAO;
import baoph.tblEmotion.TblEmotionDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
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
public class GetEmotionServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(GetEmotionServlet.class);
    private final String ERROR_PAGE = "error.html";
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
        logger.info("GetEmotionServlet is called");
        String url = ERROR_PAGE;
        String postId = request.getParameter("postId");
        try {
            /* TODO output your page here. You may use following sample code. */
            TblEmotionDAO dao = new TblEmotionDAO();
            // Get like and dislike number from postID by DAO
            int numLikeEmotion = dao.getLikeNumber(Integer.parseInt(postId));
            int numDislikeEmotion = dao.getDisLikeNumber(Integer.parseInt(postId));
            // set LIKE attribute and DISLIKE attribute in request scope.
            request.setAttribute("LIKE", numLikeEmotion);
            request.setAttribute("DISLIKE", numDislikeEmotion);
            // get user email.
            HttpSession session = request.getSession(false);
            String useremail = "";
            if (session != null) {
                TblAccountDTO user = (TblAccountDTO) session.getAttribute("User");
                useremail = user.getEmail();
            }
            // Check user reactionin this post by DAO
            TblEmotionDTO dto = dao.checkUserEmotionInPost(Integer.parseInt(postId), useremail);
            request.setAttribute("USER_REACTION", dto);
            url = VIEW_POST_PAGE;
        } catch (NamingException ex) {
            logger.error("GetEmotionServlet_Naming : " + ex.getMessage());
        } catch (SQLException ex) {
            logger.error("GetEmotionServlet_SQL : " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("GetEmotionServlet_Exception : " + ex.getMessage());
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
