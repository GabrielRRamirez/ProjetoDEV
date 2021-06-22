package DAO;

import Model.CursoAluno;
import dbConnection.connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CursoAlunoDAO {

    Connection conn = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    
    public void inserir(CursoAluno cursoAluno) {
        
        int retorno = 0;

        try {
            conn = connection.getConnection();
            if (cursoAluno.getCodigo() > 0) {
                st = conn.prepareStatement("UPDATE curso_aluno SET"
                        + " codigo_curso = " + cursoAluno.getCodCurso() + " ,"
                        + "codigo_aluno =" + cursoAluno.getCodAluno() + " WHERE codigo ="
                        + cursoAluno.getCodigo());
                retorno = st.executeUpdate();
                if (retorno > 0) {
                    JOptionPane.showMessageDialog(null, "Salvo com Sucesso!");
                    conn.commit();
                }
            } else {
                st = conn.prepareStatement("SELECT codigo FROM curso_aluno "
                        + "WHERE codigo_aluno =" + cursoAluno.getCodAluno() + " AND "
                        + "codigo_curso =" + cursoAluno.getCodCurso());
                rs = st.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Curso ja¡ cadastrado "
                            + "para o aluno " + cursoAluno.getCodAluno());
                } else {
                    st = conn.prepareStatement("INSERT INTO curso_aluno "
                            + "(codigo_aluno, codigo_curso) VALUES "
                            + "(" + cursoAluno.getCodAluno() + ", " + cursoAluno.getCodCurso() + ");");
                    retorno = st.executeUpdate();
                    if (retorno > 0) {
                        JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
                        conn.commit();
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            try{
                if(conn != null){
                    conn.rollback();
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Erro: \n" + e);
            }
        } finally {
            connection.closeResultset(rs);
            connection.closeStatement(st);
            conn = null;
        }
    }

    public List<CursoAluno> get(int codigo, String pesquisa, int consulta) {
        List<CursoAluno> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ca.*,a.nome,c.descricao FROM curso_aluno ca ");
        sql.append("INNER JOIN aluno a on a.codigo = ca.codigo_aluno ");
        sql.append("INNER JOIN curso c on c.codigo = ca.codigo_curso ");

        if (codigo > 0 && !pesquisa.equals("") && consulta == 1) {
            sql.append("WHERE a.codigo =" + codigo + "AND a.nome like '%" + pesquisa + "%'");
        } else if (codigo <= 0 && !pesquisa.equals("") && consulta == 1) {
            sql.append(" WHERE a.nome like '%" + pesquisa + "%'");
        } else if (codigo > 0 && pesquisa.equals("") && consulta == 1) {
            sql.append(" WHERE a.codigo =" + codigo);
        } else if (codigo > 0 && !pesquisa.equals("") && consulta == 2) {
            sql.append(" WHERE c.codigo =" + codigo + "AND c.descricao like '%" + pesquisa + "%'");
        } else if (codigo <= 0 && !pesquisa.equals("") && consulta == 2) {
            sql.append(" WHERE c.descricao like '%" + pesquisa + "%'");
        } else if (codigo > 0 && pesquisa.equals("") && consulta == 2) {
            sql.append(" WHERE c.codigo =" + codigo);
        } else if (codigo > 0 && consulta == 3) {
            sql.append(" WHERE ca.codigo = " + codigo);
        }

        try {
            sql.append(" ORDER BY ca.codigo");
            conn = connection.getConnection();
            st = conn.prepareStatement(sql.toString());
            rs = st.executeQuery();

            while (rs.next()) {
                CursoAluno cursoAluno = new CursoAluno();
                cursoAluno.setCodigo(rs.getInt("codigo"));
                cursoAluno.setCodCurso(rs.getInt("codigo_curso"));
                cursoAluno.setCodAluno(rs.getInt("codigo_aluno"));
                cursoAluno.setDescricaoCurso(rs.getString("descricao"));
                cursoAluno.setNomeAluno(rs.getString("nome"));

                lista.add(cursoAluno);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            connection.closeResultset(rs);
            connection.closeStatement(st);
            conn = null;
        }
        return lista;
    }

    public void delete(int codigo) {
        int retorno = 0;

        try {
            conn = connection.getConnection();
            st = conn.prepareStatement("DELETE FROM curso_aluno WHERE codigo =" + codigo);
            retorno = st.executeUpdate();
            if (retorno > 0) {
                JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
                conn.commit();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao excluir!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            try{
                if(conn != null){
                    conn.rollback();
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Erro: \n" + e);
            }
        } finally {
            connection.closeStatement(st);
            conn = null;
        }
    }
}
