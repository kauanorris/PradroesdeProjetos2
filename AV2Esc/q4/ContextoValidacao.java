package q4;

import java.util.ArrayList;
import java.util.List;

// ContextoValidacao.java
public class ContextoValidacao {
    
    private final NfeDocumento documento;
    private int falhasAcumuladas = 0;
    private final List<String> logErros = new ArrayList<>();
    private final List<Validador> comandosExecutados = new ArrayList<>(); 
    
    public ContextoValidacao(NfeDocumento documento) {
        this.documento = documento;
    }

    // --- Métodos de Controle ---
    public void incrementarFalhas() {
        this.falhasAcumuladas++;
        this.documento.setValido(false);
    }
    
    public void adicionarErro(String erro) {
        this.logErros.add(erro);
    }
    
    public void adicionarComandoExecutado(Validador validador) {
        this.comandosExecutados.add(validador);
    }

    // --- Getters ---
    public NfeDocumento getDocumento() { return documento; }
    public int getFalhasAcumuladas() { return falhasAcumuladas; }
    public List<String> getLogErros() { return logErros; }
    public List<Validador> getComandosExecutados() { return comandosExecutados; }
}