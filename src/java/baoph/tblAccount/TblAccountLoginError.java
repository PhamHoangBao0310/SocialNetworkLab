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
    private String userIsNotActiveError;

    public TblAccountLoginError() {
    }

    public TblAccountLoginError(String userNotFoundError, String userIsNotActiveError) {
        this.userNotFoundError = userNotFoundError;
        this.userIsNotActiveError = userIsNotActiveError;
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

    /**
     * @return the userIsNotActiveError
     */
    public String getUserIsNotActiveError() {
        return userIsNotActiveError;
    }

    /**
     * @param userIsNotActiveError the userIsNotActiveError to set
     */
    public void setUserIsNotActiveError(String userIsNotActiveError) {
        this.userIsNotActiveError = userIsNotActiveError;
    }


}
