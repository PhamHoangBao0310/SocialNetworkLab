/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.tblAccount;

import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class TblAccountLoginError implements Serializable {

    private String userNotFoundError;

    public TblAccountLoginError() {
    }

    public TblAccountLoginError(String userNotFoundError) {
        this.userNotFoundError = userNotFoundError;
    }

    /**
     * @return the userNotFoundError
     */
    public String getUserNotFoundError() {
        return userNotFoundError;
    }

    /**
     * @param userNotFoundError the userNotFoundError to set
     */
    public void setUserNotFoundError(String userNotFoundError) {
        this.userNotFoundError = userNotFoundError;
    }

}
