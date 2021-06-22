
package DAO;

import Controller.AlunoController;
import Model.Aluno;
import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author gabri
 */
public class AlunoDAOTest {
    Aluno aluno = null;
    AlunoController controller = new AlunoController();
                
    @Test
    public void deveInserirAluno() throws SQLException {
        aluno = new Aluno();
        aluno.setNome("teste");
        int retorno = controller.inserir(aluno);
        
       Assertions.assertEquals(1, retorno);
    }
    
    
}
