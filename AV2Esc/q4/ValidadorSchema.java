package q4;

public class ValidadorSchema extends AbstractValidador {
    public ValidadorSchema() { super("V1: Schema XML (XSD)", 2000); }
    @Override
    protected boolean executarLogica(ContextoValidacao contexto) { return true; }
}