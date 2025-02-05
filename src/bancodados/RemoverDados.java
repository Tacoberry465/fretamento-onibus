package bancodados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoverDados {
    public static boolean excluirFretamento(int id) {
        String sql = "DELETE FROM fretamento WHERE id = ?";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir fretamento: " + e.getMessage());
            return false;
        }
    }
}
