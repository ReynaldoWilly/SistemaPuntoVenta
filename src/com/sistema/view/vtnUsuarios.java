/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.view;

import com.pos.core.Validaciones;
import com.pos.dao.usuarioDao;
import com.pos.pojos.Usuario;
import com.pos.utilitarios.Utilitarios;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Reynaldo
 */
public class vtnUsuarios extends javax.swing.JInternalFrame {

    /**
     * Creates new form vtnUsuarios
     */
    public static String validaVentana;//variable que realiza la validacion de ventana abierta
    private Usuario Objetousuario = new Usuario();

    public vtnUsuarios() 
    {
        initComponents();
        validaVentana = "x";//insertando un valor a la variable que valida a la ventana
        /*Poniendo el JinternalFrame al centro de la ventana*/
        int a = vtnPrincipal.panelMDI.getWidth() - this.getWidth();
        int b = vtnPrincipal.panelMDI.getHeight() - this.getHeight();
        setLocation(a / 2, b / 2);
        bloquearCamposFormulario();
        listarUsuarios();
        Validaciones.mejorarAparienciaTablaNormal(tablaUsuarios);
    }

    //Metodo que bloquea los campos de texto
    public void bloquearCamposFormulario() {
        txtNomUsr.setEnabled(false);
        txtApeUsr.setEnabled(false);
        txtCargoUsr.setEnabled(false);
        txtCelUsr.setEnabled(false);
        txtEmailUsr.setEnabled(false);
        comboTUrs.setEnabled(false);
        comboTUrs.setEnabled(false);
        txtPassUsr.setEnabled(false);
        txtPassUsr2.setEnabled(false);
        btnAcepUser.setEnabled(false);
        btnCanUsr.setEnabled(false);
        // btnActivarOp.setEnabled(true);
        btnActualizarUsr.setEnabled(false);
        btnEliminiarUsr.setEnabled(false);
        //--------botones de accion-----
        btnHabilitar.setEnabled(true);
        btnHabilitar.setText("Habilitar Edición");

        //--------fin botones de accion---
    }

    public void limpiarCampos() {
        txtNomUsr.setText("");
        txtApeUsr.setText("");
        txtCargoUsr.setText("");
        txtCelUsr.setText("");
        txtEmailUsr.setText("");
        comboTUrs.setSelectedIndex(0);
        txtPassUsr.setText("");
        txtPassUsr2.setText("");

    }

    //Metodo que bloquea los campos de texto
    public void activarCamposFormulario() {
        txtNomUsr.setEnabled(true);
        txtApeUsr.setEnabled(true);
        txtCargoUsr.setEnabled(true);
        txtCelUsr.setEnabled(true);
        txtEmailUsr.setEnabled(true);
        comboTUrs.setEnabled(true);

        txtPassUsr.setEnabled(true);
        txtPassUsr2.setEnabled(true);
        btnAcepUser.setEnabled(true);
        btnCanUsr.setEnabled(true);
        btnActualizarUsr.setEnabled(true);
        btnEliminiarUsr.setEnabled(true);
        btnNuevoUsr.setEnabled(false);
        //--------botones de accion-----
        // btnHabilitar.setEnabled(true);
        //btnHabilitar.setText("Habilitar Edición");

        //--------fin botones de accion---
    }

    //Metodo que realiza el listado de los usuarios registrados
    public void listarUsuarios()
    {
        try 
        {
            DefaultTableModel modelo = (DefaultTableModel) this.tablaUsuarios.getModel();//creando el modela ára llenar los datos al JTableje
            Utilitarios util = new Utilitarios();
            util.limpiarTabla(tablaUsuarios);
            //realizando la consulta para realizar el listado de los datos
            usuarioDao almDao = new usuarioDao();
            List<Object[]> lista = almDao.listarUsuario();
            if (lista.size() > 0) {
                Object[] fila = new Object[modelo.getColumnCount()];

                for (int i = 0; i < lista.size(); i++) {
                    fila[0] = lista.get(i)[0];//id
                    fila[1] = lista.get(i)[1];//nombre
                    fila[2] = lista.get(i)[2];//apellido;
                    fila[3] = lista.get(i)[3];//celular
                    fila[4] = lista.get(i)[4];//cargo;
                    fila[5] = lista.get(i)[5];//email

                    if (Integer.parseInt((lista.get(i)[6]).toString()) == 1)//tipo de usuario
                    {
                        fila[6] = "Administrador";
                    } else {
                        fila[6] = "Asistente";
                    }
                    fila[7] = lista.get(i)[7];//email

                    modelo.addRow(fila);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e, null, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        labelUsuario = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNomUsr = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCargoUsr = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtEmailUsr = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtPassUsr = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        txtApeUsr = new javax.swing.JTextField();
        txtCelUsr = new javax.swing.JTextField();
        comboTUrs = new javax.swing.JComboBox();
        txtPassUsr2 = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        btnNuevoUsr = new javax.swing.JButton();
        btnAcepUser = new javax.swing.JButton();
        btnCanUsr = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaUsuarios = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        btnActualizarUsr = new javax.swing.JButton();
        btnEliminiarUsr = new javax.swing.JButton();
        btnHabilitar = new javax.swing.JToggleButton();

        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel1.setBackground(new java.awt.Color(11, 58, 126));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelUsuario.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        labelUsuario.setForeground(new java.awt.Color(255, 255, 255));
        labelUsuario.setText("REGISTRO DE USUARIO");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/images/adduser.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(172, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(530, 530, 530))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(labelUsuario)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jLabel2)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("Nombre:");

        jLabel5.setText("Cargo:");

        jLabel7.setText("Email:");

        jLabel4.setText("Apellidos:");

        jLabel6.setText("Celular:");

        jLabel8.setText("Tipo usuario:");

        jLabel9.setText("Contraseña:");

        txtPassUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassUsrActionPerformed(evt);
            }
        });

