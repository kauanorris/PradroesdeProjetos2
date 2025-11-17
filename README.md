# Questão 1

## 🛠️ Padrão de Projeto Utilizado: Strategy (Estratégia)

O Padrão Strategy (Estratégia) foi escolhido porque ele resolve diretamente o requisito central do problema: a necessidade de trocar o algoritmo de cálculo de risco em tempo de execução de forma limpa.

Motivos Chave:
Intercambialidade Imediata: Permite que o sistema passe do cálculo de VaR para Expected Shortfall (ES), ou Stress Testing (ST), com uma única chamada (setEstrategia()), sem quebrar ou modificar o código que usa esses cálculos.

Separação de Preocupações: Ele separa o O quê (o contexto de dados financeiros na classe Q1ContextoAnaliseRisco) do Como (o algoritmo de cálculo na interface Q1EstrategiaRisco e suas implementações). Isso significa que você pode adicionar um novo algoritmo de risco no futuro (ex: Incremental VaR) sem tocar no código do contexto de análise.

-------------------------------------------------------------------------------------
# Questão 2

## 🛠️ Padrão de Projeto Utilizado: Strategy (Estratégia)
O Padrão Strategy (Estratégia) foi escolhido porque ele resolve diretamente o requisito central do problema: a necessidade de trocar o algoritmo de cálculo de risco em tempo de execução de forma limpa.

Motivos Chave para a Escolha:
Intercambialidade Imediata: Permite que o sistema passe do cálculo de VaR para Expected Shortfall (ES), ou Stress Testing (ST), com uma única chamada (setEstrategia()), sem quebrar ou modificar o código que usa esses cálculos.

Separação de Preocupações: Ele separa o O Quê (o contexto de dados financeiros na classe Q1ContextoAnaliseRisco) do Como (o algoritmo de cálculo na interface Q1EstrategiaRisco e suas implementações). Isso significa que você pode adicionar um novo algoritmo de risco no futuro (ex: Incremental VaR) sem tocar no código do contexto de análise.

Princípio Aberto/Fechado (OCP): O sistema fica aberto para extensão (adicionar novas estratégias de risco) mas fechado para modificação (o código do Q1ContextoAnaliseRisco não precisa ser alterado).

-------------------------------------------------------------------------------------
# Questão 3

## 🛠️ Padrão de Projeto Utilizado: Strategy (Estratégia)
O Padrão Strategy foi escolhido para isolar os diferentes métodos de cálculo (as "estratégias") em classes separadas, permitindo que o contexto principal (a análise de risco) alterne entre elas dinamicamente.

Motivos Chave da Escolha
Intercambialidade Imediata: O objeto Q1ContextoAnaliseRisco pode passar do cálculo de VaR para Expected Shortfall (ES), ou Stress Testing (ST), com uma única chamada (setEstrategia()), sem quebrar ou exigir modificações no código que invoca o cálculo (executarCalculo()).

Separação de Preocupações: O padrão separa o O quê (o contexto de dados financeiros na classe Q1ContextoAnaliseRisco) do Como (o algoritmo de cálculo nas implementações da interface Q1EstrategiaRisco).

Extensibilidade (Open/Closed Principle): Se for necessário adicionar um novo algoritmo de risco no futuro (ex: Incremental VaR), basta criar uma nova classe que implemente Q1EstrategiaRisco. O código existente do Contexto não precisará ser alterado.

-------------------------------------------------------------------------------------
# Questão 4 

## 🛠️ Padrão de Projeto Utilizado: Strategy (Estratégia)
O Padrão Strategy foi escolhido por ser a solução ideal para desacoplar a lógica de negócio de uma família de algoritmos, permitindo que eles sejam selecionados e trocados dinamicamente.

Motivos Chave para a Escolha:
Intercambialidade Imediata: Permite que o sistema mude do cálculo de Value-at-Risk (VaR) para Expected Shortfall (ES) ou Stress Testing (ST) com uma única chamada (setEstrategia(novaEstrategia)). O código cliente não se importa como o risco é calculado, apenas que ele será calculado.

Separação de Preocupações (Single Responsibility Principle):

O Quê (Contexto): A classe Q1ContextoAnaliseRisco (o contexto) foca apenas em gerenciar os dados da carteira e delegar o cálculo.

O Como (Estratégia): As implementações de Q1EstrategiaRisco (os algoritmos) focam apenas na lógica matemática específica do VaR, ES ou ST.

Extensibilidade Aberta/Fechada (Open/Closed Principle): É possível adicionar facilmente um novo método de cálculo de risco no futuro (ex: VaR Incremental ou Marginal) criando apenas uma nova classe que implementa Q1EstrategiaRisco, sem a necessidade de modificar o contexto de análise de risco existente.
