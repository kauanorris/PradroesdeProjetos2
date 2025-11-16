package q3;

enum EstadoUsina {
    DESLIGADA,
    OPERACAO_NORMAL,
    ALERTA_AMARELO,
    ALERTA_VERMELHO,
    EMERGENCIA,
    MANUTENCAO
}

// --- 2. Classe Principal da FSM ---
class ControleUsina {
    private EstadoUsina estadoAtual;
    private double temperaturaC;
    private long tempoAlertaVermelho; 

    public ControleUsina() {
        this.estadoAtual = EstadoUsina.DESLIGADA;
        this.temperaturaC = 25.0; 
        this.tempoAlertaVermelho = 0;
        System.out.println("Sistema de Controle Inicializado. Estado: " + estadoAtual);
    }

    public void verificarETransitar(double novaTemperatura) {
        this.temperaturaC = novaTemperatura;

        if (estadoAtual == EstadoUsina.MANUTENCAO) {
            System.out.println("⚠️ Estado em MANUTENÇÃO. Ignorando parâmetros de operação.");
            return;
        }

        EstadoUsina proximoEstado = estadoAtual;

        switch (estadoAtual) {
            case OPERACAO_NORMAL:
                if (temperaturaC > 300) {
                    proximoEstado = EstadoUsina.ALERTA_AMARELO;
                    System.out.println("🚨 Temperatura excedeu 300°C.");
                }
                break;

            case ALERTA_AMARELO:
                if (temperaturaC <= 300) {
                    proximoEstado = EstadoUsina.OPERACAO_NORMAL;
                }
                else if (temperaturaC > 400) {
                    if (tempoAlertaVermelho == 0) {
                        tempoAlertaVermelho = System.currentTimeMillis(); 
                        System.out.println("🔥 Temperatura > 400°C. Timer iniciado.");
                    }
                    long duracao = System.currentTimeMillis() - tempoAlertaVermelho;
                    if (duracao >= 30000) { 
                        proximoEstado = EstadoUsina.ALERTA_VERMELHO;
                        System.out.println("❌ 30 segundos de alta temperatura. Passando para ALERTA_VERMELHO.");
                        tempoAlertaVermelho = 0;
                    }
                } else {
                    tempoAlertaVermelho = 0; 
                }
                break;

            case ALERTA_VERMELHO:
                if (simularFalhaResfriamento(temperaturaC)) { 
                    proximoEstado = EstadoUsina.EMERGENCIA;
                    System.out.println("☢️ Falha Crítica no Resfriamento. Estado: EMERGÊNCIA.");
                }
                else if (temperaturaC < 400) {
                    proximoEstado = EstadoUsina.ALERTA_AMARELO;
                    System.out.println("✅ Recuperação. Retornando para ALERTA_AMARELO.");
                }
                break;
            
            default:
                break;
        }
        if (proximoEstado != estadoAtual) {
            setEstado(proximoEstado);
        }
    }

    private boolean simularFalhaResfriamento(double temp) {
        return temp > 450; 
    }
    
    // --- Comandos de Controle ---

    public void iniciarOperacao() {
        if (estadoAtual == EstadoUsina.DESLIGADA) {
            setEstado(EstadoUsina.OPERACAO_NORMAL); 
        }
    }

    public void ativarManutencao() {
        if (estadoAtual == EstadoUsina.DESLIGADA || estadoAtual == EstadoUsina.OPERACAO_NORMAL) {
            setEstado(EstadoUsina.MANUTENCAO); 
        }
    }

    public void concluirEmergencia() {
        if (estadoAtual == EstadoUsina.EMERGENCIA) {
            setEstado(EstadoUsina.DESLIGADA); 
        }
    }

    // --- Auxiliares ---

    private void setEstado(EstadoUsina novoEstado) {
        System.out.println("\n*** Transição de Estado: " + this.estadoAtual + " -> " + novoEstado + " ***");
        this.estadoAtual = novoEstado;
        this.tempoAlertaVermelho = 0; 
    }
}

// --- 3. Classe Principal com o método main ---
public class main {
    public static void main(String[] args) throws InterruptedException {
        ControleUsina usina = new ControleUsina();
        usina.iniciarOperacao();

        System.out.println("\n--- Simulação de Elevação de Temperatura ---");

        usina.verificarETransitar(250.0);
        
        usina.verificarETransitar(350.0);

        usina.verificarETransitar(420.0);
        
        System.out.println("Aguardando 35 segundos para forçar ALERTA_VERMELHO...");
        Thread.sleep(35000); 
        usina.verificarETransitar(420.0);

        System.out.println("\n--- Simulação de Falha de Resfriamento ---");
        usina.verificarETransitar(460.0);
        
        System.out.println("\n--- Simulação do Modo MANUTENÇÃO ---");
        usina.concluirEmergencia(); 
        usina.ativarManutencao();
        usina.verificarETransitar(500.0); 
    }
}