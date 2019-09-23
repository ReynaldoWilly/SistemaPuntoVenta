/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.pojos;

import java.util.Date;

/**
 *
 * @author Reynaldo
 */
public class HistorialInventario {
    private int idHistorial;
    private Date fecha;
    private String nuEnvio;
    private int idAlmacen;
    private String observaciones;
    private int idProducto;
    private int idColor;

    public HistorialInventario() {
    }

    public HistorialInventario(Date fecha, String nuEnvio, int idAlmacen, String observaciones, int idProducto, int idColor) {
        this.fecha = fecha;
        this.nuEnvio = nuEnvio;
        this.idAlmacen = idAlmacen;
        this.observaciones = observaciones;
        this.idProducto = idProducto;
        this.idColor = idColor;
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNuEnvio() {
        return nuEnvio;
    }

    public void setNuEnvio(String nuEnvio) {
        this.nuEnvio = nuEnvio;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }
    
}
