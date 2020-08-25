/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.tabla.render;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import sun.swing.table.DefaultTableCellHeaderRenderer;

/**
 *
 * @author Reynaldo
 */
public class RenderTabla extends DefaultTableCellHeaderRenderer {

    private Font normal = new Font("Century Gothic", Font.PLAIN, 12);
    private Color color1 = new Color(255, 255, 255);
    private Color color2 = new Color(249, 249, 249);

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object value, boolean bln, boolean bln1, int i, int i1) {

        if (value instanceof JButton) {
            JButton btn = (JButton) value;
            return btn;
        }
        
        setEnabled(jtable == null || jtable.isEnabled());
        setBackground(Color.white);
        jtable.setFont(normal);

        setBackground((i % 2 == 1) ? this.color1 : this.color2);

        super.getTableCellRendererComponent(jtable, value, bln, bln1, i, i1);

        return super.getTableCellRendererComponent(jtable, value, bln, bln1, i, i1); //To change body of generated methods, choose Tools | Templates.
    }

}
