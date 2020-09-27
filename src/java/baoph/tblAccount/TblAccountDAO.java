/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.tblAccount;

import baoph.utils.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class TblAccountDAO implements Serializable {

    Connection con;
    PreparedStatement stm;
    ResultSet rs;

    public TblAccountDAO() {
    }

    public boolean createAccount(String email, String password, String fullname) throws NamingException, SQLException {
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "Insert into tbl_Account(Email , Name , Password , Role , Status) "
                        + "values (? , ? , ? , ? , ?) ";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, fullname);
                stm.setString(3, password);
                stm.setInt(4, 0);
                stm.setInt(5, 0);

                int row = stm.executeUpdate();

                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public TblAccountDTO login(String txtUserID, String txtPassword) throws NamingException, SQLException {
        TblAccountDTO dto = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "select Name , Role,  Status from tbl_Account "
                        + "where Email = ? and Password = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, txtUserID);
                stm.setString(2, txtPassword);

                rs = stm.executeQuery();

                if (rs.next()) {
                    String fullname = rs.getString("Name");
                    int role = rs.getInt("Role");
                    int status = rs.getInt("Status");

                    dto = new TblAccountDTO(txtUserID, fullname, "", role, status);
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

    public String getNameByEmail(String email) throws NamingException, SQLException {
        String result = "";
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "select Name "
                        + "from tbl_Account "
                        + "where Email = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();

                if (rs.next()) {
                    result = rs.getString("Name");
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

    public boolean activateAccount(TblAccountDTO dto) throws NamingException, SQLException {
        boolean result = false;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "update tbl_Account "
                        + "set Status = ? "
                        + "where Email = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, 1);
                stm.setString(2, dto.getEmail());

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
