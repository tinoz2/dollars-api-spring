package com.example.minty.entities;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Dollars {

    private String moneda;
    private String casa;
    private String nombre;
    private double compra;
    private double venta;
    private LocalDateTime fechaActualizacion;
}
