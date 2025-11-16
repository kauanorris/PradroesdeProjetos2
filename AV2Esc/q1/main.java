package q1;

public class main {
    public static void main(String[] args) {
        
        String dadosMercado = "Portfólio A: Ações tech, 10M USD. Taxa: 5%, Horizon: 1 dia.";

        Q1ContextoAnaliseRisco sistema = new Q1ContextoAnaliseRisco(
            new Q1VaRStrategy(),
            dadosMercado
        );

        sistema.executarCalculo();
        
        sistema.setEstrategia(new Q1ESStrategy());
        
        sistema.executarCalculo();

        sistema.setEstrategia(new Q1STStrategy());
        
        sistema.executarCalculo();
    }
}