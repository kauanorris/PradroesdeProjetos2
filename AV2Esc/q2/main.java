package q2;
import java.util.HashMap;

// ----------------------------------------------------------------------
// 1. Classes de Dados Atualizadas
// ----------------------------------------------------------------------

// Objeto de Resposta Atualizado (para o sistema cliente)
class RespostaTransacao {
    private String idTransacao;
    private boolean sucesso;
    private String mensagem;

    public RespostaTransacao(String idTransacao, boolean sucesso, String mensagem) {
        this.idTransacao = idTransacao;
        this.sucesso = sucesso;
        this.mensagem = mensagem;
    }
    
    @Override
    public String toString() {
        return "ID: " + idTransacao + ", Sucesso: " + sucesso + ", Mensagem: " + mensagem;
    }
}

// ----------------------------------------------------------------------
// 2. Interface Moderna (Target)
// ----------------------------------------------------------------------

interface ProcessadorTransacoes {
    RespostaTransacao autorizar(String cartao, double valor, String moeda);
}

// ----------------------------------------------------------------------
// 3. Sistema Legado (Adaptee)
// ----------------------------------------------------------------------

// Classe que simula o sistema legado
class SistemaBancarioLegado {

    // Método legado para processar a transação (requer HashMap)
    public HashMap<String, Object> processarTransacao(HashMap<String, Object> parametros) {
        System.out.println("--- Executando Transação no Legado ---");
        
        if (!parametros.containsKey("ID_SESSAO")) {
            System.err.println("ERRO: Campo obrigatório 'ID_SESSAO' ausente!");
            HashMap<String, Object> erro = new HashMap<>();
            erro.put("STATUS_CODE", 500);
            erro.put("MSG_ERRO", "ID_SESSAO é obrigatório");
            return erro;
        }
        String cartao = (String) parametros.get("NUM_CARTAO");
        Double valor = (Double) parametros.get("QUANTIA");
        Integer codigoMoeda = (Integer) parametros.get("COD_MOEDA");

        System.out.printf("Transação: Cartão=%s, Valor=%.2f, CodMoeda=%d%n", cartao, valor, codigoMoeda);
        System.out.println("ID de Sessão do Legado: " + parametros.get("ID_SESSAO"));
        
        HashMap<String, Object> resultadoLegado = new HashMap<>();
        resultadoLegado.put("STATUS_CODIGO", 200);
        resultadoLegado.put("TXN_ID", "TXN-" + System.currentTimeMillis());
        resultadoLegado.put("AUTORIZADO", true);
        
        System.out.println("--- Retorno do Legado Gerado ---");
        return resultadoLegado;
    }
}

// ----------------------------------------------------------------------
// 4. O Adaptador (Adapter)
// ----------------------------------------------------------------------

class LegadoTransacaoAdapter implements ProcessadorTransacoes {

    private final SistemaBancarioLegado legado;
    
    private static final HashMap<String, Integer> CODIFICACAO_MOEDA = new HashMap<>();
    static {
        CODIFICACAO_MOEDA.put("USD", 1);
        CODIFICACAO_MOEDA.put("EUR", 2);
        CODIFICACAO_MOEDA.put("BRL", 3);
    }

    public LegadoTransacaoAdapter(SistemaBancarioLegado legado) {
        this.legado = legado;
    }

    @Override
    public RespostaTransacao autorizar(String cartao, double valor, String moeda) {
        
        HashMap<String, Object> parametrosLegado = adaptarRequest(cartao, valor, moeda);
        
        HashMap<String, Object> resultadoLegado = legado.processarTransacao(parametrosLegado);
        
        return adaptarResponse(resultadoLegado);
    }

    private HashMap<String, Object> adaptarRequest(String cartao, double valor, String moeda) {
        System.out.println("\n*** Adaptando Request (Moderno -> Legado) ***");
        HashMap<String, Object> parametros = new HashMap<>();
        
        parametros.put("NUM_CARTAO", cartao); 
        parametros.put("QUANTIA", valor);

        Integer codMoeda = CODIFICACAO_MOEDA.getOrDefault(moeda.toUpperCase(), 0);
        parametros.put("COD_MOEDA", codMoeda);
        
        parametros.put("ID_SESSAO", "SESSAO-" + System.nanoTime()); 
        
        return parametros;
    }
    
    private RespostaTransacao adaptarResponse(HashMap<String, Object> resultadoLegado) {
        System.out.println("\n*** Adaptando Response (Legado -> Moderno) ***");

        if (resultadoLegado.containsKey("STATUS_CODIGO") && (Integer)resultadoLegado.get("STATUS_CODIGO") == 200) {
        
            String id = (String) resultadoLegado.getOrDefault("TXN_ID", "N/A");
            boolean autorizado = (Boolean) resultadoLegado.getOrDefault("AUTORIZADO", false);
            return new RespostaTransacao(id, autorizado, "Transação processada com sucesso no legado.");
            
        } else {
        
            String mensagemErro = (String) resultadoLegado.getOrDefault("MSG_ERRO", "Falha desconhecida no sistema legado.");
            return new RespostaTransacao("FALHA-000", false, "Erro Legado: " + mensagemErro);
        }
    }
}

// ----------------------------------------------------------------------
// 5. Classe Principal para Execução (q2/main.java)
// ----------------------------------------------------------------------

public class main {
    public static void main(String[] args) {
        

        SistemaBancarioLegado legado = new SistemaBancarioLegado();
        
        ProcessadorTransacoes processador = new LegadoTransacaoAdapter(legado);
        
        System.out.println("==================================================");
        System.out.println("✅ Execução do Adapter: Transação em USD");
        System.out.println("==================================================");
        
        RespostaTransacao respostaSucesso = processador.autorizar("1234-5678-9012-3456", 150.75, "USD");
        
        System.out.println("\n** RESULTADO FINAL (FORMATO MODERNO): **");
        System.out.println(respostaSucesso);
    }
}