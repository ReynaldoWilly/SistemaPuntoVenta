/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.pojos;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author Reynaldo
 */
public class Venta implements Serializable {

    private int idVenta;
    private int idCliente;
    private String monto;
    private String tipoVenta;
    private Date fecha;
    private int idUsuario;
    private String documentos;
    private Set<detalleVenta> itemsventa;

    public Venta() {
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDocumentos() {
        return documentos;
    }

    public void setDocumentos(String documentos) {
        this.documentos = documentos;
    }

    public Set<detalleVenta> getItemsventa() {
        return itemsventa;
    }

    public void setItemsventa(Set<detalleVenta> itemsventa) {
        this.itemsventa = itemsventa;
    }
}
