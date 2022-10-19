package com.careerclub.careerclub.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    public static boolean validate(String email){
        String emailValidateRegex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(emailValidateRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
