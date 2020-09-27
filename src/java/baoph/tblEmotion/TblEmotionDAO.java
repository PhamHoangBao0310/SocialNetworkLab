/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.tblEmotion;

import baoph.utils.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class TblEmotionDAO implements Serializable {

    Connection con;
    PreparedStatement stm;
    ResultSet rs;

    public TblEmotionDAO() {
    }

    public int getLikeNumber(int postId) throws NamingException, SQLException {
        int result = 0;
        try {
            String sql = "Select count(Emotion) as Num "
                    + "from tbl_Emotion "
                    + "where PostID = ?  and Emotion = ?";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setInt(1, postId);
                stm.setBoolean(2, true);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("Num");
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

    public int getDisLikeNumber(int postId) throws NamingException, SQLException {
        int result = 0;
        try {
            String sql = "Select count(Emotion) as Num "
                    + "from tbl_Emotion "
                    + "where PostID = ?  and Emotion = ?";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setInt(1, postId);
                stm.setBoolean(2, false);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("Num");
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

    public TblEmotionDTO checkUserEmotionInPost(int postId, String user_mail) throws NamingException, SQLException {
        TblEmotionDTO result = null;
        try {
            String sql = "select Emotion "
                    + "from tbl_Emotion "
                    + "where PostID = ? and User_Mail = ? ";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setInt(1, postId);
                stm.setString(2, user_mail);

                rs = stm.executeQuery();
                if (rs.next()) {
                    boolean emotion = rs.getBoolean("Emotion");

                    result = new TblEmotionDTO();
                    result.setEmmotion(emotion);
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

    public void deleteEmotion(int postId, String user_mail) throws NamingException, SQLException {
        try {
            String sql = "delete from tbl_Emotion "
                    + "where PostID = ? and User_Mail = ? ";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setInt(1, postId);
                stm.setString(2, user_mail);
                stm.executeUpdate();
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public boolean createEmotion(int postId, String user_mail, int emotionType) throws NamingException, SQLException {
        boolean result = false;
        try {
            String sql = "insert into tbl_Emotion(PostID , User_Mail ,  Emotion , Date) "
                    + "values(? , ? ,? ,?)";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setInt(1, postId);
                stm.setString(2, user_mail);
                if (emotionType == 1) {
                    stm.setBoolean(3, true);
                } else {
                    stm.setBoolean(3, false);
                }
                stm.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

                result = stm.executeUpdate() > 0;
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
}
