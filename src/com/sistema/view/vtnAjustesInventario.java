/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.view;

import com.pos.core.Validaciones;
import com.pos.dao.InventarioDao;
import com.pos.dao.ajustesDao;
import com.pos.dao.almacenDao;
import com.pos.dao.coloresDao;
import com.pos.dao.productoDao;
import com.pos.pojos.Ajustes;
import com.pos.pojos.Almacen;
import com.pos.pojos.Colores;
import com.pos.pojos.Usuario;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Reynaldo
 */
public class vtnAjustesInventario extends javax.swing.JInternalFrame {

    /**
     * Creates new form vtnAjustesInventario
     */
    public static String validaVentana;
    private Usuario Objetousuario = new Usuario();
    //Variable global ID del producto a realizar el ajuste
    static int _idInventario = 0;
    //vaiables globales

    public vtnAjustesInventario() {
        initComponents();

        validaVentana = "x";//insertando un valor a la variable que valida a la ventana
        /*Poniendo el JinternalFrame al centro de la ventana*/
        int a = vtnPrincipal.panelMDI.getWidth() - this.getWidth();
        int b = vtnPrincipal.panelMDI.getHeight() - this.getHeight();
        setLocation(a / 2, b / 2);
        cargarAlmacen();
        //cargarComboColores();
        Objetousuario = vtnPrincipal.userlogin;
        listarAjustes();

        /*Valores de los Spinners*/
        //Spinner cantidad 
        SpinnerNumberModel nm = new SpinnerNumberModel();
        nm.setMaximum(100);
        nm.setMinimum(0);
        txtCantidad.setModel(nm);

        /////Spiner cantidad de ajuste
        SpinnerNumberModel spajuste = new SpinnerNumberModel();
        nm.setMaximum(150);
        nm.setMinimum(0);
        txtCanAjuste.setModel(spajuste);

        //Fin de los valores de los Spinners
    }
    /*
     public void cargarComboColores() {
     coloresDao cdao = new coloresDao();
     try {
     //realizando la consulta para realizar el listado de los datos
     List<Colores> lista = cdao.listarColores();
     for (int i = 0; i < lista.size(); i++) {
     comboAjusteColor.addItem(lista.get(i).getNombre());
     comboCantidadAjuste.addItem(lista.get(i).getNombre());
     // System.err.println(lista.get(i).getNombre());
     }
     } catch (Exception e) {
     JOptionPane.showMessageDialog(null, "Error " + e, null, JOptionPane.ERROR_MESSAGE);
     }
     }
     */

    //Metodo que carga los elementos del almacen

    public void cargarAlmacen() {
        try {
            almacenDao almDao = new almacenDao();
            List<Almacen> lista = almDao.listarAlmacen();
            if (lista.size() != 0) {
                for (int i = 0; i < lista.size(); i++) {
                    comboAlmacen.addItem(lista.get(i).getNombre());
                    comboAlmacenTabla.addItem(lista.get(i).getNombre());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe tener por lo menos un almacen registrado..!! ", null, JOptionPane.ERROR_MESSAGE);
                comboAlmacen.setEnabled(false);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error " + ex, null, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarCampos() {
        comboAlmacen.setSelectedIndex(0);
        txtAjusteProducto.setText("");
        txtdetalleajuste.setText("");
        txtCantidad.setValue(0);
        txtCanAjuste.setValue(0);

    }

    //Metodo que realiza el listado de los ajustes realizados al inventario
    public void listarAjustes() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) this.tablaAjustes.getModel();//creando el modela ára llenar los datos al JTableje
            Validaciones.limpiarTabla(tablaAjustes);
            //realizando la consulta para realizar el listado de los ajustes realizados
            ajustesDao proDao = new ajustesDao();
            List<Object[]> lista = proDao.listarAjustes();
            Object[] fila = new Object[modelo.getColumnCount()];

            for (int i = 0; i < lista.size(); i++) {
                fila[0] = lista.get(i)[0];//id
                fila[1] = lista.get(i)[1];//producto
                fila[2] = lista.get(i)[2];//cantidad original
                fila[3] = lista.get(i)[3];//cantidad ajuste
                fila[4] = lista.get(i)[4];//nombre responsable
                fila[5] = lista.get(i)[5];//apellido responsable
                fila[6] = lista.get(i)[6];//glosa
                fila[7] = lista.get(i)[7];//fecha
                fila[8] = lista.get(i)[8];//almacen

                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error--> " + e, null, JOptionPane.ERROR_MESSAGE);
        }
    }

    //Metodo que realiza el listado de los ajustes realizados al inventario
    public void listarAjustesByAlmacen() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) this.tablaAjustes.getModel();//creando el modela ára llenar los datos al JTableje
            Validaciones.limpiarTabla(tablaAjustes);
            //realizando la consulta para realizar el listado de los ajustes realizados
            ajustesDao proDao = new ajustesDao();
            List<Object[]> lista = proDao.listarAjustesByAlmacen(new almacenDao().buscarAlmacenId(comboAlmacenTabla.getSelectedItem().toString()));
            Object[] fila = new Object[modelo.getColumnCount()];

