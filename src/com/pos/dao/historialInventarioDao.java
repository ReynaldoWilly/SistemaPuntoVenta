/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.dao;

import com.pos.pojos.HistorialInventario;
import com.pos.util.Conexion;
import com.pos.util.HibernateUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Reynaldo
 */
public class historialInventarioDao {

    Session sesion;
    Transaction tx;

    //Metodo que inicia la sesion 
    public void iniciarOperacion() {
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }

    public void manejaexception(HibernateException he) {
        tx.rollback();
        throw new HibernateException("Ocurrio un error en la capa de acceso a datos");
    }

    public boolean registarHistorialInventario(HistorialInventario alm) throws Exception {
        iniciarOperacion();
        sesion.save(alm);
        tx.commit();
        sesion.close();
        return true;
    }

    //Metodo que realiza la busqueda de un numero de envio 
    public boolean buscarEnvio(String nuEnvio) throws SQLException {
        Connection conex = Conexion.getConectar();//obteniendo la conexion a la BD
        Statement stm = conex.createStatement();
        String sql = "SELECT * FROM historialingreso WHERE numeroEnvio=" + nuEnvio;
        ResultSet resultado = stm.executeQuery(sql);
        if (resultado.next()) {
            return false;
        }
        return true;
    }

    public ResultSet listarEnvios() throws Exception {
        Connection conex = Conexion.getConectar();//obteniendo la conexion a la BD
        Statement stm = conex.createStatement();
        String sql = "SELECT DISTINCT numeroEnvio FROM historialingreso";
        ResultSet resultado = stm.executeQuery(sql);
        return resultado;
    }

    public ResultSet listarEnviosByFecha(String f1, String f2) throws Exception {
        Connection conex = Conexion.getConectar();//obteniendo la conexion a la BD
        Statement stm = conex.createStatement();
        String sql = "SELECT DISTINCT numeroEnvio FROM historialingreso WHERE fecha BETWEEN " + "'" + f1 + "'" + " AND " + "'" + f2 + "'";
        ResultSet resultado = stm.executeQuery(sql);
        return resultado;
    }

    public ResultSet listarDetalleEnvio(String numeroEnvio) throws Exception {
        Connection conex = Conexion.getConectar();//obteniendo la conexion a la BD
        Statement stm = conex.createStatement();
        String sql = "SELECT h.cantidad,p.nombre,c.nombre,h.fecha,h.observaciones FROM producto AS p,historialingreso AS h, colores AS c WHERE numeroEnvio=" + numeroEnvio + " AND p.idProducto=h.idProducto AND h.idColor=c.idColor";
        ResultSet resultado = stm.executeQuery(sql);
        return resultado;
    }

}
