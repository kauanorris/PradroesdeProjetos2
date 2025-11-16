package q4;

public class ValidadorSEFAZ extends AbstractValidador {
    public ValidadorSEFAZ() { super("V5: Serviço SEFAZ", 15000); }
    @Override
    protected boolean executarLogica(ContextoValidacao contexto) { return true; }
}