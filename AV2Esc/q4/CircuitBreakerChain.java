package q4;

import java.util.List;
import java.util.Collections;

public class CircuitBreakerChain {
    
    private Validador cabeca;
    private final int limiteFalhas;
    
    public CircuitBreakerChain(List<Validador> validadores, int limiteFalhas) {
        this.limiteFalhas = limiteFalhas;
        construirCadeia(validadores);
    }

    private void construirCadeia(List<Validador> validadores) {
        if (validadores == null || validadores.isEmpty()) {
            return;
        }
        
        this.cabeca = validadores.get(0);
        Validador atual = this.cabeca;
        
        for (int i = 1; i < validadores.size(); i++) {
            // Liga o validador atual ao próximo
            atual.setProximo(validadores.get(i));
            atual = validadores.get(i);
        }
    }
    
    public ContextoValidacao processar(NfeDocumento documento) {
        ContextoValidacao contexto = new ContextoValidacao(documento);
        Validador atual = this.cabeca;
        
        while (atual != null && contexto.getFalhasAcumuladas() < this.limiteFalhas) {
            
            // --- Lógica Condicional (V3 e V5 devem ser executados apenas se anteriores passarem) ---
            boolean ehValidadorCondicional = atual instanceof ValidadorFisco || atual instanceof ValidadorSEFAZ;
            
            if (ehValidadorCondicional && !contexto.getDocumento().isValido()) {
                contexto.adicionarErro(String.format("[FLUXO]: Pulando %s devido a falha anterior.", atual.getNome()));
                atual = ((AbstractValidador) atual).proximo; // Avança
                continue;
            }
            
            try {
                // Execução e controle de estado/falhas dentro do Validador
                atual.executar(contexto);
            } catch (Exception e) {
                // Caso alguma exceção crítica ocorra no processamento (além de timeout)
                contexto.adicionarErro(String.format("Exceção crítica no validador %s: %s", atual.getNome(), e.getMessage()));
                contexto.incrementarFalhas();
            }
            
            // --- Circuit Breaker ---
            if (contexto.getFalhasAcumuladas() >= this.limiteFalhas) {
                contexto.adicionarErro(String.format("🛑 CIRCUIT BREAKER ATIVADO! Limite de %d falhas alcançado.", this.limiteFalhas));
                contexto.getDocumento().setValido(false);
                break;
            }
            
            // Avança para o próximo
            atual = ((AbstractValidador) atual).proximo; 
        }
        
        // --- Rollback ---
        if (!contexto.getDocumento().isValido()) {
            // Reverte na ordem inversa da execução (LIFO)
            List<Validador> comandos = contexto.getComandosExecutados();
            Collections.reverse(comandos);
            
            for (Validador validador : comandos) {
                validador.reverter(contexto);
            }
        }
        
        return contexto;
    }
}