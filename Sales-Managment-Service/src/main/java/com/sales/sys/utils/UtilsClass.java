package com.sales.sys.utils;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.security.SecureRandom;

public class UtilsClass {

    public static String genRandomOrderNum() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(100000);
        String formattedNum = String.format("%05d", num);
        return formattedNum;
    }

    public static Date convertDate(String dateParam) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dateParam);
        return date;
    }

}
