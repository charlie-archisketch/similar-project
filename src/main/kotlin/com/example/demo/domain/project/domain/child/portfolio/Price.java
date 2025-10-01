package com.example.demo.domain.project.domain.child.portfolio;

import com.example.demo.global.CurrencyEnum;

import com.example.demo.global.util.PriceUtil;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Price {

    @NotNull
    private Double value;
    @NotNull
    private CurrencyEnum unit;

    public static Price from(String price) {
        if (price != null && PriceUtil.isCleanPrice(price)) {
            Double value = Double.parseDouble(price.split(" ")[0]);
            CurrencyEnum unit = CurrencyEnum.valueOf(price.split(" ")[1]);
            return new Price(value, unit);
        }
        return null;
    }

    public static Price of(
            Double value,
            CurrencyEnum unit
    ) {
        return new Price(value, unit);
    }

    public @NotNull Double getValue() {
        return value;
    }

    public @NotNull CurrencyEnum getUnit() {
        return unit;
    }

    public void setValue(@NotNull Double value) {
        this.value = value;
    }

    public void setUnit(@NotNull CurrencyEnum unit) {
        this.unit = unit;
    }
}
