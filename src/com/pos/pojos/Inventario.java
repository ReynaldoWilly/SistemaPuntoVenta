/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.pojos;

/**
 *
 * @author Reynaldo
 */
public class Inventario 
{
    private int idInventario;
    private int stock;
    private int idcolor;
    private int idProducto;
    private int idAlmacen;
    private String tipoInv;

    public Inventario() 
    {
      
    }

    public Inventario(int stock, int idcolor, int idProducto, int idAlmacen, String tipoInv) {
        this.stock = stock;
        this.idcolor = idcolor;
        this.idProducto = idProducto;
        this.idAlmacen = idAlmacen;
        this.tipoInv = tipoInv;
    }


    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

  
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public int getIdcolor() {
        return idcolor;
    }

    public void setIdcolor(int idcolor) {
        this.idcolor = idcolor;
    }

    public String getTipoInv() {
        return tipoInv;
    }

    public void setTipoInv(String tipoInv) {
        this.tipoInv = tipoInv;
    }
}
