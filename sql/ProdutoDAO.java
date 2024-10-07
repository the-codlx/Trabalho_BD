package sql;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Conection.Conexao;
import model.Cliente;
import model.Produto;
import java.util.ArrayList;

public class ProdutoDAO {

        private static PreparedStatement ps = null;
        private static ResultSet rs = null;
        private static Scanner entrada = new Scanner(System.in);
        private static String sql = null;
    
        public static void cadastrarProduto(Produto produto) {

            sql = "INSERT INTO PRODUTO (NOME, DESCRICAO, PRECO, QUATIDADE_ESTOQUE, CATEGORIA) VALUES (?, ?, ?, ?, ?)";
    
            try {
                
                ps = Conexao.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, produto.getNome());
                ps.setString(2, produto.getDescricao());
                ps.setLong(3, (long)produto.getPreco());
                ps.setInt(4, produto.getQuatidade_estoque());
                ps.setString(5, produto.getCategoria());

                int affectedRows = ps.executeUpdate();

                if(affectedRows > 0) {
                    
                    try(ResultSet generatedKeys = ps.getGeneratedKeys()) {

                        if(generatedKeys.next()) {
                            produto.setId_produto(generatedKeys.getInt(1));
                        }

                        else {
                            throw new SQLException("Falha ao obter o ID do produto.");
                        }
                    }
                }

                ps.close();

            
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

        public static ArrayList<Produto> listarProdutos() {

            ArrayList<Produto> produtos = new ArrayList<Produto>();

            String sql = "SELECT * FROM PRODUTO";

            try{
                ps = Conexao.getConexao().prepareStatement(sql);
                rs = ps.executeQuery();

                while(rs.next()) {
                    Produto produto = new Produto();
                    produto.setNome(rs.getString("NOME"));
                    produto.setDescricao(rs.getString("DESCRICAO"));
                    produto.setPreco(rs.getDouble("PRECO"));
                    produto.setQuatidade_estoque(rs.getInt("QUANTIDADE_ESTOQUE"));
                    produto.setCategoria(rs.getString("CATEGORIA"));
                    produtos.add(produto);
                }

                ps.close();
                rs.close();
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }

            return produtos;

        }

}