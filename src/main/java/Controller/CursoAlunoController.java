package Controller;

import DAO.CursoAlunoDAO;
import Model.CursoAluno;
import java.util.List;

public class CursoAlunoController {

    CursoAlunoDAO DAO = new CursoAlunoDAO();

    public void inserir(CursoAluno cursoAluno) {
        DAO.inserir(cursoAluno);
    }

    public List<CursoAluno> get(int codigo, String pesquisa, int tipoConsulta) {
        return DAO.get(codigo, pesquisa, tipoConsulta);
    }

    public void delete(int codigo) {
        DAO.delete(codigo);
    }
}
