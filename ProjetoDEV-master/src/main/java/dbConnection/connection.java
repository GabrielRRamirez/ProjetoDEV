package dbConnection;

import DAO.TabelasDAO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.swing.JOptionPane;

public class connection {

    public static Connection conn = null;
    private static String url = null;
    private static Properties props = new Properties();

    public static Connection getConnection() throws SQLException {
        if (conn == null) {
            try {
                url = "jdbc:postgresql://" + props.getProperty("database.ip") + ":" + props.getProperty("database.porta") + "/" + getDatabaseName();
                conn = DriverManager.getConnection(url, props);
                conn.setAutoCommit(false);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "A tentativa de Conexao Falhou!");
                conn = null;
            }
        }
        return conn;
    }

    public static boolean loadProperties() {
        boolean retorno;
        try (FileInputStream fs = new FileInputStream("c:\\configuracao\\banco.properties")) {
            props.load(fs);
            retorno = true;
        } catch (IOException e) {
            retorno = false;
        }
        return retorno;
    }

    //validação de arquivo
    public static boolean validaArquivo() {
        boolean retorno;
        try (FileInputStream fs = new FileInputStream("c:\\configuracao\\banco.properties")) {
            retorno = true;
        } catch (IOException e) {
            retorno = false;
        }
        return retorno;
    }

    //criação de arquivo
    public static boolean criaArquivo(String ip, String bancoUser, String bancoPswd, String bancoNome, String porta) {
        File pasta = new File("c:\\configuracao");
        String arquivo = "c:\\configuracao\\banco.properties";
        boolean retorno = false;
        if (!pasta.exists()) {
            pasta.mkdir();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            bw.write("useSSL = false");
            bw.newLine();
            bw.write("database.ip=" + ip);
            bw.newLine();
            bw.write("database.nome=" + bancoNome);
            bw.newLine();
            bw.write("database.porta=" + porta);
            bw.newLine();
            bw.write("password=" + bancoPswd);
            bw.newLine();
            bw.write("user=" + bancoUser);
            retorno = true;
        } catch (IOException e) {
            retorno = false;
        }
        return retorno;
    }

    public static boolean validaBanco() {
        boolean retorno = false;
        PreparedStatement st = null;
        ResultSet rs = null;
        StringBuilder sql = null;

        url = "jdbc:postgresql://" + props.getProperty("database.ip") + ":" + props.getProperty("database.porta") + "/";

        try {
            conn = DriverManager.getConnection(url, props);
            st = conn.prepareStatement("SELECT * FROM pg_database WHERE datname = '" + getDatabaseName() + "'");
            rs = st.executeQuery();
            System.out.println(getDatabaseName());
            System.out.println("SELECT * FROM pg_database WHERE datname = '" + getDatabaseName() + "'");

            if (!rs.next()) {
                int escolha = JOptionPane.showConfirmDialog(null, "O Banco: "
                        + connection.getDatabaseName() + " Nao existe, deseja Criar?",
                        "Atencao!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (escolha == 0) {// cria o banco

                    try {
                        sql = new StringBuilder();
                        sql.append("CREATE DATABASE " + props.getProperty("database.nome"));
                        st = conn.prepareStatement(sql.toString());
                        st.executeUpdate();                                     //cria o banco
                        retorno = true;

                        url += props.getProperty("database.nome");
                        conn.close();                                           //fecha conexao antiga
                        conn = DriverManager.getConnection(url, props);

                        TabelasDAO tabelas = new TabelasDAO();
                        tabelas.criaTabelaAluno();
                        tabelas.criaTabelaCurso();
                        tabelas.criaTabelaCursoAluno();
                        tabelas.criaTabelaUsuario();

                        conn.setAutoCommit(false);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erro Ao Criar banco: /n" + ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Nao foi poss�vel conectar com o banco!");
                    
                }
            } else {
                url += props.getProperty("database.nome");
                conn.close();                                           //fecha conexão antiga
                conn = DriverManager.getConnection(url, props);        //tenta conectar com a nova url
                conn.setAutoCommit(false);
                retorno = true;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "A tentativa de Conexao falhou!!\n" + ex);
        }
        return retorno;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                throw new dbException(e.getMessage());
            }
        }
    }

    public static void closeResultset(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            throw new dbException(e.getMessage());
        }
    }

    public static void closeStatement(Statement st) {
        try {
            if (st != null) {
                st.close();
            }

        } catch (SQLException e) {
            throw new dbException(e.getMessage());
        }
    }

    public static String getDatabaseName() {
        String databaseName = props.getProperty("database.nome");
        return databaseName;
    }
}
