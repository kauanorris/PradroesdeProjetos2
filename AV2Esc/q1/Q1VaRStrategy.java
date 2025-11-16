package q1;

public class Q1VaRStrategy implements Q1EstrategiaRisco {
    @Override
    public String calcularRisco(String contexto) {
        return "Calculando Value at Risk (VaR). Contexto recebido: [" + contexto + "]. Resultado: $5.000.000 (99% conf.)";
    }
}