package com.careerclub.careerclub.Utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dateformat {
    public static Date formatDate(String date) throws ParseException {
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(date));
        return date1;
    }
}
