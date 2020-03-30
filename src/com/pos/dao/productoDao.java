/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.dao;

import com.pos.pojos.Producto;
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
public class productoDao {

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

    public boolean registarProducto(Producto pro) throws Exception {
        iniciarOperacion();
        sesion.save(pro);
        tx.commit();
        sesion.close();
        return true;
    }

    public boolean actualizarProducto(Producto pro) throws Exception {
        iniciarOperacion();
        sesion.update(pro);
        tx.commit();
        sesion.close();
        return true;
    }

    public List<Producto> listarProductos() throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("From Producto");
        List<Producto> lista = query.list();
        sesion.close();
        return lista;
    }

    public List<Object[]> listarProductosCategoria(String categoria) throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("Select p.idProducto,p.nombre,p.descripcion,p.codigo, p.categoria,p.precioCompra,p.stcokMinimo,p.estado,p.medida from Producto p where p.categoria=?");
        query.setString(0, categoria);
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    public List<Object[]> listarProductos2() throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("Select p.idProducto,p.nombre,p.descripcion,p.codigo, p.categoria,p.precioCompra,p.precioVenta,p.stcokMinimo,p.estado,p.medida from Producto p");
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    public List<Object[]> listarProductosByCodigo(String codigo) throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("Select p.idProducto,p.nombre,p.descripcion,p.codigo, p.categoria,p.precioCompra,p.stcokMinimo,p.estado,p.medida from Producto p where p.codigo=?");
        query.setString(0, codigo);
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    public Producto buscarProducto(int id) throws Exception {
        Producto pro = null;
        iniciarOperacion();
        Query query = sesion.createQuery("From Producto Where idproducto=?");
        query.setInteger(0, id);
        pro = (Producto) query.uniqueResult();
        sesion.close();
        return pro;
    }

    public int buscarProductoByNombre(String nombre) throws Exception {

        iniciarOperacion();
        Query query = sesion.createQuery("Select c.idProducto  FROM Producto c Where nombre=?");
        query.setString(0, nombre);
        int id = (int) query.uniqueResult();
        sesion.close();
        return id;
    }

    

    public Producto buscarProducto2(String nom) throws Exception {
        Producto pro = null;
        iniciarOperacion();
        Query query = sesion.createQuery("From Producto Where nombre=?");
        query.setString(0, nom);
        pro = (Producto) query.uniqueResult();
        sesion.close();
        return pro;
    }

    public boolean eliminarProducto(Producto pro) throws Exception {
        iniciarOperacion();
        sesion.delete(pro);
        tx.commit();
        sesion.close();
        return true;
    }

    public List<Object[]> listarProductosProv() throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("Select p.idProducto,p.nombre from Producto p");
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    //Metodo que realiza la busqueda de un producto para el filtro por nombre
    public List<Object[]> buscarProductoFiltroBYNombreByAlmacen(String nom) throws Exception {
        List<Object[]> resultado;
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT p.idProducto,p.nombre FROM Producto p WHERE  p.nombre like  '" + nom + "%'");
        System.out.println(query);
        resultado = query.list();
        sesion.close();
        return resultado;
    }

    //Metodo que realiza la busqueda de busqueda para tabla modulo de ventas (IDINVENTARIO, NOMBREPRODUCTO,COLOR,STOCK)
    public List<Object[]> buscarProductoFiltroBYNombreByAlmacenModVentas(int idAlmacen, String nom) throws Exception {
        List<Object[]> resultado;
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT iv.idInventario,p.nombre,c.nombre,iv.stock  \n"
                + "FROM Producto p, Inventario iv, Colores c \n"
                + "WHERE  p.nombre like  '" + nom + "%' and iv.idProducto=p.idProducto and iv.idcolor=c.idColor  and iv.idAlmacen=? and iv.stock>0");
        query.setInteger(0, idAlmacen);
        System.out.println(query);
        resultado = query.list();
        sesion.close();
        return resultado;
    }

    //Metodo que recupera el precio del producto
    public String recuperarPrecioProducto(int id) throws Exception {
        String precio = null;
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT precioVenta \n"
                + "FROM Producto \n"
                + "Where idProducto=?");
        query.setInteger(0, id);
        precio = (String) query.uniqueResult();
        sesion.close();
        return precio;
    }

}
