/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.dao;

import com.pos.pojos.Inventario;
import com.pos.util.Conexion;
import com.pos.util.HibernateUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Reynaldo
 */
public class InventarioDao {

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

    public boolean registrarInventario(Inventario inv) throws Exception {
        iniciarOperacion();
        sesion.save(inv);
        tx.commit();
        sesion.close();
        return true;
    }

    public boolean actualizarInventario(Inventario inv) throws Exception {
        iniciarOperacion();
        sesion.update(inv);
        tx.commit();
        sesion.close();
        return true;
    }

    //Funcion que verifica si un producto ya existe en almacen
    public Inventario existeProducto(int idAlmacen, int idProducto, int idColor) {
        Inventario inv = new Inventario();
        inv = null;
        iniciarOperacion();
        Query query = sesion.createQuery("FROM Inventario  WHERE idProducto=? and idAlmacen=? and idcolor=?");
        query.setInteger(0, idAlmacen);
        query.setInteger(1, idProducto);
        query.setInteger(2, idColor);
        inv = (Inventario) query.uniqueResult();
        sesion.close();
        return inv;
    }

    //retorna False si el producto no existe
    public boolean existeProducto2(int idAlmacen, int idProducto, int idColor) throws Exception {
        int id = 0;
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT idInventario FROM Inventario WHERE idAlmacen=? and idProducto=? and idColor=?");
        query.setInteger(0, idAlmacen);
        query.setInteger(1, idProducto);
        query.setInteger(2, idColor);
        id = (int) query.uniqueResult();
        tx.commit();
        sesion.close();
        if (id != 0) {
            return true;
        }
        return false;
    }

    public boolean extisteProductoJDBC(int idAlmacen, int idProducto, int idColor) throws SQLException {
        Connection conex = Conexion.getConectar();//obteniendo la conexion a la BD
        Statement stm = conex.createStatement();
        String sql = "SELECT * FROM inventario WHERE idAlmacen = " + idAlmacen + " AND idProducto=" + idProducto + " AND idcolor=" + idColor;
        ResultSet resultado = stm.executeQuery(sql);
        if (resultado.next()) {
            return false;
        }
        return true;
    }

    //Metodo que recupera el stock del producto 
    public Inventario recuperaStockInventario(int idAlmacen, int idProducto, int idColor) throws SQLException {
        Connection conex = Conexion.getConectar();//obteniendo la conexion a la BD
        Statement stm = conex.createStatement();
        String sql = "SELECT *  FROM inventario WHERE idAlmacen = " + idAlmacen + " AND idProducto=" + idProducto + " AND idcolor=" + idColor;
        ResultSet resultado = stm.executeQuery(sql);
        int stock = 0;
        Inventario inv = new Inventario();
        
        while (resultado.next()) 
        {
            inv.setIdInventario(resultado.getInt("idInventario"));
            inv.setStock(resultado.getInt("stock"));
            inv.setIdcolor(resultado.getInt("idcolor"));
            inv.setIdProducto(resultado.getInt("idProducto"));
            inv.setIdAlmacen(resultado.getInt("idAlmacen"));
            inv.setTipoInv(resultado.getString("tipoInventario"));
        }
        return inv;
    }

}
