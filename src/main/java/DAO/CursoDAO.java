package DAO;

import Model.Curso;
import dbConnection.connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CursoDAO {

    public void inserir(Curso curso) {
        int linhasAfetadas = 0;
        Connection conn = null;
        PreparedStatement st = null;
        StringBuilder sql = null;

        try {
            sql = new StringBuilder();
            if (curso.getCodigo() > 0) {
                sql.append("UPDATE curso SET ");
                sql.append("  descricao= '" + curso.getDescricao() + "'");
                sql.append(" , ementa= '" + curso.getEmenta() + "' WHERE "
                        + "codigo =" + curso.getCodigo());
            } else {
                sql.append("INSERT INTO curso(descricao, ementa) VALUES");
                sql.append(" ('" + curso.getDescricao() + "',");
                sql.append("'" + curso.getEmenta() + "');");
            }
            conn = connection.getConnection();
            st = conn.prepareStatement(sql.toString());
            linhasAfetadas = st.executeUpdate();
            if(linhasAfetadas > 0){
                JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            connection.closeStatement(st);
        }
    }

    public void delete(int codigo) {
        int linhasAfetadas = 0;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            if (codigo > 0) {
                conn = connection.getConnection();
                st = conn.prepareStatement("SELECT codigo FROM curso_aluno WHERE "
                        + "codigo_curso ="+codigo);
                rs = st.executeQuery();
                
                if(rs.next()){
                    JOptionPane.showMessageDialog(null, "O Curso está vinculado "
                            + "à uma classe e não pode ser excluido!");
                }else{
                    st = conn.prepareStatement("DELETE FROM curso WHERE codigo = " + codigo);
                linhasAfetadas = st.executeUpdate();
                if(linhasAfetadas > 0){
                    JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
                }
                }
                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            connection.closeConnection();
        }
    }

    public List<Curso> getCursos(int codigo, String descricao) {
        List<Curso> cursos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        StringBuilder sql = null;

        try {
            sql = new StringBuilder();
            sql.append("SELECT * FROM curso ");
            if (codigo > 0 && !descricao.equals("")) {
                sql.append("WHERE codigo = " + codigo + " AND descricao like '%" + descricao + "%'");
            } else if (codigo > 0 && descricao.equals("")) {
                sql.append("WHERE codigo =" + codigo);
            } else if (codigo <= 0 && !descricao.equals("")) {
                sql.append("WHERE descricao like '%" + descricao + "%'");
            }

            conn = connection.getConnection();
            st = conn.prepareStatement(sql.toString());
            rs = st.executeQuery();

            while (rs.next()) {
                Curso curso = new Curso();
                curso.setCodigo(rs.getInt("codigo"));
                curso.setDescricao(rs.getString("descricao"));
                curso.setEmenta(rs.getString("ementa"));

                cursos.add(curso);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            connection.closeResultset(rs);
            connection.closeStatement(st);
        }
        return cursos;
    }

    public int criaTabela() {
        int retorno = 0;
        Connection conn = null;
        PreparedStatement st = null;
        String database = connection.getDatabaseName();
        StringBuilder sql = new StringBuilder();
        //cria tabela curso
        sql.append(" CREATE TABLE IF NOT EXISTS " + database + ".curso(");
        sql.append(" codigo INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,");
        sql.append("descricao VARCHAR(50) NOT NULL,");
        sql.append("ementa TEXT);");

        try {
            conn = connection.getConnection();
            st = conn.prepareStatement(sql.toString());
            retorno = st.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return retorno;
    }
}
