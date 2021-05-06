package Controller;

import DAO.CursoDAO;
import Model.Curso;
import java.util.List;

/**
 *
 * @author Samsung
 */
public class CursoController {
    private CursoDAO DAO = new CursoDAO();
    private Curso aluno = new Curso();
    
    public void inserir(Curso curso){
        DAO.inserir(curso);
    }
    
    public void delete(int codigo){
        DAO.delete(codigo);
    }
    public List<Curso>getCursos(int codigo, String descricao){
       return DAO.getCursos(codigo, descricao);
    } 
}
