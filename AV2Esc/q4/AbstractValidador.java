package q4;

import java.util.concurrent.TimeoutException;

public abstract class AbstractValidador implements Validador {
    
    protected String nome;
    protected Validador proximo;
    protected boolean modificaEstado = false;
    protected long timeoutMillis; 
    
    public AbstractValidador(String nome, long timeoutMillis) {
        this.nome = nome;
        this.timeoutMillis = timeoutMillis;
    }

    // --- Implementação da Interface ---
    @Override
    public String getNome() { return nome; }
    @Override
    public boolean isModificaEstado() { return modificaEstado; }
    @Override
    public long getTimeoutMillis() { return timeoutMillis; }
    
    @Override
    public Validador setProximo(Validador proximo) {
        this.proximo = proximo;
        return proximo;
    }

    @Override
    public boolean executar(ContextoValidacao contexto) throws TimeoutException {
        long startTime = System.currentTimeMillis();
        boolean sucesso = false;
        
        try {
            sucesso = executarLogica(contexto);
            long elapsed = System.currentTimeMillis() - startTime;
            
            if (elapsed > timeoutMillis) {
                throw new TimeoutException("Excedeu o timeout de " + timeoutMillis + "ms.");
            }
            
        } catch (Exception e) {
            sucesso = false;
            contexto.adicionarErro(String.format("[%s]: Falha na execução: %s", nome, e.getMessage()));
        }

        // 1. Gerenciamento de Estado
        if (sucesso) {
            if (this.modificaEstado) {
                contexto.adicionarComandoExecutado(this);
            }
        } else {
            contexto.incrementarFalhas();
        }
        
        return sucesso;
    }

    // Lógica específica de cada validador
    protected abstract boolean executarLogica(ContextoValidacao contexto) throws Exception;

    @Override
    public void reverter(ContextoValidacao contexto) {
        if (this.modificaEstado) {
            contexto.adicionarErro(String.format("[%s]: Executando Rollback... (Ação desfeita)", nome));
        }
    }
}