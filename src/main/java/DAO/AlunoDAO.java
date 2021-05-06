package DAO;

import Model.Aluno;
import dbConnection.connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AlunoDAO {

    public void inserir(Aluno aluno) {
        int linhasAfetadas = 0;
        Connection conn = null;
        PreparedStatement st = null;
        StringBuilder sql = null;

        try {
            sql = new StringBuilder();
            if (aluno.getCodigo() > 0) {
                sql.append("UPDATE aluno SET nome ='" + aluno.getNome() + "' "
                        + "WHERE codigo =" + aluno.getCodigo());
            } else {
                sql.append("INSERT INTO aluno(nome) VALUES ('" + aluno.getNome() + "')");
            }
            conn = connection.getConnection();
            st = conn.prepareStatement(sql.toString());
            linhasAfetadas = st.executeUpdate();
            if (linhasAfetadas > 0) {
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
                        + "codigo_aluno =" + codigo);
                rs = st.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "O Aluno está vinculado "
                            + "à uma classe e não pode ser excluido!");
                } else {
                    st = conn.prepareStatement("DELETE FROM aluno WHERE codigo = " + codigo);
                    linhasAfetadas = st.executeUpdate();
                    if (linhasAfetadas > 0) {
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

    public List<Aluno> getAlunos(int codigo, String nome) {
        List<Aluno> alunos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        StringBuilder sql = null;

        try {
            sql = new StringBuilder();
            sql.append("SELECT * FROM aluno ");
            if (codigo > 0 && !nome.equals("")) {
                sql.append("WHERE codigo = " + codigo + " AND nome like '%" + nome + "%'");
            } else if (codigo > 0 && nome.equals("")) {
                sql.append("WHERE codigo =" + codigo);
            } else if (codigo <= 0 && !nome.equals("")) {
                sql.append("WHERE nome like '%" + nome + "%'");
            }

            conn = connection.getConnection();
            st = conn.prepareStatement(sql.toString());
            rs = st.executeQuery();

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setCodigo(rs.getInt("codigo"));
                aluno.setNome(rs.getString("nome"));

                alunos.add(aluno);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            connection.closeResultset(rs);
            connection.closeStatement(st);
        }
        return alunos;
    }

    public int criaTabela() {
        int retorno = 0;
        Connection conn = null;
        PreparedStatement st = null;
        String database = connection.getDatabaseName();
        StringBuilder sql = new StringBuilder();
        //cria tabela aluno
        sql.append("CREATE TABLE IF NOT EXISTS " + database + ".aluno(");
        sql.append("codigo INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,");
        sql.append("nome VARCHAR(50) NOT NULL );");

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
