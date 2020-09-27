/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.utils;

import java.util.regex.Pattern;

/**
 *
 * @author DELL
 */
public class Validation {

    public static boolean checkEmailInput(String email) {
//        String emailRegex = "\\w+@\\w+\\.*[A-Za-z]*\\.[A-Za-z]{2,4}";
        String emailRegex = "^([A-Za-z0-9._-]+)@([a-zA-Z0-9.-_]+)\\.([a-z]{2,4})$";

        return Pattern.matches(emailRegex, email);

    }
}
