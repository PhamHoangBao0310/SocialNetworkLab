/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.servlet;

import baoph.tblAccount.TblAccountDTO;
import baoph.tblArticle.TblArticleDAO;
import baoph.tblArticle.TblArticleDTO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.log4j.Logger;

/**
 *
 * @author DELL
 */
@MultipartConfig
public class CreateArticleServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(CreateArticleServlet.class);

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
        logger.info("CreateArticleServlet is called");
        try {
            /* TODO output your page here. You may use following sample code. */
            // get txtTitle parameter
            String txtTitle = request.getParameter("txtTitle");
            // get txtContent parameter
            String txtContent = request.getParameter("txtContent");
            // Get user email 
            HttpSession session = request.getSession(false);
            String useremail = "";
            if (session != null) {
                TblAccountDTO user = (TblAccountDTO) session.getAttribute("User");
                useremail = user.getEmail();
            }
            String filename = "";
            String imageLink = "";
            int index = 1;
            // Loop in all parts from form and look up image part.
            for (Part part : request.getParts()) {
//                System.out.println("Part name : " + part.getName());
                // Check this part is file image
                if (part.getSubmittedFileName() != null) {
                    // check is this image is empty or not
                    if (!part.getSubmittedFileName().trim().isEmpty()) {
//                        System.out.println("submitedFileName : " + part.getSubmittedFileName());
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                        // create image file name
                        filename = useremail + timeStamp + "_" + index + ".jpg";
                        index++;
                        // add image link to save in database
                        imageLink = imageLink + "image/" + filename + " ";
//                        System.out.println("path : " + filename);
                        // create image path to write image
                        String contextPath = getServletContext().getRealPath("/");
                        String realPath = contextPath + "image\\" + filename;
//                        System.out.println("RPath " + realPath);
                        // Write image by inputstream and outputstream
                        try (InputStream fileInput = part.getInputStream()) {
                            File f = new File(realPath);
                            try (OutputStream os = new FileOutputStream(f)) {
                                byte[] buf = new byte[1024];
                                int len;
                                while ((len = fileInput.read(buf)) > 0) {
                                    os.write(buf, 0, len);
                                }
                            }
                        }
                    }
                }
            }
            // DAO times
            // convert txtTitle and txtContent to iso-8859-1
            String reTxtTitle = new String(txtTitle.getBytes("iso-8859-1"), "UTF-8");
            String reTxtContent = new String(txtContent.getBytes("iso-8859-1"), "UTF-8");
            TblArticleDAO dao = new TblArticleDAO();
            TblArticleDTO dto = new TblArticleDTO();
            dto.setTitle(reTxtTitle);
            dto.setEmail(useremail);
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            dto.setDate(currentTime);
            dto.setImage(imageLink);
            dto.setPostContent(reTxtContent);
            dto.setStatus(true);
            boolean result = dao.createPost(dto);
            if (result) {
                logger.info("User created post successfully");
            } else {
                logger.info("User created post unsuccessfully");
            }
        } catch (IOException ex) {
            logger.error("CreateArticleServlet_IOE : " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("CreateArticleServlet_Naming : " + ex.getMessage());
        } catch (SQLException ex) {
            logger.error("CreateArticleServlet_SQL : " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("CreateArticleServlet_Exception : " + ex.getMessage());
        } finally {
            response.sendRedirect("DispatchController?btnAction=Get my post");
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
