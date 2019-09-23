/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.dao;

import com.pos.pojos.Colores;
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
public class coloresDao {

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

    public boolean registarColor(Colores pro) throws Exception {
        iniciarOperacion();
        sesion.save(pro);
        tx.commit();
        sesion.close();
        return true;
    }

    public boolean eliminarColor(Colores pro) throws Exception {
        iniciarOperacion();
        sesion.delete(pro);
        tx.commit();
        sesion.close();
        return true;
    }

    public List<Colores> listarColores() throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("From Colores");
        List<Colores> lista = query.list();
        sesion.close();
        return lista;
    }

    public int buscarColorById(String nombre) throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("Select  c.idColor From Colores c where nombre=?");
        query.setString(0, nombre);
        int id = (int) query.uniqueResult();
        tx.commit();
        sesion.close();
        return id;
    }
}
