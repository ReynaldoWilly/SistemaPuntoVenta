/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.dao;

import com.pos.pojos.Ajustes;
import com.pos.util.Conexion;
import com.pos.util.HibernateUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 *
 * @author Reynaldo
 */
public class ajustesDao {

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

    public boolean registarAjuste(Ajustes alm) throws Exception {
        iniciarOperacion();
        sesion.save(alm);
        tx.commit();
        sesion.close();
        return true;
    }

    public List<Ajustes> listarAlmacen() throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("From Almacen");
        List<Ajustes> lista = query.list();
        sesion.close();
        return lista;
    }

    //Metodo que realiza el listado del kardex de inventario por almacen
    public List<Object[]> listarAjustes() throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT a.idAjustes, p.nombre,a.cantidadOriginal,a.cantidadAjuste, u.nombres,u.apellidos,a.detalle, a.fecha, al.nombre,c.nombre\n"
                + "FROM Ajustes as a, Producto as p, Usuario as u, Almacen as al,Colores as c\n"
                + "WHERE a.idProducto=p.idProducto and a.idUsuario=u.idUsuario and a.idAlmacen=al.idAlmacen and a.idColor=c.idColor");
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    //Metodo que realiza el listado del kardex de inventario por almacen
    public List<Object[]> listarAjustesByAlmacen(int idAlmacen) throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT a.idAjustes, p.nombre,a.cantidadOriginal,a.cantidadAjuste, u.nombres,u.apellidos,a.detalle, a.fecha, al.nombre\n"
                + "FROM Ajustes as a, Producto as p, Usuario as u, Almacen as al\n"
                + "WHERE a.idProducto=p.idProducto and a.idUsuario=u.idUsuario and a.idAlmacen=al.idAlmacen and a.idAlmacen=?");
        query.setInteger(0, idAlmacen);
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    //Metodo que realiza el ajuste en la BD
    public boolean realizarAjusteInventario(int cantidadAjuste, int idColorAjuste, int idInventario) {
        iniciarOperacion();
        Query query = sesion.createQuery("UPDATE Inventario\n"
                + "SET stock=?, idcolor=?\n"
                + "WHERE idInventario=? ");
        query.setInteger(0, cantidadAjuste);
        query.setInteger(1, idColorAjuste);
        query.setInteger(2, idInventario);
        query.executeUpdate();
        sesion.close();
        return true;
    }

    public int buscarIdInventario(int idProd) {
        int inv = 0;
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT idinventario  FROM Inventario   WHERE producto_idproducto=?");
        query.setInteger(0, idProd);
        inv = (int) query.uniqueResult();
        sesion.close();
        return inv;
    }

    public static void actualizaStockInventario(int id, String stock) {
        try {
            Connection miConexion = Conexion.getConectar();
            PreparedStatement statement = miConexion.prepareStatement("UPDATE inventario SET stock=?  WHERE idinventario=?");
            statement.setString(1, stock);
            statement.setInt(2, id);
            statement.executeUpdate();
            statement.close();
            miConexion.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el precio del producto..!!" + ex.getMessage());
        }
    }
}
