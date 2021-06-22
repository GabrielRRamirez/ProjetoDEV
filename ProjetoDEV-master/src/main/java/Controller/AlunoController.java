package Controller;

import DAO.AlunoDAO;
import Model.Aluno;
import java.util.List;

/**
 *
 * @author Samsung
 */
public class AlunoController {
    private AlunoDAO DAO = new AlunoDAO();
    private Aluno aluno = new Aluno();
    
    public Integer inserir(Aluno aluno){
        return DAO.inserir(aluno);
    }
    
    public void delete(int codigo){
        DAO.delete(codigo);
    }
    public List<Aluno>getAlunos(int codigo, String nome){
       return DAO.getAlunos(codigo, nome);
    } 
    
}
