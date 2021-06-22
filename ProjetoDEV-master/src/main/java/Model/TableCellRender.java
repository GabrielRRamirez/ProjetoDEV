/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author gabri
 */
public class TableCellRender implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        JLabel label = new JLabel((value).toString());
        label.setFocusable(true);
        label.setOpaque(true); 
        label.setBackground(Color.white);
        label.setForeground(Color.black);
        label.setHorizontalAlignment(JLabel.CENTER);
        
        if(value.getClass().equals(Integer.class)){
            if(Integer.parseInt(value.toString())< 0){
                label.setForeground(Color.red);
            }
        }
        
        if(value.getClass().equals(Double.class)){
            if(Double.parseDouble(value.toString())< 0){
                label.setForeground(Color.red);
            }
        }
        
        if(isSelected){
            label.setBackground(Color.gray);
        }
        return label;
    }
    
}
