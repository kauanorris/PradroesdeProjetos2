package q1;

public class Q1ContextoAnaliseRisco {
    private Q1EstrategiaRisco estrategiaAtual;
    private String contextoFinanceiroCompartilhado; 

    public Q1ContextoAnaliseRisco(Q1EstrategiaRisco estrategiaInicial, String contextoFinanceiro) {
        this.estrategiaAtual = estrategiaInicial;
        this.contextoFinanceiroCompartilhado = contextoFinanceiro;
        System.out.println("--- Contexto Inicializado ---");
        System.out.println("Dados financeiros complexos: " + contextoFinanceiro);
        System.out.println("Estratégia inicial: " + estrategiaInicial.getClass().getSimpleName());
        System.out.println("-----------------------------");
    }

    public void setEstrategia(Q1EstrategiaRisco novaEstrategia) {
        this.estrategiaAtual = novaEstrategia;
        System.out.println("\n*** Estratégia alterada para: " + novaEstrategia.getClass().getSimpleName() + " ***");
    }

    public void executarCalculo() {
        String resultado = estrategiaAtual.calcularRisco(contextoFinanceiroCompartilhado);
        System.out.println("Resultado da Análise: " + resultado);
    }
}