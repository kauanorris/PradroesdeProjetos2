package q1;

public class Q1STStrategy implements Q1EstrategiaRisco {
    @Override
    public String calcularRisco(String contexto) {
        return "Executando Stress Testing. Contexto recebido: [" + contexto + "]. Cenário: Crise de 2008. Perda Máxima Estimada: $25.000.000.";
    }
}