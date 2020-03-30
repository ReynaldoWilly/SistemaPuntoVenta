/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.view;

import com.pos.core.Validaciones;
import com.pos.dao.InformacionDao;
import com.pos.dao.UnidadesMedidaDao;
import com.pos.dao.coloresDao;
import com.pos.pojos.Colores;
import com.pos.pojos.Informacion;
import com.pos.pojos.UnidadesMedida;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Reynaldo
 */
public class vtnParametrosSistema extends javax.swing.JInternalFrame {

    /**
     * Creates new form vtnParametrosSistema
     */
    public static String validaVentana;//variable que realiza la validacion de ventana abierta
    private Colores ObjColor;
    //----------Objeto Informacion
    private Informacion objInfo;

    private UnidadesMedida ObjMedida;
    //-----------------------------

    public vtnParametrosSistema() {
        initComponents();
        bloquearCamposUnidades();
        listarDatosUMedida();
        validaVentana = "x";//insertando un valor a la variable que valida a la ventana
        /*Poniendo el JinternalFrame al centro de la ventana*/
        int a = vtnPrincipal.panelMDI.getWidth() - this.getWidth();
        int b = vtnPrincipal.panelMDI.getHeight() - this.getHeight();
        setLocation(a / 2, b / 2);
        ObjColor = new Colores();
        listarColores();

        //validando los campos de texto de la pestaña de informacion
        //Validando los campos de ingreso de numero 
        Validaciones.validaNumeros(txtNit);
        Validaciones.validaNumeros(txtFonoInformacion);
        Validaciones.validaNumeros(txtFaxInformacion);
        Validaciones.validaNumeros(txtImpuestos);
        Validaciones.validaNumeros(txtDescuentos);
        

        //recuperando los daros de la tabla informacion que ya debe ser llenada por defecto 
        //al momento de la instalacion del sistema
        try 
        {
            List<Informacion> datosInfo = new InformacionDao().listarInformacion();

            for (int i = 0; i < datosInfo.size(); ++i) {
                txtRazonS.setText(datosInfo.get(i).getRazonSocial());
                txtNit.setText(datosInfo.get(i).getNit());
                txtDirInformacion.setText(datosInfo.get(i).getDireccion());
                txtFonoInformacion.setText(datosInfo.get(i).getFono());
                txtMailInfo.setText(datosInfo.get(i).getEmail());
                txtFaxInformacion.setText(datosInfo.get(i).getFax());

                txtWebInfo.setText(datosInfo.get(i).getWeb());
                txtImpuestos.setText(datosInfo.get(i).getImpuestos());
                txtDescuentos.setText(datosInfo.get(i).getDescuentos());
                

                //recuperando la imagen y convirtiendo en ImageIco
                InputStream is = new ByteArrayInputStream(datosInfo.get(i).getLogo());
                BufferedImage image = ImageIO.read(is);
                ImageIcon ico = new ImageIcon(Validaciones.dimensionarImagen(image, 250, 246));

                lbmInformacion.setIcon(ico);
                repaint();

                //fin de la recuperacion y muetra de la imagen
                //generando el objeto informacion
                Informacion infoBD = new Informacion();
                infoBD.setIdParametros(datosInfo.get(i).getIdParametros());
                infoBD.setRazonSocial(datosInfo.get(i).getRazonSocial());
                infoBD.setNit(datosInfo.get(i).getNit());
                infoBD.setDireccion(datosInfo.get(i).getDireccion());
                infoBD.setFono(datosInfo.get(i).getFono());
                infoBD.setFax(datosInfo.get(i).getFax());
                infoBD.setEmail(datosInfo.get(i).getEmail());
                infoBD.setWeb(datosInfo.get(i).getWeb());
                infoBD.setLogo(datosInfo.get(i).getLogo());
                this.setObjInfo(infoBD);//insertando el objeto a actualizar
                //--------------------------------------------
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al recuperar la infomación del sistema..!!" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    //Metodo que bloquea los campos de texto
    public void bloquearCamposUnidades() {
        //txtCodigoFpago.setEnabled(false);
        // txtFormaPago.setEnabled(false);
        btnGuardarU.setEnabled(false);

    }

    public void listarColores() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) this.tablaUmedida.getModel();//creando el modela ára llenar los datos al JTableje
            limpiarTabla(tablaUmedida);
            //realizando la consulta para realizar el listado de los datos
            coloresDao pgDao = new coloresDao();
            List<Colores> lista = pgDao.listarColores();
            Object[] fila = new Object[modelo.getColumnCount()];
            for (int i = 0; i < lista.size(); i++) {
                fila[0] = lista.get(i).getIdColor();
                fila[1] = lista.get(i).getNombre();
                fila[2] = lista.get(i).getCodigo();

                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e, null, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listarDatosUMedida() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) this.tablaUmedida1.getModel();//creando el modela ára llenar los datos al JTableje
            limpiarTabla(tablaUmedida1);
            //realizando la consulta para realizar el listado de los datos
            UnidadesMedidaDao pgDao = new UnidadesMedidaDao();
            List<UnidadesMedida> lista = pgDao.listarUnidades();
            Object[] fila = new Object[modelo.getColumnCount()];
            for (int i = 0; i < lista.size(); i++) {
                fila[0] = lista.get(i).getIdUnidadMedida();
                fila[1] = lista.get(i).getNombre();
                fila[2] = lista.get(i).getAbreviacion();
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e, null, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarTabla(JTable tabla) {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            int filas = tabla.getRowCount();
            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaUmedida1 = new javax.swing.JTable();
        btnGuardarU = new javax.swing.JButton();
        btnActU = new javax.swing.JButton();
        btnEliminarU = new javax.swing.JButton();
        btnNuevoU = new javax.swing.JToggleButton();
        jLabel17 = new javax.swing.JLabel();
        txtUnidadM = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtAbreviacion = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        btnSaveColor = new javax.swing.JButton();
        btnDeleteColor = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtNomColor = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtAbrColor = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaUmedida = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtImpuestos = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtDescuentos = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtRazonS = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNit = new javax.swing.JTextField();
        txtDirInformacion = new javax.swing.JTextField();
        txtFonoInformacion = new javax.swing.JTextField();
        txtFaxInformacion = new javax.swing.JTextField();
        txtMailInfo = new javax.swing.JTextField();
        txtWebInfo = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtImgInformacion = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        lbmInformacion = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setTitle("Parametros del sistema..");
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
        labelUsuario.setText("PARAMETROS DEL SISTEMA");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/images/ajustes.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jTabbedPane1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "UNIDADES DE MEDIDA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 15))); // NOI18N

        tablaUmedida1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        tablaUmedida1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "ABREVIACIÓN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaUmedida1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaUmedida1MousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tablaUmedida1);
        if (tablaUmedida1.getColumnModel().getColumnCount() > 0) {
            tablaUmedida1.getColumnModel().getColumn(0).setMinWidth(70);
            tablaUmedida1.getColumnModel().getColumn(0).setPreferredWidth(70);
            tablaUmedida1.getColumnModel().getColumn(0).setMaxWidth(80);
        }

        btnGuardarU.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        btnGuardarU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/guardar.png"))); // NOI18N
        btnGuardarU.setText("Guardar");
        btnGuardarU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarUActionPerformed(evt);
            }
        });

        btnActU.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        btnActU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/modificar.png"))); // NOI18N
        btnActU.setText("Actualizar");
        btnActU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActUActionPerformed(evt);
            }
        });

        btnEliminarU.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        btnEliminarU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/delete.png"))); // NOI18N
        btnEliminarU.setText("Eliminar");
        btnEliminarU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarUActionPerformed(evt);
            }
        });

        btnNuevoU.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        btnNuevoU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/agregar.png"))); // NOI18N
        btnNuevoU.setText("Nuevo");
        btnNuevoU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoUActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel17.setText("Unidad:");

        txtUnidadM.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel18.setText("Abreviación:");

        txtAbreviacion.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(btnNuevoU)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarU)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnActU)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarU)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel17)
                        .addGap(29, 29, 29)
                        .addComponent(txtUnidadM, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(txtAbreviacion)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtUnidadM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAbreviacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoU, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarU)
                    .addComponent(btnActU)
                    .addComponent(btnEliminarU))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Unidades de medida", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Categorias", jPanel4);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("COLORES"));
        jPanel8.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N

        btnSaveColor.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        btnSaveColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/guardar.png"))); // NOI18N
        btnSaveColor.setText("Guardar");
        btnSaveColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveColorActionPerformed(evt);
            }
        });

        btnDeleteColor.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        btnDeleteColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/delete.png"))); // NOI18N
        btnDeleteColor.setText("Eliminar");
        btnDeleteColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteColorActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel12.setText("Nombre:");

        txtNomColor.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel13.setText("Codigo:");

        txtAbrColor.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        tablaUmedida.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        tablaUmedida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "ABREVIACIÓN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaUmedida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaUmedidaMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tablaUmedida);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel12)
                        .addGap(29, 29, 29)
                        .addComponent(txtNomColor, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAbrColor))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(btnSaveColor)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteColor))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtNomColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAbrColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveColor)
                    .addComponent(btnDeleteColor))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Colores", jPanel5);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "IMPUESTOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 15))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel3.setText("Porcentaje de impuesto % :");

        txtImpuestos.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        txtImpuestos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtImpuestos.setText("1");
        txtImpuestos.setToolTipText("Ingrese el procentaje de impuestos.");

        jButton3.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/guardar.png"))); // NOI18N
        jButton3.setText("Guardar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(txtImpuestos, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtImpuestos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jLabel3))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DESCUENTOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 15))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel19.setText("Descuentos % :");

        txtDescuentos.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        txtDescuentos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescuentos.setText("0");
        txtDescuentos.setToolTipText("Ingrese el porcentaje para realizar descuentos en los productos.");

        jButton4.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/guardar.png"))); // NOI18N
        jButton4.setText("Guardar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(txtDescuentos, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescuentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4)
                    .addComponent(jLabel19))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(151, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("  Impuestos - Descuentos", jPanel6);

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel4.setText("Razon social:");

        txtRazonS.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtRazonS.setToolTipText("Ingrese el nombre de su empresa");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel5.setText("Nit:");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel6.setText("Dirección:");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel7.setText("Teléfono:");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel8.setText("Fax:");

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel9.setText("Email:");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel10.setText("Web:");

        txtNit.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtNit.setToolTipText("Ingrese el número de Nit/CI");

        txtDirInformacion.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtDirInformacion.setToolTipText("Ingrese la dirección de su empresa");

        txtFonoInformacion.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtFonoInformacion.setToolTipText("Ingrese el teléfono de su empresa");

        txtFaxInformacion.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtFaxInformacion.setToolTipText("Ingrese el número de fax");
        txtFaxInformacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFaxInformacionKeyTyped(evt);
            }
        });

        txtMailInfo.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtMailInfo.setToolTipText("Ingrese el E-mail de su empresa");

        txtWebInfo.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtWebInfo.setToolTipText("Ingrese la dirección de la pagina de su empresa");

        jLabel14.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 51, 204));
        jLabel14.setText("Click para seleccionar una imagen");

        txtImgInformacion.setFont(new java.awt.Font("Tahoma", 0, 1)); // NOI18N

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.setToolTipText("Click para seleccionar una imagen..");
        jPanel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lbmInformacion.setBackground(new java.awt.Color(153, 0, 102));
        lbmInformacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbmInformacionMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbmInformacion, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbmInformacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/guardar.png"))); // NOI18N
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/cancelar.png"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addGap(64, 64, 64)
                                                .addComponent(txtMailInfo))
                                            .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel4)
                                                    .addComponent(jLabel5)
                                                    .addComponent(jLabel6)
                                                    .addComponent(jLabel7))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txtNit, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                                                    .addComponent(txtRazonS, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                                                    .addComponent(txtDirInformacion)
                                                    .addComponent(txtFonoInformacion)))
                                            .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addGap(78, 78, 78)
                                                .addComponent(txtFaxInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE))
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtWebInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)))
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addGap(38, 38, 38))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtImgInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtImgInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtRazonS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtDirInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtFonoInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtFaxInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txtMailInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(txtWebInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addGap(15, 15, 15)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Información", jPanel7);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        this.dispose();
        validaVentana = null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void tablaUmedidaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaUmedidaMousePressed
        // TODO add your handling code here:
        DefaultTableModel tm = (DefaultTableModel) tablaUmedida.getModel();
        String dato = String.valueOf(tm.getValueAt(tablaUmedida.getSelectedRow(), 0));
        coloresDao pgDao = new coloresDao();
        try {
            Colores pg = new Colores(Integer.parseInt(dato), String.valueOf(tm.getValueAt(tablaUmedida.getSelectedRow(), 1)), String.valueOf(tm.getValueAt(tablaUmedida.getSelectedRow(), 2)));
            this.setObjColor(pg);//insertando el
            txtNomColor.setText(pg.getNombre());
            txtAbrColor.setText(pg.getCodigo());

            //JOptionPane.showMessageDialog(null, "Precionado" + cat.getNombre());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error" + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tablaUmedidaMousePressed

    private void btnSaveColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveColorActionPerformed
        // TODO add your handling code here:
        try {
            List<String> validar = new ArrayList<>();
            validar.add(txtNomColor.getText());
            validar.add(txtAbrColor.getText());

            if (Validaciones.validarCampos(validar)) {
                Colores pg = new Colores(txtNomColor.getText(), txtAbrColor.getText());
                coloresDao uniDao = new coloresDao();
                if (uniDao.registarColor(pg)) {
                    JOptionPane.showMessageDialog(this, "Registro de Coloe  correcto..", null, JOptionPane.INFORMATION_MESSAGE);
                    txtNomColor.setText("");
                    txtAbrColor.setText("");

                    listarColores();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Faltan campos por llenar..!!", null, JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error" + e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveColorActionPerformed

    private void btnDeleteColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteColorActionPerformed
        // TODO add your handling code here:
        if (tablaUmedida.getSelectedRows().length != 0) {
            int y = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?");
            try {
                if (y == JOptionPane.YES_OPTION) {
                    coloresDao pgDao = new coloresDao();
                    if (pgDao.eliminarColor(this.getObjColor())) {
                        JOptionPane.showMessageDialog(this, "Eliminacion correcta", null, JOptionPane.INFORMATION_MESSAGE);
                        txtNomColor.setText("");
                        txtAbrColor.setText("");

                        listarColores();
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hay productos asociados a este color..\npor lo que no se puede eliminar..!!", null, JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una color \na eliminar..!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteColorActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            Informacion info = this.getObjInfo();
            InformacionDao infoDao = new InformacionDao();

            //Iniciando la validacion de campos vacios
            List<String> validar = new ArrayList<>();
            validar.add(txtRazonS.getText());
            validar.add(txtNit.getText());
            validar.add(txtDirInformacion.getText());
            validar.add(txtFonoInformacion.getText());
            validar.add(txtFaxInformacion.getText());
            validar.add(txtMailInfo.getText());
            validar.add(txtWebInfo.getText());

            if (Validaciones.validarCampos(validar)) {
                info.setIdParametros(this.getObjInfo().getIdParametros());
                //JOptionPane.showMessageDialog(this, "--->"+getObjInfo().getIdParametros(), "mensaje", JOptionPane.INFORMATION_MESSAGE);
                info.setRazonSocial(txtRazonS.getText());
                info.setNit(txtNit.getText());
                info.setDireccion(txtDirInformacion.getText());
                info.setFono(txtFonoInformacion.getText());
                info.setEmail(txtMailInfo.getText());
                info.setFax(txtFaxInformacion.getText());
                info.setWeb(txtWebInfo.getText());
                info.setImpuestos(txtImpuestos.getText());
                info.setDescuentos(txtDescuentos.getText());
                

                if (txtImgInformacion.getText().length() > 0) {
                    //Tratando la imagen
                    File archivoImagen = new File(txtImgInformacion.getText());//recuperando la url de la imagen
                    byte[] bytefile = new byte[(int) archivoImagen.length()];
                    FileInputStream fs = new FileInputStream(archivoImagen);
                    fs.read(bytefile);
                    fs.close();

                    info.setLogo(bytefile);

                    if (infoDao.actualizarInformacion(info)) {
                        JOptionPane.showMessageDialog(this, "Información guardada", "mensaje", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al realizar al guardar la información", "mensaje", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if (infoDao.actualizarInformacion(info)) {
                        JOptionPane.showMessageDialog(this, "Información guardada", "mensaje", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al realizar al guardar la información", "mensaje", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Faltan campos por llenar..!!", "Mensaje", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al realizar la actualización de los datos..!!" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        validaVentana = null;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void lbmInformacionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbmInformacionMousePressed
        // TODO add your handling code here:
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("JPG, PNG & GIF", "jpg", "png", "gif");
        JFileChooser abrir = new JFileChooser();
        abrir.setFileFilter(filtroImagen);
        abrir.setDialogTitle("Seleccionar imagen..");
        int ventana = abrir.showOpenDialog(null);
        //FIN DE LA ALINEACION DE LA VENTANA
        if (ventana == JFileChooser.APPROVE_OPTION) {
            File file = abrir.getSelectedFile();
            txtImgInformacion.setText(String.valueOf(file));
            Image foto = getToolkit().getImage(txtImgInformacion.getText());//recuperando la URL de la imagen y convirtiendola en un objeto de tipo imagen
            foto = foto.getScaledInstance(lbmInformacion.getWidth(), lbmInformacion.getHeight(), Image.SCALE_DEFAULT);
            lbmInformacion.setIcon(new ImageIcon(foto));
        }
    }//GEN-LAST:event_lbmInformacionMousePressed

    private void tablaUmedida1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaUmedida1MousePressed
        // TODO add your handling code here:
        DefaultTableModel tm = (DefaultTableModel) tablaUmedida1.getModel();
        String dato = String.valueOf(tm.getValueAt(tablaUmedida1.getSelectedRow(), 0));
        UnidadesMedidaDao pgDao = new UnidadesMedidaDao();
        try {
            UnidadesMedida pg = new UnidadesMedida(Integer.parseInt(dato), String.valueOf(tm.getValueAt(tablaUmedida1.getSelectedRow(), 1)), String.valueOf(tm.getValueAt(tablaUmedida1.getSelectedRow(), 2)));
            this.setObjMedida(pg);//insertando el
            txtUnidadM.setText(pg.getNombre());
            txtAbreviacion.setText(pg.getAbreviacion());

            //JOptionPane.showMessageDialog(null, "Precionado" + cat.getNombre());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error" + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tablaUmedida1MousePressed

    private void btnGuardarUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarUActionPerformed
        // TODO add your handling code here:
        try {
            List<String> validar = new ArrayList<>();
            validar.add(txtUnidadM.getText());
            validar.add(txtAbreviacion.getText());

            if (Validaciones.validarCampos(validar)) {
                UnidadesMedida pg = new UnidadesMedida(txtUnidadM.getText(), txtAbreviacion.getText());
                UnidadesMedidaDao uniDao = new UnidadesMedidaDao();
                if (uniDao.registarUnidadMedida(pg)) {
                    JOptionPane.showMessageDialog(this, "Registro de Unidad de Medida correcto..", "Satisfactorio", JOptionPane.INFORMATION_MESSAGE);
                    txtUnidadM.setText("");
                    txtAbreviacion.setText("");
                    btnGuardarU.setEnabled(false);
                    btnActU.setEnabled(true);
                    btnEliminarU.setEnabled(true);
                    btnNuevoU.setText("Nuevo");

                    listarDatosUMedida();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Faltan campos por llenar..!!", "Mensaje", JOptionPane.WARNING_MESSAGE);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error" + e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarUActionPerformed

    private void btnActUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActUActionPerformed
        // TODO add your handling code here:
        try {
            List<String> validar = new ArrayList<>();
            validar.add(txtUnidadM.getText());
            validar.add(txtAbreviacion.getText());
            if (Validaciones.validarCampos(validar)) {

                UnidadesMedida pg = this.getObjMedida();
                pg.setNombre(txtUnidadM.getText());
                pg.setAbreviacion(txtAbreviacion.getText());

                UnidadesMedidaDao uniDao = new UnidadesMedidaDao();

                if (uniDao.actualizarUnidadMedida(pg)) {
                    JOptionPane.showMessageDialog(this, "Actualizacion correcta", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    txtUnidadM.setText("");
                    txtAbreviacion.setText("");
                    btnGuardarU.setEnabled(false);
                    btnActU.setEnabled(true);
                    btnEliminarU.setEnabled(true);
                    btnNuevoU.setText("Nuevo");

                    listarDatosUMedida();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Faltan campos por llenar..!!", "Mensaje", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error" + e.getMessage(), "Falla", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnActUActionPerformed

    private void btnEliminarUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUActionPerformed
        // TODO add your handling code here:
        if (tablaUmedida1.getSelectedRows().length != 0) {
            int y = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?");
            try {
                if (y == JOptionPane.YES_OPTION) {
                    UnidadesMedidaDao pgDao = new UnidadesMedidaDao();
                    if (pgDao.eliminarUnidadMedida(this.getObjMedida())) {
                        JOptionPane.showMessageDialog(this, "Eliminacion correcta", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                        txtUnidadM.setText("");
                        txtAbreviacion.setText("");

                        bloquearCamposUnidades();
                        listarDatosUMedida();
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hay productos asociados a esta Unidad de Medida\npor lo que no se puede eliminar..!!", null, JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una unidad de medida\na eliminar..!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarUActionPerformed

    private void btnNuevoUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoUActionPerformed
        // TODO add your handling code here:
        if (btnNuevoU.isSelected()) {
            txtUnidadM.setText("");
            txtAbreviacion.setText("");

            txtUnidadM.setEnabled(true);
            txtAbreviacion.setEnabled(true);

            btnEliminarU.setEnabled(false);
            btnActU.setEnabled(false);
            btnNuevoU.setText("Cancelar");
            btnGuardarU.setEnabled(true);
        } else {
            btnNuevoU.setText("Nuevo");

            txtAbreviacion.setText("");
            btnGuardarU.setEnabled(false);
            btnEliminarU.setEnabled(true);
            btnActU.setEnabled(true);
        }
    }//GEN-LAST:event_btnNuevoUActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
            Informacion info = this.getObjInfo();
            InformacionDao infoDao = new InformacionDao();

            //Iniciando la validacion de campos vacios
            List<String> validar = new ArrayList<>();
            validar.add(txtImpuestos.getText());

            if (Validaciones.validarCampos(validar)) {
                if (!infoDao.actualizarImpuestos(txtImpuestos.getText(), info)) {
                    JOptionPane.showMessageDialog(this, "Información guardada", "mensaje", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al realizar al guardar la información", "mensaje", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Faltan campos por llenar..!!", "Mensaje", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al realizar la actualización de los datos..!!" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtFaxInformacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFaxInformacionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFaxInformacionKeyTyped

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        try 
        {
            Informacion info = this.getObjInfo();
            InformacionDao infoDao = new InformacionDao();

            //Iniciando la validacion de campos vacios
            List<String> validar = new ArrayList<>();
            validar.add(txtDescuentos.getText());

            if (Validaciones.validarCampos(validar)) {
                if (!infoDao.actualizarDescuentos(txtDescuentos.getText(), info)) {
                    JOptionPane.showMessageDialog(this, "Información guardada", "mensaje", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al realizar al guardar la información", "mensaje", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Faltan campos por llenar..!!", "Mensaje", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al realizar la actualización de los datos..!!" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActU;
    private javax.swing.JButton btnDeleteColor;
    private javax.swing.JButton btnEliminarU;
    private javax.swing.JButton btnGuardarU;
    private javax.swing.JToggleButton btnNuevoU;
    private javax.swing.JButton btnSaveColor;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JLabel lbmInformacion;
    private javax.swing.JTable tablaUmedida;
    private javax.swing.JTable tablaUmedida1;
    private javax.swing.JTextField txtAbrColor;
    private javax.swing.JTextField txtAbreviacion;
    private javax.swing.JTextField txtDescuentos;
    private javax.swing.JTextField txtDirInformacion;
    private javax.swing.JTextField txtFaxInformacion;
    private javax.swing.JTextField txtFonoInformacion;
    private javax.swing.JTextField txtImgInformacion;
    private javax.swing.JTextField txtImpuestos;
    private javax.swing.JTextField txtMailInfo;
    private javax.swing.JTextField txtNit;
    private javax.swing.JTextField txtNomColor;
    private javax.swing.JTextField txtRazonS;
    private javax.swing.JTextField txtUnidadM;
    private javax.swing.JTextField txtWebInfo;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JButton getBtnDeleteColor() {
        return btnDeleteColor;
    }

    public void setBtnDeleteColor(javax.swing.JButton btnDeleteColor) {
        this.btnDeleteColor = btnDeleteColor;
    }

    public Colores getObjColor() {
        return ObjColor;
    }

    public void setObjColor(Colores ObjColor) {
        this.ObjColor = ObjColor;
    }

    public Informacion getObjInfo() {
        return objInfo;
    }

    public void setObjInfo(Informacion objInfo) {
        this.objInfo = objInfo;
    }

    public UnidadesMedida getObjMedida() {
        return ObjMedida;
    }

    public void setObjMedida(UnidadesMedida ObjMedida) {
        this.ObjMedida = ObjMedida;
    }
}
