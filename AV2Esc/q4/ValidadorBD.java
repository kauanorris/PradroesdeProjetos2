package q4;

public class ValidadorBD extends AbstractValidador {
    
    private String idInserido; 
    
    public ValidadorBD() {
        super("V4: Banco de Dados (Duplicidade)", 10000); 
        this.modificaEstado = true; // Marca para rollback
    }
    
    @Override
    protected boolean executarLogica(ContextoValidacao contexto) throws Exception {
        // Simula pré-inserção da NF com status 'pendente'
        this.idInserido = "TEMP_ID_" + contexto.getDocumento().getNumero();
        return true; 
    }

    @Override
    public void reverter(ContextoValidacao contexto) {
        super.reverter(contexto);
        // Lógica real de DELETE FROM NF_TEMP WHERE ID = this.idInserido
        contexto.adicionarErro("  -> Ação: DELETE (rollback) da NF com ID temporário: " + this.idInserido);
    }
}