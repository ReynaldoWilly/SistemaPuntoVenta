/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.view;

import com.pos.core.Validaciones;
import com.pos.dao.ComboDao;
import com.pos.dao.InformacionDao;
import com.pos.dao.InventarioDao;
import com.pos.dao.almacenDao;
import com.pos.dao.clienteDao;
import com.pos.dao.coloresDao;
import com.pos.dao.productoDao;
import com.pos.dao.ventaDao;
import com.pos.pojos.Almacen;
import com.pos.pojos.Cliente;
import com.pos.pojos.Informacion;
import com.pos.pojos.Inventario;
import com.pos.pojos.Producto;
import com.pos.pojos.Usuario;
import com.pos.pojos.Venta;
import com.pos.pojos.detalleVenta;
import com.pos.tabla.render.HeaderCellRenderer;
import com.pos.tabla.render.RenderTabla;
import com.pos.util.Numero_a_Letra;
import static com.sistema.view.vtnListaPrecios.tablaComboProducto;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.awt.Color;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import java.util.function.ObjDoubleConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

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
    private Informacion info;//
    public static String _impuestos;
    private static int _banderaActualizar;
    private static HashMap composicion = new HashMap<Integer, Integer>();//coleccion de datoa que contiene la composicion del combo
    private static boolean controlCombos;//variable que controlla si se cambia el combo seleccionado
    private static HashMap CantidadesCombo = new HashMap<Integer, Integer>();// (idproducto,cantidad) estructura que contiene las cantidades de los combos 

    public vtnVentas() {
        initComponents();

        //fin variables globales
        btnAgregarCarro.setEnabled(false);
        validaVentana = "x";//insertando un valor a la variable que valida a la ventana
        controlCombos = true;// TRUE= composicion por primera vez de la estructura; FALSE= la estructura ya contiene productos
        /*Poniendo el JinternalFrame al centro de la ventana*/
        int a = vtnPrincipal.panelMDI.getWidth() - this.getWidth();
        int b = vtnPrincipal.panelMDI.getHeight() - this.getHeight();
        setLocation(a / 2, b / 2);
        _banderaActualizar = 1;
        //recuperando el usuario que realizo el login de usuario
        userlogin = VtnLogin.user;//recibiendo el objeto usuario  de la ventana de logueqo
        lbUsuario.setText(userlogin.getNombres() + " " + userlogin.getApellidos());
        /*fin-----*/

        //campos pago a credito
        txtNuCuotas.setEnabled(false);
        txtMontoCuota.setEnabled(false);
        txtTotal.setEnabled(false);
        btnCredito.setEnabled(false);
        comboPago.setEnabled(false);
        //fin campos a credito

        Validaciones.validaNumeros(txtNit);

        cargarAlmacen();
        mejorarAparienciaTabla();
        //Añadiendo el evento de cambio de valor en el Jtable
        
        anadeListenerAlModelo();
        
        //fin------------------------------------------------
        //Recuperando informacion de descuentos e impuestos
        try {
            List<Informacion> datosInfo = new InformacionDao().listarInformacion();

            for (int i = 0; i < datosInfo.size(); ++i) {
                _impuestos = datosInfo.get(i).getImpuestos();
                //txtImpuestos.setText(datosInfo.get(i).getImpuestos());
                txtDescuentos.setText(datosInfo.get(i).getDescuentos());

            }

            //fin de la recuoeracion de los datos de impuestos y descuentos
        } catch (Exception ex) {
            Logger.getLogger(vtnVentas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al recuperar información de impuestos y descuentos " + ex, "Mensaje", JOptionPane.ERROR_MESSAGE);

        }
    }
    
    
    public void mejorarAparienciaTabla() {
        tablaVenta.setDefaultRenderer(Object.class, new RenderTabla());//renderizando la tabla
        tablaVenta.setRowHeight(30);
        JTableHeader header = tablaVenta.getTableHeader();
        header.setDefaultRenderer(new HeaderCellRenderer());
        tablaVenta.setSelectionBackground(new Color(231, 247, 252));
        tablaVenta.setSelectionForeground(new Color(0, 0, 0));
        tablaVenta.setGridColor(new java.awt.Color(221, 221, 221));
    }

//Metodo que actualiza el valor de los mostos totales 
    public void anadeListenerAlModelo() 
    {
        
       //JOptionPane.showMessageDialog(null, "INGRESA AL MODELO", "Mensaje..", JOptionPane.ERROR_MESSAGE);
             
        this.tablaVenta.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent evento) {
                actualizarMontos(evento);
            }
        });
    }

    //Metodo que realiza la actualizacion de los montos 
    public void actualizarMontos(TableModelEvent e) {
        try {
            if (e.getType() == TableModelEvent.UPDATE)
            {

                // Se obtiene el modelo de la tabla y la fila/columna que han cambiado.
                TableModel modelo = ((TableModel) (e.getSource()));

                int fila = e.getFirstRow();
                int columna = e.getColumn();
                if (columna == 5) 
                {
                    return;
                }
                //JOptionPane.showMessageDialog(null, "--->" + fila + "CON LA COLUMNA" + columna, "Mensaje..", JOptionPane.ERROR_MESSAGE);
                //System.out.println(e.getFirstRow() + "-----" + e.getColumn());
                int cantidad = Integer.parseInt(String.valueOf(tablaVenta.getValueAt(fila, 3)));//Cantidad del producto
                double nuevoValor = Double.parseDouble(String.valueOf(tablaVenta.getValueAt(fila, columna)));

                tablaVenta.setValueAt(String.valueOf(Validaciones.redondear(cantidad * nuevoValor)), fila, 5);

                DefaultTableModel modelo2 = (DefaultTableModel) vtnVentas.tablaVenta.getModel();//creando el modelo pára llenar los datos al JTabble
                suma(modelo2);
                generarSubTotal();
                generarPagoTotal();

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la actualización de los montos" + ex.getMessage(), "Mensaje..", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Metodo que verifica si el producto seleccionado es parte de un combo
    public boolean buscarProductoCombo(int id) throws Exception {
        DefaultTableModel modelo = (DefaultTableModel) tablaDetalleCombo.getModel();
        productoDao pdao = new productoDao();
        for (int i = 0; i < modelo.getRowCount(); ++i) {
            if (pdao.buscarProductoByNombre(String.valueOf(tablaDetalleCombo.getValueAt(i, 2))) == id) {
                return true;
            }
        }
        return false;
    }

    //Metodo que realiza la carga de los almacenes en el comboBox
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
            JOptionPane.showMessageDialog(null, "Error " + e.getMessage(), "Mensaje..::..", JOptionPane.ERROR_MESSAGE);
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
                fila[1] = lista.get(i)[1];
                fila[2] = lista.get(i)[2];
                fila[3] = lista.get(i)[3];
                modelo.addRow(fila);
            }
        }
    }

    //metodo que hace el refrescado de la tabla despues del filtro de datos 
    public void recargarTable3(List<Object[]> lista) {

        DefaultTableModel modelo = (DefaultTableModel) this.tablaProductosCombo.getModel();//creando el modela ára llenar los datos al JTableje
        new Validaciones().limpiarTabla(tablaProductosCombo);
        Object[] fila = new Object[modelo.getColumnCount()];
        if (lista.size() != 0) {
            for (int i = 0; i < lista.size(); i++) {
                fila[0] = lista.get(i)[0];//id
                fila[1] = lista.get(i)[1];
                fila[2] = lista.get(i)[2];
                fila[3] = lista.get(i)[3];
                modelo.addRow(fila);
            }
        }
    }

    //metodo que realiza el limpiado de los campos de la ventana ventas
    public void limpiarCamposVenta() {
        txtCliente.setText("");
        txtNit.setText("");
        comboAlmacen.setSelectedIndex(0);
        txtCiCliente.setText("");
        SpCantidad.setValue(0);
        txtSubTotal.setText("0");
        txtImpuestos.setText("0");
        txtDescuentos.setText("0");
        txtTotalPago.setText("0");
        Validaciones.limpiarTabla(tablaProductos);

    }

    //metodo que hace la carga de datos de los clientes despues del filtro por CI/NI
    public void recargarTablaClientes(List<Object[]> lista) {

        DefaultTableModel modelo = (DefaultTableModel) this.tablaClientes.getModel();//creando el modela ára llenar los datos al JTableje
        new Validaciones().limpiarTabla(tablaClientes);

        Object[] fila = new Object[modelo.getColumnCount()];
        if (lista.size() != 0) {
            for (int i = 0; i < lista.size(); i++) {
                fila[0] = lista.get(i)[0];//id
                fila[1] = lista.get(i)[1];//Nombre
                fila[2] = lista.get(i)[2];//ci
                fila[3] = lista.get(i)[3];//credito
                fila[4] = lista.get(i)[4];//plazo
                fila[5] = lista.get(i)[5];//estado

                modelo.addRow(fila);
            }
        }
    }
