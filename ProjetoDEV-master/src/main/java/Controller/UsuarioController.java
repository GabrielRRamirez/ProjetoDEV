package Controller;

import DAO.AlunoDAO;
import DAO.CursoAlunoDAO;
import DAO.CursoDAO;
import DAO.UsuarioDAO;
import Model.Usuario;
import dbConnection.connection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Samsung
 */
public class UsuarioController {

    UsuarioDAO DAO = new UsuarioDAO();
    CursoDAO cursoDAO = new CursoDAO();
    AlunoDAO alunoDAO = new AlunoDAO();
    CursoAlunoDAO cursoAlunoDAO = new CursoAlunoDAO();

    public void salvaUsuario(Usuario usuario) {
        DAO.insereUsuario(usuario);
    }

    public void deletaUsuario(int id) {
        DAO.deletaUsuario(id);
    }

    public List<Usuario> get(int id, String login) {
        return DAO.get(id, login);
    }

    public int validaUsuario(String login, String senha) {
        return DAO.validaUsuario(login, senha);
    }

}
