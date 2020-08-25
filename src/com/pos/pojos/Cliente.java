/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.pojos;

import java.io.Serializable;

/**
 *
 * @author Reynaldo
 */
public class Cliente implements Serializable {

    private int idCliente;
    private String nombre;
    private String ci;
    private String telefono;
    private String celContacto;
    private String email;
    private String direccion;
    private String credito;
    private String plazo;
    private String estado;

    public Cliente() {
    }

    public Cliente(String nombre, String ci, String credito, String plazo, String estado) {
        this.nombre = nombre;
        this.ci = ci;
        this.credito = credito;
        this.plazo = plazo;
        this.estado = estado;
    }

    public Cliente(String nombre, String ci, String telefono, String celContacto, String email, String direccion, String credito, String plazo, String estado) {
        this.nombre = nombre;
        this.ci = ci;
        this.telefono = telefono;
        this.celContacto = celContacto;
        this.email = email;
        this.direccion = direccion;
        this.credito = credito;
        this.plazo = plazo;
        this.estado = estado;
    }

   
    public Cliente(int idCliente, String nombre, String ci, String telefono, String celContacto, String email, String direccion, String credito, String plazo, String estado) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.ci = ci;
        this.telefono = telefono;
        this.celContacto = celContacto;
        this.email = email;
        this.direccion = direccion;
        this.credito = credito;
        this.plazo = plazo;
        this.estado = estado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelContacto() {
        return celContacto;
    }

    public void setCelContacto(String celContacto) {
        this.celContacto = celContacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

   
}
