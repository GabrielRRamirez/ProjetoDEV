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

    public void inserir(CursoAluno cursoAluno) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
                }
            } else {
                st = conn.prepareStatement("SELECT codigo FROM curso_aluno "
                        + "WHERE codigo_aluno =" + cursoAluno.getCodAluno() + " AND "
                        + "codigo_curso =" + cursoAluno.getCodCurso());
                rs = st.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Curso já cadastrado "
                            + "para o aluno " + cursoAluno.getCodAluno());
                } else {
                    st = conn.prepareStatement("INSERT INTO curso_aluno "
                            + "(codigo_aluno, codigo_curso) VALUES "
                            + "(" + cursoAluno.getCodAluno() + ", " + cursoAluno.getCodCurso() + ");");
                    retorno = st.executeUpdate();
                    if (retorno > 0) {
                        JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            connection.closeResultset(rs);
            connection.closeStatement(st);
        }
    }

    public List<CursoAluno> get(int codigo, String pesquisa, int consulta) {
        List<CursoAluno> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
        }
        return lista;
    }

    public void delete(int codigo) {
        Connection conn = null;
        PreparedStatement st = null;
        int retorno = 0;

        try {
            conn = connection.getConnection();
            st = conn.prepareStatement("DELETE FROM curso_aluno WHERE codigo =" + codigo);
            retorno = st.executeUpdate();
            if (retorno > 0) {
                JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao salvar!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            connection.closeStatement(st);
        }
    }

    public int criaTabela() {
        int retorno = 0;
        Connection conn = null;
        PreparedStatement st = null;
        String database = connection.getDatabaseName();
        StringBuilder sql = new StringBuilder();
        //cria tabela curso_aluno
        sql.append(" CREATE TABLE IF NOT EXISTS " + database + ".curso_aluno(");
        sql.append(" codigo INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,");
        sql.append("codigo_aluno INTEGER NOT NULL,");
        sql.append("codigo_curso INTEGER NOT NULL);");

        try {
            conn = connection.getConnection();
            st = conn.prepareStatement(sql.toString());
            st.executeUpdate();
            //adiciona chaves estrangeiras
            sql = new StringBuilder();
            sql.append(" ALTER TABLE " + database + ".curso_aluno ");
            sql.append("ADD FOREIGN KEY(codigo_curso) REFERENCES " + database + ".curso(codigo);");
            st = conn.prepareStatement(sql.toString());
            retorno = st.executeUpdate();
            sql = new StringBuilder();
            sql.append(" ALTER TABLE " + database + ".curso_aluno ");
            sql.append("ADD FOREIGN KEY(codigo_aluno) REFERENCES " + database + ".aluno(codigo);");
            st = conn.prepareStatement(sql.toString());
            retorno = st.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return retorno;
    }
}
