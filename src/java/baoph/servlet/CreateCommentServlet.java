/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.servlet;

import baoph.tblAccount.TblAccountDTO;
import baoph.tblComment.TblCommentDAO;
import baoph.tblComment.TblCommentDTO;
import baoph.tblNotification.TblNotificationDAO;
import baoph.tblNotification.TblNotificationDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
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
public class CreateCommentServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(CreateCommentServlet.class);
    private String ERROR_PAGE = "error.html";

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
        // get postID , txtComment, txtAuthor parameter.
        String postID = request.getParameter("postID");
        String comment_content = request.getParameter("txtComment");
        String author = request.getParameter("txtAuthor");
        logger.info("CreateCommentServlet is called");
        try {
            /* TODO output your page here. You may use following sample code. */
            TblCommentDAO dao = new TblCommentDAO();
            TblCommentDTO newComment = new TblCommentDTO();
            // get user email
            HttpSession session = request.getSession(false);
            String useremail = "";
            if (session != null) {
                TblAccountDTO user = (TblAccountDTO) session.getAttribute("User");
                useremail = user.getEmail();
            }
            // set new property to newComment
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            newComment.setCommentContent(comment_content);
            newComment.setPostID(Integer.parseInt(postID));
            newComment.setUserEmail(useremail);
            newComment.setDate(currentTime);
            newComment.setStatus(true);
            // create comment by DAO
            boolean createComment = dao.createComment(newComment);
            // check create comment success or not
            if (createComment) {
                int id = dao.getMaxID();
                // write response message to client to display comment .
                out.write(id + "/" + currentTime.toString() + "/" + comment_content + "/" + useremail);
                // verify the comment is from post's owner or other members
                if (!author.equals(useremail)) {
                    TblNotificationDAO notificationDao = new TblNotificationDAO();
                    TblNotificationDTO notificationDto = new TblNotificationDTO();
                    // set properties to notification
                    notificationDto.setPostId(Integer.parseInt(postID));
                    notificationDto.setEmail(author);
                    notificationDto.setNotificationContent(useremail + " commented in your post : " + comment_content);
                    notificationDto.setDate(new Timestamp(System.currentTimeMillis()));
                    // send notification by DAO
                    boolean rs = notificationDao.createNotification(notificationDto);
                    if (rs) {
                        logger.debug("Create notification OK");
                    } else {
                        logger.debug("Create notification not OK");
                    }
                }
            } else {
                out.write("no comment is created");
            }
        } catch (NamingException ex) {
            logger.error("CreateCommentServlet_Naming : " + ex.getMessage());
        } catch (SQLException ex) {
            logger.error("CreateCommentServlet_SQL : " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("CreateCommentServlet_Exception : " + ex.getMessage());
        } finally {
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
