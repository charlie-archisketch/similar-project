package com.example.demo.global.util;

import com.example.demo.global.CurrencyEnum;
import com.google.common.base.Strings;
import org.bson.types.ObjectId;

import java.text.NumberFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class StringUtil {
    private static final String Rixits = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";

    public static String genId() {
        String uuid = UUID.randomUUID().toString();
        String[] arr = uuid.split("-");
        String id = arr[0] + arr[1] + arr[2];
        return genTimedRandomStr() + id.toUpperCase();
    }

    private static String genTimedRandomStr() {
        Date now = new Date();
        Long dateNumber = now.getTime();

        Long residual = dateNumber;
        Long rixit; // like 'digit', only in some non-decimal radix
        String result = "";
        while (true) {
            rixit = residual % 64;
            result = Rixits.charAt(rixit.intValue()) + result;
            residual = residual / 64;
            if (residual == 0) break;
        }
        return result;
    }

    public static String genObjectId() {
        return ObjectId.get().toString();
    }

    public static String genRandom6Digit() {
        String chars = "0123456789";
        Random rd = new Random();
        String str = "";
        for(int i = 0; i<6; i++) {
            char letter = chars.charAt(rd.nextInt(chars.length()));
            str += letter;
        }
        return str;
    }

    public static String extractSubdomain(String url) {
        if(Strings.isNullOrEmpty(url) ||
                (url.startsWith("http://") == false && url.startsWith("https://") == false)) {
            return null;
        }
        String domain = url.split("://")[1];
        String[] arr = domain.split("\\.");
        String host = url.split("://")[1]; // my.dev.archisketch.com
        String[] hostElements = host.split("\\."); // [my, dev, archisketch, com]

        if (hostElements.length > 1) {
            return hostElements[0];
        } else {
            return null;
        }
    }

    public static String extractSubdomainDotDev(String url) {
        if (Strings.isNullOrEmpty(url) ||
                (url.startsWith("http://") == false && url.startsWith("https://") == false)) {
            return null;
        }

        String host = url.split("://")[1]; // my.dev.archisketch.com
        String[] hostElements = host.split("\\."); // [my, dev, archisketch, com]
        String subdomain;
        if (hostElements.length > 1) {
            subdomain = hostElements[0]; // my
            if (Objects.equals(hostElements[1], "dev")) {
                return subdomain + ".dev";
            }
            return subdomain;
        } else {
            return null;
        }
    }

    private static final String PASSWORD_SOURCE =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~!@#$%^&*()_+=-/?><";

    public static int randomNumberInRange(int x, int y) {
        if(x > y)
            throw new RuntimeException("'x' must be smaller than 'y'.");
        Random random = new Random();
        int min = x;
        int diff = y - x;
        return random.nextInt(diff) + min;
    }

    public static String genRandomPassword() {
        int len = randomNumberInRange(20, 40);
        Random rd = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<len; i++) {
            char letter = PASSWORD_SOURCE.charAt(rd.nextInt(PASSWORD_SOURCE.length()));
            sb.append(letter);
        }
        return sb.toString();
    }

    private static final String Alphanumeric =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String genRandomAlphanumericStr(int len) {
        Random rd = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<len; i++) {
            char letter = Alphanumeric.charAt(rd.nextInt(Alphanumeric.length()));
            sb.append(letter);
        }
        return sb.toString();
    }

    private static NumberFormat krwCurrFormatter = null;
    private static NumberFormat usdCurrFormatter = null;

    public static String toCurrencyStr(long cost, CurrencyEnum currency) {
        String strCost = String.valueOf(cost);
        String res = null;
        switch(currency) {
            case KRW:
                if(krwCurrFormatter == null) {
                    Locale krLocale = new Locale("ko", "KR");
                    krwCurrFormatter = NumberFormat.getCurrencyInstance(krLocale);
                }
                res = krwCurrFormatter.format(cost);
                break;
            case USD:
                double d = cost / 100;
                if(usdCurrFormatter != null) {
                    Locale usLocale = new Locale("en", "US");
                    usdCurrFormatter = NumberFormat.getCurrencyInstance(usLocale);
                }
                res = usdCurrFormatter.format(d);
                break;
        }
        return res;
    }

    public static String base64Encoding(String str) {
        byte[] targetBytes = str.getBytes();

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(targetBytes);

        return new String(encodedBytes);
    }
}
