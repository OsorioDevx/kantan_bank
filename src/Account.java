/**
 ABSTRACT CLASS: Account
 > Cannot be instantiated: new Account() WILL FAIL
 > Designed to be extended by subclasses
 > Can contain fields, constructors, and concrete methods
 > Abstract methods REQUIRE subclass implementation

 DIFFERENCE: Interface vs Abstract Class

 Interface > Contract only, no state, multiple implementations
 Abstract Class > Shared state + base behavior + inheritance

 CONCEPTS APPLIED:

 > Abstract classes / abstract methods
 > implements (fulfills the interface contract)
 > protected (accessible to subclasses only)
 > static (belongs to the class, not the instance)
 > String arrays (transaction history storage)
 > Garbage Collector context
 */
public abstract class Account implements Operable {

    /*
    * static final: belongs to the class and represents a constant (convention: UPPER_CASE)*/
    private static final int MAX_HISTORY = 50;

    // Static counter
    // static: compartilhado entre TODAS as instâncias de Conta
    // Garante números de conta únicos e sequenciais
    private static int contadorContas = 1000;


    // ─── ATRIBUTOS PROTEGIDOS ──────────────────────────────────────
    // protected: subclasses podem acessar diretamente (importante para polimorfismo)
    protected int    numeroConta;
    protected double saldo;        // primitivo: double
    protected boolean ativa;       // primitivo: boolean
    protected Cliente titular;     // não primitivo: referência a objeto

    // ARRAY DE REFERÊNCIA: cada índice aponta para um objeto String na memória
    // historico[0] → "Conta aberta..."  (referência)
    // historico[1] → "Depósito..."      (referência)
    // historico[n] → null (sem objeto, elegível para Garbage Collector)
    protected String[] historico;
    protected int      totalTransacoes;

    // ─── CONSTRUTOR ───────────────────────────────────────────────
    // Chamado pelas subclasses via super(...)
    // Não pode ser chamado diretamente pois a classe é abstract
    public Conta(Cliente titular, double saldoInicial) {
        this.numeroConta     = ++contadorContas; // incrementa e atribui
        this.titular         = titular;
        this.saldo           = saldoInicial;
        this.ativa           = true;
        this.historico       = new String[MAX_HISTORY]; // array de não primitivos
        this.totalTransacoes = 0;

        registrarTransacao("Conta aberta com saldo inicial de R$ "
                + String.format("%.2f", saldoInicial));
    }

    // ─── MÉTODOS ABSTRATOS ─────────────────────────────────────────
    // abstract: cada subclasse DEVE implementar com comportamento próprio
    // É aqui que o polimorfismo "nasce"
    public abstract String getTipoConta();
    public abstract double calcularRendimento(); // CC cobra taxa; CP rende juros

    // ─── MÉTODO PROTEGIDO (utilitário interno) ─────────────────────
    protected void registrarTransacao(String descricao) {
        if (totalTransacoes < MAX_HISTORY) {
            historico[totalTransacoes] = descricao; // String é não primitivo (referência)
            totalTransacoes++;
        }
    }

    // ─── IMPLEMENTAÇÕES DA INTERFACE Operavel ─────────────────────
    // Estes métodos são concretos: subclasses HERDAM automaticamente
    // ContaCorrente pode sobrescrever sacar() para adicionar cheque especial

    @Override
    public boolean depositar(double valor) {
        if (!ativa) {
            System.out.println("  ❌ Conta encerrada. Operação não permitida.");
            return false;
        }
        // Condicional de validação
        if (valor <= 0) {
            System.out.println("  ❌ Valor de depósito deve ser positivo.");
            return false;
        }

        saldo += valor; // operador de atribuição composto
        registrarTransacao(String.format("Depósito: + R$ %.2f", valor));
        System.out.printf("  ✅ Depósito realizado. Saldo atual: R$ %.2f%n", saldo);
        return true;
    }

    @Override
    public boolean sacar(double valor) {
        if (!ativa) {
            System.out.println("  ❌ Conta encerrada. Operação não permitida.");
            return false;
        }
        if (valor <= 0) {
            System.out.println("  ❌ Valor de saque deve ser positivo.");
            return false;
        }
        if (valor > saldo) {
            System.out.printf("  ❌ Saldo insuficiente. Disponível: R$ %.2f%n", saldo);
            return false;
        }

        saldo -= valor;
        registrarTransacao(String.format("Saque:    - R$ %.2f", valor));
        System.out.printf("  ✅ Saque realizado. Saldo atual: R$ %.2f%n", saldo);
        return true;
    }

