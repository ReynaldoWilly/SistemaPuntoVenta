/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.dao;

import com.pos.pojos.Ajustes;
import com.pos.pojos.ComboProducto;
import com.pos.pojos.Producto;
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
public class ComboDao {

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

    public boolean registarCombo(ComboProducto combo) throws Exception {
        iniciarOperacion();
        sesion.save(combo);
        tx.commit();
        sesion.close();
        return true;
    }

    //Metodo que verifica si el codigo ingresado ya esta registrado 
    public boolean buscarCodigo(String codigo) {
        ComboProducto cm = null;
        Producto pro = null;
        iniciarOperacion();
        Query query = sesion.createQuery("From ComboProducto Where codigoCombo=?");
        query.setString(0, codigo);
        cm = (ComboProducto) query.uniqueResult();
        if (cm != null) {
            return true;//TRUE= codigo ya regitrado
        }

        Query query2 = sesion.createQuery("From Producto Where codigo=?");
        query2.setString(0, codigo);
        pro = (Producto) query2.uniqueResult();
        sesion.close();
        if (pro != null) {
            return true;//TRUE= codigo ya regitrado
        }
        return false;//FALSE= codigo no registrado y listo para regitrarse
    }

    //metodo que realiza devuelve el codigo del combo solicitado
    public String buscarCodigo(int id) 
    {
        String cm = null;
      
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT codigoCombo From ComboProducto Where id=?");
        query.setInteger(0, id);
        cm = (String) query.uniqueResult();
         return cm;
    }

    public List<Object[]> listarProductos() throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("Select p.idProducto,p.nombre from Producto p");
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    public List<Object[]> listarProductosByAlmacen() throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("Select p.idProducto,p.nombre from Producto p");
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    //Metodo que realiza la busqueda de un producto para el filtro 
    public List<Object[]> buscarProductoFiltroBYNombre(String nom) throws Exception {
        List<Object[]> resultado;
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT p.idProducto,p.nombre FROM Producto p WHERE  p.nombre like  '" + nom + "%'");
        System.out.println(query);
        resultado = query.list();
        sesion.close();
        return resultado;
    }

    //Metodo que realiza el listado de los combos registrados
    public List<Object[]> listarCombos() throws Exception {
        List<Object[]> resultado;
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT idComboProducto,nombreCombo,codigoCombo,precioCombo,estado\n"
                + "FROM ComboProducto");
        resultado = query.list();

        sesion.close();
        return resultado;
    }

    //Metodo que verifica si existe el combo
    public boolean extisteComboJDBC(String nombreCombo) throws SQLException 
    {
        Connection conex = Conexion.getConectar();//obteniendo la conexion a la BD
        Statement stm = conex.createStatement();
        String sql = "SELECT * FROM comboProducto  WHERE nombreCombo = \'" + nombreCombo + "\'";
        ResultSet resultado = stm.executeQuery(sql);
        if (resultado.next()) 
        {
            return true;
        }
        return true;
        
    }

    //Metodo que realiza el listado de los combos para el modulo de ventas
    public List<Object[]> listarCombosVentas() throws Exception {
        List<Object[]> resultado;
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT idComboProducto,nombreCombo,codigoCombo,precioCombo\n"
                + "FROM ComboProducto");
        resultado = query.list();

        sesion.close();
        return resultado;
    }

    //metodo que lista en detalle del combo 
    //Metodo que realiza el listado del detalle del combo
    public ResultSet listarDetalleCombo(int idCombo) throws Exception {
        Connection conex = Conexion.getConectar();//obteniendo la conexion a la BD
        Statement stm = conex.createStatement();
        String sql = "SELECT distinct d.idDetalleCombo, p.idProducto,d.cantidad, p.nombre\n"
                + "FROM producto As p, detallecombo As d, comboproducto As c\n"
                + "WHERE d.idComboProducto=" + idCombo + " AND d.idProducto=p.idProducto ";
        ResultSet resultado = stm.executeQuery(sql);
        return resultado;
    }

}
