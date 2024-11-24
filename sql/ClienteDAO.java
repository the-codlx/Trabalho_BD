package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import Conection.Conexao;
import model.Cliente;

public class ClienteDAO {

    public void cadastrarCliente(Cliente cliente) {
        String sql = "INSERT INTO CLIENTE (NOME, EMAIL, CPF, CEP, RUA, BAIRRO, CIDADE, NUMERO, COMPLEMENTO, TELEFONE, SENHA, NOME_USUARIO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getCpf());
            ps.setString(4, cliente.getCep());
            ps.setString(5, cliente.getRua());
            ps.setString(6, cliente.getBairro());
            ps.setString(7, cliente.getCidade());
            ps.setString(8, cliente.getNumero());
            ps.setString(9, cliente.getComplemento());
            ps.setString(10, cliente.getTelefone());
            ps.setString(11, cliente.getSenha());
            ps.setString(12, cliente.getNome_Usuario());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cliente.setId_cliente(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Falha ao obter o ID do cliente.");
                    }
                }
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                System.out.println("Nome de usuário já cadastrado.");
            } else {
                System.out.println(e);
            }
        }
    }

    public static Cliente buscarClientePeloId(int id) {
        Cliente cliente = new Cliente();
        String sql = "SELECT * FROM Cliente WHERE id_cliente = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente.setId_cliente(id);
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setCep(rs.getString("cep"));
                    cliente.setRua(rs.getString("rua"));
                    cliente.setBairro(rs.getString("bairro"));
                    cliente.setCidade(rs.getString("cidade"));
                    cliente.setNumero(rs.getString("numero"));
                    cliente.setComplemento(rs.getString("complemento"));
                    cliente.setTelefone(rs.getString("telefone"));
                    cliente.setSenha(rs.getString("senha"));
                    cliente.setNome_Usuario(rs.getString("nome_usuario"));
                    cliente.setRole(rs.getString("ROLE"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return cliente;
    }

    public static int verificarCredenciaisERetornaID(String Nome_Usuario, String senha) {
        int id = 0;
        String sql = "SELECT * FROM Cliente WHERE nome_usuario= ? AND senha= ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, Nome_Usuario);
            ps.setString(2, senha);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("id_cliente");
                } else {
                    id = -1;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao verificar credenciais: " + e);
        }

        return id;
    }

    public int quantidadeClientes() {
        int quantidade = 0;
        String sql = "SELECT COUNT(*) AS total FROM cliente";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                quantidade = rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return quantidade;
    }

    public int totalElementosTodasTabelas() {
        String[] tabelas = {"carrinho_de_compras", "cliente", "itens_do_carrinho", "pedido", "produto", "relatorio"};
        int totalRegistros = 0;

        try (Connection conn = Conexao.getConexao()) {
            for (String tabela : tabelas) {
                String sql = "SELECT COUNT(*) AS total FROM " + tabela;

                try (PreparedStatement pstmt = conn.prepareStatement(sql);
                     ResultSet rs = pstmt.executeQuery()) {

                    if (rs.next()) {
                        totalRegistros += rs.getInt("total");
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return totalRegistros;
    }
}
