/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.tblNotification;

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
public class TblNotificationDAO implements Serializable {

    Connection con;
    PreparedStatement stm;
    ResultSet rs;

    public TblNotificationDAO() {
    }

    public boolean createNotification(TblNotificationDTO dto) throws NamingException, SQLException {
        boolean result = false;
        try {
            String sql = "insert into tbl_Notification(PostID , Email , Date , Notification_Content) "
                    + "values (? , ?, ? ,? )";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setInt(1, dto.getPostId());
                stm.setString(2, dto.getEmail());
                stm.setTimestamp(3, dto.getDate());
                stm.setNString(4, dto.getNotificationContent());

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

    public List<TblNotificationDTO> getNotification(String userMail) throws NamingException, SQLException {
        List<TblNotificationDTO> result = null;
        try {
            String sql = "select PostID , Notification_Content , Date "
                    + "from tbl_Notification "
                    + "where Email = ? "
                    + "order by Date DESC ";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setString(1, userMail);
                rs = stm.executeQuery();

                result = new ArrayList<>();
                while (rs.next()) {
                    int postId = rs.getInt("PostID");
                    String notificationContent = rs.getString("Notification_Content");
                    Timestamp date = rs.getTimestamp("Date");
                    TblNotificationDTO dto = new TblNotificationDTO();
                    dto.setPostId(postId);
                    dto.setNotificationContent(notificationContent);
                    dto.setDate(date);

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
}
