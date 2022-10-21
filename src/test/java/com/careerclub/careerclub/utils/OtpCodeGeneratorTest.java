package com.careerclub.careerclub.utils;

import com.careerclub.careerclub.Utils.OtpCodeGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OtpCodeGeneratorTest {


    @Test
    @DisplayName("Testing otp code generator")
    public void test_otp_code(){
        Assertions.assertEquals(5, OtpCodeGenerator.generate().length());
    }

}
