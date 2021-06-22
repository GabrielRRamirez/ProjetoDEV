package DAO;

import dbConnection.connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class TabelasDAO {

    public int criaTabelaCurso() {
        int retorno = 0;
        Connection conn = null;
        PreparedStatement st = null;
        StringBuilder sql = new StringBuilder();
        //cria tabela curso
        sql.append(" CREATE TABLE IF NOT EXISTS public.curso(");
        sql.append(" codigo SERIAL PRIMARY KEY,");
        sql.append("descricao VARCHAR(50) NOT NULL,");
        sql.append("ementa TEXT);");

        try {
            conn = connection.getConnection();
            st = conn.prepareStatement(sql.toString());
            st.executeUpdate();
            retorno = 1;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar tabela: /n" + ex);
            try{
                if(conn != null){
                    conn.rollback();
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Erro: \n" + e);
            }

        }
        return retorno;
    }

    public int criaTabelaUsuario() {
        int retorno = 0;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder();
        //cria tabela curso
        sql.append(" CREATE TABLE IF NOT EXISTS public.usuario(");
        sql.append(" id SERIAL PRIMARY KEY, ");
        sql.append("nome VARCHAR(50) NOT NULL, ");
        sql.append("login VARCHAR(50) NOT NULL,");
        sql.append("senha VARCHAR(50) NOT NULL);");

        try {
            conn = connection.getConnection();
            st = conn.prepareStatement(sql.toString());
            st.executeUpdate();

            st = conn.prepareStatement("SELECT id FROM public.usuario WHERE ID = 0");
            rs = st.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao criar tabela: /n" + ex);
        }
        
        try {
            if (!rs.next()) {
                sql = new StringBuilder();
                sql.append("INSERT INTO public.usuario(id,nome,login,senha)");
                sql.append("VALUES(");
                sql.append("0,'administrador','admin','admin');");
                st = conn.prepareStatement(sql.toString());
                st.executeUpdate();
                retorno = 1;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar tabela: /n" + ex);
        }
        return retorno;
    }

    public int criaTabelaAluno() {
        int retorno = 0;
        Connection conn = null;
        PreparedStatement st = null;
        StringBuilder sql = new StringBuilder();
        //cria tabela aluno
        sql.append("CREATE TABLE IF NOT EXISTS public.aluno(");
        sql.append("codigo SERIAL PRIMARY KEY ,");
        sql.append("nome VARCHAR(50) NOT NULL );");
        System.out.println(sql.toString());

        try {
            conn = connection.getConnection();
            st = conn.prepareStatement(sql.toString());
            st.executeUpdate();
            retorno =  1;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar tabela: /n" + ex);
        }
        return retorno;
    }

    public int criaTabelaCursoAluno() {
        int retorno = 0;
        Connection conn = null;
        PreparedStatement st = null;
        StringBuilder sql = new StringBuilder();
        //cria tabela curso_aluno
        sql.append(" CREATE TABLE IF NOT EXISTS public.curso_aluno(");
        sql.append(" codigo SERIAL PRIMARY KEY,");
        sql.append("codigo_aluno INTEGER NOT NULL,");
        sql.append("codigo_curso INTEGER NOT NULL);");

        try {
            conn = connection.getConnection();
            st = conn.prepareStatement(sql.toString());
            st.executeUpdate();
            
            //adiciona chaves estrangeiras
            sql = new StringBuilder();
            sql.append(" ALTER TABLE public.curso_aluno ");
            sql.append("ADD FOREIGN KEY(codigo_curso) REFERENCES public.curso(codigo);");
            st = conn.prepareStatement(sql.toString());
            retorno = st.executeUpdate();
            sql = new StringBuilder();
            sql.append(" ALTER TABLE public.curso_aluno ");
            sql.append("ADD FOREIGN KEY(codigo_aluno) REFERENCES public.aluno(codigo);");
            st = conn.prepareStatement(sql.toString());
            st.executeUpdate();
            retorno = 1;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar tabela: /n" + ex);
        }
        return retorno;
    }
}
