package bancodados;

import model.Fretamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsereDados {
    public static boolean inserirFretamento(Fretamento fretamento) {
        String sql = "INSERT INTO fretamento (data, horario, local, empresa, linha_onibus) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(fretamento.getData()));
            stmt.setTime(2, java.sql.Time.valueOf(fretamento.getHorario()));
            stmt.setString(3, fretamento.getLocal());
            stmt.setString(4, fretamento.getEmpresa());
            stmt.setString(5, fretamento.getLinhaOnibus());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir fretamento: " + e.getMessage());
            return false;
        }
    }
}
