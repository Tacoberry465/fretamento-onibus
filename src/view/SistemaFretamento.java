package view;

import model.Fretamento;
import bancodados.InsereDados;
import bancodados.SelecionaDados;
import bancodados.ExportaTxt;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class SistemaFretamento {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n=== SISTEMA DE FRETAMENTO ===");
            System.out.println("1. Cadastrar Fretamento");
            System.out.println("2. Listar Fretamentos");
            System.out.println("3. Exportar Relatório para TXT");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa buffer do teclado

            switch (opcao) {
                case 1:
                    cadastrarFretamento();
                    break;
                case 2:
                    listarFretamentos();
                    break;
                case 3:
                    exportarRelatorio();
                    break;
                case 4:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 4);

        scanner.close();
    }

    private static void cadastrarFretamento() {
        System.out.println("\n=== Cadastrar Novo Fretamento ===");
        System.out.print("Digite a data (AAAA-MM-DD): ");
        LocalDate data = LocalDate.parse(scanner.nextLine());

        System.out.print("Digite o horário (HH:MM): ");
        LocalTime horario = LocalTime.parse(scanner.nextLine());

        System.out.print("Digite o local de apresentação: ");
        String local = scanner.nextLine();

        System.out.print("Digite o nome da empresa: ");
        String empresa = scanner.nextLine();

        System.out.print("Digite a linha de ônibus: ");
        String linhaOnibus = scanner.nextLine();

        Fretamento fretamento = new Fretamento(data, horario, local, empresa, linhaOnibus);
        if (InsereDados.inserirFretamento(fretamento)) {
            System.out.println("✅ Fretamento cadastrado com sucesso!");
        } else {
            System.out.println("❌ Erro ao cadastrar fretamento.");
        }
    }

    private static void listarFretamentos() {
        System.out.println("\n=== Lista de Fretamentos ===");
        List<Fretamento> lista = SelecionaDados.listarFretamentos();

        if (lista.isEmpty()) {
            System.out.println("Nenhum fretamento encontrado.");
        } else {
            for (Fretamento f : lista) {
                System.out.println(f);
            }
        }
    }

    private static void exportarRelatorio() {
        String caminhoArquivo = "relatorio_fretamentos.txt";
        ExportaTxt.exportarParaTxt(caminhoArquivo);
    }
}
