package q1;

public class Q1ESStrategy implements Q1EstrategiaRisco {
    @Override
    public String calcularRisco(String contexto) {
        return "Calculando Expected Shortfall (ES). Contexto recebido: [" + contexto + "]. Resultado: $6.500.000 (média de perdas na cauda).";
    }
}