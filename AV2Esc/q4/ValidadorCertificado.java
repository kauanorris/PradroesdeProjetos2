package q4;

public class ValidadorCertificado extends AbstractValidador {
    public ValidadorCertificado() { super("V2: Certificado Digital", 500); }
    @Override
    protected boolean executarLogica(ContextoValidacao contexto) { 
        // Simulação de SUCESSO. Para testar o rollback, simule uma falha aqui (return false).
        return true; 
    }
}