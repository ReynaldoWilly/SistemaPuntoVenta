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
public class UnidadesMedida {

    private int idUnidadMedida;
    private String nombre;
    private String abreviacion;

    public UnidadesMedida() {
    }

    public UnidadesMedida(int idUnidadMedida, String nombre, String abreviacion) {
        this.idUnidadMedida = idUnidadMedida;
        this.nombre = nombre;
        this.abreviacion = abreviacion;
    }

    public UnidadesMedida(String nombre, String abreviacion) {
        this.nombre = nombre;
        this.abreviacion = abreviacion;
    }

    public int getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(int idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

   
}
