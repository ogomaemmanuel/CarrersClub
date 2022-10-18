package com.careerclub.careerclub.Utils;

import java.util.ArrayList;

public class OtpCodeGenerator {
    public static String generate(){
        StringBuilder code = new StringBuilder();
        for(int num = 0; num<5;num++){
            int randomNumber = (int)(Math.random()*(9 +1)+0);
            code.append(randomNumber);
        }
        return code.toString();
    }
}
