package com.example.minty.dtos;

import java.math.BigDecimal;

public record RateDTO(
        BigDecimal amount,
        String fromConvert,
        String toConvert
) {}