package Aplicacao;

import View.DadosBanco;
import View.Login;
import dbConnection.connection;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Main {

    private static Connection conn = null;

    public static void main(String args[]) {
        //carrega tema
        try {
            System.out.println(System.getProperties());
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            JOptionPane.showConfirmDialog(null, "Erro", "Erro ao Carregar Tema",
                    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
        }

        //Valida se existe o properties
        if (connection.validaArquivo()) {
            //se o arquivo existe carrega o properties e chama a tela de login
            if (connection.loadProperties()) {
                //se conseguir ler o arquivo valida o banco
                if (connection.validaBanco()) {
                    Login frmLogin = new Login();
                    frmLogin.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Erro Ao Carregar Arquivo Properties!");
            }
            //se o arquivo não existir 
        } else {
            DadosBanco frmDadosBanco = new DadosBanco();
            frmDadosBanco.setVisible(true);
        }
    }
}
