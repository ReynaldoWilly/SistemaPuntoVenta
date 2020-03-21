/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.view;

import com.pos.core.Validaciones;
import com.pos.dao.ComboDao;
import com.pos.dao.almacenDao;
import com.pos.dao.productoDao;
import com.pos.pojos.Almacen;
import com.pos.pojos.Producto;
import com.pos.pojos.Usuario;
import static com.sistema.view.vtnListaPrecios.tablaComboProducto;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Reynaldo
 */
public class vtnVentas extends javax.swing.JInternalFrame {

    /**
     * Creates new form vtnVentas
     */
    public static String validaVentana;//variable que realiza la validacion de ventana abierta
    public static Usuario userlogin;// Clase que contiene al usuario logueado en el sistema

    public vtnVentas() {
        initComponents();

        validaVentana = "x";//insertando un valor a la variable que valida a la ventana

        /*Poniendo el JinternalFrame al centro de la ventana*/
        int a = vtnPrincipal.panelMDI.getWidth() - this.getWidth();
        int b = vtnPrincipal.panelMDI.getHeight() - this.getHeight();
        setLocation(a / 2, b / 2);

        //recuperando el usuario que realizo el login de usuario
        userlogin = VtnLogin.user;//recibiendo el objeto usuario  de la ventana de logueqo
        txtVendedor.setText(userlogin.getNombres() + " " + userlogin.getApellidos());
        txtVendedor.setEnabled(false);

        /*fin-----*/
        cargarAlmacen();
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
//metodo que hace el refrescado de la tabla despues del filtro de datos 

    public void recargarTable2(List<Object[]> lista) {

        DefaultTableModel modelo = (DefaultTableModel) this.tablaProductos.getModel();//creando el modela ára llenar los datos al JTableje
        new Validaciones().limpiarTabla(tablaProductos);
        Object[] fila = new Object[modelo.getColumnCount()];
        if (lista.size() != 0) {
            for (int i = 0; i < lista.size(); i++) {
                fila[0] = lista.get(i)[0];//id
                fila[1] = lista.get(i)[1];//Nombre

                modelo.addRow(fila);
            }
        }
    }

    //Metodo que realiza el filtrado segun el typeo en el JtextField
    private void actualizarBusqueda() {
        try {
            productoDao pro = new productoDao();

            ArrayList<Producto> result = null;
            List<Object[]> result2 = null;

            result2 = pro.buscarProductoFiltroBYNombreByAlmacen(txtNombreProducto.getText());
            recargarTable2(result2);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el listado de los productos" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Metodo que realiza la suma del subtotal de la compra
    public void generarSubTotal() {
        // int filas = this.tablaVenta.getRowCount();//recuperando la cantidad de filas de la tabla
        //JOptionPane.showMessageDialog(this, "NUmero de filas recuperadas de la tabla" + filas, "Error", JOptionPane.ERROR_MESSAGE);
        try {
            double subtotal = 0.0;
            double valor = 0.0;
            for (int i = 0; i < tablaVenta.getRowCount(); ++i) {

                valor = Double.parseDouble(String.valueOf(tablaVenta.getValueAt(i, 4)));//precio producto
                subtotal = subtotal + valor;
            }
            txtSubTotal.setText(String.valueOf(Validaciones.redondear(subtotal)));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR EN EL CALCULO DEL SUBTOTAL" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Metodo que realiza el listado de los datos en un JTable de java

    public void listarDatosProductos() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) this.tablaProductos.getModel();//creando el modela ára llenar los datos al JTableje
            new Validaciones().limpiarTabla(tablaProductos);
            //realizando la consulta para realizar el listado de los datos
            ComboDao proDao = new ComboDao();
            List<Object[]> lista = proDao.listarProductos();
            Object[] fila = new Object[modelo.getColumnCount()];

            for (int i = 0; i < lista.size(); i++) {
                fila[0] = lista.get(i)[0];//id
                fila[1] = lista.get(i)[1];//nombre
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e, null, JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean buscarRepetidos(int id) {
        DefaultTableModel modelo = (DefaultTableModel) vtnVentas.tablaVenta.getModel();//recuperando la tabla OC
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (Integer.parseInt((String) tablaVenta.getValueAt(i, 0)) == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DialogoProductoVentas = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        SpCantidad = new javax.swing.JSpinner();
        jButton7 = new javax.swing.JButton();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel12 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        txtFechaVenta1 = new com.toedter.calendar.JDateChooser();
        jLabel29 = new javax.swing.JLabel();
        txtVendedor1 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txtNuventa1 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        txtCliente1 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        txtNit1 = new javax.swing.JTextField();
        comboPago1 = new javax.swing.JComboBox();
        jLabel35 = new javax.swing.JLabel();
        txtNuCuotas1 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtMontoCuota1 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        txtTotal1 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        comboAlmacen1 = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaVenta1 = new javax.swing.JTable();
        jLabel41 = new javax.swing.JLabel();
        txtSubTotal1 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtFechaVenta = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        txtVendedor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNuventa = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtNit = new javax.swing.JTextField();
        comboPago = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        txtNuCuotas = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtMontoCuota = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        comboAlmacen = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaVenta = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();

        DialogoProductoVentas.setTitle("Seleccione producto..::..");
        DialogoProductoVentas.setModal(true);

        jPanel8.setBackground(new java.awt.Color(11, 58, 126));

        jLabel18.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Seleccionar producto");

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/producto.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel19)
                .addContainerGap(657, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addGap(251, 251, 251))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel22.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel22.setText("Nombre producto:");

        txtNombreProducto.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtNombreProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreProductoKeyReleased(evt);
            }
        });

        jScrollPane3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablaProductos.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "ID", "NOMBRE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tablaProductos);
        if (tablaProductos.getColumnModel().getColumnCount() > 0) {
            tablaProductos.getColumnModel().getColumn(0).setMinWidth(25);
            tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(25);
            tablaProductos.getColumnModel().getColumn(0).setMaxWidth(40);
            tablaProductos.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel23.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel23.setText("Cantidad:");

        jButton7.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/agregar.png"))); // NOI18N
        jButton7.setText("Adicionar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jInternalFrame1.setTitle("Ventas..::..::..");
        jInternalFrame1.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                jInternalFrame1formInternalFrameClosing(evt);
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

        jPanel12.setBackground(new java.awt.Color(11, 58, 126));

        jLabel26.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("VENTAS");

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/images/ventas2_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addGap(319, 319, 319)
                .addComponent(jLabel27)
                .addGap(18, 18, 18)
                .addComponent(jLabel26)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addContainerGap())))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel26)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel28.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel28.setText("Fecha:");

        txtFechaVenta1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel29.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel29.setText("Vendedor:");

        txtVendedor1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel30.setText("Nro. de venta:");

        txtNuventa1.setEditable(false);
        txtNuventa1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel31.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel31.setText("Tipo documento:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---------------------", "Nota de venta", "Factura" }));

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel32.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel32.setText("Cliente:");

        txtCliente1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        jLabel33.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel33.setText("Tipo pago:");

        jLabel34.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel34.setText("NIT/CI:");

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/Search-icon.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        txtNit1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        comboPago1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        comboPago1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Contado", "Credito" }));
        comboPago1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPago1ActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel35.setText("Nro. Cuotas:");

        txtNuCuotas1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtNuCuotas1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel36.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel36.setText("Monto X Cuota:");

        txtMontoCuota1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtMontoCuota1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMontoCuota1.setText("0.0");

        jLabel37.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel37.setText("Seleccionar producto:");

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/agregar.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel38.setText("Total:");

        txtTotal1.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        txtTotal1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotal1.setText("0.0");

        jLabel39.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel39.setText("Seleccionar Combo:");

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/agregar.png"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboPago1, 0, 201, Short.MAX_VALUE)
                            .addComponent(txtCliente1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNit1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNuCuotas1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMontoCuota1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton10)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel32)
                        .addComponent(txtCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel34)
                        .addComponent(txtNit1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton8))
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(comboPago1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(txtNuCuotas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(txtMontoCuota1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(txtTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton10)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGap(9, 9, 9)
                                    .addComponent(jLabel39))))))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel40.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel40.setText("Almacen:");

        comboAlmacen1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        comboAlmacen1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---------------------" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtVendedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboAlmacen1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtFechaVenta1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNuventa1)
                            .addComponent(jComboBox2, 0, 142, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaVenta1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel30)
                        .addComponent(txtNuventa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel29)
                        .addComponent(txtVendedor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel40)
                        .addComponent(comboAlmacen1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel31)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton11.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/guardar.png"))); // NOI18N
        jButton11.setText("Generar venta");

        jButton12.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/revert_32.png"))); // NOI18N
        jButton12.setText("Restablecer");

        jButton13.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/salir.png"))); // NOI18N
        jButton13.setText("Salir");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton12)
                .addGap(18, 18, 18)
                .addComponent(jButton13)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        tablaVenta1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tablaVenta1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PRODUCTO", "CANT.", "PRECIO U.", "PRECIO TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tablaVenta1);
        if (tablaVenta1.getColumnModel().getColumnCount() > 0) {
            tablaVenta1.getColumnModel().getColumn(0).setMinWidth(30);
            tablaVenta1.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablaVenta1.getColumnModel().getColumn(0).setMaxWidth(30);
            tablaVenta1.getColumnModel().getColumn(2).setMinWidth(50);
            tablaVenta1.getColumnModel().getColumn(2).setPreferredWidth(50);
            tablaVenta1.getColumnModel().getColumn(2).setMaxWidth(50);
            tablaVenta1.getColumnModel().getColumn(3).setMinWidth(90);
            tablaVenta1.getColumnModel().getColumn(3).setPreferredWidth(90);
            tablaVenta1.getColumnModel().getColumn(3).setMaxWidth(90);
            tablaVenta1.getColumnModel().getColumn(4).setMinWidth(150);
            tablaVenta1.getColumnModel().getColumn(4).setPreferredWidth(150);
            tablaVenta1.getColumnModel().getColumn(4).setMaxWidth(150);
        }

        jLabel41.setText("SubTotal:");

        txtSubTotal1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtSubTotal1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSubTotal1.setText("0.0");

        jLabel42.setText("IVA%:");

        jTextField11.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTextField11.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel43.setText("Total a Pagar:");

        jTextField12.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTextField12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField12.setText("0.0");

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane4)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSubTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txtSubTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout DialogoProductoVentasLayout = new javax.swing.GroupLayout(DialogoProductoVentas.getContentPane());
        DialogoProductoVentas.getContentPane().setLayout(DialogoProductoVentasLayout);
        DialogoProductoVentasLayout.setHorizontalGroup(
            DialogoProductoVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialogoProductoVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DialogoProductoVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialogoProductoVentasLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(46, 46, 46)
                        .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(187, 187, 187))
                    .addGroup(DialogoProductoVentasLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SpCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7)))
                .addContainerGap())
            .addGroup(DialogoProductoVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(DialogoProductoVentasLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        DialogoProductoVentasLayout.setVerticalGroup(
            DialogoProductoVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogoProductoVentasLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(DialogoProductoVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(DialogoProductoVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DialogoProductoVentasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DialogoProductoVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(SpCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DialogoProductoVentasLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButton7)))
                .addGap(0, 14, Short.MAX_VALUE))
            .addGroup(DialogoProductoVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(DialogoProductoVentasLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setTitle("Ventas..::..::..");
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

        jPanel11.setBackground(new java.awt.Color(11, 58, 126));

        jLabel21.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("VENTAS");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/images/ventas2_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addGap(319, 319, 319)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap())))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel21)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel2.setText("Fecha:");

        txtFechaVenta.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel3.setText("Vendedor:");

        txtVendedor.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Nro. de venta:");

        txtNuventa.setEditable(false);
        txtNuventa.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel5.setText("Tipo documento:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---------------------", "Nota de venta", "Factura" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel6.setText("Cliente:");

        txtCliente.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel7.setText("Tipo pago:");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel8.setText("NIT/CI:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/Search-icon.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtNit.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        comboPago.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        comboPago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Contado", "Credito" }));
        comboPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPagoActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel9.setText("Nro. Cuotas:");

        txtNuCuotas.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtNuCuotas.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel10.setText("Monto X Cuota:");

        txtMontoCuota.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtMontoCuota.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMontoCuota.setText("0.0");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel11.setText("Seleccionar producto:");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/agregar.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel13.setText("Total:");

        txtTotal.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotal.setText("0.0");

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel12.setText("Seleccionar Combo:");

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/agregar.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboPago, 0, 201, Short.MAX_VALUE)
                            .addComponent(txtCliente))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNuCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMontoCuota, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(comboPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtNuCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtMontoCuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton4)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(9, 9, 9)
                                    .addComponent(jLabel12))))))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel17.setText("Almacen:");

        comboAlmacen.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        comboAlmacen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---------------------" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtFechaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNuventa)
                            .addComponent(jComboBox1, 0, 142, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtNuventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)
                        .addComponent(comboAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton2.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/guardar.png"))); // NOI18N
        jButton2.setText("Generar venta");

        jButton5.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/revert_32.png"))); // NOI18N
        jButton5.setText("Restablecer");

        jButton6.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/salir.png"))); // NOI18N
        jButton6.setText("Salir");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        tablaVenta.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tablaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PRODUCTO", "CANT.", "PRECIO U.", "PRECIO TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaVenta);
        if (tablaVenta.getColumnModel().getColumnCount() > 0) {
            tablaVenta.getColumnModel().getColumn(0).setMinWidth(30);
            tablaVenta.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablaVenta.getColumnModel().getColumn(0).setMaxWidth(30);
            tablaVenta.getColumnModel().getColumn(2).setMinWidth(50);
            tablaVenta.getColumnModel().getColumn(2).setPreferredWidth(50);
            tablaVenta.getColumnModel().getColumn(2).setMaxWidth(50);
            tablaVenta.getColumnModel().getColumn(3).setMinWidth(90);
            tablaVenta.getColumnModel().getColumn(3).setPreferredWidth(90);
            tablaVenta.getColumnModel().getColumn(3).setMaxWidth(90);
            tablaVenta.getColumnModel().getColumn(4).setMinWidth(150);
            tablaVenta.getColumnModel().getColumn(4).setPreferredWidth(150);
            tablaVenta.getColumnModel().getColumn(4).setMaxWidth(150);
        }

        jLabel14.setText("SubTotal:");

        txtSubTotal.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtSubTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSubTotal.setText("0.0");

        jLabel15.setText("IVA%:");

        jTextField9.setEditable(false);
        jTextField9.setBackground(new java.awt.Color(204, 204, 255));
        jTextField9.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTextField9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField9.setToolTipText("Porcentaje de IVA");

        jLabel16.setText("Total a Pagar:");

        jTextField10.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTextField10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField10.setText("0.0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        this.dispose();
        validaVentana = null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
       /* ClienteDao cdao = new ClienteDao();
         Cliente cli;
         try {
         if (txtNit.getText().length() > 0) {
         cli = cdao.buscarCliente(Integer.parseInt(txtNit.getText()));
         if (cli != null) {
         txtCliente.setText(cli.getNombre());
         } else {
         JOptionPane.showMessageDialog(this, "Cliente no registrado");
         txtCliente.setText("");
         }
         } else {
         JOptionPane.showMessageDialog(this, "Ingrese el CI o NIT del cliente", "Mensaje", JOptionPane.WARNING_MESSAGE);
         }
         } catch (Exception ex) {
         Logger.getLogger(vtnVentas.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }//GEN-LAST:event_jButton1ActionPerformed

    private void comboPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPagoActionPerformed
        // TODO add your handling code here:
       /* if (String.valueOf(comboPago.getSelectedItem()).equalsIgnoreCase("Contado")) {
         bloquearCamposCredito();
         } else {
         habilitarCamposCredito();
         }*/
    }//GEN-LAST:event_comboPagoActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
            DialogoProductoVentas.setSize(700, 400);
            DialogoProductoVentas.setLocation(550, 400);
            listarDatosProductos();
            DialogoProductoVentas.setVisible(true);

        } catch (Exception ex) {
            Logger.getLogger(vtnAjustesInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        // vtnCombo vc=new vtnCombo(null, closable);
        // vc.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        this.dispose();

    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtNombreProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProductoKeyReleased
        // TODO add your handling code here:
        actualizarBusqueda();
    }//GEN-LAST:event_txtNombreProductoKeyReleased

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        try {
            productoDao pdao = new productoDao();

            if (tablaProductos.getSelectedRows().length != 0) {
                DefaultTableModel modelo = (DefaultTableModel) vtnVentas.tablaVenta.getModel();//creando el modelo pára llenar los datos al JTabble
                //realizando la consulta para realizar el listado de los datos
                Object[] fila = new Object[modelo.getColumnCount()];
                int cant = Integer.parseInt(SpCantidad.getValue().toString());//recuperando la cantidad
                int idprod = Integer.parseInt(String.valueOf(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 0)));//ID PRODUCTO

                if (cant > 0) {
                    int idbuscar = Integer.parseInt(String.valueOf(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 0)));

                    if (!buscarRepetidos(idbuscar)) {
                        fila[0] = String.valueOf(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 0));//ID PRODUCTO
                        fila[1] = String.valueOf(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 1));//productov
                        fila[2] = cant;//cantidad
                        double precio = Double.parseDouble(pdao.recuperarPrecioProducto(idprod));
                        fila[3] = String.valueOf(precio); //precio prducto

                        double subtotal = cant * precio;

                        fila[4] = String.valueOf(subtotal);

                        //JOptionPane.showMessageDialog(null,subtotal, "Mensaje...", JOptionPane.ERROR_MESSAGE);
                        modelo.addRow(fila);//adicionando la fila a la tabla
                        generarSubTotal();
                        DialogoProductoVentas.dispose();

                        SpCantidad.setValue(0);

                    } else {
                        JOptionPane.showMessageDialog(null, "El producto ya fue añadido..!!", "Mensaje...", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La cantidad del producto no puede ser 0 ", "Mensaje..", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un producto...!!", "Mensaje..", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e.getMessage(), "Mensaje..", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void comboPago1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPago1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPago1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jInternalFrame1formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_jInternalFrame1formInternalFrameClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_jInternalFrame1formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog DialogoProductoVentas;
    private javax.swing.JSpinner SpCantidad;
    public static javax.swing.JComboBox comboAlmacen;
    public static javax.swing.JComboBox comboAlmacen1;
    private javax.swing.JComboBox comboPago;
    private javax.swing.JComboBox comboPago1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTable tablaProductos;
    public static javax.swing.JTable tablaVenta;
    public static javax.swing.JTable tablaVenta1;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtCliente1;
    private com.toedter.calendar.JDateChooser txtFechaVenta;
    private com.toedter.calendar.JDateChooser txtFechaVenta1;
    private javax.swing.JTextField txtMontoCuota;
    private javax.swing.JTextField txtMontoCuota1;
    private javax.swing.JTextField txtNit;
    private javax.swing.JTextField txtNit1;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtNuCuotas;
    private javax.swing.JTextField txtNuCuotas1;
    private javax.swing.JTextField txtNuventa;
    private javax.swing.JTextField txtNuventa1;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtSubTotal1;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotal1;
    private javax.swing.JTextField txtVendedor;
    private javax.swing.JTextField txtVendedor1;
    // End of variables declaration//GEN-END:variables
}
