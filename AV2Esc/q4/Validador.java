package q4;

import java.util.concurrent.TimeoutException;

public interface Validador {
    
    String getNome();
    boolean isModificaEstado();
    long getTimeoutMillis();
    
    boolean executar(ContextoValidacao contexto) throws TimeoutException;
    
    void reverter(ContextoValidacao contexto);
    
    Validador setProximo(Validador proximo);
}