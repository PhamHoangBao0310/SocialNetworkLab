/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.tblArticle;

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
public class TblArticleDAO implements Serializable {

    Connection con;
    PreparedStatement stm;
    ResultSet rs;

    public TblArticleDAO() {
    }

    public List<TblArticleDTO> searchArticle(String text, int index) throws SQLException, NamingException {
        List<TblArticleDTO> result = null;
        try {
            String sql = "Select PostID , Email , Date , Title , Status "
                    + "from tbl_Article "
                    + "where (Title like ?) and (Status = ?) "
                    + "order by Date DESC "
                    + "offset ? rows fetch next 2 rows only";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setNString(1, "%" + text + "%");
                stm.setBoolean(2, true);
                stm.setInt(3, (index - 1) * 2);

                rs = stm.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    if (rs.getBoolean("Status")) {
                        int postId = rs.getInt("PostID");
                        String email = rs.getString("Email");
                        String title = rs.getNString("Title");
                        Timestamp date = rs.getTimestamp("Date");
                        boolean status = rs.getBoolean("Status");

                        TblArticleDTO dto = new TblArticleDTO();
                        dto.setPostId(postId);
                        dto.setEmail(email);
                        dto.setTitle(title);
                        dto.setDate(date);
                        dto.setStatus(status);

                        result.add(dto);
                    }
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

    public List<TblArticleDTO> getArticleByEmail(String userEmail) throws SQLException, NamingException {
        List<TblArticleDTO> result = null;
        try {
            String sql = "Select PostID  , Date , Title  , Status "
                    + "from tbl_Article "
                    + "where Email = ? "
                    + "order by Date DESC ";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setNString(1, userEmail);

                rs = stm.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    int postId = rs.getInt("PostID");
                    String title = rs.getNString("Title");
                    Timestamp date = rs.getTimestamp("Date");
                    boolean status = rs.getBoolean("Status");

                    TblArticleDTO dto = new TblArticleDTO();
                    dto.setPostId(postId);
                    dto.setTitle(title);
                    dto.setDate(date);
                    dto.setStatus(status);

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

    public int countArticle(String text) throws NamingException, SQLException {
        int result = 0;
        try {
            String sql = "Select count(Title) as Num "
                    + "from tbl_Article "
                    + "where Title like ? and Status = ? ";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setNString(1, "%" + text + "%");
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

    public TblArticleDTO viewPost(int index) throws SQLException, NamingException {
        TblArticleDTO dto = null;
        try {
            String sql = "Select Email , Date , Title , Post_Content , Image, Status "
                    + "from tbl_Article "
                    + "where PostID = ? ";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setInt(1, index);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String email = rs.getString("Email");
                    String title = rs.getNString("Title");
                    String postContent = rs.getNString("Post_Content");
                    Timestamp date = rs.getTimestamp("Date");
                    String image = rs.getString("Image");
                    boolean status = rs.getBoolean("Status");
                    dto = new TblArticleDTO();
                    dto.setPostId(index);
                    dto.setEmail(email);
                    dto.setTitle(title);
                    dto.setImage(image);
                    dto.setPostContent(postContent);
                    dto.setDate(date);
                    dto.setStatus(status);
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
        return dto;
    }

    public boolean createPost(TblArticleDTO dto) throws NamingException, SQLException {
        boolean result = false;
        try {
            String sql = "insert into tbl_Article( Email , Date , Title , Post_Content , Image , Status ) "
                    + "values (? ,? ,? ,? ,? ,?) ";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getEmail());
                stm.setTimestamp(2, dto.getDate());
                stm.setNString(3, dto.getTitle());
                stm.setNString(4, dto.getPostContent());
                stm.setString(5, dto.getImage());
                stm.setBoolean(6, dto.isStatus());

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

    public boolean deletePost(int postID) throws NamingException, SQLException {
        boolean result = false;
        try {
            String sql = "Update tbl_Article "
                    + "set Status = ? "
                    + "where PostID = ?";
            con = DBHelpers.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, false);
                stm.setInt(2, postID);

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

    
}
