/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.core;

import com.pos.tabla.render.HeaderCellRenderer;
import com.pos.tabla.render.RenderEstiloUno;
import com.pos.tabla.render.RenderTabla;
import static com.sistema.view.vtnVentas.tablaVenta;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Reynaldo
 */
public class Validaciones {

    public static boolean validarCampos(List<String> campos) {
        for (String nombre : campos) {
            if (nombre.equals("")) {
                return false;
            }
        }
        return true;
    }

    //Funcion que da el formato a la fecha
    public static String formatoFecha(Date dateString) {
        Date date = null;
        String formatofecha = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //date = df.parse(dateString);
            formatofecha = df.format(dateString);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return formatofecha;
    }

    //metodo que realiza la validacion de un JtextFiel de ingreso de numeros
    public static void validaNumeros(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
    }

    //metodo que realiza la validacion de un JtextFiel de ingreso de texto
    public static void validaTexto(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
    }

    public static void limpiarTabla(JTable tabla) {
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

    public static BigDecimal redondear(double valor) {
        //double valor = 1254.625;
        String val = valor + "";
        BigDecimal big = new BigDecimal(val);
        big = big.setScale(1, RoundingMode.HALF_UP);
        //System.out.println("Número : " + big);
        return big;
    }

    //Metodo que realiza la redimension de la imagen recuperada de la BD
    public static BufferedImage dimensionarImagen(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    public void formatearNumero(double monto) {
        DecimalFormat formateador = new DecimalFormat("###,###.##");
        //System.out.println(formateador.format(1250.4));
        //System.out.println(formateador.format(100000.78));

        //return (formateador.format(monto));
    }

    public static void mejorarAparienciaTabla(JTable tabla) {
        tabla.setDefaultRenderer(Object.class, new RenderTabla());//renderizando la tabla
        tabla.setRowHeight(30);
        JTableHeader header = tabla.getTableHeader();
        header.setDefaultRenderer(new HeaderCellRenderer());
        tabla.setSelectionBackground(new Color(231, 247, 252));
        tabla.setSelectionForeground(new Color(0, 0, 0));
        tabla.setGridColor(new java.awt.Color(221, 221, 221));
    }
    
    public static void mejorarAparienciaTablaNormal(JTable tabla) {
        tabla.setDefaultRenderer(Object.class, new RenderEstiloUno());//renderizando la tabla
        tabla.setRowHeight(30);
        JTableHeader header = tabla.getTableHeader();
        header.setDefaultRenderer(new HeaderCellRenderer());
        tabla.setSelectionBackground(new Color(231, 247, 252));
        tabla.setSelectionForeground(new Color(0, 0, 0));
        tabla.setGridColor(new java.awt.Color(221, 221, 221));
    }
}