        jLabel10.setText("Repita contraseña:");

        txtCelUsr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelUsrKeyTyped(evt);
            }
        });

        comboTUrs.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione una opción", "Vendedor", "administrador", "Super usuario" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPassUsr))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtEmailUsr))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtCargoUsr))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomUsr, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPassUsr2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboTUrs, 0, 185, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtApeUsr)
                            .addComponent(txtCelUsr, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNomUsr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtApeUsr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCargoUsr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtCelUsr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtEmailUsr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(comboTUrs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtPassUsr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtPassUsr2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnNuevoUsr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/agregar.png"))); // NOI18N
        btnNuevoUsr.setText("Nuevo");
        btnNuevoUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoUsrActionPerformed(evt);
            }
        });

        btnAcepUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/guardar.png"))); // NOI18N
        btnAcepUser.setText("Guardar");
        btnAcepUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcepUserActionPerformed(evt);
            }
        });

        btnCanUsr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/cancelar_1.png"))); // NOI18N
        btnCanUsr.setText("Cancelar");
        btnCanUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCanUsrActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(btnNuevoUsr)
                .addGap(27, 27, 27)
                .addComponent(btnAcepUser)
                .addGap(18, 18, 18)
                .addComponent(btnCanUsr)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAcepUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCanUsr))
                    .addComponent(btnNuevoUsr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jScrollPane1MousePressed(evt);
            }
        });

        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NOMBRE", "APELLIDO", "CELULAR", "CARGO", "EMAIL", "T. USUARIO", "PASSOWRD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaUsuariosMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaUsuarios);
        if (tablaUsuarios.getColumnModel().getColumnCount() > 0) {
            tablaUsuarios.getColumnModel().getColumn(0).setMinWidth(50);
            tablaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(50);
            tablaUsuarios.getColumnModel().getColumn(0).setMaxWidth(50);
            tablaUsuarios.getColumnModel().getColumn(1).setResizable(false);
            tablaUsuarios.getColumnModel().getColumn(2).setResizable(false);
            tablaUsuarios.getColumnModel().getColumn(3).setResizable(false);
            tablaUsuarios.getColumnModel().getColumn(4).setResizable(false);
            tablaUsuarios.getColumnModel().getColumn(5).setResizable(false);
            tablaUsuarios.getColumnModel().getColumn(6).setResizable(false);
            tablaUsuarios.getColumnModel().getColumn(7).setResizable(false);
            tablaUsuarios.getColumnModel().getColumn(7).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnActualizarUsr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/modificar.png"))); // NOI18N
        btnActualizarUsr.setText("Actualizar");
        btnActualizarUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarUsrActionPerformed(evt);
            }
        });

        btnEliminiarUsr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/delete.png"))); // NOI18N
        btnEliminiarUsr.setText("Eliminar");
        btnEliminiarUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminiarUsrActionPerformed(evt);
            }
        });

        btnHabilitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/habilitar.png"))); // NOI18N
        btnHabilitar.setText("Habilitar opciones");
        btnHabilitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHabilitarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(btnHabilitar)
                .addGap(18, 18, 18)
                .addComponent(btnActualizarUsr)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminiarUsr)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizarUsr)
                    .addComponent(btnEliminiarUsr)
                    .addComponent(btnHabilitar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoUsrActionPerformed
        // TODO add your handling code here:
        activarCamposFormulario();
        limpiarCampos();
        btnHabilitar.setEnabled(false);
        btnActualizarUsr.setEnabled(false);
        btnEliminiarUsr.setEnabled(false);
    }//GEN-LAST:event_btnNuevoUsrActionPerformed

    private void btnAcepUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcepUserActionPerformed
        // TODO add your handling code here:
        try {
            List<String> validar = new ArrayList<>();
            validar.add(txtNomUsr.getText());
            validar.add(txtApeUsr.getText());
            validar.add(txtCargoUsr.getText());
            validar.add(txtCelUsr.getText());
            validar.add(txtEmailUsr.getText());
            validar.add(txtPassUsr.getText());
            validar.add(txtPassUsr2.getText());

            if (new Validaciones().validarCampos(validar)) {
                if (comboTUrs.getSelectedIndex() != 0) {
                    if (txtPassUsr.getText().equals(txtPassUsr2.getText())) {

                        Usuario user = new Usuario(txtNomUsr.getText(), txtApeUsr.getText(), txtCelUsr.getText(), txtCargoUsr.getText(), txtEmailUsr.getText(), txtPassUsr.getText(), comboTUrs.getSelectedIndex());
                        usuarioDao userDao = new usuarioDao();
                        if (userDao.registarUsuario(user)) {
                            JOptionPane.showMessageDialog(this, "Registro de usuario correcto..!!", null, JOptionPane.INFORMATION_MESSAGE);
                            limpiarCampos();
                            bloquearCamposFormulario();
                            listarUsuarios();
                            btnNuevoUsr.setEnabled(true);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Las contraseñas ingresadas no son iguales..!!", null, JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Seleccione un elemento de la lista tipo de usuario..!!", null, JOptionPane.WARNING_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Faltan campos por llenar..!!", null, JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception E) {
            JOptionPane.showMessageDialog(this, E.getMessage(), null, JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnAcepUserActionPerformed

    private void btnCanUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCanUsrActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
        bloquearCamposFormulario();
        btnNuevoUsr.setEnabled(true);
        btnHabilitar.setEnabled(true);
    }//GEN-LAST:event_btnCanUsrActionPerformed

    private void btnActualizarUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarUsrActionPerformed
        try {
            List<String> validar = new ArrayList<>();
            validar.add(txtNomUsr.getText());
            validar.add(txtApeUsr.getText());
            validar.add(txtCargoUsr.getText());
            validar.add(txtCelUsr.getText());
            validar.add(txtEmailUsr.getText());
            validar.add(txtPassUsr.getText());
            validar.add(txtPassUsr2.getText());

            if (new Validaciones().validarCampos(validar)) {
                if (comboTUrs.getSelectedIndex() != 0) {
                    if (txtPassUsr.getText().equals(txtPassUsr2.getText())) {

                        Usuario user = new Usuario(this.getObjetousuario().getIdUsuario(), txtNomUsr.getText(), txtApeUsr.getText(), txtCelUsr.getText(), txtCargoUsr.getText(), txtEmailUsr.getText(), txtPassUsr.getText(), comboTUrs.getSelectedIndex());
                        usuarioDao userDao = new usuarioDao();

                        if (userDao.actualizarUsuario(user)) {
                            JOptionPane.showMessageDialog(this, "Actualización de usuario correcto..!!", null, JOptionPane.INFORMATION_MESSAGE);
                            limpiarCampos();
                            bloquearCamposFormulario();
                            listarUsuarios();
                            btnNuevoUsr.setEnabled(true);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Las contraseñas ingresadas no son iguales..!!", null, JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Seleccione un elemento de la lista tipo de usuario..!!", null, JOptionPane.WARNING_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Faltan campos por llenar..!!", null, JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception E) {
            JOptionPane.showMessageDialog(this, E.getMessage(), null, JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnActualizarUsrActionPerformed

    private void btnEliminiarUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminiarUsrActionPerformed
        // TODO add your handling code here:
        try {
            if (!txtNomUsr.getText().equals("")) {
                int y = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?");
                if (y == JOptionPane.YES_OPTION) {
                    usuarioDao userDao = new usuarioDao();
                    if (userDao.eliminarUsuario(this.getObjetousuario())) {
                        JOptionPane.showMessageDialog(this, "Eliminacion correcta", "Mensaje..!!", JOptionPane.INFORMATION_MESSAGE);
                        limpiarCampos();
                        bloquearCamposFormulario();
                        listarUsuarios();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un Usuario a eliminar..!!", "Mensaje..", JOptionPane.ERROR_MESSAGE);

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hay proyectos asociados a este usuario..!!", "Mensaje..", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminiarUsrActionPerformed

    private void btnHabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHabilitarActionPerformed
        // TODO add your handling code here:
        if (btnHabilitar.isSelected()) {
            activarCamposFormulario();
            btnNuevoUsr.setEnabled(false);
            btnCanUsr.setEnabled(false);
            btnAcepUser.setEnabled(false);
            btnActualizarUsr.setEnabled(true);
            btnEliminiarUsr.setEnabled(true);
            btnHabilitar.setText("Cancelar");
        } else {
            limpiarCampos();
            bloquearCamposFormulario();
            btnHabilitar.setEnabled(true);
            tablaUsuarios.setEnabled(true);
            btnHabilitar.setText("Habilitar Edición");
            btnNuevoUsr.setEnabled(true);
        }
    }//GEN-LAST:event_btnHabilitarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        this.dispose();
        validaVentana = null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtPassUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassUsrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassUsrActionPerformed

    private void jScrollPane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MousePressed

        // TODO add your handling code here:

    }//GEN-LAST:event_jScrollPane1MousePressed

    private void tablaUsuariosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaUsuariosMousePressed
        // TODO add your handling code here:
        DefaultTableModel tm = (DefaultTableModel) tablaUsuarios.getModel();
        String dato = String.valueOf(tm.getValueAt(tablaUsuarios.getSelectedRow(), 0));
        usuarioDao userDao = new usuarioDao();
        try {
            Usuario user = new Usuario();
            user.setIdUsuario(Integer.parseInt(dato));
            user.setNombres(String.valueOf(tm.getValueAt(tablaUsuarios.getSelectedRow(), 1)));
            user.setApellidos(String.valueOf(tm.getValueAt(tablaUsuarios.getSelectedRow(), 2)));
            user.setCelular(String.valueOf(tm.getValueAt(tablaUsuarios.getSelectedRow(), 3)));
            user.setCargo(String.valueOf(tm.getValueAt(tablaUsuarios.getSelectedRow(), 4)));
            user.setEmail(String.valueOf(tm.getValueAt(tablaUsuarios.getSelectedRow(), 5)));
            user.setPassword(String.valueOf(tm.getValueAt(tablaUsuarios.getSelectedRow(), 7)));

            String Tusuario = String.valueOf(tm.getValueAt(tablaUsuarios.getSelectedRow(), 6));
            //JOptionPane.showMessageDialog(null, Tusuario,null,JOptionPane.CANCEL_OPTION);
            if (Tusuario.equals("Vendedor")) {
                user.setTipousuario(1);
            } else if (Tusuario.equals("Administrador")) {
                user.setTipousuario(2);
            } else  {
                user.setTipousuario(3);
            }

            this.setObjetousuario(user);//seteando el usuario

            txtNomUsr.setText(user.getNombres());
            txtApeUsr.setText(user.getApellidos());
            txtCargoUsr.setText(user.getCargo());
            txtCelUsr.setText(user.getCelular());
            txtEmailUsr.setText(user.getEmail());
            txtPassUsr.setText(user.getPassword());
            txtPassUsr2.setText(user.getPassword());
            comboTUrs.setSelectedIndex(user.getTipousuario());
            // comboTUrs.setSelectedIndex(user.getTipoUsuario());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error" + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tablaUsuariosMousePressed

    private void txtCelUsrKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelUsrKeyTyped
        // TODO add your handling code here:
        new Validaciones().validaNumeros(txtCelUsr);
    }//GEN-LAST:event_txtCelUsrKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcepUser;
    private javax.swing.JButton btnActualizarUsr;
    private javax.swing.JButton btnCanUsr;
    private javax.swing.JButton btnEliminiarUsr;
    private javax.swing.JToggleButton btnHabilitar;
    private javax.swing.JButton btnNuevoUsr;
    private javax.swing.JComboBox comboTUrs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JTable tablaUsuarios;
    private javax.swing.JTextField txtApeUsr;
    private javax.swing.JTextField txtCargoUsr;
    private javax.swing.JTextField txtCelUsr;
    private javax.swing.JTextField txtEmailUsr;
    private javax.swing.JTextField txtNomUsr;
    private javax.swing.JPasswordField txtPassUsr;
    private javax.swing.JPasswordField txtPassUsr2;
    // End of variables declaration//GEN-END:variables

    public Usuario getObjetousuario() {
        return Objetousuario;
    }

    public void setObjetousuario(Usuario Objetousuario) {
        this.Objetousuario = Objetousuario;
    }
}
