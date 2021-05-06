package Model;

public class CursoAluno {
    private int codigo;
    private int codAluno;
    private int codCurso;
    private String nomeAluno;
    private String descricaoCurso;
    

    public CursoAluno() {
    }

    public CursoAluno(int codigo, int codAluno, int codCurso) {
        this.codigo = codigo;
        this.codAluno = codAluno;
        this.codCurso = codCurso;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodAluno() {
        return codAluno;
    }

    public void setCodAluno(int codAluno) {
        this.codAluno = codAluno;
    }

    public int getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(int codCurso) {
        this.codCurso = codCurso;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getDescricaoCurso() {
        return descricaoCurso;
    }

    public void setDescricaoCurso(String descricaoCurso) {
        this.descricaoCurso = descricaoCurso;
    }
    
    
    
    
    
}
