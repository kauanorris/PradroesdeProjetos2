package q4;

public class ValidadorFisco extends AbstractValidador {
    public ValidadorFisco() { super("V3: Regras Fiscais", 3000); }
    @Override
    protected boolean executarLogica(ContextoValidacao contexto) { return true; }
}