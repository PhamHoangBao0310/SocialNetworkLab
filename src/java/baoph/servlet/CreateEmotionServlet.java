/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.servlet;

import baoph.tblAccount.TblAccountDTO;
import baoph.tblEmotion.TblEmotionDAO;
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
public class CreateEmotionServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(CreateEmotionServlet.class);
    private final int LIKE = 1;
    private final int DISLIKE = 0;
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
        logger.info("CreateEmotionServlet is called");
        try {
            /* TODO output your page here. You may use following sample code. */
            // get postID , txtAuthor parameter.
            String postID = request.getParameter("postID");
            String author = request.getParameter("txtAuthor");
            // get user email.
            HttpSession session = request.getSession(false);
            String useremail = null;
            if (session != null) {
                TblAccountDTO user = (TblAccountDTO) session.getAttribute("User");
                useremail = user.getEmail();
            }
            TblEmotionDAO dao = new TblEmotionDAO();
            // get emotionType parameter . It must be unlike , like ,dislike or undislike.
            String emotionType = request.getParameter("emotionType");
            logger.debug("emotionType :" + emotionType + "/mmm");
            // If emotionType is unlike or undislike , just delete the record in database by DAO
            if (emotionType.equals("unlike") || emotionType.equals("undislike")) {
                dao.deleteEmotion(Integer.parseInt(postID), useremail);
            }
            // If emtotionType is like or dislike
            if (emotionType.equals("like") || emotionType.equals("dislike")) {
                // Step 1: Delete current emotion record.
                dao.deleteEmotion(Integer.parseInt(postID), useremail);
                boolean result = false;
                // Step 2: create new record to Database through DAO(like is 1 and dislike is 0)
                if (emotionType.equals("like")) {
                    result = dao.createEmotion(Integer.parseInt(postID), useremail, LIKE);
                }
                if (emotionType.equals("dislike")) {
                    result = dao.createEmotion(Integer.parseInt(postID), useremail, DISLIKE);
                }
                // Step 3 :Check if create success
                if (result) {
                    // // Step 4 :verify the emotion is from post's owner or other members
                    if (!author.equals(useremail)) {
                        TblNotificationDAO notificationDao = new TblNotificationDAO();
                        TblNotificationDTO notificationDto = new TblNotificationDTO();
                        // set property to notification
                        notificationDto.setEmail(author);
                        notificationDto.setPostId(Integer.parseInt(postID));
                        notificationDto.setNotificationContent(useremail + " " + emotionType + "d your post");
                        notificationDto.setDate(new Timestamp(System.currentTimeMillis()));
                        // create notification by DAO
                        notificationDao.createNotification(notificationDto);
                    }
                }
                if (result) {
                    out.write("OK");
                } else {
                    out.write("not OK");
                }
            }

        } catch (NamingException ex) {
            logger.error("CreateEmotionServlet_Naming :" + ex.getMessage());
        } catch (SQLException ex) {
            logger.error("CreateEmotionServlet_SQL :" + ex.getMessage());
        } catch (Exception ex) {
            logger.error("CreateEmotionServlet_Exception :" + ex.getMessage());
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