            for (int i = 0; i < lista.size(); i++) {
                fila[0] = lista.get(i)[0];//id
                fila[1] = lista.get(i)[1];//producto
                fila[2] = lista.get(i)[2];//cantidad original
                fila[3] = lista.get(i)[3];//cantidad ajuste
                fila[4] = lista.get(i)[4];//nombre responsable
                fila[5] = lista.get(i)[5];//apellido responsable
                fila[6] = lista.get(i)[6];//glosa
                fila[7] = lista.get(i)[7];//fecha
                fila[8] = lista.get(i)[8];//almacen

                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error--> " + e, null, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listarDatosKardex(int idAlmacen) {
        try {
            DefaultTableModel modelo = (DefaultTableModel) this.tablaProductoAjuste.getModel();//creando el modela ára llenar los datos al JTableje
            Validaciones.limpiarTabla(tablaProductoAjuste);
            //realizando la consulta para realizar el listado de los datos

            InventarioDao invDao = new InventarioDao();
            List<Object[]> lista = invDao.kardexInventarioAlmacen(idAlmacen);//obteniendo el listado del Kardex por Almacen
            Object[] fila = new Object[modelo.getColumnCount()];
            if (lista.size() > 0) {
                for (int i = 0; i < lista.size(); i++) {
                    fila[0] = lista.get(i)[0];//ID
                    fila[1] = lista.get(i)[1];//CODIGO
                    fila[2] = lista.get(i)[2];//NOMBRE
                    fila[3] = lista.get(i)[3];//STOCK
                    fila[4] = lista.get(i)[4];//COLOR

                    modelo.addRow(fila);
                }
                //estiloTabla(tablaProductoAjuste);
            } else {
                JOptionPane.showMessageDialog(null, "El almacen selecconado no tiene porductos..!!", "Mensaje..", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar los datos del Almacen " + e.getMessage(), "Mensaje..", JOptionPane.ERROR_MESSAGE);
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

        DialogoProductos = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProductoAjuste = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        comboAlmacen = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtAjusteProducto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        txtCanAjuste = new javax.swing.JSpinner();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtdetalleajuste = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaAjustes = new javax.swing.JTable();
        comboAlmacenTabla = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        DialogoProductos.setTitle("Seleccione producto..::..");
        DialogoProductos.setModal(true);

        tablaProductoAjuste.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "CODIGO", "PRODUCTO", "STOCK", "COLOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaProductoAjuste);
        if (tablaProductoAjuste.getColumnModel().getColumnCount() > 0) {
            tablaProductoAjuste.getColumnModel().getColumn(0).setMinWidth(50);
            tablaProductoAjuste.getColumnModel().getColumn(0).setPreferredWidth(50);
            tablaProductoAjuste.getColumnModel().getColumn(0).setMaxWidth(55);
        }

        jPanel6.setBackground(new java.awt.Color(11, 58, 126));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Seleccionar producto");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel10))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(jLabel8)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/agregar.png"))); // NOI18N
        jButton5.setText("Adicionar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DialogoProductosLayout = new javax.swing.GroupLayout(DialogoProductos.getContentPane());
        DialogoProductos.getContentPane().setLayout(DialogoProductosLayout);
        DialogoProductosLayout.setHorizontalGroup(
            DialogoProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(DialogoProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DialogoProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialogoProductosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5)))
                .addContainerGap())
        );
        DialogoProductosLayout.setVerticalGroup(
            DialogoProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogoProductosLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setTitle("Ajustes ..::..");
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

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Ajustes inventario");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/historyFile.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(220, 220, 220)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(23, 23, 23))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("Seleccione almacen:");

        comboAlmacen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comboAlmacen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "********" }));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel5.setText("Buscar Producto:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/buscar.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de ajuste", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 14))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel7.setText("Producto:");

        txtAjusteProducto.setEditable(false);
        txtAjusteProducto.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel6.setText("Detalle:");

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel9.setText("Cantidad:");

        txtCantidad.setModel(new javax.swing.SpinnerNumberModel(0, 0, 500000, 1));
        txtCantidad.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel12.setText("Cantidad ajuste:");

        txtCanAjuste.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        txtCanAjuste.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100000, 1));
        txtCanAjuste.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCanAjusteFocusLost(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton2.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/guardar.png"))); // NOI18N
        jButton2.setText("Realizar Ajuste");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/cancelar.png"))); // NOI18N
        jButton3.setText("Cancelar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jButton2)
                .addGap(39, 39, 39)
                .addComponent(jButton3)
                .addContainerGap(124, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3))
                .addGap(26, 26, 26))
        );

        txtdetalleajuste.setColumns(20);
        txtdetalleajuste.setRows(5);
        jScrollPane3.setViewportView(txtdetalleajuste);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAjusteProducto))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(txtCanAjuste, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAjusteProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtCanAjuste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(38, 38, 38)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(comboAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("LISTADO DE AJUSTES DE INVENTARIO"));

        tablaAjustes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PRODUCTO", "CANT. ORIGINAL", "CANT. AJUSTE", "NOMBRE", "APELLIDO", "GLOSA", "FECHA", "ALMACEN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaAjustes);
        if (tablaAjustes.getColumnModel().getColumnCount() > 0) {
            tablaAjustes.getColumnModel().getColumn(0).setMinWidth(45);
            tablaAjustes.getColumnModel().getColumn(0).setPreferredWidth(40);
            tablaAjustes.getColumnModel().getColumn(0).setMaxWidth(50);
            tablaAjustes.getColumnModel().getColumn(2).setMinWidth(115);
            tablaAjustes.getColumnModel().getColumn(2).setPreferredWidth(115);
            tablaAjustes.getColumnModel().getColumn(2).setMaxWidth(120);
            tablaAjustes.getColumnModel().getColumn(3).setMinWidth(110);
            tablaAjustes.getColumnModel().getColumn(3).setPreferredWidth(110);
            tablaAjustes.getColumnModel().getColumn(3).setMaxWidth(115);
        }

        comboAlmacenTabla.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*******" }));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel11.setText("Seleccione Almacen:");

        jButton6.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/buscar.png"))); // NOI18N
        jButton6.setText("Buscar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/print.png"))); // NOI18N
        jButton7.setText("Imprimir");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1111, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(comboAlmacenTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton7)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboAlmacenTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        this.dispose();
        validaVentana = null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            if (comboAlmacen.getSelectedIndex() > 0) {
                DialogoProductos.setSize(600, 350);
                DialogoProductos.setLocation(400, 400);

                listarDatosKardex(new almacenDao().buscarAlmacenId(comboAlmacen.getSelectedItem().toString()));
                DialogoProductos.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un Almacen..!!", "Mensaje..", JOptionPane.ERROR_MESSAGE);
            }
            /* try
             {
             if (comboAlmacen.getSelectedIndex() > 0)
             {
             String varValicadion = vtnProductoAjuste.validaVentana;//recuperando el valor de la variable de validacion de la ventana
             if (varValicadion == null)
             {
             AlmacenDao almDao = new AlmacenDao();
             int idAlm = almDao.buscarAlmacenId(comboAlmacen.getSelectedItem().toString());
            
             vtnProductoAjuste listaPro = new vtnProductoAjuste(idAlm);
            
             vtnPrincipal.sysMDI.add(listaPro);
             listaPro.setClosable(true);//si se puede cerra la ventana
             listaPro.setTitle("Seleccione un producto");
             s             //listaPro.setLocationRelativeTo(null);
             listaPro.setVisible(true);
             }
             else
             {
             JOptionPane.showMessageDialog(this, "La ventana de adicionar productos ya esta activa..!!", "Mensaje..", JOptionPane.ERROR_MESSAGE);
             }        // TODO add your handling code here:
             } else {
             JOptionPane.showMessageDialog(this, "Seleccione un Almacen..!!", "Mensaje..", JOptionPane.ERROR_MESSAGE);
             }
             } catch (Exception ex) {
             JOptionPane.showMessageDialog(this, "Error inesperado.." + ex.getMessage(), "Mensaje..", JOptionPane.ERROR_MESSAGE);
             }*/
        } catch (Exception ex) {
            Logger.getLogger(vtnAjustesInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try {
            //Valida el numero de envio
            if (txtdetalleajuste.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Ingrese el detalle asociado al ajuste..!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
                txtdetalleajuste.requestFocusInWindow();
                return;
            }

            //Valida la cantidad 
            if (Integer.parseInt(txtCanAjuste.getValue().toString()) == Integer.parseInt(txtCantidad.getValue().toString())) {
                JOptionPane.showMessageDialog(null, "La cantidad de ajuste no puede ser igual a la cantidad original..!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
                txtCanAjuste.requestFocusInWindow();
                return;
            }

            Ajustes ajuste = new Ajustes();
            ajuste.setIdProducto(new productoDao().buscarProductoByNombre(txtAjusteProducto.getText()));//idProducto
            ajuste.setCantidadOriginal(txtCantidad.getValue().toString());//cantidad original
            ajuste.setCantidadAjuste(txtCanAjuste.getValue().toString());//cantidad ajuste
            ajuste.setIdUsuario(Objetousuario.getIdUsuario());//idusuario
            ajuste.setDetalle(txtdetalleajuste.getText());//detalle ajuste

            //recuperando la fecha de registro del ajuste
            Date fecha = new Date();
            Date sqldate = new java.sql.Date(fecha.getTime());
            ajuste.setFecha(sqldate);

            ajuste.setIdAlmacen(new almacenDao().buscarAlmacenId(comboAlmacen.getSelectedItem().toString()));//idAlmacen

            ajustesDao ajDao = new ajustesDao();
            

            if (ajDao.registarAjuste(ajuste) && ajDao.realizarAjusteInventario((Integer) txtCanAjuste.getValue(),  _idInventario)) {
                JOptionPane.showMessageDialog(null, "Ajuste realizado correctamente..!!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                listarAjustes();
                limpiarCampos();
            }

            //fin del registro del ajuste
           /*
             //actualizando el inventario de acuerdo al ajuste
             InventarioDao invDao = new InventarioDao();
             Inventario inv = invDao.existeProducto(alm.buscarAlmacenId(comboAlmacen.getSelectedItem().toString()), pr.buscarProductoByNombre(txtAjusteProducto.getText()));
             //JOptionPane.showMessageDialog(this, "-->"+inv.getIdinventario());
             Operaciones.actualizaStockInventario(inv.getIdinventario(), String.valueOf(txtCanAjuste.getValue()));
             Operaciones.actualizaPrecioProducto(pr.buscarProductoByNombre(txtAjusteProducto.getText()), txtPrecioAj.getText());
             //fin de la actualizacion del inventario

             //realizando la insercion el el kardex fifo
             Fifo fifo = new Fifo();
             fifo.setDocumento("INVENTARIO INICIAL");
             fifo.setInventario(inv);
             //JOptionPane.showMessageDialog(this, inv.getIdinventario());
             fifo.setNdocumento(0);
             fifo.setDetalle(txtdetalleajuste.getText());
             fifo.setCant_entrada((int) txtCanAjuste.getValue());
             fifo.setPrecio_entrada(txtPrecioAj.getText());
             fifo.setP_total_entrada(txtTotalAj.getText());//precio total

             //------------saldos---------
             fifo.setCant_saldo((int) txtCanAjuste.getValue());
             fifo.setPrecio_saldo(txtPrecioAj.getText());
             fifo.setP_total_saldo(txtTotalAj.getText());//precio total
             //----------------------------
             Operaciones.ingresoFifoAjuste(fifo);//ingresando el registro del ajuste al Kardex del Fifo
             //fin de la insercion del kardex fifo
             JOptionPane.showMessageDialog(this, "Ajuste realizado correctamente..!!");
             this.dispose();
             validaVentana = null;
             */
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado.." + ex.getMessage(), "Mensaje..", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        validaVentana = null;
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtCanAjusteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCanAjusteFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_txtCanAjusteFocusLost

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        try {

            if (tablaProductoAjuste.getSelectedRows().length != 0) {
                // JOptionPane.showMessageDialog(this, "entro");
                txtAjusteProducto.setText(String.valueOf(tablaProductoAjuste.getValueAt(tablaProductoAjuste.getSelectedRow(), 2)));
                String stock = tablaProductoAjuste.getValueAt(tablaProductoAjuste.getSelectedRow(), 3).toString();
                // JOptionPane.showMessageDialog(null, "Stock recuperado" + stock, "Mensaje..", JOptionPane.WARNING_MESSAGE);
                txtCantidad.setValue(Integer.parseInt(stock));
                coloresDao cDao = new coloresDao();
                String color = tablaProductoAjuste.getValueAt(tablaProductoAjuste.getSelectedRow(), 4).toString();

                _idInventario = Integer.parseInt(tablaProductoAjuste.getValueAt(tablaProductoAjuste.getSelectedRow(), 0).toString());

                DialogoProductos.dispose();

                //validaVentana = null;
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un producto..!!", "Mensaje..", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la operación.!!" + ex.getMessage(), "Mensaje..", JOptionPane.WARNING_MESSAGE);

        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        try {

            if (comboAlmacenTabla.getSelectedIndex() > 0) {
                almacenDao aDao = new almacenDao();
                ajustesDao ajDao = new ajustesDao();
                int idAlmacen = aDao.buscarAlmacenId(comboAlmacenTabla.getSelectedItem().toString());
                listarAjustesByAlmacen();

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un almacen..!!", "Mensaje..", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la operación.!!" + ex.getMessage(), "Mensaje..", JOptionPane.WARNING_MESSAGE);

        }
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog DialogoProductos;
    private javax.swing.JComboBox comboAlmacen;
    private javax.swing.JComboBox comboAlmacenTabla;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tablaAjustes;
    private javax.swing.JTable tablaProductoAjuste;
    public static javax.swing.JTextField txtAjusteProducto;
    private javax.swing.JSpinner txtCanAjuste;
    public static javax.swing.JSpinner txtCantidad;
    private javax.swing.JTextArea txtdetalleajuste;
    // End of variables declaration//GEN-END:variables
}
