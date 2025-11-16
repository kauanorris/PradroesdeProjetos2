package q4;

import java.util.Arrays;
import java.util.List;

public class main {
    public static void main(String[] args) {
        
        // 1. Definição da Cadeia de Validadores na ordem requisitada
        List<Validador> validadores = Arrays.asList(
            new ValidadorSchema(),      // V1
            new ValidadorCertificado(), // V2
            new ValidadorFisco(),       // V3 (Condicional)
            new ValidadorBD(),          // V4 (Rollback)
            new ValidadorSEFAZ()        // V5 (Condicional)
        );
        
        // 2. Construção e Processamento
        CircuitBreakerChain cadeia = new CircuitBreakerChain(validadores, 3);
        
        // Para simular uma falha que exige Rollback:
        // Crie um Validador Certificado que retorna false, forçando o sistema a falhar após V4.
        
        NfeDocumento nf = new NfeDocumento("54321");
        
        System.out.println("--- 🚀 Iniciando Validação da NF " + nf.getNumero() + " ---");
        ContextoValidacao resultado = cadeia.processar(nf);
        
        // 3. Apresentação dos Resultados
        System.out.println("\n--- ✅ Resultados ---");
        System.out.println("Status Final: " + (resultado.getDocumento().isValido() ? "VÁLIDO" : "INVÁLIDO"));
        System.out.println("Falhas Acumuladas: " + resultado.getFalhasAcumuladas());
        System.out.println("Comandos que tentaram Rollback: " + resultado.getComandosExecutados().size());
        
        System.out.println("\n--- 📋 Log de Erros/Eventos ---");
        for (String log : resultado.getLogErros()) {
            System.out.println(log);
        }
    }
}