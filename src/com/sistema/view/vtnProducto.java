/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.view;

import com.pos.core.Validaciones;
import com.pos.dao.UnidadesMedidaDao;
import com.pos.dao.productoDao;
import com.pos.pojos.Producto;
import com.pos.pojos.UnidadesMedida;
import com.pos.tabla.render.HeaderCellRenderer;
import com.pos.tabla.render.RenderTabla;
import static com.sistema.view.vtnVentas.tablaVenta;
import java.awt.Color;
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
import javax.swing.table.JTableHeader;

/**
 *
 * @author Reynaldo
 */
public class vtnProducto extends javax.swing.JInternalFrame {

    /**
     * Creates new form vtnProducto
     */
    public static String validaVentana;//variable que realiza la validacion de ventana abierta
    private Producto ObjProducto = new Producto();//Objeto global

    public vtnProducto() {
        initComponents();
        validaVentana = "x";//insertando un valor a la variable que valida a la ventana

        /*Poniendo el JinternalFrame al centro de la ventana*/
        int a = vtnPrincipal.panelMDI.getWidth() - this.getWidth();
        int b = vtnPrincipal.panelMDI.getHeight() - this.getHeight();
        setLocation(a / 2, b / 2);
        /*fin-----*/

        listarDatos();
        cargarComboUnidades();
        bloquearCamposProducto();
        Validaciones.mejorarAparienciaTablaNormal(tablaProductos);
        
    }

    public void limpiarCampos() {
        txtNomPro.setText("");
        txtCodigoPro.setText("");
        txtDesPro.setText("");
        txtImgProducto.setText("");
        txtPrecioPro.setText("0");
        txtPrecioVenta.setText("0");
    }

    

    //Metodo que bloquea los campos de texto
    public void bloquearCamposProducto() {
        txtNomPro.setEnabled(false);
        txtCodigoPro.setEnabled(false);
        txtDesPro.setEnabled(false);
        txtImgProducto.setEnabled(false);
        RacInac.setEnabled(false);
        RacPro.setEnabled(false);
        comboCategoria.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnRegPro.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        comboUnidades.setEnabled(false);
        txtPrecioPro.setEnabled(false);
        txtPrecioVenta.setEnabled(false);
        //--------botones de accion-----
        btnEdicion.setEnabled(true);
        btnNuevo.setEnabled(true);
        //--------fin botones de accion---
        txtStockMinimo.setEnabled(false);
    }

    //Metodo que habilita los campos de texto
    public void habilitarCamposProducto() {
        txtNomPro.setEnabled(true);
        txtCodigoPro.setEnabled(true);
        txtDesPro.setEnabled(true);
        txtImgProducto.setEnabled(true);
        lbmProducto.setEnabled(true);
        RacInac.setEnabled(true);
        RacPro.setEnabled(true);
        comboCategoria.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnRegPro.setEnabled(true);
        txtPrecioPro.setEnabled(true);
        txtStockMinimo.setEnabled(true);
        comboUnidades.setEnabled(true);
        txtPrecioVenta.setEnabled(true);
    }

    public void cargarComboUnidades() {
        UnidadesMedidaDao cdao = new UnidadesMedidaDao();
        try {
            //realizando la consulta para realizar el listado de los datos
            List<UnidadesMedida> lista = cdao.listarUnidades();
            for (int i = 0; i < lista.size(); i++) {
                comboUnidades.addItem(lista.get(i).getNombre());
                // System.err.println(lista.get(i).getNombre());
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

    //Metodo que realiza el listado de los datos en un JTable de java
    public void listarDatos() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) this.tablaProductos.getModel();//creando el modela ára llenar los datos al JTableje
            limpiarTabla(tablaProductos);
            //realizando la consulta para realizar el listado de los datos
            productoDao proDao = new productoDao();
            List<Object[]> lista = proDao.listarProductos2();
            Object[] fila = new Object[modelo.getColumnCount()];

            for (int i = 0; i < lista.size(); i++) {
                fila[0] = lista.get(i)[0];//id
                fila[1] = lista.get(i)[1];//nombre
                fila[2] = lista.get(i)[2];//descripcion
                fila[3] = lista.get(i)[3];//codigo
                fila[4] = lista.get(i)[4];//categoria
                fila[5] = lista.get(i)[5];//precio compra
                fila[6] = lista.get(i)[6];//precio venta
                fila[7] = lista.get(i)[7];//sctok
                fila[8] = lista.get(i)[8];//estado
                fila[9] = lista.get(i)[9];//medida

                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e, null, JOptionPane.ERROR_MESSAGE);
        }
    }

