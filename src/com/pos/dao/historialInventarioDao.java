/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.dao;

import com.pos.pojos.HistorialInventario;
import com.pos.util.HibernateUtil;
import org.hibernate.HibernateException;
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
}
