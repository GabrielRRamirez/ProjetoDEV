/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author gabri
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel implements Serializable{

    private List<String> columns = new ArrayList();
    private List<Object[]> rows = new ArrayList();
    private List<Integer> colunasEditaveis = new ArrayList();

    public TableModel() {
        columns = new ArrayList();
        rows = new ArrayList();

        colunasEditaveis = new ArrayList();
    }
    
    public TableModel clone() {
       TableModel clone = new TableModel();
       clone.columns = new ArrayList(this.columns);
       clone.colunasEditaveis = new ArrayList(this.colunasEditaveis);
       clone.rows = new ArrayList(this.rows);

       return clone;
   }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    public void addColumn(String i_column) {
        columns.add(i_column);
    }

    public void addRow(Object[] i_row) {
        rows.add(i_row);
        int ultimoIndice = getRowCount() - 1;

        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void setColunasEditaveis(List<Integer> i_colunasEditaveis) {
        this.colunasEditaveis = i_colunasEditaveis;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return colunasEditaveis.contains(col);
    }

    @Override
    public Class getColumnClass(int c) {
        try {
            if (getValueAt(0, c) == null) {
                return Object.class;
            }

            return getValueAt(0, c).getClass();

        } catch (Exception ex) {
            return Object.class;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rows.get(rowIndex)[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        rows.get(rowIndex)[columnIndex] = aValue;
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void removeRows(List<Integer> vLinhas) {
        for (int linha : vLinhas) {
            this.rows.remove(linha);
            this.fireTableRowsDeleted(linha, linha);
        }
    }

    public void removeRow(int i_linha) {
        this.rows.remove(i_linha);
        this.fireTableRowsDeleted(i_linha, i_linha);
    }

    public List<Object[]> getRows() {
        return rows;
    }
}