package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import bancodados.ExportaTxt;
import bancodados.InsereDados;
import bancodados.RemoverDados;
import bancodados.SelecionaDados;
import model.Fretamento;


public class SistemaFretamentoGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField txtData, txtHorario, txtLocal, txtEmpresa, txtLinhaOnibus;
    private JTable tabela;
    private DefaultTableModel modeloTabela;


    public SistemaFretamentoGUI() {
        setTitle("Sistema de Fretamento");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de Cadastro
        JPanel painelCadastro = new JPanel(new GridLayout(6, 2, 5, 5));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Cadastrar Fretamento"));

        painelCadastro.add(new JLabel("Data (AAAA-MM-DD):"));
        txtData = new JTextField();
        painelCadastro.add(txtData);

        painelCadastro.add(new JLabel("Horário (HH:MM):"));
        txtHorario = new JTextField();
        painelCadastro.add(txtHorario);

        painelCadastro.add(new JLabel("Local:"));
        txtLocal = new JTextField();
        painelCadastro.add(txtLocal);

        painelCadastro.add(new JLabel("Empresa:"));
        txtEmpresa = new JTextField();
        painelCadastro.add(txtEmpresa);

        painelCadastro.add(new JLabel("Linha de Ônibus:"));
        txtLinhaOnibus = new JTextField();
        painelCadastro.add(txtLinhaOnibus);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(this::cadastrarFretamento);
        painelCadastro.add(btnCadastrar);

        // Painel de Tabela
        modeloTabela = new DefaultTableModel(new String[]{"ID", "Data", "Horário", "Local", "Empresa", "Linha"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane painelTabela = new JScrollPane(tabela);
        atualizarTabela();

        // Painel de Botões
        JPanel painelBotoes = new JPanel();
        JButton btnExportar = new JButton("Exportar Relatório para TXT");
        btnExportar.addActionListener(this::exportarRelatorio);
        painelBotoes.add(btnExportar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(this::excluirFretamento);
        painelBotoes.add(btnExcluir);
        
        // Adicionando componentes à Janela
        add(painelCadastro, BorderLayout.NORTH);
        add(painelTabela, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void cadastrarFretamento(ActionEvent e) {
        try {
            // Ajustar a data para aceitar diferentes formatos
            String dataTexto = txtData.getText().trim();
            if (!dataTexto.matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new IllegalArgumentException("Data deve estar no formato AAAA-MM-DD.");
            }
            LocalDate data = LocalDate.parse(dataTexto);

            // Ajustar o horário para aceitar diferentes formatos
            String horarioTexto = txtHorario.getText().trim();
            if (!horarioTexto.matches("\\d{2}:\\d{2}")) {
                throw new IllegalArgumentException("Horário deve estar no formato HH:MM.");
            }
            LocalTime horario = LocalTime.parse(horarioTexto);

            String local = txtLocal.getText().trim();
            String empresa = txtEmpresa.getText().trim();
            String linhaOnibus = txtLinhaOnibus.getText().trim();

            // Verifica se os campos obrigatórios estão vazios
            if (local.isEmpty() || empresa.isEmpty() || linhaOnibus.isEmpty()) {
                throw new IllegalArgumentException("Todos os campos devem ser preenchidos.");
            }

            // Criar objeto Fretamento e salvar no banco
            Fretamento fretamento = new Fretamento(data, horario, local, empresa, linhaOnibus);
            if (InsereDados.inserirFretamento(fretamento)) {
                JOptionPane.showMessageDialog(this, "✅ Fretamento cadastrado com sucesso!");
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Erro ao cadastrar fretamento.");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "❌ " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Erro inesperado: " + ex.getMessage());
        }
    }


    private void atualizarTabela() {
        modeloTabela.setRowCount(0); // Limpa a tabela
        List<Fretamento> lista = SelecionaDados.listarFretamentos();
        for (Fretamento f : lista) {
            modeloTabela.addRow(new Object[]{f.getId(), f.getData(), f.getHorario(), f.getLocal(), f.getEmpresa(), f.getLinhaOnibus()});
        }
    }

    private void exportarRelatorio(ActionEvent e) {
        ExportaTxt.exportarParaTxt("relatorio_fretamentos.txt");
        JOptionPane.showMessageDialog(this, "✅ Relatório exportado para 'relatorio_fretamentos.txt'");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaFretamentoGUI().setVisible(true));
    }
    private void excluirFretamento(ActionEvent e) {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "❌ Selecione um fretamento para excluir.");
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);

        if (RemoverDados.excluirFretamento(id)) {
            JOptionPane.showMessageDialog(this, "✅ Fretamento excluído com sucesso!");
            atualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Erro ao excluir fretamento.");
        }
    }

}
