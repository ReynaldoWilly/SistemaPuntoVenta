/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.dao;

import com.pos.pojos.Cliente;
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
public class clienteDao {

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

    public boolean registarCliente(Cliente usr) throws Exception {
        iniciarOperacion();
        sesion.save(usr);
        tx.commit();
        sesion.close();
        return true;
    }

    public List<Cliente> listarClientes() throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("From Cliente");
        List<Cliente> lista = query.list();
        sesion.close();
        return lista;
    }
    
    
    
    public List<Object[]> listarUsuario() throws Exception {
        iniciarOperacion();
        Query query = sesion.createQuery("Select u.idUsuario, u.nombres,u.apellidos,u.celular,u.cargo,u.email,u.tipousuario, u.password from Usuario As u");
        List<Object[]> lista = query.list();
        sesion.close();
        return lista;
    }

    //Buscar usuarios
    public List<Object[]> UsuarioById(int id) throws Exception {

        iniciarOperacion();
        Query query = sesion.createQuery("Select u.idUsuario, u.nombre,u.apellido,u.celular,u.cargo,u.email,u.tipoUsuario, u.password FROM Usuario As u WHERE u.idUsuario=?");
        query.setInteger(0, id);
        List<Object[]> lista = query.list();

        for (int i = 0; i < lista.size(); i++) {
            System.out.println("" + lista.size());
            System.out.println("--" + lista.get(i)[0]);
            System.out.println("--" + lista.get(i)[1]);
            System.out.println("--" + lista.get(i)[2]);
            System.out.println("--" + lista.get(i)[3]);
            System.out.println("--" + lista.get(i)[4]);

            sesion.close();
        }
        return lista;
    }

    public Cliente buscarUsuario(String usuario, String password) throws Exception {
        Cliente user = null;
        iniciarOperacion();
        Query query = sesion.createQuery("From Usuario where email=? and password=?");
        query.setString(0, usuario);

        query.setString(1, password);
        user = (Cliente) query.uniqueResult();
        tx.commit();
        sesion.close();
        return user;
    }

    public boolean eliminarCliente(Cliente user) throws Exception {
        iniciarOperacion();
        sesion.delete(user);
        tx.commit();
        sesion.close();
        return true;
    }

    public boolean actualizarCliente(Cliente user) throws Exception {
        iniciarOperacion();
        sesion.update(user);
        tx.commit();
        sesion.close();
        return true;
    }
}
