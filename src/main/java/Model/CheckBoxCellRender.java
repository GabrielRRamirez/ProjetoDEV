/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author gabri
 */
public class CheckBoxCellRender extends JCheckBox implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        setHorizontalAlignment(JLabel.CENTER);
        this.setFocusable(true);
        this.setOpaque(true); 
        this.setBackground(Color.white);
        this.setForeground(Color.black);
        
         if(isSelected){
            this.setBackground(Color.gray);
        }
         
         if(value.getClass().equals(Boolean.class)){
             if((Boolean)value){
                 setSelected(true); 
             }else{
                 setSelected(false);
             }
             
         }
         
         
         
         this.addActionListener(actionListener);
        
        return this;
    }
    
}
