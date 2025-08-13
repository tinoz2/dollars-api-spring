package com.example.minty.dtos;

public record CurrencyResponse(
        String code,
        String name,
        int decimal_digits,
        String name_plural,
        int rounding,
        String symbol,
        String symbol_native
) {
}