/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.view;

import com.pos.core.Validaciones;
import com.pos.dao.almacenDao;
import com.pos.dao.coloresDao;

import com.pos.dao.productoDao;
import com.pos.pojos.Almacen;
import com.pos.pojos.Colores;
import com.pos.pojos.HistorialInventario;
import com.pos.pojos.Producto;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Reynaldo
 */
public class vtnAddProductoIngresoInventario extends javax.swing.JInternalFrame {

    /**
     * Creates new form vtnAddProductoIngresoInventario
     */
    public static String validaVentana;

    public vtnAddProductoIngresoInventario() {
        initComponents();
        validaVentana = "x";//insertando un valor a la variable que valida a la ventana
        /*Poniendo el JinternalFrame al centro de la ventana*/
        int a = vtnPrincipal.panelMDI.getWidth() - this.getWidth();
        int b = vtnPrincipal.panelMDI.getHeight() - this.getHeight();
        setLocation(a / 2, b / 2);
        cargarComboColores();
        cargarAlmacen();
        listarProductos();
        configurarSpinner();

    }

    public void configurarSpinner() {
        SpinnerNumberModel modelo = new SpinnerNumberModel();
        modelo.setMaximum(800);
        modelo.setMinimum(0);
        this.SpCantidad.setModel(modelo);

    }

