package com.example.minty.dtos;

import java.time.LocalDate;

public record DollarsByDate(
        String casa,
        double compra,
        double venta,
        LocalDate fecha
) {
}
