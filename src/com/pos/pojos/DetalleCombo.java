/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Reynaldo
 */

public class DetalleCombo {
    private int idDetalleCombo;
    
    private int cantidad;
    
    
    private int idProducto;
    
    private ComboProducto comboproducto;

    public DetalleCombo() {
    }

    public DetalleCombo(int idDetalleCombo, int cantidad, int idProducto, ComboProducto comboproducto) 
    {
        this.idDetalleCombo = idDetalleCombo;
        this.cantidad = cantidad;
        this.idProducto = idProducto;
        this.comboproducto = comboproducto;
    }

    public ComboProducto getComboproducto() {
        return comboproducto;
    }

    public void setComboproducto(ComboProducto comboproducto) {
        this.comboproducto = comboproducto;
    }

    public int getIdDetalleCombo() {
        return idDetalleCombo;
    }

    public void setIdDetalleCombo(int idDetalleCombo) {
        this.idDetalleCombo = idDetalleCombo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
}
