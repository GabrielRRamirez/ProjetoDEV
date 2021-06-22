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
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel{

    private List<Integer> colunasEditaveis = new ArrayList();
    private List<String> colunas = new ArrayList();
    private List<Object[]> linhas = new ArrayList();
    

    public TableModel() {
        colunas = new ArrayList();
        linhas = new ArrayList();
        colunasEditaveis = new ArrayList();
    }
    

    @Override
    public int getRowCount() {
        return linhas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.size();
    }

    public void addColumn(String coluna) {
        colunas.add(coluna);
    }

    public void addRow(Object[] linha) {
        linhas.add(linha);
        int ultimoIndice = getRowCount() - 1;

        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void setColunasEditaveis(List<Integer> colunasEditaveis) {
        this.colunasEditaveis = colunasEditaveis;
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return colunasEditaveis.contains(coluna);
    }

    @Override
    public Class getColumnClass(int coluna) {
        try {
            if (getValueAt(0, coluna) == null) {
                return Object.class;
            }

            return getValueAt(0, coluna).getClass();

        } catch (Exception ex) {
            return Object.class;
        }
    }

    @Override
    public String getColumnName(int coluna) {
        return colunas.get(coluna);
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        return linhas.get(linha)[coluna];
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {
        linhas.get(linha)[coluna] = valor;
        fireTableRowsUpdated(linha, linha);
    }

    public void removeRow(int linha) {
        this.linhas.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }

    public List<Object[]> getRows() {
        return linhas;
    }
}