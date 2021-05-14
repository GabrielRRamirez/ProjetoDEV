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
               conn.commit();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            try{
                if(conn != null){
                    conn.rollback();
                }
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Erro: \n" + ex);
            }

        } finally {
            connection.closeStatement(st);
            connection.closeResultset(rs);
            
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
                JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
                conn.commit();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            try{
                if(conn != null){
                    conn.rollback();
                }
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Erro: \n" + e);
            }
        } finally {
            connection.closeStatement(st);
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
}
