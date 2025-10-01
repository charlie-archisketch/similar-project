package com.example.demo.global.util;

import com.example.demo.global.CurrencyEnum;
import com.google.common.base.Strings;

import java.util.regex.Pattern;

public class PriceUtil {

    private static final Pattern valuePattern = Pattern.compile("\\d+(\\.\\d+)?");
    private static final Pattern valueBlankPattern = Pattern.compile(valuePattern + " ");

    public static boolean isCleanPrice(String price) {
        if (Strings.isNullOrEmpty(price)) return false;
        if (!CurrencyEnum.contains(price)) return false;
        String value = CurrencyEnum.excludeCurrency(price);
        if (!valueBlankPattern.matcher(value).matches()) return false;
        return isCorrectValue(value.trim());
    }

    public static boolean isCorrectValue(String value) {
        if (Strings.isNullOrEmpty(value)) return false;
        return valuePattern.matcher(value).matches();
    }

    public static String refactorPrice(String price) {
        if (Strings.isNullOrEmpty(price)) {
            return 0 + " " + CurrencyEnum.getDefaultCurrency();
        }

        String modifiedPrice = price.trim().toUpperCase();
        String currency = CurrencyEnum.extractCurrency(modifiedPrice);
        if (currency == null) {
            currency = CurrencyEnum.getDefaultCurrency();
        }

        String value = CurrencyEnum.excludeCurrency(modifiedPrice).trim();
        if (Strings.isNullOrEmpty(value)) {
            value = "0";
        }

        if (!isCorrectValue(value)) {
            value = value.replaceAll("[^\\d.]", "");
        }

        return value + " " + currency;
    }
}
