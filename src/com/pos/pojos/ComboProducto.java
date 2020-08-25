/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.pojos;

import java.io.Serializable;
import java.util.Set;

public class ComboProducto implements Serializable {

    private int idComboProducto;

    private String nombreCombo;

    private String codigoCombo;


    private String estado;

    private String precioCombo;

    private Set<DetalleCombo> detalleCombo;//listado del detalle del combo

    public ComboProducto() {
    }

    public ComboProducto(int idComboProducto, String nombreCombo, String codigoCombo, String estado, String precioCombo, Set<DetalleCombo> detalleCombo) {
        this.idComboProducto = idComboProducto;
        this.nombreCombo = nombreCombo;
        this.codigoCombo = codigoCombo;
        this.estado = estado;
        this.precioCombo = precioCombo;
        this.detalleCombo = detalleCombo;
    }

    public ComboProducto(String nombreCombo, String codigoCombo, String estado, String precioCombo, Set<DetalleCombo> detalleCombo) {
        this.nombreCombo = nombreCombo;
        this.codigoCombo = codigoCombo;
        this.estado = estado;
        this.precioCombo = precioCombo;
        this.detalleCombo = detalleCombo;
    }

    public int getIdComboProducto() {
        return idComboProducto;
    }

    public void setIdComboProducto(int idComboProducto) {
        this.idComboProducto = idComboProducto;
    }

    public String getNombreCombo() {
        return nombreCombo;
    }

    public void setNombreCombo(String nombreCombo) {
        this.nombreCombo = nombreCombo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPrecioCombo() {
        return precioCombo;
    }

    public void setPrecioCombo(String precioCombo) {
        this.precioCombo = precioCombo;
    }

    public Set<DetalleCombo> getDetalleCombo() {
        return detalleCombo;
    }

    public void setDetalleCombo(Set<DetalleCombo> detalleCombo) {
        this.detalleCombo = detalleCombo;
    }

    public String getCodigoCombo() {
        return codigoCombo;
    }

    public void setCodigoCombo(String codigoCombo) {
        this.codigoCombo = codigoCombo;
    }
}
