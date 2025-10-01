package com.example.demo.global

enum class CurrencyEnum(
    val desc: String,
    val sign: String,
) {
    KRW(desc = "대한민국 원", sign = "₩"),
    USD(desc = "미국 달러", sign = "$"),
    EUR(desc = "유로", sign = "€"),
    JPY(desc = "일본 엔", sign = "¥"),
    GBP(desc = "영국 파운드", sign = "£"),
    CNY(desc = "중국 위안", sign = "¥"),
    VND(desc = "베트남 동", sign = "₫"),
    TWD(desc = "신대만 달러", sign = "NT\$"),
    ;

    companion object {
        @JvmStatic
        val defaultCurrency: String
            get() = KRW.name

        @JvmStatic
        operator fun contains(value: String): Boolean {
            return CurrencyEnum.entries.any { value.contains(it.name) }
        }

        @JvmStatic
        fun extractCurrency(value: String): String? {
            return CurrencyEnum.entries.firstOrNull { value.contains(it.name) }?.name
        }

        @JvmStatic
        fun excludeCurrency(value: String): String {
            return CurrencyEnum.entries.fold(value) { acc, i -> acc.replace(i.name, "") }
        }
    }
}