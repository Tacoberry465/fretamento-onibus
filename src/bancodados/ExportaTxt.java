package bancodados;

import model.Fretamento;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportaTxt {
    public static void exportarParaTxt(String caminhoArquivo) {
        List<Fretamento> lista = SelecionaDados.listarFretamentos();

        if (lista.isEmpty()) {
            System.out.println("Nenhum fretamento encontrado para exportar.");
            return;
        }

        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            // Cabeçalho do relatório
            writer.write("ID;Data;Horário;Local;Empresa;Linha de Ônibus\n");

            // Adiciona cada fretamento no arquivo
            for (Fretamento f : lista) {
                writer.write(f.getId() + ";");
                writer.write(f.getData() + ";");
                writer.write(f.getHorario() + ";");
                writer.write(f.getLocal() + ";");
                writer.write(f.getEmpresa() + ";");
                writer.write(f.getLinhaOnibus() + "\n");
            }

            System.out.println("✅ Relatório exportado para " + caminhoArquivo);
        } catch (IOException e) {
            System.out.println("❌ Erro ao exportar: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        exportarParaTxt("relatorio_fretamentos.txt");
    }
}
