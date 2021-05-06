package DAO;

import Model.Usuario;
import dbConnection.connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    public void insereUsuario(Usuario usuario) {
        int linhasAfetadas = 0;
        int idSalvo = 0;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            conn = connection.getConnection();
            if(usuario.getCodigo()> 0){
                st = conn.prepareStatement("UPDATE usuario set "
                        + "nome = ?, login = ?,senha = ? WHERE id = ?");
                st.setString(1, usuario.getNome());
                st.setString(2, usuario.getLogin());
                st.setString(3, usuario.getSenha());
                st.setInt(4, usuario.getCodigo());
                linhasAfetadas = st.executeUpdate();
            }else{
                st = conn.prepareStatement("INSERT INTO usuario "
                    + "(nome, login, senha)"
                    + " values(?,?, ?)");

                st.setString(1, usuario.getNome());
                st.setString(2, usuario.getLogin());
                st.setString(3, usuario.getSenha());
                linhasAfetadas = st.executeUpdate();
            }
                
            if(linhasAfetadas > 0){
               JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        } finally {
            connection.closeStatement(st);
            connection.closeResultset(rs);
            connection.closeConnection();
        }
    }

    public void deletaUsuario(int id) {
        int linhasAfetadas = 0;
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = connection.getConnection();
            st = conn.prepareStatement("DELETE FROM usuario WHERE id = ?");
            st.setInt(1, id);
            linhasAfetadas = st.executeUpdate();
            if(linhasAfetadas > 0){
                JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            connection.closeStatement(st);
            connection.closeConnection();
        }
    }

    public List<Usuario> get(int id, String nome) {
        List<Usuario> lista = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement st = null;
        StringBuilder sql = null;

        sql = new StringBuilder();
        sql.append("SELECT * FROM usuario");

        if (id > 0 && !nome.equals("")) {
            sql.append(" WHERE nome like'%" + nome + "%' AND id =" + id);
        } else if (id > 0 && nome.equals("")) {
            sql.append(" WHERE id = " + id);
        } else if (id <= 0 && !nome.equals("")) {
            sql.append(" WHERE nome like'%" + nome + "%'");
        }

        try {
            conn = connection.getConnection();
            st = conn.prepareStatement(sql.toString());
            rs = st.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setCodigo(rs.getInt("id"));
                usuario.setLogin(rs.getString("login"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha"));

                if(usuario.getCodigo() > 0){
                    lista.add(usuario);
                }
            }
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            connection.closeResultset(rs);
            connection.closeStatement(st);
        }
        return lista;
    }

    public int validaUsuario(String login, String senha) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement st = null;
        int retorno = 0;

        try {
            conn = connection.getConnection();
            st = conn.prepareStatement("SELECT * FROM usuario WHERE login = '"
                    + login + "' and senha = '" + senha + "'");
            rs = st.executeQuery();
            if (rs.next()) {
                retorno = 1;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            connection.closeResultset(rs);
            connection.closeStatement(st);
        }
        return retorno;
    }

    public int criaTabela() {
        int retorno = 0;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String database = connection.getDatabaseName();
        StringBuilder sql = new StringBuilder();
        //cria tabela curso
        sql.append(" CREATE TABLE IF NOT EXISTS " + database + ".usuario(");
        sql.append(" id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, ");
        sql.append("nome VARCHAR(50) NOT NULL, ");
        sql.append("login VARCHAR(50) NOT NULL,");
        sql.append("senha VARCHAR(50) NOT NULL);");

        try {
            conn = connection.getConnection();
            st = conn.prepareStatement(sql.toString());
            retorno = st.executeUpdate();
            st = conn.prepareStatement("SELECT id FROM " + database + ".usuario "
                    + "WHERE id = 0 ");
            rs = st.executeQuery();

            if (!rs.next()) {
                sql = new StringBuilder();
                sql.append("INSERT INTO " + database + ".usuario(nome,login,senha)");
                sql.append("VALUES(");
                sql.append("'administrador','admin','admin');");
                st = conn.prepareStatement(sql.toString());
                retorno = st.executeUpdate();
                
                st =conn.prepareStatement("UPDATE usuario set id = 0 where id = 1");
                retorno = st.executeUpdate();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return retorno;
    }
}
