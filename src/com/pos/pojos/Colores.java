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
public class Colores {
    private int idColor;
    private String nombre;
    private String codigo;

    public Colores() {
    }

    public Colores(int idColor, String nombre, String codigo) {
        this.idColor = idColor;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public Colores(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
