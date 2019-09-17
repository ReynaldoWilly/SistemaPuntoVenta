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
public class Usuario {
    
    private int idUsuario;
    private String nombres;
    private String apellidos;
    private String celular;
    private String cargo;
    private String email;
    private String password;
    private int tipousuario;

    public Usuario() {
    }

    public Usuario(String nombres, String apellidos, String celular, String cargo, String email, String password, int tipousuario) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.celular = celular;
        this.cargo = cargo;
        this.email = email;
        this.password = password;
        this.tipousuario = tipousuario;
    }

    public Usuario(int idUsuario, String nombres, String apellidos, String celular, String cargo, String email, String password, int tipousuario) {
        this.idUsuario = idUsuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.celular = celular;
        this.cargo = cargo;
        this.email = email;
        this.password = password;
        this.tipousuario = tipousuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(int tipousuario) {
        this.tipousuario = tipousuario;
    }

    
}
