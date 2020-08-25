/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.dao;

import com.pos.pojos.Venta;
import com.pos.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Reynaldo
 */
public class ventaDao {

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

    public boolean registarVenta(Venta cm) throws Exception {
        iniciarOperacion();
        sesion.save(cm);
        tx.commit();
        sesion.close();
        return true;
    }

    public boolean actualizarventa(Venta cm) throws Exception {
        iniciarOperacion();
        sesion.update(cm);
        tx.commit();
        sesion.close();
        return true;
    }

    public List<Object[]> listarVentas() throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("Select c.idcompra,c.fecha,c.fechaemtrega,c.preciototal,c.codigo,c.estado,c.ncotizacion,c.responsable,c.tipoDocumento,c.numeroDocumento from Compra c where c.estado=?");
        query.setString(0, "Procesado");
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    public List<Object[]> listarComprasOC() throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("Select c.idcompra,c.fecha,c.fechaemtrega,c.preciototal,c.codigo,c.estado,c.ncotizacion,c.responsable from Compra c where c.estado=?");
        query.setString(0, "En espera");
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    //Metodo que recupera el detalle de una compra
    public List<Object[]> listarDetalle(int idcompra) throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT p.idproducto,p.nombre,d.cantidad,d.precioCompra,d.estado FROM Detallecompra d, Producto p WHERE d.compra.idcompra=? and  d. produto_idproducto=p.idproducto and d.estado='0'");
        query.setInteger(0, idcompra);
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    //Metodo que recupera el detalle de una compra
    public List<Object[]> listarDetalleIngresoAlmacen(int idcompra) throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT d.iddetallecompra,p.idproducto,p.nombre,d.cantidad,p.precio,d.estado FROM Detallecompra d, Producto p WHERE d.compra.idcompra=? and  d. produto_idproducto=p.idproducto and d.estado='0'");
        query.setInteger(0, idcompra);
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    //metofo que recupera el ultimo numero de registro de la tabla Compras
    public int numeroUltimoReg() {
        iniciarOperacion();
        Query query = null;
        query = sesion.createQuery("SELECT MAX(c.idcompra) FROM Compra c");
        try {
            List<Object> li = query.list();
            //JOptionPane.showMessageDialog(null, "--->" + li.size());
            int ultimoReg = 1;
            if (li.size() > 0) {
                ultimoReg = (int) query.uniqueResult();
                sesion.close();
                return ultimoReg + 1;
            } else {
                return ultimoReg;
            }
        } catch (Exception e) {
            //    JOptionPane.showMessageDialog(null, e.getMessage());
            return 1;
        }
    }

    public boolean eliminarVenta(Venta cm) throws Exception {
        iniciarOperacion();
        sesion.delete(cm);
        tx.commit();
        sesion.close();
        return true;
    }

    public Venta buscarVenta(int id) throws Exception {
        Venta cm = null;
        iniciarOperacion();
        Query query = sesion.createQuery("From Compra Where idcompra=?");
        query.setInteger(0, id);
        cm = (Venta) query.uniqueResult();
        sesion.close();
        return cm;
    }

    /*Metodo que realiza la busqueda de compras por fecha*/
    public List<Object[]> BuscarCompraFecha(String f1, String f2) throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("Select c.idcompra,c.fecha,c.fechaemtrega,c.preciototal,c.codigo,c.estado,c.ncotizacion,c.responsable from Compra c where c.fecha between ? and ?");
        query.setString(0, f1);
        query.setString(1, f2);
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }
}
