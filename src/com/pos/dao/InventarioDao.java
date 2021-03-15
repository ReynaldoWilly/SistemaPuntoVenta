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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        while (resultado.next()) {
            inv.setIdInventario(resultado.getInt("idInventario"));
            inv.setStock(resultado.getInt("stock"));
            inv.setIdcolor(resultado.getInt("idcolor"));
            inv.setIdProducto(resultado.getInt("idProducto"));
            inv.setIdAlmacen(resultado.getInt("idAlmacen"));
            inv.setTipoInv(resultado.getString("tipoInventario"));
        }
        return inv;
    }

    //Metodo que realiza el listado del kardex de inventario por almacen
    public List<Object[]> kardexInventarioAlmacen(int idAlmacen) throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT  iv.idInventario,p.codigo,p.nombre,iv.stock,c.nombre,p.stcokMinimo FROM Producto p, Inventario iv,Colores c WHERE iv.idProducto=p.idProducto and iv.idcolor=c.idColor  and iv.idAlmacen=? and iv.stock>0");
        query.setInteger(0, idAlmacen);
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    //Metodo que realiza la consulta HQL para el modulo de ventas ventana escoja producto 
    public List<Object[]> kardexInventarioByAlmacenForVentas(int idAlmacen) throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT  iv.idInventario,p.nombre,c.nombre,iv.stock FROM Producto p, Inventario iv,Colores c WHERE iv.idProducto=p.idProducto and iv.idcolor=c.idColor  and iv.idAlmacen=? and iv.stock>0");
        query.setInteger(0, idAlmacen);
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    //Metodo que realiza la consulta HQL para el modulo de ventas modulo seleccion de combo
    public List<Object[]> kardexInventarioByAlmacenVentasCombo(int idAlmacen) throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT  DISTINCT p.idProducto, p.nombre,c.nombre,i.stock FROM Producto As p, Inventario As i, Colores As c, Almacen As a WHERE p.idProducto=i.idProducto and i.idcolor=c.idColor and i.idAlmacen=? and  i.stock> 0");
        query.setInteger(0, idAlmacen);
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    //Metodo que realiza el listado del kardex de inventario por almacen
    public List<Object[]> KardexProducto(int idAlmacen, String nomProd) throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("SELECT  p.codigo,p.nombre,iv.stock,c.nombre,p.stcokMinimo FROM Producto p, Inventario iv,Colores c WHERE iv.idProducto=p.idProducto and iv.idAlmacen=? and  iv.idcolor=c.idColor  and p.nombre=? and iv.stock>0");
        query.setInteger(0, idAlmacen);
        query.setString(1, nomProd);
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    //Medoti que realiaza la recuperacion del stock del producto 
    public int recuperarStockProducto(int idProducto, int idColor,int idAlmacen) {
        int stock=0;
        Query query = sesion.createQuery("SELECT stock FROM Inventario WHERE idProducto=? and idcolor=? and idAlmacen=?");
        query.setInteger(0, idProducto);
        query.setInteger(1, idColor);
        query.setInteger(2, idAlmacen);
        
        stock = (int) query.uniqueResult();
        tx.commit();
        sesion.close();
        return stock;
    }
    
    
    //metodo que realiza la actualizacion de los datos en la BD
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
    
    public  int recuperarStock(int idProducto, int idAlm,int idColor) {
        try 
        {
            Connection miConexion = Conexion.getConectar();
            PreparedStatement statement = miConexion.prepareStatement("SELECT stock FROM inventario WHERE idcolor="+idColor+"and idProducto="+idProducto+"and idAlmacen="+idAlm);
            statement.executeUpdate();
            statement.close();
            miConexion.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el precio del producto..!!" + ex.getMessage());
        }
        return 0;
    }
}
