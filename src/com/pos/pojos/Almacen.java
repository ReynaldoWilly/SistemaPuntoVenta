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
public class Almacen {

    private int idAlmacen;
    private String nombre;
    private String direccion;
    private String fono;
    private String codigo;

    public Almacen() {
    }

    public Almacen(String nombre, String direccion, String fono, String codigo) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.fono = fono;
        this.codigo = codigo;
    }

    public Almacen(int idAlmacen, String nombre, String direccion, String fono, String codigo) {
        this.idAlmacen = idAlmacen;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fono = fono;
        this.codigo = codigo;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFono() {
        return fono;
    }

    public void setFono(String fono) {
        this.fono = fono;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }    
}
