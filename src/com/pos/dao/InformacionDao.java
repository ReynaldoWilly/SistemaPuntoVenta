/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.dao;

import com.pos.pojos.Informacion;
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
public class InformacionDao {

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

    public boolean registarInformacion(Informacion pro) throws Exception {
        iniciarOperacion();
        sesion.save(pro);
        tx.commit();
        sesion.close();
        return true;
    }

    public boolean actualizarInformacion(Informacion pro) throws Exception {
        iniciarOperacion();
        sesion.update(pro);
        tx.commit();
        sesion.close();
        return true;
    }

    public Informacion buscarInformacion(int id) throws Exception {
        Informacion info = null;
        iniciarOperacion();
        Query query = sesion.createQuery("From Informacion Where idinformacion=?");
        query.setInteger(0, id);
        info = (Informacion) query.uniqueResult();
        sesion.close();
        return info;
    }

    public List<Informacion> listarInformacion() throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("From Informacion");
        List<Informacion> lista = query.list();
        sesion.close();
        return lista;
    }
}
