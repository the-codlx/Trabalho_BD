package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;
import model.Cliente;
import Conection.Conexao;

public class ClienteDAO {

    private static ResultSet rs = null;
    private static PreparedStatement ps = null;
    private static Scanner entrada = new Scanner(System.in);

    public void cadastrarCliente(Cliente cliente) {

        String sql = "INSERT INTO CLIENTE (NOME, EMAIL, CPF, CEP, RUA, BAIRRO, CIDADE, NUMERO, COMPLEMENTO, TELEFONE, SENHA, NOME_USUARIO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try  {

            ps = Conexao.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

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
                    }

                    else {
                        throw new SQLException("Falha ao obter o ID do cliente.");
                    }
                }
            }

            ps.close();
            
        }

        catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                System.out.println("Nome de usuário já cadastrado.");
            } else {
                System.out.println(e);
            }
        }

    }

    public static Cliente buscarCliente(String nome_usuario, String senha) {

        Cliente cliente = new Cliente();

        try {
            Connection conn = Conexao.getConexao();
            String sql = "SELECT * FROM Cliente WHERE nome_usuario = ? AND senha= ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, nome_usuario);
            ps.setString(2, senha);
            
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            if(rs.next()) {
                cliente.setId_cliente(rs.getInt("id_cliente"));
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

            ps.close();
            conn.close();
        }

        catch(SQLException e) {
            System.out.println(e);
        }

        return cliente;

    }

    public static boolean verificarCredenciais(String Nome_Usuario, String senha) {

        boolean autenticado = false;

        try{

            Connection conn = Conexao.getConexao();

            String sql = "SELECT * FROM Cliente WHERE nome_usuario= ? AND senha= ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, Nome_Usuario);
            ps.setString(2, senha);

            rs = ps.executeQuery();

            if (rs.next()) {
                autenticado = true;
            }

            ps.close();
            rs.close();

        } catch (Exception e) {
            System.out.println("Erro ao verificar credencias: " + e);
        }

        return autenticado;

    }

}