    @Override
    public boolean transferir(double valor, Conta destino) {
        // Validação de referência nula (null = sem objeto na heap)
        if (destino == null) {
            System.out.println("  ❌ Conta destino inválida (null).");
            return false;
        }
        if (!destino.ativa) {
            System.out.println("  ❌ Conta destino está encerrada.");
            return false;
        }
        if (this.numeroConta == destino.numeroConta) {
            System.out.println("  ❌ Conta de origem e destino não podem ser iguais.");
            return false;
        }

        // POLIMORFISMO EM AÇÃO:
        // this.sacar() chamará o sacar() do tipo real do objeto
        // Se 'this' for ContaCorrente → usa sacar() com cheque especial
        // Se 'this' for ContaPoupanca → usa sacar() da Conta (sem limite extra)
        if (this.sacar(valor)) {
            destino.depositar(valor);
            // Ajusta o histórico: remove a mensagem genérica e coloca a específica
            if (totalTransacoes > 0) {
                historico[totalTransacoes - 1] =
                        String.format("Transferência enviada → Conta %d: - R$ %.2f",
                                destino.numeroConta, valor);
            }
            if (destino.totalTransacoes > 0) {
                destino.historico[destino.totalTransacoes - 1] =
                        String.format("Transferência recebida ← Conta %d: + R$ %.2f",
                                this.numeroConta, valor);
            }
            System.out.printf("  ✅ Transferência de R$ %.2f concluída.%n", valor);
            return true;
        }
        return false;
    }

    @Override
    public void exibirExtrato() {
        System.out.println("\n  ╔════════════════════════════════════╗");
        System.out.printf ("  ║         EXTRATO DA CONTA           ║%n");
        System.out.println("  ╠════════════════════════════════════╣");
        System.out.printf ("  ║  Nº: %-6d  Tipo: %-13s  ║%n", numeroConta, getTipoConta());
        System.out.printf ("  ║  Titular: %-26s║%n", titular.getNome());
        System.out.println("  ╠════════════════════════════════════╣");
        System.out.println("  ║  MOVIMENTAÇÕES:                    ║");

        if (totalTransacoes == 0) {
            System.out.println("  ║    Nenhuma movimentação            ║");
        } else {
            // Laço de repetição percorrendo array
            for (int i = 0; i < totalTransacoes; i++) {
                // Exibe no máximo 36 caracteres para caber no layout
                String linha = (i + 1) + ". " + historico[i];
                if (linha.length() > 36) linha = linha.substring(0, 33) + "...";
                System.out.printf("  ║  %-36s║%n", linha);
            }
        }

        System.out.println("  ╠════════════════════════════════════╣");
        System.out.printf ("  ║  Saldo atual: R$ %,-17.2f  ║%n", saldo);

        // Operador ternário: condição ? valor_se_true : valor_se_false
        String statusLabel = ativa ? "ATIVA  " : "ENCERRADA";
        System.out.printf ("  ║  Status: %-28s║%n", statusLabel);
        System.out.println("  ╚════════════════════════════════════╝");
    }

    // ─── MÉTODO PARA ENCERRAR CONTA ────────────────────────────────
    public void encerrarConta() {
        this.ativa = false;
        registrarTransacao("Conta encerrada.");
        System.out.printf("  🔒 Conta %d encerrada.%n", numeroConta);
        // GARBAGE COLLECTOR NOTE:
        // Quando a referência a este objeto for removida do array do Banco
        // (contas[i] = null), nenhuma variável mais apontará para este objeto.
        // O Garbage Collector da JVM poderá liberar a memória da heap
        // que este objeto ocupava. Não é imediato — a JVM decide quando.
    }

    // ─── GETTERS PÚBLICOS ─────────────────────────────────────────
    public int     getNumeroConta() { return numeroConta; }
    public double  getSaldo()       { return saldo; }
    public Cliente getTitular()     { return titular; }
    public boolean isAtiva()        { return ativa; }

    // ─── toString() ───────────────────────────────────────────────
    @Override
    public String toString() {
        // Operador ternário para status
        String status = ativa ? "✅ ATIVA " : "🔒 ENCERRADA";
        return String.format("  [%04d] %-16s | Titular: %-20s | Saldo: R$ %,10.2f | %s",
                numeroConta, getTipoConta(), titular.getNome(), saldo, status);
    }
}