//Metodo que realiza la suma de las columnas de los totales

    public void suma(DefaultTableModel model) {
        double subtotalM = 0.0;
        //recorrer todas las filas de la segunda columna y va sumando las cantidades
        for (int i = 0; i < model.getRowCount(); i++) {
            double numero = 0;
            try {
                //capturamos valor de celda
                numero = Double.parseDouble(model.getValueAt(i, 4).toString());

            } catch (NumberFormatException nfe) { //si existe un error se coloca 0 a la celda
                JOptionPane.showMessageDialog(null, "Error " + nfe.getMessage(), "Mensaje..::..", JOptionPane.ERROR_MESSAGE);
            }
            //se suma al total
            subtotalM += numero;
        }
        txtSubTotal.setText(String.valueOf(Validaciones.redondear(subtotalM)));
    }

    //Metodo que realiza el filtrado segun el typeo en el JtextField
    private void actualizarBusqueda() {
        try {
            productoDao pro = new productoDao();

            ArrayList<Producto> result = null;
            List<Object[]> result2 = null;

            result2 = pro.buscarProductoFiltroBYNombreByAlmacenModVentas(new almacenDao().buscarAlmacenId(comboAlmacen.getSelectedItem().toString()), txtCiCliente.getText());
            recargarTable2(result2);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el listado de los productos" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Metodo que realiza el filtrado segun el typeo en el JtextField para la tabla de seleccion de combo producto modulo de ventas
    private void actualizarBusqueda2() {
        try {
            productoDao pro = new productoDao();

            ArrayList<Producto> result = null;
            List<Object[]> result2 = null;

            result2 = pro.buscarProductoFiltroBYNombreByAlmacenModVentas(new almacenDao().buscarAlmacenId(comboAlmacen.getSelectedItem().toString()), txtproducto.getText());
            recargarTable3(result2);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el listado de los productos" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarBusquedaClientes() {
        try {
            clienteDao pro = new clienteDao();

            ArrayList<Producto> result = null;
            List<Object[]> result2 = null;
            result2 = pro.buscarClienteFiltroBYNombreByAlmacenModVentas(txtFindCiNit.getText());
            recargarTablaClientes(result2);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el listado de los clientes..!!" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Metodo que realiza la suma del subtotal de la compra
    public void generarSubTotal() 
    {
         //int filas = this.tablaVenta.getRowCount();//recuperando la cantidad de filas de la tabla
        //JOptionPane.showMessageDialog(this, "NUmero de filas recuperadas de la tabla" + filas, "Error", JOptionPane.ERROR_MESSAGE);
        try {
            double subtotal = 0.0;
            double valor = 0.0;
            for (int i = 0; i < tablaVenta.getRowCount(); ++i) {

                valor = Double.parseDouble(String.valueOf(tablaVenta.getValueAt(i, 5)));//precio producto
                subtotal = subtotal + valor;
              // JOptionPane.showMessageDialog(this, "valor-->" + filas+"= "+valor, "Error", JOptionPane.ERROR_MESSAGE);
                
            }
            txtSubTotal.setText(String.valueOf(Validaciones.redondear(subtotal)));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR EN EL CALCULO DEL SUBTOTAL" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void generarPagoTotal() {
        // int filas = this.tablaVenta.getRowCount();//recuperando la cantidad de filas de la tabla
        try 
        {
            double pagoTotal = Double.parseDouble(txtSubTotal.getText());
            BigDecimal factura = Validaciones.redondear(pagoTotal * Double.parseDouble(txtImpuestos.getText()) / 100);

            if (comboDocumento.getSelectedItem().equals("Factura")) {
                //obteniendo el calor de la factura 
                if (txtDescuentos.getText().equals("0")) {
                    pagoTotal = pagoTotal + factura.doubleValue();
                    txtTotalPago.setText(String.valueOf(Validaciones.redondear(pagoTotal)));
                    LBLiteral.setText(new Numero_a_Letra().Convertir(String.valueOf(pagoTotal), true));

                } else {
                    //formato de numero  
                    DecimalFormat formateador = new DecimalFormat("###,###.##");
                    //fin de formato a numero 

                    BigDecimal descuento = Validaciones.redondear((pagoTotal + factura.doubleValue()) * Double.parseDouble(txtDescuentos.getText()) / 100);

                    //JOptionPane.showMessageDialog(this, "NO ENTRO DESCUENTO"+descuento, "Error", JOptionPane.ERROR_MESSAGE);
                    pagoTotal = (pagoTotal + factura.doubleValue()) - descuento.doubleValue();
                    txtTotalPago.setText(String.valueOf(formateador.format(Validaciones.redondear(pagoTotal))));
                    LBLiteral.setText(new Numero_a_Letra().Convertir(String.valueOf(pagoTotal), true));
                }

            } //BLOQUE DE CODIGO PARA DOCUMENTO= RECIBO DE VENTA
            else {
                if (txtDescuentos.getText().equals("0")) {

                    txtTotalPago.setText(String.valueOf(Validaciones.redondear(pagoTotal)));
                    LBLiteral.setText(new Numero_a_Letra().Convertir(String.valueOf(pagoTotal), true));

                } else {
                    BigDecimal descuento = Validaciones.redondear(pagoTotal * Double.parseDouble(txtDescuentos.getText()) / 100);

                    //JOptionPane.showMessageDialog(this, "NO ENTRO DESCUENTO"+descuento, "Error", JOptionPane.ERROR_MESSAGE);
                    pagoTotal = pagoTotal - descuento.doubleValue();
                    txtTotalPago.setText(String.valueOf(Validaciones.redondear(pagoTotal)));
                    LBLiteral.setText(new Numero_a_Letra().Convertir(String.valueOf(pagoTotal), true));
                }
            }
//            valor = Double.parseDouble(String.valueOf(tablaVenta.getValueAt(i, 4)));//precio producto
//            subtotal = subtotal + valor;
//
//            txtSubTotal.setText(String.valueOf(Validaciones.redondear(subtotal)));
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

            //almacenDao almDao = new almacenDao();
            // listarDatosKardex(almDao.buscarAlmacenId(comboAlmacen.getSelectedItem().toString()));
            InventarioDao invDao = new InventarioDao();
            List<Object[]> lista = invDao.kardexInventarioByAlmacenForVentas(new almacenDao().buscarAlmacenId(comboAlmacen.getSelectedItem().toString()));
            Object[] fila = new Object[modelo.getColumnCount()];

            for (int i = 0; i < lista.size(); i++) {
                fila[0] = lista.get(i)[0];//id
                fila[1] = lista.get(i)[1];//nombre
                fila[2] = lista.get(i)[2];//color
                fila[3] = lista.get(i)[3];//stock
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error al cargar los productos..!! " + e.getMessage(), "Mensaje..::..", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listarDatosProductosCombo() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) this.tablaProductosCombo.getModel();//creando el modela ára llenar los datos al JTableje
            new Validaciones().limpiarTabla(tablaProductosCombo);
            //realizando la consulta para realizar el listado de los datos
            ComboDao proDao = new ComboDao();

            //almacenDao almDao = new almacenDao();
            // listarDatosKardex(almDao.buscarAlmacenId(comboAlmacen.getSelectedItem().toString()));
            InventarioDao invDao = new InventarioDao();
            List<Object[]> lista = invDao.kardexInventarioByAlmacenForVentas(new almacenDao().buscarAlmacenId(comboAlmacen.getSelectedItem().toString()));
            Object[] fila = new Object[modelo.getColumnCount()];

            for (int i = 0; i < lista.size(); i++) {
                fila[0] = lista.get(i)[0];//id
                fila[1] = lista.get(i)[1];//nombre
                fila[2] = lista.get(i)[2];//color
                fila[3] = lista.get(i)[3];//stock
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error al cargar los productos..!! " + e.getMessage(), "Mensaje..::..", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Metodo que realiza la carga de los datos de los cliente en el modulo de ventas
    public void listarDatosClientes() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) this.tablaClientes.getModel();//creando el modela ára llenar los datos al JTableje
            new Validaciones().limpiarTabla(tablaClientes);
            //realizando la consulta para realizar el listado de los datos
            clienteDao proDao = new clienteDao();

            List<Object[]> lista = proDao.listarClienteForVentas();
            Object[] fila = new Object[modelo.getColumnCount()];

            for (int i = 0; i < lista.size(); i++) {
                fila[0] = lista.get(i)[0];//idCliente
                fila[1] = lista.get(i)[1];//nombre
                fila[2] = lista.get(i)[2];//cci
                fila[3] = lista.get(i)[3];//credito
                fila[4] = lista.get(i)[4];//plazo
                fila[5] = lista.get(i)[5];//estado
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error al cargar los datos de los clientes..!! " + e.getMessage(), "Mensaje..::..", JOptionPane.ERROR_MESSAGE);
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

    //Metodo que realiza el listado de los combos registrados en el sistema 
    public void listarComboTablaVentas() {
        try {

            DefaultTableModel modelo = (DefaultTableModel) this.tablaCombo.getModel();

            new Validaciones().limpiarTabla(tablaCombo);
            Object[] fila = new Object[modelo.getColumnCount()];
            ComboDao cdao = new ComboDao();

            List<Object[]> lista = cdao.listarCombosVentas();//recueprando el listado de los combos 
            String estado = "";
            if (lista.size() != 0) {
                for (int i = 0; i < lista.size(); i++) {
                    fila[0] = lista.get(i)[0];//idCombo
                    fila[1] = lista.get(i)[1];//Nombre combo
                    fila[2] = lista.get(i)[2];//codigo combo
                    fila[3] = lista.get(i)[3];//precio codigo
                    modelo.addRow(fila);
                }
                mejorarAparienciaTabla();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al realizar el listado de los combos..!" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //metodo que realiza la composicion del combo
    public void componerCombo(int idProducto, int cantidad) {

        //Verificando si la clave existe
        if (composicion.containsKey(idProducto)) {
            int valor = (int) composicion.get(idProducto) + cantidad;
            composicion.put(idProducto, valor);
        } else {
            composicion.put(idProducto, cantidad);
        }

        for (Object key : composicion.keySet()) {
            System.out.println("IDPRODUCTO: " + key + ", CANTIDAD PRODUCTO: " + composicion.get(key));
        }
    }

    //Metodo que realiza la verificacion de que le combo esta completo para añdir al carro de compras   
    public void gerenarCantidadCombos(int idProducto, int cantidadCombos) {
        try {
            int cantidadTotal = 0;

            DefaultTableModel modelo = (DefaultTableModel) tablaDetalleCombo.getModel();
            productoDao pdao = new productoDao();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                CantidadesCombo.put(pdao.buscarProductoByNombre(String.valueOf(tablaDetalleCombo.getValueAt(i, 2))), cantidadCombos * Integer.parseInt(String.valueOf(tablaDetalleCombo.getValueAt(i, 1))));//recuperando el producto;;
                //JOptionPane.showMessageDialog(this, "-->ENCONTRADO", "Error", JOptionPane.ERROR_MESSAGE);
            }

            for (Object key : CantidadesCombo.keySet()) {
                System.out.println("ID: " + key + ", CANTIDAD: " + CantidadesCombo.get(key));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al realizar la verificacion de los componentes del combo" + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //metodo que realiza la actualizacion de las cantidades del inventario
    public void actualizaInventario() {
        try {
            //verificando si hay un combo en el carro de compras para realizar el descuento respectivo en el inventarios
            ComboDao cdao = new ComboDao();
            InventarioDao invDao = new InventarioDao();
            coloresDao colordao = new coloresDao();
            productoDao pdao = new productoDao();
            almacenDao almDao = new almacenDao();
            
            Inventario inventario=new Inventario();
            
            int stock = 0;
            int idAlm=0;
            
            for (int i = 0; i < tablaVenta.getRowCount(); i++)
            {
                //Ingresa si no existe el combo y existe un combo se va por else
                if (cdao.extisteComboJDBC(String.valueOf(tablaVenta.getValueAt(i, 1)))) 
                {
                    String color = String.valueOf(tablaVenta.getValueAt(i, 2));

                    int cantidad = Integer.parseInt(String.valueOf(tablaVenta.getValueAt(i, 3)));//cantidad de la venta a descontar
                    idAlm=almDao.buscarAlmacenId(comboAlmacen.getSelectedItem().toString());//recuperando el id del almacen para generar el stock
                    
                    int id = pdao.buscarProductoByNombre(String.valueOf(tablaVenta.getValueAt(i, 1)));
                    int idcolor = colordao.buscarColorById(color);
                    
                    

                    JOptionPane.showMessageDialog(this, inventario.getStock()+"-"+inventario.getIdProducto(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al realizar el descuento de los stocks en el intenraio del sistema..!!" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Metodo que realiza el descuento del inventario una vez realizada la venta del producto
    public void descontarInventario() {
        /*try {
            for (int i = 0; i < tablaVenta.getRowCount(); i++) {
                //verificando si hay un combo en el carro de compras para realizar el descuento respectivo en el inventarios
                ComboDao cdao = new ComboDao();
                InventarioDao invDao = new InventarioDao();
                //Ingresa si no existe el combo y existe un combo se va por else
                if (cdao.extisteComboJDBC(String.valueOf(tablaVenta.getValueAt(i, 1)))) {
                    String color = String.valueOf(tablaVenta.getValueAt(i, 2));
                    int stock = invDao.recuperarStockProducto(Integer.parseInt(String.valueOf(tablaVenta.getValueAt(i, 0))), new coloresDao().buscarColorById(color));//recuperando el stock
                    int cantVenta = Integer.parseInt(String.valueOf(tablaVenta.getValueAt(i, 3)));//recuprando la cantidad de 
                    int cantidadActual = stock - cantVenta;
                    JOptionPane.showMessageDialog(this, "Cantidad actualizaza " + stock + " - " + cantVenta + "= " + cantidadActual + "", "Error", JOptionPane.ERROR_MESSAGE);

                } //reduciendo la cantidad del combo por falso
                // se debe realizar el descuento de los componentes del combo
                else {
                }

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al realizar el descuento de los stocks en el intenraio del sistema..!!" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }*/
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
        txtCiCliente = new javax.swing.JTextField();
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
        DialogoClientes = new javax.swing.JDialog();
        jPanel15 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        txtFindCiNit = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        jButton19 = new javax.swing.JButton();
        DialogoCombo = new javax.swing.JDialog();
        jPanel16 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaCombo = new javax.swing.JTable();
        jLabel53 = new javax.swing.JLabel();
        txtCiCliente1 = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaDetalleCombo = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        txtproducto = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaProductosCombo = new javax.swing.JTable();
        jLabel54 = new javax.swing.JLabel();
        SpCantidadProductoCombo = new javax.swing.JSpinner();
        jButton18 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaComboDetalleVenta = new javax.swing.JTable();
        SpCantidadCombos = new javax.swing.JSpinner();
        jLabel55 = new javax.swing.JLabel();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        btnAgregarCarro = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbUsuario = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtFechaVenta = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        comboDocumento = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtNit = new javax.swing.JTextField();
        jButton17 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        comboAlmacen = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        comboPago = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaVenta = new javax.swing.JTable();
        jButton14 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        LBLiteral = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtNuCuotas = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtMontoCuota = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnCredito = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtImpuestos = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txtDescuentos = new javax.swing.JTextField();
        txtTotalPago = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();

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

        txtCiCliente.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtCiCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCiClienteKeyReleased(evt);
            }
        });

        jScrollPane3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablaProductos.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "ID", "NOMBRE", "COLOR", "STOCK"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
            tablaProductos.getColumnModel().getColumn(2).setResizable(false);
            tablaProductos.getColumnModel().getColumn(3).setMinWidth(75);
            tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(70);
            tablaProductos.getColumnModel().getColumn(3).setMaxWidth(80);
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
                        .addComponent(txtCiCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(txtCiCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        DialogoClientes.setTitle("Clientes..::..");
        DialogoClientes.setModal(true);

        jPanel15.setBackground(new java.awt.Color(11, 58, 126));

        jLabel47.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Seleccione Cliente");

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/usuarios.png"))); // NOI18N

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel48)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(120, Short.MAX_VALUE)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel47)
                .addGap(251, 251, 251))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49)
                    .addComponent(jLabel47))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel50.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel50.setText("CI/NIT:");

        txtFindCiNit.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtFindCiNit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFindCiNitKeyReleased(evt);
            }
        });

        jScrollPane6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablaClientes.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NOMBRE", "CI/NIT", "CREDITO", "PLAZO (DIAS)", "ESTADO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tablaClientes);
        if (tablaClientes.getColumnModel().getColumnCount() > 0) {
            tablaClientes.getColumnModel().getColumn(0).setMinWidth(25);
            tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(25);
            tablaClientes.getColumnModel().getColumn(0).setMaxWidth(40);
            tablaClientes.getColumnModel().getColumn(1).setResizable(false);
            tablaClientes.getColumnModel().getColumn(2).setResizable(false);
            tablaClientes.getColumnModel().getColumn(3).setResizable(false);
        }

        jButton19.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/agregar.png"))); // NOI18N
        jButton19.setText("Seleccionar");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DialogoClientesLayout = new javax.swing.GroupLayout(DialogoClientes.getContentPane());
        DialogoClientes.getContentPane().setLayout(DialogoClientesLayout);
        DialogoClientesLayout.setHorizontalGroup(
            DialogoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogoClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFindCiNit, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(253, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialogoClientesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton19)
                .addContainerGap())
            .addGroup(DialogoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(DialogoClientesLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(DialogoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane6))
                    .addContainerGap()))
        );
        DialogoClientesLayout.setVerticalGroup(
            DialogoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogoClientesLayout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addGroup(DialogoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(txtFindCiNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                .addComponent(jButton19)
                .addGap(26, 26, 26))
            .addGroup(DialogoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialogoClientesLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(48, 48, 48)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(66, 66, 66)))
        );

        DialogoCombo.setTitle("Combo Producto");
        DialogoCombo.setModal(true);
        DialogoCombo.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                DialogoComboWindowClosing(evt);
            }
        });

        jPanel16.setBackground(new java.awt.Color(11, 58, 126));

        jLabel45.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Seleccione un combo");

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/producto.png"))); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel46)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel51)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel45)
                .addGap(251, 251, 251))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51)
                    .addComponent(jLabel45))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablaCombo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tablaCombo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "ID", "NOMBRE", "CODIGO", "PRECIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

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
        jScrollPane5.setViewportView(tablaCombo);
        if (tablaCombo.getColumnModel().getColumnCount() > 0) {
            tablaCombo.getColumnModel().getColumn(0).setMinWidth(25);
            tablaCombo.getColumnModel().getColumn(0).setPreferredWidth(25);
            tablaCombo.getColumnModel().getColumn(0).setMaxWidth(40);
            tablaCombo.getColumnModel().getColumn(2).setResizable(false);
            tablaCombo.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel53.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel53.setText("Nombre Combo:");

        txtCiCliente1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtCiCliente1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCiCliente1KeyReleased(evt);
            }
        });

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos Combo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 15))); // NOI18N

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
        jScrollPane7.setViewportView(tablaDetalleCombo);
        if (tablaDetalleCombo.getColumnModel().getColumnCount() > 0) {
            tablaDetalleCombo.getColumnModel().getColumn(0).setMinWidth(30);
            tablaDetalleCombo.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablaDetalleCombo.getColumnModel().getColumn(0).setMaxWidth(30);
            tablaDetalleCombo.getColumnModel().getColumn(1).setMinWidth(70);
            tablaDetalleCombo.getColumnModel().getColumn(1).setPreferredWidth(70);
            tablaDetalleCombo.getColumnModel().getColumn(1).setMaxWidth(70);
        }

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel53)
                        .addGap(46, 46, 46)
                        .addComponent(txtCiCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(187, 187, 187))
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCiCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Inventario disponible", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 13))); // NOI18N

        jLabel52.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel52.setText("Nombre producto:");

        txtproducto.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        txtproducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtproductoKeyReleased(evt);
            }
        });

        jScrollPane8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablaProductosCombo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tablaProductosCombo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "ID", "NOMBRE", "COLOR", "STOCK"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(tablaProductosCombo);
        if (tablaProductosCombo.getColumnModel().getColumnCount() > 0) {
            tablaProductosCombo.getColumnModel().getColumn(0).setMinWidth(25);
            tablaProductosCombo.getColumnModel().getColumn(0).setPreferredWidth(25);
            tablaProductosCombo.getColumnModel().getColumn(0).setMaxWidth(40);
            tablaProductosCombo.getColumnModel().getColumn(1).setResizable(false);
            tablaProductosCombo.getColumnModel().getColumn(2).setResizable(false);
            tablaProductosCombo.getColumnModel().getColumn(3).setMinWidth(75);
            tablaProductosCombo.getColumnModel().getColumn(3).setPreferredWidth(70);
            tablaProductosCombo.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        jLabel54.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel54.setText("Cantidad:");

        jButton18.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/agregar.png"))); // NOI18N
        jButton18.setText("Adicionar");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel54)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SpCantidadProductoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton18))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel54)
                        .addComponent(SpCantidadProductoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton18)))
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Combo ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 13))); // NOI18N

        tablaComboDetalleVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CANTIDAD", "PRODUCTO", "COLOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaComboDetalleVenta);
        if (tablaComboDetalleVenta.getColumnModel().getColumnCount() > 0) {
            tablaComboDetalleVenta.getColumnModel().getColumn(0).setMinWidth(50);
            tablaComboDetalleVenta.getColumnModel().getColumn(0).setPreferredWidth(50);
            tablaComboDetalleVenta.getColumnModel().getColumn(0).setMaxWidth(50);
            tablaComboDetalleVenta.getColumnModel().getColumn(1).setMinWidth(65);
            tablaComboDetalleVenta.getColumnModel().getColumn(1).setPreferredWidth(65);
            tablaComboDetalleVenta.getColumnModel().getColumn(1).setMaxWidth(70);
        }

        jLabel55.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel55.setText("Cantidad de combos:");

        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/Remove.png"))); // NOI18N
        jButton20.setToolTipText("Eliminar producto.");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton21.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton21.setText("Validar");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SpCantidadCombos, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton21))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton20)))))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SpCantidadCombos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton21))
                    .addComponent(jLabel55, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAgregarCarro.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        btnAgregarCarro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/Cart-icon.png"))); // NOI18N
        btnAgregarCarro.setText("Agregar al carrito");
        btnAgregarCarro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCarroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DialogoComboLayout = new javax.swing.GroupLayout(DialogoCombo.getContentPane());
        DialogoCombo.getContentPane().setLayout(DialogoComboLayout);
        DialogoComboLayout.setHorizontalGroup(
            DialogoComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(DialogoComboLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(DialogoComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialogoComboLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAgregarCarro)))
                .addContainerGap())
        );
        DialogoComboLayout.setVerticalGroup(
            DialogoComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogoComboLayout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DialogoComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(DialogoComboLayout.createSequentialGroup()
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarCarro)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/images/ventas2.png"))); // NOI18N

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/usuarios.png"))); // NOI18N
        jLabel3.setText("Usuario:");

        lbUsuario.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lbUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lbUsuario.setText("NameUser");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel20))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbUsuario)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addComponent(jLabel21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(39, 39, 39))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel21)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel2.setText("Fecha:");

        txtFechaVenta.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel5.setText("Documento:");

        comboDocumento.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        comboDocumento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Recibo de venta", "Factura" }));
        comboDocumento.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboDocumentoItemStateChanged(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información del cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 13))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel6.setText("Cliente:");

        txtCliente.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel8.setText("NIT/CI:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/Search-icon.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtNit.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/Search-icon.png"))); // NOI18N
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton17)
                    .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jButton1)))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel17.setText("Almacen:");

        comboAlmacen.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        comboAlmacen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---------------------" }));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccione producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 13))); // NOI18N
        jPanel7.setToolTipText("");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel11.setText("Seleccionar producto:");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/agregar.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel12.setText("Seleccionar Combo:");

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/agregar.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel11))
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton4)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addGap(9, 9, 9)
                            .addComponent(jLabel12))))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel7.setText("Tipo pago:");

        comboPago.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        comboPago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Contado", "Credito" }));
        comboPago.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboPagoItemStateChanged(evt);
            }
        });
        comboPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPagoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(12, 12, 12)
                        .addComponent(txtFechaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(comboAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(comboDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 47, Short.MAX_VALUE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel17)
                        .addComponent(comboDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(comboPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton2.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/guardar.png"))); // NOI18N
        jButton2.setText("Grabar y Cerrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton15.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/guardar.png"))); // NOI18N
        jButton15.setText("Grabar y Nuevo");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/revert_32.png"))); // NOI18N
        jButton5.setText("Limpiar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/upload.png"))); // NOI18N
        jButton6.setText("Gargar de cotización");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton16.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/salir.png"))); // NOI18N
        jButton16.setText("Salir");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton16)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle de la venta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 13))); // NOI18N

        jScrollPane2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jScrollPane2KeyReleased(evt);
            }
        });

        tablaVenta.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tablaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PRODUCTO", "COLOR", "CANT.", "PRECIO U.", "PRECIO TOTAL"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaVenta.getTableHeader().setReorderingAllowed(false);
        tablaVenta.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablaVentaPropertyChange(evt);
            }
        });
        tablaVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablaVentaKeyReleased(evt);
            }
        });
        tablaVenta.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tablaVentaVetoableChange(evt);
            }
        });
        jScrollPane2.setViewportView(tablaVenta);
        if (tablaVenta.getColumnModel().getColumnCount() > 0) {
            tablaVenta.getColumnModel().getColumn(0).setMinWidth(30);
            tablaVenta.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablaVenta.getColumnModel().getColumn(0).setMaxWidth(30);
            tablaVenta.getColumnModel().getColumn(2).setMinWidth(60);
            tablaVenta.getColumnModel().getColumn(2).setPreferredWidth(60);
            tablaVenta.getColumnModel().getColumn(2).setMaxWidth(60);
            tablaVenta.getColumnModel().getColumn(3).setMinWidth(50);
            tablaVenta.getColumnModel().getColumn(3).setPreferredWidth(50);
            tablaVenta.getColumnModel().getColumn(3).setMaxWidth(50);
            tablaVenta.getColumnModel().getColumn(4).setMinWidth(90);
            tablaVenta.getColumnModel().getColumn(4).setPreferredWidth(90);
            tablaVenta.getColumnModel().getColumn(4).setMaxWidth(90);
            tablaVenta.getColumnModel().getColumn(5).setMinWidth(150);
            tablaVenta.getColumnModel().getColumn(5).setPreferredWidth(150);
            tablaVenta.getColumnModel().getColumn(5).setMaxWidth(150);
        }

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pos/iconos/Remove.png"))); // NOI18N
        jButton14.setToolTipText("Eliminar producto.");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel4.setText("SON:");

        LBLiteral.setFont(new java.awt.Font("Century Gothic", 3, 13)); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LBLiteral, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton14))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 885, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton14)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LBLiteral, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pago a credito", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 13))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel9.setText("Nro. Cuotas:");

        txtNuCuotas.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        txtNuCuotas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNuCuotas.setText("0");
        txtNuCuotas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNuCuotasKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuCuotasKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel10.setText("Monto X Cuota:");

        txtMontoCuota.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        txtMontoCuota.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMontoCuota.setText("0.0");
        txtMontoCuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoCuotaActionPerformed(evt);
            }
        });
        txtMontoCuota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMontoCuotaKeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel13.setText("Total:");

        txtTotal.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotal.setText("0.0");

        btnCredito.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        btnCredito.setText("Calcular");
        btnCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreditoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel13)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNuCuotas)
                    .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                    .addComponent(txtMontoCuota))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(84, Short.MAX_VALUE)
                .addComponent(btnCredito)
                .addGap(72, 72, 72))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtNuCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtMontoCuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCredito)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel14.setText("SubTotal:");

        txtSubTotal.setEditable(false);
        txtSubTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtSubTotal.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        txtSubTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSubTotal.setText("0.0");

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel15.setText("IVA %:");

        txtImpuestos.setEditable(false);
        txtImpuestos.setBackground(new java.awt.Color(204, 204, 255));
        txtImpuestos.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        txtImpuestos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtImpuestos.setText("0");
        txtImpuestos.setToolTipText("Porcentaje de IVA");
        txtImpuestos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImpuestosActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel44.setText("Descuento %:");

        txtDescuentos.setEditable(false);
        txtDescuentos.setBackground(new java.awt.Color(204, 204, 255));
        txtDescuentos.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        txtDescuentos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescuentos.setText("0");
        txtDescuentos.setToolTipText("Porcentaje de IVA");

        txtTotalPago.setEditable(false);
        txtTotalPago.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalPago.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        txtTotalPago.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalPago.setText("0.0");

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel16.setText("Total a Pagar:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtImpuestos)
                            .addComponent(txtSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTotalPago)
                            .addComponent(txtDescuentos))))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtImpuestos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txtDescuentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 401, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

        try {
            DialogoClientes.setSize(700, 400);
            DialogoClientes.setLocation(550, 400);
            listarDatosClientes();
            DialogoClientes.setVisible(true);

        } catch (Exception ex) {
            Logger.getLogger(vtnAjustesInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            if (comboAlmacen.getSelectedIndex() > 0) {

                DialogoProductoVentas.setSize(700, 400);
                DialogoProductoVentas.setLocation(550, 400);
                listarDatosProductos();
                DialogoProductoVentas.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un Almacen.", "Mensaje..", JOptionPane.INFORMATION_MESSAGE);
                comboAlmacen.requestFocusInWindow();
                return;
            }
        } catch (Exception ex) {
            Logger.getLogger(vtnAjustesInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            if (comboAlmacen.getSelectedIndex() > 0) {

                DialogoCombo.setSize(1400, 700);
                DialogoCombo.setLocation(300, 200);
                listarComboTablaVentas();
                listarDatosProductosCombo();

                DialogoCombo.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un Almacen.", "Mensaje..", JOptionPane.INFORMATION_MESSAGE);
                comboAlmacen.requestFocusInWindow();
                return;
            }
        } catch (Exception ex) {
            Logger.getLogger(vtnAjustesInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        validaVentana = null;
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtCiClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCiClienteKeyReleased
        // TODO add your handling code here:
        actualizarBusqueda();
    }//GEN-LAST:event_txtCiClienteKeyReleased

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        try {
            productoDao pdao = new productoDao();

            if (tablaProductos.getSelectedRows().length != 0) {
                DefaultTableModel modelo = (DefaultTableModel) vtnVentas.tablaVenta.getModel();//creando el modelo pára llenar los datos al JTabble
                //realizando la consulta para realizar el listado de los datos
                Object[] fila = new Object[modelo.getColumnCount()];
                int cant = Integer.parseInt(SpCantidad.getValue().toString());//recuperando la cantidad

                if (cant > 0) {
                    int idbuscar = Integer.parseInt(String.valueOf(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 0)));
                    //JOptionPane.showMessageDialog(null," "+stokProd, "Mensaje...", JOptionPane.ERROR_MESSAGE);

                    if (!buscarRepetidos(idbuscar)) {
                        int stokProd = Integer.parseInt(String.valueOf(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 3)));//STOCK DE INVENTARIO DEL PRODUCTO;

                        if (cant <= stokProd) {
                            fila[0] = String.valueOf(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 0));//ID PRODUCTO
                            fila[1] = String.valueOf(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 1));//producto 
                            fila[2] = String.valueOf(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 2));//color
                            fila[3] = cant;//cantidad
                            double precio = Double.parseDouble(pdao.recuperarPrecioProducto(new productoDao().buscarProductoByNombre((String) fila[1])));
                            fila[4] = precio; //precio prducto

                            double subtotal = cant * precio;

                            fila[5] = String.valueOf(subtotal);

                            modelo.addRow(fila);//adicionando la fila a la tabla
                            suma(modelo);
                            generarSubTotal();
                            generarPagoTotal();

                            DialogoProductoVentas.dispose();

                            SpCantidad.setValue(0);
                            mejorarAparienciaTabla();

                        } else {
                            JOptionPane.showMessageDialog(null, "No hay suficiente Stock..!!", "Mensaje...", JOptionPane.ERROR_MESSAGE);
                        }

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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (txtCliente.getText().length() <= 0 || txtNit.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Llene los datos del cliente", "Mensaje..", JOptionPane.INFORMATION_MESSAGE);
            txtCiCliente.requestFocusInWindow();
            return;
        }

        if (txtFechaVenta.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Ingrese la fecha", "Mensaje..", JOptionPane.INFORMATION_MESSAGE);
            txtFechaVenta.requestFocusInWindow();
            return;
        }
        if (tablaVenta.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No tiene productos para realizar la venta..!!", "Mensaje..", JOptionPane.INFORMATION_MESSAGE);
            txtFechaVenta.requestFocusInWindow();
            return;
        }

        try {
            //realizando la verificacion de la existencia del cliente en la BD
            clienteDao cliDao = new clienteDao();
            List<Object[]> cliente = cliDao.buscarClienteByNit(txtNit.getText());
            int idCliente = 0;
            if (cliente.size() == 0) {
                Cliente cli = new Cliente(txtCliente.getText(), txtNit.getText(), "", "", "", "", "NO", "0", "Activo");
                cliDao.registarCliente(cli);
            } else {
                Cliente cli2 = new Cliente();
                cli2 = cliDao.ClienteByNit(txtNit.getText());
                idCliente = cli2.getIdCliente();
            }
            //Fin de la verificacion de la existencia del cliente (Si el cliente no existe lo registra y sis existe recupera el idCliente)

            //seteando los valores de la clase venta
            int año = txtFechaVenta.getCalendar().get(Calendar.YEAR);
            int mes = txtFechaVenta.getCalendar().get(Calendar.MONTH) + 1;
            int dia = txtFechaVenta.getCalendar().get(Calendar.DAY_OF_MONTH);
            String fecha = año + "-" + mes + "-" + dia;
            //JOptionPane.showMessageDialog(this, "fecha--->" + fecha);

            //seteando los valores para el registro de venta en la BD
            Venta venta = new Venta();

            venta.setIdCliente(idCliente);//idCliente
            venta.setMonto(txtTotalPago.getText());//Monto pagado
            venta.setTipoVenta(comboPago.getSelectedItem().toString());//tipo de pago
            venta.setFecha(txtFechaVenta.getDate());//fecha venta
            venta.setIdUsuario(userlogin.getIdUsuario());//id de usuario logueado
            venta.setDocumentos(comboDocumento.getSelectedItem().toString());//documento de venta
            //-------------------------seteo de datos de venta

            //--------------detalle de venta----------------//
            //RECUPERANDO LOS ITEMS DEL COMBOPRODUCTO
            Set<detalleVenta> items = new HashSet<>();

            productoDao pdao = new productoDao();

            ComboDao cdao = new ComboDao();
            int sw = 0;//variable que se activa cuando solo hay productos en la cesta de venta

            for (int i = 0; i < tablaVenta.getRowCount(); i++) {
                detalleVenta det = new detalleVenta();

                //verificando si hay un combo en el carrito de venta
                //Ingresa si no hay un combo en la tabla venta ()
                if (cdao.extisteComboJDBC(String.valueOf(tablaVenta.getValueAt(i, 1)))) {
                    det.setIdProducto(pdao.buscarProductoByNombre(String.valueOf(tablaVenta.getValueAt(i, 1))));//id Producto

                    det.setCantidad(Integer.parseInt(String.valueOf(tablaVenta.getValueAt(i, 3))));//cantidad producto
                    det.setPrecio(String.valueOf(tablaVenta.getValueAt(i, 4)));//precio 
                    det.setColor(new coloresDao().buscarColorById(String.valueOf(tablaVenta.getValueAt(i, 2))));
                    det.setVenta(venta);
                    items.add(det);
                    sw = 1; //activando la variable para realizar el descuento del producto en inventario

                } else {
                    det.setIdProducto(Integer.parseInt(String.valueOf(tablaVenta.getValueAt(i, 0))));//seteando el id del combo
                    det.setCodigo(cdao.buscarCodigo(Integer.parseInt(String.valueOf(tablaVenta.getValueAt(i, 0)))));//codigo del combo
                    det.setCantidad(Integer.parseInt(String.valueOf(tablaVenta.getValueAt(i, 3))));//seteando la cantidad de los combos
                    det.setPrecio(String.valueOf(tablaVenta.getValueAt(i, 4)));//precio combo
                    det.setColor(11);//color generico 

                    det.setVenta(venta);
                    items.add(det);
                }
            }

            venta.setItemsventa(items);
            //--------------fin del seteo de la venta
            //REALIZANDO LA INSERCION DE LOS DATOS
            ventaDao vDao = new ventaDao();

            if (vDao.registarVenta(venta)) {
                JOptionPane.showMessageDialog(this, "La venta se registro correctamente..!", "Mensaje..", JOptionPane.INFORMATION_MESSAGE);
                //ingresnado cuandoen la cesta

                actualizaInventario();//realizando el respectivo descuento en el inventario
                limpiarCamposVenta();
                Validaciones.limpiarTabla(tablaVenta);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "A ocurrido un error contacte con el administrador" + e.getMessage(), "Mensaje..", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        validaVentana = null;
        limpiarCamposVenta();
        Validaciones.limpiarTabla(tablaComboDetalleVenta);
        Validaciones.limpiarTabla(tablaVenta);
        Validaciones.limpiarTabla(tablaProductosCombo);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void txtImpuestosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImpuestosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImpuestosActionPerformed

    private void comboDocumentoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboDocumentoItemStateChanged
        // TODO add your handling code here:
        if (comboDocumento.getSelectedItem().toString().equals("Factura")) {
            txtImpuestos.setText(_impuestos);
        } else {
            txtImpuestos.setText("0");
        }
        generarPagoTotal();

    }//GEN-LAST:event_comboDocumentoItemStateChanged

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelo = (DefaultTableModel) tablaVenta.getModel();
        if (tablaVenta.getRowCount() > 0) {
            if (tablaVenta.getSelectedRows().length != 0) {
                modelo.removeRow(tablaVenta.getSelectedRow());
                suma(modelo);
                generarPagoTotal();
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione el producto a eliminar ", "Mensaje..", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No tiene productos a eliminar..!! ", "Mensaje..", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        try {
            if (txtNit.getText().length() > 0) {
                clienteDao cliDao = new clienteDao();
                List<Object[]> lista = cliDao.buscarClienteByNit(txtNit.getText());
                String nombre = null, estado = null;
                if (lista.size() > 0) {
                    for (int i = 0; i < lista.size(); i++) {
                        nombre = (String) lista.get(i)[0];
                        estado = (String) lista.get(i)[1];

                        String credito = (String) lista.get(i)[2];
                        if (credito.equals("NO")) {
                            comboPago.setSelectedIndex(0);
                            comboPago.setEnabled(false);
                        } else {
                            comboPago.setSelectedIndex(0);
                            comboPago.setEnabled(true);
                        }

                    }

                    if (estado.equals("Activo")) {
                        txtCliente.setText(nombre);
                    } else {
                        txtCliente.setText("");
                        JOptionPane.showMessageDialog(null, "Se encontro al cliente pero se encuentra INACTIVO\nIngrese al modulo de Clientes y cambie el status a ACTIVO ", "Mensaje..", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro al Cliente..!!", "Mensaje..", JOptionPane.INFORMATION_MESSAGE);
                    comboPago.setSelectedIndex(0);
                    comboPago.setEnabled(false);
                    txtCliente.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el numero de CI/NIT..!!", "Mensaje..", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la busqueda del Cliente ", "Mensaje..", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void comboPagoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboPagoItemStateChanged
        // TODO add your handling code here:
        Validaciones.validaNumeros(txtNuCuotas);
        if (comboPago.getSelectedItem().equals("Credito")) {
            txtNuCuotas.setEnabled(true);
            btnCredito.setEnabled(true);
        } else {
            txtNuCuotas.setEnabled(false);
            btnCredito.setEnabled(false);
            txtNuCuotas.setText("0");
            txtMontoCuota.setText("");
            txtTotal.setText("");
        }
    }//GEN-LAST:event_comboPagoItemStateChanged

    private void txtMontoCuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoCuotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoCuotaActionPerformed

    private void txtNuCuotasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuCuotasKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_txtNuCuotasKeyTyped

    private void txtNuCuotasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuCuotasKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuCuotasKeyReleased

    private void txtMontoCuotaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoCuotaKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtMontoCuotaKeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        Validaciones.limpiarTabla(tablaVenta);
        Validaciones.limpiarTabla(tablaComboDetalleVenta);
        Validaciones.limpiarTabla(tablaProductosCombo);

        comboAlmacen.setSelectedIndex(0);
        comboPago.setSelectedIndex(0);
        comboDocumento.setSelectedIndex(0);
        txtCliente.setText("");
        comboPago.setEnabled(true);
        txtNit.setText("");
        generarSubTotal();
        generarPagoTotal();


    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreditoActionPerformed
        // TODO add your handling code here:
        double cuota = Double.parseDouble(txtTotalPago.getText());
        if (cuota > 0) {
            double monto = Double.parseDouble(txtNuCuotas.getText());
            txtMontoCuota.setText(String.valueOf(Validaciones.redondear(cuota / monto)));
            txtTotal.setText(String.valueOf(txtTotalPago.getText()));
        } else {
            JOptionPane.showMessageDialog(null, "No tiene productos en el carro..!! ", "Mensaje..", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCreditoActionPerformed

    private void txtFindCiNitKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFindCiNitKeyReleased
        // TODO add your handling code here:
        actualizarBusquedaClientes();
    }//GEN-LAST:event_txtFindCiNitKeyReleased

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        try {
            if (tablaClientes.getSelectedRows().length != 0) {

                String estado = String.valueOf(tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 5));

                if (estado.equals("Activo")) {
                    txtCliente.setText(String.valueOf(tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 1)));//Nombre Cliente
                    txtNit.setText(String.valueOf(tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 2)));//CI/NI Cliente 
                    String credito = String.valueOf(tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 3));
                    if (credito.equals("NO")) {
                        comboPago.setSelectedIndex(0);
                        comboPago.setEnabled(false);
                    } else if (credito.equals("SI")) {
                        comboPago.setEnabled(true);
                    }
                    DialogoClientes.dispose();

                } else {
                    txtCliente.setText("");
                    comboPago.setSelectedIndex(0);
                    comboPago.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "Se encontro al cliente pero se encuentra INACTIVO\nIngrese al modulo de Clientes y cambie el status a ACTIVO ", "Mensaje..", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un Cliente...!!", "Mensaje..", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e.getMessage(), "Mensaje..", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jScrollPane2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jScrollPane2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane2KeyReleased

    private void tablaVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaVentaKeyReleased


    }//GEN-LAST:event_tablaVentaKeyReleased

    private void tablaVentaVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tablaVentaVetoableChange
        // TODO add your handling code here:

    }//GEN-LAST:event_tablaVentaVetoableChange

    private void tablaVentaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablaVentaPropertyChange
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tablaVentaPropertyChange

    private void txtCiCliente1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCiCliente1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiCliente1KeyReleased

    private void tablaComboMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaComboMouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_tablaComboMouseClicked

    private void tablaComboMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaComboMousePressed
        // TODO add your handling code here:
        try 
        {
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

    private void txtproductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproductoKeyReleased
        // TODO add your handling code here:
        actualizarBusqueda2();
    }//GEN-LAST:event_txtproductoKeyReleased

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        try {
            //Validacion de llenado de informacion para el combo producto
            if (tablaCombo.getSelectedRows().length == 0) {
                JOptionPane.showMessageDialog(null, "Seleccione un combo..!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Validacion de llenado de informacion para el combo producto
            if (tablaProductosCombo.getSelectedRows().length == 0) {
                JOptionPane.showMessageDialog(null, "Seleccione productos para componer el combo..!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //validando el contador de productos a adicionar
            int cant = Integer.parseInt(SpCantidadProductoCombo.getValue().toString());//recuperando la cantidad
            if (cant == 0) {
                JOptionPane.showMessageDialog(null, "La cantidad del producto no puede ser 0..!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
                SpCantidadProductoCombo.requestFocusInWindow();
                return;
            }
            //validando el contador de productos a adicionar
            int cantCombos = Integer.parseInt(SpCantidadCombos.getValue().toString());//recuperando la cantidad
            if (cantCombos == 0) {
                JOptionPane.showMessageDialog(null, "Ingrese la cantidad de combos a preparar..!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
                SpCantidadCombos.requestFocusInWindow();
                return;
            }

            //creando la estructura para crear la tabla combo  
            DefaultTableModel modelo = (DefaultTableModel) tablaComboDetalleVenta.getModel();//creando el modelo pára llenar los datos al JTabble
            Object[] fila = new Object[modelo.getColumnCount()];
            String producto = String.valueOf(tablaProductosCombo.getValueAt(tablaProductosCombo.getSelectedRow(), 1));//recuperando el producto;
            productoDao pdao = new productoDao();

            if (buscarProductoCombo(pdao.buscarProductoByNombre(producto))) {
                int stokProd = Integer.parseInt(String.valueOf(tablaProductosCombo.getValueAt(tablaProductosCombo.getSelectedRow(), 3)));//STOCK DE INVENTARIO DEL PRODUCTO;
                int cantProd = Integer.parseInt(SpCantidadProductoCombo.getValue().toString());//recuperando la cantidad
                if (stokProd >= cantProd) {

                    fila[0] = String.valueOf(tablaProductosCombo.getValueAt(tablaProductosCombo.getSelectedRow(), 0));//ID PRODUCTO
                    fila[1] = cantProd;
                    fila[2] = String.valueOf(tablaProductosCombo.getValueAt(tablaProductosCombo.getSelectedRow(), 1));//producto 
                    fila[3] = String.valueOf(tablaProductosCombo.getValueAt(tablaProductosCombo.getSelectedRow(), 2));//color

                    modelo.addRow(fila);//adicionando la fila a la tabla

                    SpCantidadProductoCombo.setValue(0);

                    tablaCombo.setEnabled(false);//bloqueando la tabla combo
                    SpCantidadCombos.setEnabled(false);//bloqueando el contador de combos

                    //------------------------------------------compisicion del combo
                    String nombreProd = String.valueOf(tablaProductosCombo.getValueAt(tablaProductosCombo.getSelectedRow(), 1));//recuperando el producto;

                    //llenando la estructura que contiene el total de items del combo
                    if (controlCombos) {
                        gerenarCantidadCombos(pdao.buscarProductoByNombre(nombreProd), cantCombos);//llamando al metodo que realiza la composicion del combo que se requiere generar
                        controlCombos = false;
                    }

                    componerCombo(pdao.buscarProductoByNombre(nombreProd), cantProd);
                    //fin de la composicion

                    JOptionPane.showMessageDialog(null, "--->" + stokProd + "--" + nombreProd, "Mensaje", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No tiene suficiente stock..!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "El producto no corresponde al combo seleccionado..!!", "Mensaje", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ocurrio un problema contacte con el soporte técnico..!!" + ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed

        try {
// TODO add your handling code here:
            DefaultTableModel modelo = (DefaultTableModel) tablaComboDetalleVenta.getModel();
            if (tablaComboDetalleVenta.getRowCount() > 0) {
                if (tablaComboDetalleVenta.getSelectedRows().length != 0) {
                    //--------------recuperando el idProducto y la cantidad del producto
                    int cantidadProd = Integer.parseInt(String.valueOf(tablaComboDetalleVenta.getValueAt(tablaComboDetalleVenta.getSelectedRow(), 1)));//cantidad del producto
                    String producto = String.valueOf(tablaComboDetalleVenta.getValueAt(tablaComboDetalleVenta.getSelectedRow(), 2));//idProductoproducto 
                    int idProducto = new productoDao().buscarProductoByNombre(producto);
                    JOptionPane.showMessageDialog(null, "--->---" + cantidadProd, "Mensaje..", JOptionPane.WARNING_MESSAGE);

                    if (composicion.containsKey(idProducto)) {

                        int valor = (int) composicion.get(idProducto) - cantidadProd;
                        if (valor > 0) {
                            composicion.put(idProducto, valor);
                        } else {
                            composicion.remove(idProducto);
                        }
                        btnAgregarCarro.setEnabled(false);
                    }

                    //--------fin de la recuperacion 
                    modelo.removeRow(tablaComboDetalleVenta.getSelectedRow());

                    if (tablaComboDetalleVenta.getRowCount() == 0) {
                        tablaCombo.setEnabled(true);
                        SpCantidadCombos.setEnabled(true);
                        composicion.clear();//limpiando el HashMap
                        CantidadesCombo.clear();//limpiando la estructura de cantidades totales de los items del combo seleccionado
                        controlCombos = true;
                        btnAgregarCarro.setEnabled(false);
                    }

                    for (Object key : composicion.keySet()) {
                        System.out.println("ID: " + key + ", CANTIDAD: " + composicion.get(key));
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione el producto a eliminar ", "Mensaje..", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                tablaCombo.setEnabled(true);
                JOptionPane.showMessageDialog(null, "No tiene productos a eliminar..!! ", "Mensaje..", JOptionPane.ERROR_MESSAGE);

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la fila seleccionada..!! " + ex.getMessage(), "Mensaje..", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        //Recorriendo la estructura que contiene las cantitades totales del combo a verificar
        try {

            if (tablaComboDetalleVenta.getRowCount() > 0) {
                int verificador = 0;
                if (CantidadesCombo.size() == composicion.size() && CantidadesCombo.keySet().equals(composicion.keySet())) {
                    //recorriendo loa valores comparar los valores
                    for (Object key : CantidadesCombo.keySet()) {
                        if (!CantidadesCombo.get(key).equals(composicion.get(key))) {
                            verificador = 1;
                        }
                    }
                    if (verificador == 0) {
                        JOptionPane.showMessageDialog(null, "Validacion correcta..!!", "Mensaje..", JOptionPane.INFORMATION_MESSAGE);
                        btnAgregarCarro.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Revise las cantidades adicionadas", "Mensaje..", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Revise las cantidades adicionadas", "Mensaje..", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No tiene productos a validar..!!", "Mensaje..", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error contacte con el  adminisrador del sistema..!!" + ex.getMessage(), "Mensaje..", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void btnAgregarCarroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCarroActionPerformed

        DefaultTableModel modelo = (DefaultTableModel) vtnVentas.tablaVenta.getModel();//creando el modelo pára llenar los datos al JTabble
        //realizando la consulta para realizar el listado de los datos
        Object[] fila = new Object[modelo.getColumnCount()];
        int cant = Integer.parseInt(SpCantidadCombos.getValue().toString());//recuperando la cantidad de los combos formados
        fila[0] = String.valueOf(tablaCombo.getValueAt(tablaCombo.getSelectedRow(), 0));//ID PRODUCTO
        fila[1] = String.valueOf(tablaCombo.getValueAt(tablaCombo.getSelectedRow(), 1));//Nombre combo 
        fila[2] = "---";//color 

        fila[3] = cant;

        double precio = Double.parseDouble(String.valueOf(tablaCombo.getValueAt(tablaCombo.getSelectedRow(), 3)));
        fila[4] = precio; //precio prducto

        double subtotal = cant * precio;

        fila[5] = String.valueOf(subtotal);

        modelo.addRow(fila);//adicionando la fila a la tabla
        suma(modelo);
        generarPagoTotal();
        DialogoCombo.dispose();
        tablaCombo.setEnabled(true);
        txtproducto.setText("");//limpiando el campo de busqueda del producto Dialogo Combo
        Validaciones.limpiarTabla(tablaDetalleCombo);
        Validaciones.limpiarTabla(tablaComboDetalleVenta);
        SpCantidadProductoCombo.setValue(0);
        SpCantidadCombos.setValue(0);
        SpCantidadCombos.setEnabled(true);

    }//GEN-LAST:event_btnAgregarCarroActionPerformed

    private void DialogoComboWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_DialogoComboWindowClosing
        // TODO add your handling code here:
        tablaCombo.setEnabled(true);
        txtproducto.setText("");//limpiando el campo de busqueda del producto Dialogo Combo
        Validaciones.limpiarTabla(tablaDetalleCombo);
        Validaciones.limpiarTabla(tablaComboDetalleVenta);
        SpCantidadProductoCombo.setValue(0);
        SpCantidadCombos.setValue(0);
        SpCantidadCombos.setEnabled(true);
    }//GEN-LAST:event_DialogoComboWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog DialogoClientes;
    private javax.swing.JDialog DialogoCombo;
    private javax.swing.JDialog DialogoProductoVentas;
    private javax.swing.JLabel LBLiteral;
    private javax.swing.JSpinner SpCantidad;
    private javax.swing.JSpinner SpCantidadCombos;
    private javax.swing.JSpinner SpCantidadProductoCombo;
    private javax.swing.JButton btnAgregarCarro;
    private javax.swing.JButton btnCredito;
    public static javax.swing.JComboBox comboAlmacen;
    public static javax.swing.JComboBox comboAlmacen1;
    private javax.swing.JComboBox comboDocumento;
    private javax.swing.JComboBox comboPago;
    private javax.swing.JComboBox comboPago1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
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
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JLabel lbUsuario;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTable tablaCombo;
    private javax.swing.JTable tablaComboDetalleVenta;
    private javax.swing.JTable tablaDetalleCombo;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTable tablaProductosCombo;
    public static javax.swing.JTable tablaVenta;
    public static javax.swing.JTable tablaVenta1;
    private javax.swing.JTextField txtCiCliente;
    private javax.swing.JTextField txtCiCliente1;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtCliente1;
    private javax.swing.JTextField txtDescuentos;
    private com.toedter.calendar.JDateChooser txtFechaVenta;
    private com.toedter.calendar.JDateChooser txtFechaVenta1;
    private javax.swing.JTextField txtFindCiNit;
    private javax.swing.JTextField txtImpuestos;
    private javax.swing.JTextField txtMontoCuota;
    private javax.swing.JTextField txtMontoCuota1;
    private javax.swing.JTextField txtNit;
    private javax.swing.JTextField txtNit1;
    private javax.swing.JTextField txtNuCuotas;
    private javax.swing.JTextField txtNuCuotas1;
    private javax.swing.JTextField txtNuventa1;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtSubTotal1;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotal1;
    private javax.swing.JTextField txtTotalPago;
    private javax.swing.JTextField txtVendedor1;
    private javax.swing.JTextField txtproducto;
    // End of variables declaration//GEN-END:variables

    public HashMap getComposicion() {
        return composicion;
    }

    public void setComposicion(HashMap composicion) {
        this.composicion = composicion;
    }
}
