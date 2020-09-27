/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.tblComment;

import baoph.utils.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class TblCommentDAO implements Serializable {

    Connection con;
    PreparedStatement stm;
    ResultSet rs;

    public TblCommentDAO() {
    }

    public List<TblCommentDTO> getListCommentInPost(int postID) throws NamingException, SQLException {
        List<TblCommentDTO> result = null;
        try {
            String sql = "select CommentID , User_Mail , Date , Comment_Content  "
                    + "from tbl_Comment "
                    + "where PostID = ? and Status = ? "
                    + "order by Date DESC ";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setInt(1, postID);
                stm.setBoolean(2, true);
                rs = stm.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {

                    int commentID = rs.getInt("CommentID");
                    String userMail = rs.getString("User_Mail");
                    Timestamp date = rs.getTimestamp("Date");
                    String commentContent = rs.getString("Comment_Content");

                    TblCommentDTO dto = new TblCommentDTO();
                    dto.setCommentID(commentID);
                    dto.setDate(date);
                    dto.setPostID(postID);
                    dto.setCommentContent(commentContent);
                    dto.setUserEmail(userMail);
                    dto.setStatus(true);

                    result.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public boolean createComment(TblCommentDTO comment) throws NamingException, SQLException {
        boolean result = false;
        try {
            String sql = "insert into tbl_Comment(PostID , User_Mail , Date , Comment_Content , Status) "
                    + "values(? ,? ,?, ?, ?) ";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setInt(1, comment.getPostID());
                stm.setString(2, comment.getUserEmail());
                stm.setTimestamp(3, comment.getDate());
                stm.setNString(4, comment.getCommentContent());
                stm.setBoolean(5, comment.isStatus());

                result = stm.executeUpdate() > 0;

            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public boolean deleteComment(int commentID) throws NamingException, SQLException {
        boolean result = false;
        try {
            String sql = "update tbl_Comment "
                    + "set Status = ? "
                    + "where CommentID = ? ";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, false);
                stm.setInt(2, commentID);
                result = stm.executeUpdate() > 0;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public int getMaxID() throws NamingException, SQLException {
        int id = 0;
        try {
            String sql = "Select MAX(CommentID) as ID from tbl_Comment";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()){
                    id = rs.getInt("ID");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return id;
    }
}
