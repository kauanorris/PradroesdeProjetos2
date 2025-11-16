package q4;

import java.util.ArrayList;
import java.util.List;

// NfeDocumento.java
class NfeDocumento {
    private String numero;
    private boolean valido = true;
    
    public NfeDocumento(String numero) {
        this.numero = numero;
    }
    
    public boolean isValido() { return valido; }
    public void setValido(boolean valido) { this.valido = valido; }
    public String getNumero() { return numero; }
}