    //Metodo que realiza el filtrado segun el typeo en el JtextField
    private void actualizarBusqueda() {
        try {
            productoDao pro = new productoDao();

            ArrayList<Producto> result = null;
            List<Object[]> result2 = null;

            if (String.valueOf(comboByProducto.getSelectedItem()).equalsIgnoreCase("Nombre")) {
                almacenDao almDao = new almacenDao();
                result2 = pro.buscarProductoFiltroBYNombreByAlmacen(txtProd.getText());
                recargarTable2(result2);
            } /* if (String.valueOf(comboByProducto.getSelectedItem()).equalsIgnoreCase("Codigo")) {
             almacenDao almDao = new almacenDao();
             result2 = pro.buscarProductoFiltroBYNombreByAlmacen(txtProd.getText());
             recargarTable2(result2);
             }*/ else {
                result = (ArrayList<Producto>) pro.listarProductos();
                recargarTable(result);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el listado de los productos" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //metodo que hace el refrescado de la tabla despues del filtro de datos 
    public void recargarTable(ArrayList<Producto> lista) {
        DefaultTableModel modelo = (DefaultTableModel) this.tablaAdicionarPro.getModel();//creando el modela ára llenar los datos al JTableje
        new Validaciones().limpiarTabla(tablaAdicionarPro);
        Object[] fila = new Object[modelo.getColumnCount()];
        if (lista.size() != 0) {
            for (int i = 0; i < lista.size(); i++) {
                fila[0] = lista.get(i).getIdProducto();//id
                fila[1] = lista.get(i).getNombre();//id
                //fila[2] = lista.get(i)[2];//id
                modelo.addRow(fila);
            }
        }
    }

    //metodo que hace el refrescado de la tabla despues del filtro de datos 
    public void recargarTable2(List<Object[]> lista) {

        DefaultTableModel modelo = (DefaultTableModel) this.tablaAdicionarPro.getModel();//creando el modela ára llenar los datos al JTableje
        new Validaciones().limpiarTabla(tablaAdicionarPro);
        Object[] fila = new Object[modelo.getColumnCount()];
        if (lista.size() != 0) {
            for (int i = 0; i < lista.size(); i++) {
                fila[0] = lista.get(i)[0];//id
                fila[1] = lista.get(i)[1];//Nombre

                modelo.addRow(fila);
            }
        }
    }

    public void listarProductos() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) this.tablaAdicionarPro.getModel();//creando el modela ára llenar los datos al JTableje
            new Validaciones().limpiarTabla(tablaAdicionarPro);
            //realizando la consulta para realizar el listado de los datos
            productoDao proDao = new productoDao();
            List<Object[]> lista = proDao.listarProductosProv();
            Object[] fila = new Object[modelo.getColumnCount()];

            if (lista.size() != 0) {
                for (int i = 0; i < lista.size(); i++) {
                    fila[0] = lista.get(i)[0];//id
                    fila[1] = lista.get(i)[1];//nombre

                    modelo.addRow(fila);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encuentran productos registrados..!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
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

        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaAdicionarPro = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        SpCantidad = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        comboColor = new javax.swing.JComboBox();
        btnAddProd = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtFecha = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        txtNnota = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        comboAlmacen = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtProd = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        comboByProducto = new javax.swing.JComboBox();

        jLabel6.setText("jLabel6");

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

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("INGRESO A ALMACEN");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/images/addproductos.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(287, 287, 287))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablaAdicionarPro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PRODUCTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaAdicionarPro);
        if (tablaAdicionarPro.getColumnModel().getColumnCount() > 0) {
            tablaAdicionarPro.getColumnModel().getColumn(0).setMinWidth(30);
            tablaAdicionarPro.getColumnModel().getColumn(0).setMaxWidth(30);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("Cantidad:");

        SpCantidad.setModel(new javax.swing.SpinnerNumberModel(1, 1, 5000, 1));
        SpCantidad.setToolTipText("Cantidad de piezas a ingresar al inventario.");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel5.setText("Color:");

        comboColor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---------" }));

        btnAddProd.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        btnAddProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/agregar.png"))); // NOI18N
        btnAddProd.setText("Ingresar producto");
        btnAddProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProdActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel8.setText("Fecha:");

        txtFecha.setToolTipText("Fecha de nota de envio.");

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel9.setText("Número de control:");

        txtNnota.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtNnota.setToolTipText("Ingrese el número de nota de envio.");
        txtNnota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNnotaKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel10.setText("Almacen:");

        comboAlmacen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-------" }));

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel12.setText("Observaciones:");

        txtObservaciones.setColumns(20);
        txtObservaciones.setRows(5);
        jScrollPane2.setViewportView(txtObservaciones);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SpCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNnota, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboColor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(comboAlmacen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(btnAddProd, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(102, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtNnota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(comboAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(SpCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 29, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel12)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddProd)
                        .addGap(65, 65, 65))))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtrar producto"));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel7.setText("Producto:");

        txtProd.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtProd.setToolTipText("Escriba el producto que desea encontrar en la tabla.");
        txtProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProdKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProdKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel11.setText("Filtrar por:");

        comboByProducto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProd, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboByProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(comboByProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void cargarComboColores() {
        coloresDao cdao = new coloresDao();
        try {
            //realizando la consulta para realizar el listado de los datos
            List<Colores> lista = cdao.listarColores();
            for (int i = 0; i < lista.size(); i++) {
                comboColor.addItem(lista.get(i).getNombre());
                // System.err.println(lista.get(i).getNombre());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e, null, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarAlmacen() {
        almacenDao adao = new almacenDao();
        try {
            //realizando la consulta para realizar el listado de los datos
            List<Almacen> lista = adao.listarAlmacen();
            for (int i = 0; i < lista.size(); i++) {
                comboAlmacen.addItem(lista.get(i).getNombre());
                //System.err.println(lista.get(i).getNombre());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e, null, JOptionPane.ERROR_MESSAGE);
        }
    }

    //Metodo que verifica si el item seleccionado esta repetido en la tabla
    public boolean buscarRepetidos(int id) {
        DefaultTableModel modelo = (DefaultTableModel) vtnIngresoAlmacen.tablaIngresoAlm.getModel();//recuperando la tabla OC
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (Integer.parseInt((String) vtnIngresoAlmacen.tablaIngresoAlm.getValueAt(i, 0)) == id) {
                return true;
            }
        }
        return false;
    }
    private void btnAddProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProdActionPerformed
        // TODO add your handling code here:

        try {
            if (tablaAdicionarPro.getSelectedRows().length != 0) {
                if (comboAlmacen.getSelectedIndex() != 0) {
                    if (comboColor.getSelectedIndex() != 0) {
                        if (txtNnota.getText().length() > 0) {
                            if (txtFecha.getDate() != null) {
                                if (Integer.parseInt(SpCantidad.getValue().toString()) > 0) {
                                    
                                     DefaultTableModel tm = (DefaultTableModel) tablaAdicionarPro.getModel();
                                     String idProducto = String.valueOf(tm.getValueAt(tablaAdicionarPro.getSelectedRow(), 0));
                                    /*
                                     //Fin del registro de hiatorial de inventario--------------------
                                     historialInventarioDao hdao = new historialInventarioDao();

                                     if (hdao.registrarHistorialInventario(hinventario)) {
                                     JOptionPane.showMessageDialog(this, "Ingreso de producto correcto..!!", "Mensaje..", JOptionPane.INFORMATION_MESSAGE);

                                     } 
                                     else
                                     {
                                     JOptionPane.showMessageDialog(null, "Error al ingresar los productos", "Mensaje", JOptionPane.ERROR_MESSAGE);
                                     }
                                     */

                                } else {
                                    JOptionPane.showMessageDialog(null, "La cantidad del producto no puede ser cero..!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Seleccione fecha..!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Ingrese el numero de control..!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Seleccione un color para el producto....!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un almacen....!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un producto..!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error-->" + e.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddProdActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        this.dispose();
        validaVentana = null;

    }//GEN-LAST:event_formInternalFrameClosing

    private void txtNnotaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNnotaKeyTyped
        // TODO add your handling code here:
        new Validaciones().validaNumeros(txtNnota);
    }//GEN-LAST:event_txtNnotaKeyTyped

    private void txtProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProdKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProdKeyTyped

    private void txtProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProdKeyReleased
        // TODO add your handling code here:
        actualizarBusqueda();
    }//GEN-LAST:event_txtProdKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JSpinner SpCantidad;
    public static javax.swing.JButton btnAddProd;
    private javax.swing.JComboBox comboAlmacen;
    private javax.swing.JComboBox comboByProducto;
    private javax.swing.JComboBox comboColor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaAdicionarPro;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtNnota;
    private javax.swing.JTextArea txtObservaciones;
    private javax.swing.JTextField txtProd;
    // End of variables declaration//GEN-END:variables
}
