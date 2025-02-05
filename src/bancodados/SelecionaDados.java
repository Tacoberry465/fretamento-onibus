package bancodados;

import model.Fretamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelecionaDados {
    public static List<Fretamento> listarFretamentos() {
        List<Fretamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM fretamento";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Fretamento fretamento = new Fretamento(
                    rs.getDate("data").toLocalDate(),
                    rs.getTime("horario").toLocalTime(),
                    rs.getString("local"),
                    rs.getString("empresa"),
                    rs.getString("linha_onibus")
                );
                fretamento.setId(rs.getInt("id"));
                lista.add(fretamento);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar fretamentos: " + e.getMessage());
        }
        return lista;
    }
}