    //metodo que inserta el icono de usuario por defecto
    public void setIcono() {
        ImageIcon imagenUser = new ImageIcon(getClass().getResource("/com/pos/images/selectImagen.png"));
        lbmProducto.setIcon(imagenUser);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GrupoEstadoProducto = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lbmProducto = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDesPro = new javax.swing.JTextArea();
        txtCodigoPro = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtImgProducto = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        comboCategoria = new javax.swing.JComboBox();
        RacPro = new javax.swing.JRadioButton();
        RacInac = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        comboUnidades = new javax.swing.JComboBox();
        txtNomPro = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtPrecioPro = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtStockMinimo = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        txtPrecioVenta = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnRegPro = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        comboFiltroCategoria = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodigoProdFind = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        btnEdicion = new javax.swing.JToggleButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();

        setTitle("Gestión de productos");
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

        jPanel4.setBackground(new java.awt.Color(11, 58, 126));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("GESTIÓN DE PRODUCTOS");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/images/ingresoAlm.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel15))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(390, 390, 390)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)))
                .addContainerGap(1192, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel1))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTabbedPane1.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Nombre:");

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbmProducto.setForeground(new java.awt.Color(153, 153, 153));
        lbmProducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbmProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/images/selectImagen.png"))); // NOI18N
        lbmProducto.setToolTipText("Click para seleccionar una imagen..");
        lbmProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbmProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbmProductoMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbmProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbmProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Imagen:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Descripcion:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Codigo:");

        txtDesPro.setColumns(20);
        txtDesPro.setRows(5);
        jScrollPane1.setViewportView(txtDesPro);

        txtCodigoPro.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel7.setText("Estado:");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel8.setText("Categoria:");

        comboCategoria.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        comboCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-----------------------", "Muebles clinicos", "Oficina", "Estanteria", "Sillas" }));

        GrupoEstadoProducto.add(RacPro);
        RacPro.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        RacPro.setText("Activo");

        GrupoEstadoProducto.add(RacInac);
        RacInac.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        RacInac.setText("Inactivo");

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel16.setText("Unidad de medida:");

        comboUnidades.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        comboUnidades.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "------------------" }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(RacPro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(RacInac)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtCodigoPro)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(20, 20, 20))
                            .addComponent(txtImgProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 55, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(txtNomPro))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboUnidades, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(23, 23, 23)
                        .addComponent(comboCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtImgProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNomPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel4)
                        .addGap(66, 66, 66))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RacPro)
                    .addComponent(jLabel7)
                    .addComponent(RacInac))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(comboUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jLabel11.setForeground(new java.awt.Color(204, 0, 51));
        jLabel11.setText("Click para seleccionar una imagen");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(80, 80, 80))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        jTabbedPane1.addTab("General", jPanel5);

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel12.setText("Precio de compra:");

        txtPrecioPro.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtPrecioPro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecioPro.setText("0.0");
        txtPrecioPro.setToolTipText("Ingrese el precio de compra del producto.");
        txtPrecioPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel13.setText("Stock minimo:");

        txtStockMinimo.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        txtStockMinimo.setModel(new javax.swing.SpinnerNumberModel(1, 1, 10000, 1));

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel14.setText("Precio venta:");

        txtPrecioVenta.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtPrecioVenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecioVenta.setText("0.0");
        txtPrecioVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioVentaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtPrecioPro)
                    .addComponent(txtStockMinimo)
                    .addComponent(txtPrecioVenta))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtPrecioPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtStockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(362, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Costos", jPanel8);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnNuevo.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/agregar.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnRegPro.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        btnRegPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/guardar.png"))); // NOI18N
        btnRegPro.setText("Aceptar");
        btnRegPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegProActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/cancelar_1.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRegPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegPro, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel9.setText("Filtrar por categoria:");

        comboFiltroCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Muebles clinicos", "Oficina", "Estanteria", "Sillas" }));

        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/buscar.png"))); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/buscar.png"))); // NOI18N
        jButton2.setText("Listado general");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel6.setText("Buscar por codigo:");

        jButton3.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/buscar.png"))); // NOI18N
        jButton3.setText("Buscar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(comboFiltroCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(56, 56, 56)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigoProdFind, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(comboFiltroCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jLabel6)
                    .addComponent(txtCodigoProdFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(16, 16, 16))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnEdicion.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnEdicion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/habilitar.png"))); // NOI18N
        btnEdicion.setText("Habilitar Edición");
        btnEdicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdicionActionPerformed(evt);
            }
        });

        btnActualizar.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/modificar.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/delete.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(358, 358, 358)
                .addComponent(btnEdicion)
                .addGap(52, 52, 52)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdicion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnActualizar)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tablaProductos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tablaProductos.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "DESCRIPCION", "CODIGO", "CATEGORIA", "PRECIO COMPRA", "PRECIO VENTA", "SCTOK MIN", "ESTADO", "MEDIDA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaProductos.setGridColor(new java.awt.Color(0, 51, 51));
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaProductosMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tablaProductos);
        if (tablaProductos.getColumnModel().getColumnCount() > 0) {
            tablaProductos.getColumnModel().getColumn(0).setMinWidth(15);
            tablaProductos.getColumnModel().getColumn(0).setMaxWidth(20);
        }

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        this.dispose();
        validaVentana = null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void lbmProductoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbmProductoMousePressed
        // TODO add your handling code here:
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("JPG, PNG & GIF", "jpg", "png", "gif");
        JFileChooser abrir = new JFileChooser();
        abrir.setFileFilter(filtroImagen);
        abrir.setDialogTitle("Seleccionar imagen..");
        int ventana = abrir.showOpenDialog(null);
        if (ventana == JFileChooser.APPROVE_OPTION) {
            File file = abrir.getSelectedFile();
            txtImgProducto.setText(String.valueOf(file));
            Image foto = getToolkit().getImage(txtImgProducto.getText());//recuperando la URL de la imagen y convirtiendola en un objeto de tipo imagen
            foto = foto.getScaledInstance(lbmProducto.getWidth(), lbmProducto.getHeight(), Image.SCALE_DEFAULT);
            lbmProducto.setText("");
            lbmProducto.setIcon(new ImageIcon(foto));
        }
    }//GEN-LAST:event_lbmProductoMousePressed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        habilitarCamposProducto();
        limpiarCampos();
        RacPro.setSelected(true);
        tablaProductos.setEnabled(true);
        btnEdicion.setEnabled(false);
        setIcono();

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnRegProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegProActionPerformed
        // TODO add your handling code here:
        try {
            //Iniciando la validacion de campos vacios
            List<String> validar = new ArrayList<>();
            validar.add(txtCodigoPro.getText());
            validar.add(txtDesPro.getText());
            validar.add(txtImgProducto.getText());
            validar.add(txtNomPro.getText());
            validar.add(txtPrecioPro.getText());
            validar.add(txtPrecioVenta.getText());

            if (Validaciones.validarCampos(validar)) {
                File archivoImagen = new File(txtImgProducto.getText());//recuperando la url de la imagen
                byte[] bytefile = new byte[(int) archivoImagen.length()];
                FileInputStream fs = new FileInputStream(archivoImagen);
                fs.read(bytefile);
                fs.close();

                Producto pro = new Producto();

                if (RacPro.isSelected()) {
                    pro.setEstado("Activo");
                } else if (RacInac.isSelected()) {
                    pro.setEstado("Inactivo");
                }

                pro.setNombre(txtNomPro.getText());//nombre producto
                pro.setDescripcion(txtDesPro.getText());//descripcion
                pro.setCodigo(txtCodigoPro.getText());//codigo
                pro.setPrecioCompra(txtPrecioPro.getText());//precio compra
                pro.setPrecioVenta(txtPrecioVenta.getText());//precio venta del producto
                pro.setCategoria(comboCategoria.getSelectedItem().toString());
                pro.setStcokMinimo(Integer.parseInt(txtStockMinimo.getValue().toString()));
                pro.setImagen(bytefile);
                pro.setMedida(comboUnidades.getSelectedItem().toString());

                productoDao proDao = new productoDao();

                if (proDao.registarProducto(pro)) {
                    JOptionPane.showMessageDialog(this, "Registro de producto correcto..!!", "Satisfactorio..::..", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    listarDatos();
                    bloquearCamposProducto();
                    limpiarCampos();
                    setIcono();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Faltan campos por llenar..!!", "Mensaje", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error" + e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRegProActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
        bloquearCamposProducto();
        btnEdicion.setEnabled(true);
        tablaProductos.setEnabled(true);

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEdicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdicionActionPerformed
        // TODO add your handling code here:
        if (btnEdicion.isSelected()) {
            habilitarCamposProducto();
            btnNuevo.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnRegPro.setEnabled(false);
            btnActualizar.setEnabled(true);
            btnEliminar.setEnabled(true);
            btnEdicion.setText("  Cancelar  ");
        } else {
            limpiarCampos();
            bloquearCamposProducto();
            btnEdicion.setEnabled(true);
            tablaProductos.setEnabled(true);
            btnEdicion.setText("Habilitar Edición");
        }
    }//GEN-LAST:event_btnEdicionActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed

        try {
            List<String> validar = new ArrayList<>();
            validar.add(txtCodigoPro.getText());
            validar.add(txtDesPro.getText());
            validar.add(txtNomPro.getText());
            validar.add(txtPrecioPro.getText());
            validar.add(txtPrecioVenta.getText());

            Producto pro = this.getObjProducto();//recuperando el objeto recuperado desde la tabla
            productoDao proDao = new productoDao();

            if (Validaciones.validarCampos(validar)) {
                if (lbmProducto.getText().length() > 0) {
                    pro.setNombre(txtNomPro.getText());
                    pro.setDescripcion(txtDesPro.getText());
                    pro.setPrecioCompra(txtPrecioPro.getText());
                    pro.setPrecioVenta(txtPrecioVenta.getText());
                    pro.setCodigo(txtCodigoPro.getText());
                    pro.setStcokMinimo(Integer.parseInt(txtStockMinimo.getValue().toString()));
                    pro.setCategoria(comboCategoria.getSelectedItem().toString());
                    //recuperando la nueva imagen a actualizar
                    File archivoImagen = new File(txtImgProducto.getText());//recuperando la url de la imagen
                    byte[] bytefile = new byte[(int) archivoImagen.length()];
                    FileInputStream fs = new FileInputStream(archivoImagen);
                    fs.read(bytefile);
                    fs.close();
                    pro.setImagen(bytefile);
                    //fin de la actualizacion de la imagen

                    String estado = "";
                    if (RacPro.isSelected()) {
                        estado = "Activo";
                    } else if (RacInac.isSelected()) {
                        estado = "Inactivo";
                    }

                    pro.setEstado(estado);

                    if (proDao.actualizarProducto(pro)) {
                        JOptionPane.showMessageDialog(this, "Actualización correcta", "Satisfactorio..::..", JOptionPane.INFORMATION_MESSAGE);
                        limpiarCampos();
                        bloquearCamposProducto();
                        listarDatos();
                        setIcono();
                    }
                } else {
                    pro.setNombre(txtNomPro.getText());
                    pro.setDescripcion(txtDesPro.getText());
                    pro.setPrecioCompra(txtPrecioPro.getText());
                    pro.setPrecioVenta(txtPrecioVenta.getText());
                    pro.setCodigo(txtCodigoPro.getText());
                    pro.setStcokMinimo(Integer.parseInt(txtStockMinimo.getValue().toString()));
                    pro.setCategoria(comboCategoria.getSelectedItem().toString());
                    String estado = "";

                    if (RacPro.isSelected()) {
                        estado = "Activo";
                    } else if (RacInac.isSelected()) {
                        estado = "Inactivo";
                    }
                    pro.setEstado(estado);

                    if (proDao.actualizarProducto(pro)) {
                        JOptionPane.showMessageDialog(this, "Actualización correcta", "Satisfactorio..::..", JOptionPane.INFORMATION_MESSAGE);
                        limpiarCampos();
                        bloquearCamposProducto();
                        listarDatos();
                        setIcono();
                    }
                }

            } else {
                JOptionPane.showMessageDialog(this, "Faltan campos por llenar..!!", null, JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error" + e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        int y = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?");
        try {
            if (y == JOptionPane.YES_OPTION) {
                productoDao pr = new productoDao();
                if (pr.eliminarProducto(this.getObjProducto())) {
                    JOptionPane.showMessageDialog(this, "Eliminacion correcta", "Mensaje..::..", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    bloquearCamposProducto();
                    listarDatos();
                    setIcono();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hay productos asociados a esta categoria\npor lo que no se puede eliminar..!!", null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtPrecioProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
//        if (c < '0' || c > '9') {
//            evt.consume();
//        }
        if (!Character.isDigit(evt.getKeyChar()) && evt.getKeyChar() != '.') {
            evt.consume();
        }
        if (evt.getKeyChar() == '.' && txtPrecioPro.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPrecioProKeyTyped

    private void tablaProductosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductosMousePressed
        // TODO add your handling code here:
        DefaultTableModel tm = (DefaultTableModel) tablaProductos.getModel();
        String dato = String.valueOf(tm.getValueAt(tablaProductos.getSelectedRow(), 0));
        productoDao proDao = new productoDao();
        try {
            Producto pro = proDao.buscarProducto(Integer.parseInt(dato));
            this.setObjProducto(pro);//insertando el objeto recuperado por la consulta
            txtNomPro.setText(pro.getNombre());
            txtDesPro.setText(pro.getDescripcion());
            txtPrecioPro.setText(pro.getPrecioCompra());
            txtPrecioVenta.setText(pro.getPrecioVenta());
            txtCodigoPro.setText(pro.getCodigo());
            txtStockMinimo.setValue(pro.getStcokMinimo());
            lbmProducto.setText("");
            //recuperando la imagen y convirtiendo en ImageIco
            InputStream is = new ByteArrayInputStream(pro.getImagen());
            BufferedImage image = ImageIO.read(is);
            ImageIcon ico = new ImageIcon(image);
            ImageIcon icono = new ImageIcon(ico.getImage().getScaledInstance(lbmProducto.getWidth(), lbmProducto.getHeight(), Image.SCALE_DEFAULT));
            lbmProducto.setIcon(icono);
            //fin de la recuperacion y muetra de la imagen

            if (pro.getEstado().equals("Activo")) {
                RacPro.setSelected(true);
            } else if (pro.getEstado().equals("Inactivo")) {
                RacInac.setSelected(true);
            }
            comboUnidades.setSelectedItem(pro.getMedida().toString());

            String tipo = String.valueOf(tm.getValueAt(tablaProductos.getSelectedRow(), 4));
            comboCategoria.setSelectedItem(tipo);

            String medida = String.valueOf(tm.getValueAt(tablaProductos.getSelectedRow(), 8));
            comboUnidades.setSelectedItem(medida);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error" + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tablaProductosMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            DefaultTableModel modelo = (DefaultTableModel) this.tablaProductos.getModel();//creando el modela ára llenar los datos al JTableje
            limpiarTabla(tablaProductos);
            //realizando la consulta para realizar el listado de los datos
            productoDao proDao = new productoDao();
            List<Object[]> lista = proDao.listarProductosCategoria(comboFiltroCategoria.getSelectedItem().toString());

            Object[] fila = new Object[modelo.getColumnCount()];

            for (int i = 0; i < lista.size(); i++) {
                fila[0] = lista.get(i)[0];//id
                fila[1] = lista.get(i)[1];//nombre
                fila[2] = lista.get(i)[2];//descripcion
                fila[3] = lista.get(i)[3];//codigo
                fila[4] = lista.get(i)[4];//categoria
                fila[5] = lista.get(i)[5];//precio compra
                fila[6] = lista.get(i)[6];//precio venta
                fila[7] = lista.get(i)[7];//sctok
                fila[8] = lista.get(i)[8];//estado
                fila[9] = lista.get(i)[9];//medida

                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e, null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        listarDatos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
            DefaultTableModel modelo = (DefaultTableModel) this.tablaProductos.getModel();//creando el modela ára llenar los datos al JTableje
            limpiarTabla(tablaProductos);
            //realizando la consulta para realizar el listado de los datos
            productoDao proDao = new productoDao();
            if (txtCodigoProdFind.getText().length() > 0) {
                List<Object[]> lista = proDao.listarProductosByCodigo(txtCodigoProdFind.getText());

                if (lista.size() > 0) {

                    Object[] fila = new Object[modelo.getColumnCount()];

                    for (int i = 0; i < lista.size(); i++) {
                        fila[0] = lista.get(i)[0];//id
                        fila[1] = lista.get(i)[1];//nombre
                        fila[2] = lista.get(i)[2];//descripcion
                        fila[3] = lista.get(i)[3];//codigo
                        fila[4] = lista.get(i)[4];//categoria
                        fila[5] = lista.get(i)[5];//precio compra
                        fila[6] = lista.get(i)[6];//precio venta
                        fila[7] = lista.get(i)[7];//sctok
                        fila[8] = lista.get(i)[8];//estado
                        fila[9] = lista.get(i)[9];//medida
                        modelo.addRow(fila);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontro ningun producto..!!", null, JOptionPane.INFORMATION_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Ingrese un codigo a buscar..!!", null, JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e, null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtPrecioVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioVentaKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
//        if (c < '0' || c > '9') {
//            evt.consume();
//        }
        if (!Character.isDigit(evt.getKeyChar()) && evt.getKeyChar() != '.') {
            evt.consume();
        }
        if (evt.getKeyChar() == '.' && txtPrecioVenta.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPrecioVentaKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GrupoEstadoProducto;
    private javax.swing.JRadioButton RacInac;
    private javax.swing.JRadioButton RacPro;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JToggleButton btnEdicion;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRegPro;
    private javax.swing.JComboBox comboCategoria;
    private javax.swing.JComboBox comboFiltroCategoria;
    private javax.swing.JComboBox comboUnidades;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbmProducto;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTextField txtCodigoPro;
    private javax.swing.JTextField txtCodigoProdFind;
    private javax.swing.JTextArea txtDesPro;
    private javax.swing.JTextField txtImgProducto;
    private javax.swing.JTextField txtNomPro;
    private javax.swing.JTextField txtPrecioPro;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JSpinner txtStockMinimo;
    // End of variables declaration//GEN-END:variables

    public Producto getObjProducto() {
        return ObjProducto;
    }

    public void setObjProducto(Producto ObjProducto) {
        this.ObjProducto = ObjProducto;
    }
}
