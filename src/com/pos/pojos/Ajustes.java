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
public class Ajustes {

    private int idAjustes;
    private int idProducto;
    private String cantidadOriginal;
    private String cantidadAjuste;
    private int idUsuario;
    private String detalle;
    private Date fecha;
    private int idAlmacen;
    private int idColor;
    private int idColorAjuste;

    public Ajustes() {
    }

    public Ajustes(int idAjustes, int idProducto, String cantidadOriginal, String cantidadAjuste, int idUsuario, String detalle, Date fecha, int idAlmacen, int idColorAjuste) {
        this.idAjustes = idAjustes;
        this.idProducto = idProducto;
        this.cantidadOriginal = cantidadOriginal;
        this.cantidadAjuste = cantidadAjuste;
        this.idUsuario = idUsuario;
        this.detalle = detalle;
        this.fecha = fecha;
        this.idAlmacen = idAlmacen;
        this.idColorAjuste = idColorAjuste;
    }

    public int getIdColorAjuste() {
        return idColorAjuste;
    }

    public void setIdColorAjuste(int idColorAjuste) {
        this.idColorAjuste = idColorAjuste;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdAjustes() {
        return idAjustes;
    }

    public void setIdAjustes(int idAjustes) {
        this.idAjustes = idAjustes;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getCantidadOriginal() {
        return cantidadOriginal;
    }

    public void setCantidadOriginal(String cantidadOriginal) {
        this.cantidadOriginal = cantidadOriginal;
    }

    public String getCantidadAjuste() {
        return cantidadAjuste;
    }

    public void setCantidadAjuste(String cantidadAjuste) {
        this.cantidadAjuste = cantidadAjuste;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }
}
