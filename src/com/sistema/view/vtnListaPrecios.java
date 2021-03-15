/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.view;

import com.pos.core.Validaciones;
import com.pos.dao.ComboDao;
import com.pos.dao.productoDao;
import com.pos.pojos.ComboProducto;
import com.pos.pojos.DetalleCombo;
import com.pos.pojos.Producto;
import com.pos.tabla.render.HeaderCellRenderer;
import com.pos.tabla.render.RenderTabla;
import com.pos.util.Conexion;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Reynaldo
 */
public class vtnListaPrecios extends javax.swing.JInternalFrame {

    /**
     * Creates new form vtnListaPrecios
     */
    public static String validaVentana;
    public SpinnerNumberModel nm = new SpinnerNumberModel();
    private ComboProducto objCombo = new ComboProducto();
    DetalleCombo objDetalleCombo = new DetalleCombo();

    public vtnListaPrecios() {
        initComponents();
        validaVentana = "x";//insertando un valor a la variable que valida a la ventana
        /*Poniendo el JinternalFrame al centro de la ventana*/
        int a = vtnPrincipal.panelMDI.getWidth() - this.getWidth();
        int b = vtnPrincipal.panelMDI.getHeight() - this.getHeight();
        setLocation(a / 2, b / 2);
        nm.setMaximum(100);
        nm.setMinimum(0);
        SpCantidad.setModel(nm);
        listarCombos();

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

    public void mejorarAparienciaTabla() {
        tablaCombo.setDefaultRenderer(Object.class, new RenderTabla());//renderizando la tabla
        tablaCombo.setRowHeight(30);
        JTableHeader header = tablaCombo.getTableHeader();
        header.setDefaultRenderer(new HeaderCellRenderer());
        tablaCombo.setSelectionBackground(new Color(231, 247, 252));
        tablaCombo.setSelectionForeground(new Color(0, 0, 0));
        tablaCombo.setGridColor(new java.awt.Color(221, 221, 221));
    }

    //Metodo que realiza el listado de los combos registrados en el sistema 
    public void listarCombos() {
        try {

            DefaultTableModel modelo = (DefaultTableModel) this.tablaCombo.getModel();

            new Validaciones().limpiarTabla(tablaCombo);
            Object[] fila = new Object[modelo.getColumnCount()];
            ComboDao cdao = new ComboDao();

            JButton btnA = new JButton("Actualizar");
            btnA.setName("a");
            JButton btnE = new JButton("Eliminar");
            btnE.setName("e");
            List<Object[]> lista = cdao.listarCombos();
            String estado = "";
            if (lista.size() != 0) {
                for (int i = 0; i < lista.size(); i++) {
                    fila[0] = lista.get(i)[0];//idCombo
                    fila[1] = lista.get(i)[1];//Nombre combo
                    fila[2] = lista.get(i)[2];//codigo combo
                    fila[3] = lista.get(i)[3];//precio codigo
                    estado = (String) lista.get(i)[4];
                    if (estado.equals("Activo")) {
                        fila[4] = true;
                    } else {
                        fila[4] = false;
                    }
                    fila[5] = btnA;//precio codigo
                    fila[6] = btnE;//estado precio
                    modelo.addRow(fila);

                }
                mejorarAparienciaTabla();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al realizar el listado de los combos..!" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }
    
    

    //Metodo que actualiza el combo
    public boolean actualizarCombo(int idCombo, String nombreCombo, String precioCombo, String estado) {
        try {

            String sqlCombo = "UPDATE comboproducto SET nombreCombo=\"" + nombreCombo + "\",estado=\"" + estado + "\",precioCombo=" + precioCombo + " WHERE idComboProducto=" + idCombo;
            System.out.print(sqlCombo);
            Connection con2 = Conexion.getConectar();

            Statement stm2 = con2.createStatement();

            stm2.execute(sqlCombo);

            stm2.close();

            con2.close();
            return true;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "A ocurrido un error al actualizar el combo..!" + ex.getMessage(), "Mensaje..::..", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //Metodo que realiza la eliminacion del combo seleccionado
    public boolean eliminarCOmbo(int idCombo) {
        try {
            String sqlDetalleCombo = "DELETE FROM detallecombo WHERE idComboProducto=" + idCombo;
            String sqlCombo = "DELETE FROM comboproducto WHERE idComboProducto=" + idCombo;

            Connection con1 = Conexion.getConectar();
            Connection con2 = Conexion.getConectar();

            Statement stm1 = con1.createStatement();
            Statement stm2 = con2.createStatement();

            stm1.execute(sqlDetalleCombo);

            stm2.execute(sqlCombo);

            stm1.close();
            stm2.close();

            con1.close();
            con2.close();
            return true;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "A ocurrido un error al eliminar el combo..!" + ex.getMessage(), "Mensaje..::..", JOptionPane.ERROR_MESSAGE);
            return false;
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

    public void limpiarTablaCombo() {
        limpiarTabla(tablaCombo);
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

    /**
     *
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DialogoExaminarProductos = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        txtNombreProducto = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        SpCantidad = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCodigoCombo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNombreCombo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        comboEstado = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaComboProducto = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtPrecioComboProd = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnRes = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaCombo = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDetalleCombo = new javax.swing.JTable();

        DialogoExaminarProductos.setTitle("Seleccionar producto..");
        DialogoExaminarProductos.setModal(true);

        jPanel8.setBackground(new java.awt.Color(11, 58, 126));

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Seleccionar producto");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel10))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablaProductos.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
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
        jScrollPane2.setViewportView(tablaProductos);
        if (tablaProductos.getColumnModel().getColumnCount() > 0) {
            tablaProductos.getColumnModel().getColumn(0).setMinWidth(80);
            tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(80);
            tablaProductos.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        jButton2.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/agregar.png"))); // NOI18N
        jButton2.setText("Adicionar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtNombreProducto.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtNombreProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreProductoKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel11.setText("Nombre producto:");

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel12.setText("Cantidad:");

        javax.swing.GroupLayout DialogoExaminarProductosLayout = new javax.swing.GroupLayout(DialogoExaminarProductos.getContentPane());
        DialogoExaminarProductos.getContentPane().setLayout(DialogoExaminarProductosLayout);
        DialogoExaminarProductosLayout.setHorizontalGroup(
            DialogoExaminarProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(DialogoExaminarProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DialogoExaminarProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DialogoExaminarProductosLayout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialogoExaminarProductosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addGap(46, 46, 46)
                        .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(199, 199, 199))
                    .addGroup(DialogoExaminarProductosLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SpCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addContainerGap())))
        );
        DialogoExaminarProductosLayout.setVerticalGroup(
            DialogoExaminarProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogoExaminarProductosLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(DialogoExaminarProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(DialogoExaminarProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DialogoExaminarProductosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DialogoExaminarProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(SpCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DialogoExaminarProductosLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButton2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setTitle("Combo producto");
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
        jLabel1.setText("COMBO PRODUCTO");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/list_notes_930 (1).png"))); // NOI18N

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
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(23, 23, 23))
        );

        jTabbedPane1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel4.setText("Codigo:");

        txtCodigoCombo.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        txtCodigoCombo.setToolTipText("Codigo asociado al item");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel5.setText("Nombre:");

        txtNombreCombo.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        txtNombreCombo.setToolTipText("Escriba el nombre del combo");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel6.setText("Estado:");

        comboEstado.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        comboEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Inactivo" }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCodigoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtNombreCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(comboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtNombreCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtCodigoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel7.setText("Seleccione producto");

        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/buscar.png"))); // NOI18N
        jButton1.setText("Examinar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setToolTipText("Lista de productos que componen el Combo");

        tablaComboProducto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tablaComboProducto.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        tablaComboProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PRODUCTO", "CANTIDAD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaComboProducto);
        if (tablaComboProducto.getColumnModel().getColumnCount() > 0) {
            tablaComboProducto.getColumnModel().getColumn(0).setMinWidth(50);
            tablaComboProducto.getColumnModel().getColumn(0).setPreferredWidth(50);
            tablaComboProducto.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/Remove.png"))); // NOI18N
        jButton5.setToolTipText("Seleccione un producto de la tabla a eliminar\n");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Modalidad de precios"));
        jPanel7.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel8.setText("Precio Venta:");

        txtPrecioComboProd.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        txtPrecioComboProd.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecioComboProd.setText("0.0");
        txtPrecioComboProd.setToolTipText("Precio de venta a cliente nuevo");
        txtPrecioComboProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioComboProdKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(29, 29, 29)
                .addComponent(txtPrecioComboProd, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtPrecioComboProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnGuardar.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/guardar.png"))); // NOI18N
        btnGuardar.setText("Crear combo");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnRes.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        btnRes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/cancelar.png"))); // NOI18N
        btnRes.setText("Reestablecer");
        btnRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRes)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnRes))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion registrada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 15))); // NOI18N

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "COMBOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 15))); // NOI18N

        tablaCombo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tablaCombo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NOMBRE ", "CODIGO", "PRECIO", "ESTADO", "ACTUALIZAR", "ELIMINAR"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, true, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCombo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaComboMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaComboMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tablaCombo);
        if (tablaCombo.getColumnModel().getColumnCount() > 0) {
            tablaCombo.getColumnModel().getColumn(0).setMinWidth(30);
            tablaCombo.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablaCombo.getColumnModel().getColumn(0).setMaxWidth(30);
            tablaCombo.getColumnModel().getColumn(1).setMinWidth(300);
            tablaCombo.getColumnModel().getColumn(1).setPreferredWidth(300);
            tablaCombo.getColumnModel().getColumn(1).setMaxWidth(300);
            tablaCombo.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 863, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos Combo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 15))); // NOI18N

        tablaDetalleCombo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CANTIDAD", "NOMBRE PRODUCTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tablaDetalleCombo);
        if (tablaDetalleCombo.getColumnModel().getColumnCount() > 0) {
            tablaDetalleCombo.getColumnModel().getColumn(0).setMinWidth(30);
            tablaDetalleCombo.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablaDetalleCombo.getColumnModel().getColumn(0).setMaxWidth(30);
            tablaDetalleCombo.getColumnModel().getColumn(1).setMinWidth(100);
            tablaDetalleCombo.getColumnModel().getColumn(1).setPreferredWidth(100);
            tablaDetalleCombo.getColumnModel().getColumn(1).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        jTabbedPane1.addTab("Armar Combo", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        this.dispose();
        validaVentana = null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtPrecioComboProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioComboProdKeyTyped
        char c = evt.getKeyChar();
        //        if (c < '0' || c > '9') {
        //            evt.consume();
        //        }
        if (!Character.isDigit(evt.getKeyChar()) && evt.getKeyChar() != '.') {
            evt.consume();
        }
        if (evt.getKeyChar() == '.' && txtPrecioComboProd.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPrecioComboProdKeyTyped

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:

        DefaultTableModel modelo = (DefaultTableModel) tablaComboProducto.getModel();

        if (tablaComboProducto.getRowCount() > 0) {
            if (tablaComboProducto.getSelectedRows().length != 0) {
                modelo.removeRow(tablaComboProducto.getSelectedRow());

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione el producto a eliminar ", "Mensaje..", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No tiene productos a eliminar..!! ", "Mensaje..", JOptionPane.ERROR_MESSAGE);

        }

    }//GEN-LAST:event_jButton5ActionPerformed
    public void limpiarCampos() {
        new Validaciones().limpiarTabla(tablaProductos);
        txtNombreCombo.setText("");
        txtCodigoCombo.setText("");
        txtPrecioComboProd.setText("0.0");
    }
    private void btnResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResActionPerformed
        // TODO add your handling code here:
        // limpiarCampos();
    }//GEN-LAST:event_btnResActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        try {
            List<String> validar = new ArrayList<>();
            validar.add(txtNombreCombo.getText());
            validar.add(txtCodigoCombo.getText());

            ComboDao pr = new ComboDao();

            if (pr.buscarCodigo(txtCodigoCombo.getText()) == false)//Verificando si el codigo ingresado no esta registrado
            {
                if (Validaciones.validarCampos(validar)) {
                    if (tablaComboProducto.getRowCount() > 0) {
                        double precioM = Double.parseDouble(txtPrecioComboProd.getText());//recuperando el precio

                        if (precioM > 0) 
                        {
                            //SETEANDO LOS CAMPOS COMBO PRODUCTO
                            ComboProducto cmb = new ComboProducto();
                            cmb.setIdComboProducto(1);
                            cmb.setNombreCombo(txtNombreCombo.getText());//nombre comboProducto
                            cmb.setCodigoCombo(txtCodigoCombo.getText());//codigo comboProducto
                            cmb.setEstado(comboEstado.getSelectedItem().toString());// estado
                            cmb.setPrecioCombo(txtPrecioComboProd.getText());//Precio comboProducto

                            //RECUPERANDO LOS ITEMS DEL COMBOPRODUCTO
                            Set<DetalleCombo> items = new HashSet<>();

                            for (int i = 0; i < tablaComboProducto.getRowCount(); i++) {
                                DetalleCombo det = new DetalleCombo();

                                det.setIdProducto(Integer.parseInt(String.valueOf(tablaComboProducto.getValueAt(i, 0))));

                                det.setCantidad(Integer.parseInt(String.valueOf(tablaComboProducto.getValueAt(i, 2))));

                                det.setComboproducto(cmb);

                                items.add(det);
                            }

                            cmb.setDetalleCombo(items);

                            //REALIZANDO LA INSERCION DE LOS DATOS
                            ComboDao cmbDao = new ComboDao();
                            if (cmbDao.registarCombo(cmb)) {
                                JOptionPane.showMessageDialog(this, "Registro de Combo Producto Correcto..!!", "Mensaje..", JOptionPane.INFORMATION_MESSAGE);
                                limpiarCampos();
                                System.out.print("-->" + cmb.getIdComboProducto());
                                limpiarCampos();
                                listarCombos();
                                Validaciones.limpiarTabla(tablaComboProducto);
                                Validaciones.limpiarTabla(tablaDetalleCombo);
                            }

                        } else {
                            JOptionPane.showMessageDialog(this, "El precio de venta no puede ser CERO..!!", "Mensaje", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Adicione un producto(s)", "Mensaje", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Faltan campos por llenar", "Mensaje", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "El codigo ingresado ya esta registrado..!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error--->" + ex.getMessage(), "Mensaje..", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            DialogoExaminarProductos.setSize(700, 400);
            DialogoExaminarProductos.setLocation(550, 400);
            listarDatosProductos();
            DialogoExaminarProductos.setVisible(true);

        } catch (Exception ex) {
            Logger.getLogger(vtnAjustesInventario.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    //Metodo que verifica si el item seleccionado esta repetido en la tabla

    public boolean buscarRepetidos(int id) {
        DefaultTableModel modelo = (DefaultTableModel) vtnListaPrecios.tablaComboProducto.getModel();//recuperando la tabla OC
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (Integer.parseInt((String) tablaComboProducto.getValueAt(i, 0)) == id) {
                return true;
            }
        }
        return false;
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try {
            if (tablaProductos.getSelectedRows().length != 0) {
                DefaultTableModel modelo = (DefaultTableModel) vtnListaPrecios.tablaComboProducto.getModel();//creando el modelo pára llenar los datos al JTableje
                //realizando la consulta para realizar el listado de los datos
                Object[] fila = new Object[modelo.getColumnCount()];
                int cant = Integer.parseInt(SpCantidad.getValue().toString());//recuperando la cantidad

                if (cant > 0) {

                    int idbuscar = Integer.parseInt(String.valueOf(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 0)));

                    if (!buscarRepetidos(idbuscar)) {
                        fila[0] = String.valueOf(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 0));//ID PRODUCTO
                        fila[1] = String.valueOf(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 1));//productov
                        fila[2] = cant;//cantidad
                        modelo.addRow(fila);//adicionando la fila a la tabla
                        DialogoExaminarProductos.dispose();
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


    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtNombreProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProductoKeyReleased
        // TODO add your handling code here:
        actualizarBusqueda();
    }//GEN-LAST:event_txtNombreProductoKeyReleased

    private void tablaComboMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaComboMousePressed
        // TODO add your handling code here:
        try {
            DefaultTableModel modelo = (DefaultTableModel) tablaDetalleCombo.getModel();//creando el modelo pára llenar los datos al JTableje

            int idCombo = Integer.parseInt(String.valueOf(tablaCombo.getValueAt(tablaCombo.getSelectedRow(), 0)));//recuperando el id del combo

            ComboDao cmDao = new ComboDao();

            DefaultTableModel modelodetalle = (DefaultTableModel) tablaDetalleCombo.getModel();//creando el modelo pára llenar los datos al JTableje
            Object[] fila = new Object[modelodetalle.getColumnCount()];
            Validaciones.limpiarTabla(tablaDetalleCombo);

            ResultSet rs = cmDao.listarDetalleCombo(idCombo);

            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("p.idProducto"), rs.getString("d.cantidad"), rs.getString("p.nombre")});
            }
            tablaDetalleCombo.setRowHeight(25);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar el detalle de la compra " + e.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tablaComboMousePressed

    //metodo que realiza la actualizacion del valor del combo producto
    private void tablaComboMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaComboMouseClicked
        // TODO add your handling code here:
        try {
            int column = tablaCombo.getColumnModel().getColumnIndexAtX(evt.getX());
            int row = evt.getY() / tablaCombo.getRowHeight();
            if (row < tablaCombo.getRowCount() && row >= 0 && column < tablaCombo.getColumnCount() && column >= 0) {
                Object value = tablaCombo.getValueAt(row, column);
                if (value instanceof JButton) {
                    ((JButton) value).doClick();
                    JButton boton = (JButton) value;

                    //BOTON ACTUALIZAR
                    if (boton.getName().equals("a")) {
                        int idCombo = Integer.parseInt(String.valueOf(tablaCombo.getValueAt(tablaCombo.getSelectedRow(), 0)));//recuperando el id del combo

                        //conmbre combo
                        String nombreCombo = String.valueOf(tablaCombo.getValueAt(tablaCombo.getSelectedRow(), 1));
                        //precio combo
                        String precioCombo = String.valueOf(tablaCombo.getValueAt(tablaCombo.getSelectedRow(), 3));
                        //estado combo
                        String estado;
                        boolean est = (boolean) tablaCombo.getValueAt(tablaCombo.getSelectedRow(), 4);
                        if (est) {
                            estado = "Activo";
                        } else {
                            estado = "Inactivo";
                        }

                        //JOptionPane.showMessageDialog(null, " "+idCombo+"- -"+nombreCombo+"--"+codigoCombo+"--"+precioCombo+" -->"+estado, "Mensaje", JOptionPane.ERROR_MESSAGE);
                        if (actualizarCombo(idCombo, nombreCombo, precioCombo, estado)) {

                            JOptionPane.showMessageDialog(this, "Actualización realizada de forma correcta..!!", "Mensaje..", JOptionPane.INFORMATION_MESSAGE);
                            listarCombos();

                        } else {
                            JOptionPane.showMessageDialog(this, "Error al actualizar los datos del combo..!!", "Mensaje..", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    //BOTON ELIMINAR
                    if (boton.getName().equals("e")) {

                        if (tablaCombo.getSelectedRowCount() > 0) {
                            int idCombo = Integer.parseInt(String.valueOf(tablaCombo.getValueAt(tablaCombo.getSelectedRow(), 0)));//recuperando el id del combo
                            int y = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?");

                            if (y == JOptionPane.YES_OPTION) {
                                if (eliminarCOmbo(idCombo)) {
                                    JOptionPane.showMessageDialog(this, "El combo se elimino correctamente", "Mensaje..::..", JOptionPane.INFORMATION_MESSAGE);

                                    Validaciones.limpiarTabla(tablaDetalleCombo);
                                    limpiarTablaCombo();
                                    this.listarCombos();
                                    limpiarCampos();
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Seleccione un combo a eliminar..!!", "Mensaje..::..", JOptionPane.ERROR_MESSAGE);
                        }

                    }

                }

            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hay productos asociados a este combo\npor lo que no se puede eliminar..!!", "Mensaje..", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tablaComboMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog DialogoExaminarProductos;
    private javax.swing.JSpinner SpCantidad;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnRes;
    private javax.swing.JComboBox comboEstado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tablaCombo;
    public static javax.swing.JTable tablaComboProducto;
    private javax.swing.JTable tablaDetalleCombo;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTextField txtCodigoCombo;
    private javax.swing.JTextField txtNombreCombo;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtPrecioComboProd;
    // End of variables declaration//GEN-END:variables

    public ComboProducto getObjCombo() {
        return objCombo;
    }

    public void setObjCombo(ComboProducto objCombo) {
        this.objCombo = objCombo;
    }
}
