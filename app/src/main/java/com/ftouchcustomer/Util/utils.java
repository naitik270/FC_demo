package com.ftouchcustomer.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class utils {
    public static String selectedState="";
    public static int selectedStateId;
    public static int activityCode = 0; //0 = MainActivity , 1 = StateListActivity

    public static boolean isValidEmail(String email) {

        if (email == null) {
            return false;
        }

        final String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Matcher matcher;
        Pattern pattern = Pattern.compile(emailPattern);

        matcher = pattern.matcher(email);

        if (matcher != null) {
            return matcher.matches();
        } else {
            return false;
        }
    }

}
