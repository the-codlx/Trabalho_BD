package sql;
import java.util.Scanner;
import Conection.Conexao;
import Controller.ProdutoController;
import model.CarrinhoDeCompras;
import model.Cliente;
import model.ItensDoCarrinho;
import model.Produto;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class ProdutoDAO {

    private Produto produto;

    private static String sql = null;

    public ProdutoDAO(Produto produto) {
        this.produto = produto;
    }

    public ProdutoDAO() {
    }

    // cadastra produto
    public void cadastrarProduto() {
        sql = "INSERT INTO PRODUTO (NOME, DESCRICAO, PRECO, QUANTIDADE_ESTOQUE, CATEGORIA) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, this.produto.getNome());
            ps.setString(2, this.produto.getDescricao());
            ps.setLong(3, (long) this.produto.getPreco());
            ps.setInt(4, this.produto.getQuatidade_estoque());
            ps.setString(5, this.produto.getCategoria());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // alterar produtos
    public void alterarProduto(int id, Produto produto_novo) {
        sql = "UPDATE produto SET NOME = ?, DESCRICAO = ?, PRECO = ?, QUANTIDADE_ESTOQUE = ? WHERE ID = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, produto_novo.getNome());
            ps.setString(2, produto_novo.getDescricao());
            ps.setDouble(3, produto_novo.getPreco());
            ps.setInt(4, produto_novo.getQuatidade_estoque());
            ps.setInt(5, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // listar produtos
    public ArrayList<Produto> listarProdutos() {
        ArrayList<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM PRODUTO";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int quantidade_estoque = rs.getInt("QUANTIDADE_ESTOQUE");

                if (quantidade_estoque > 0) {
                    Produto produto = new Produto();
                    produto.setId_produto(rs.getInt("ID_PRODUTO"));
                    produto.setNome(rs.getString("NOME"));
                    produto.setDescricao(rs.getString("DESCRICAO"));
                    produto.setPreco(rs.getDouble("PRECO"));
                    produto.setQuatidade_estoque(quantidade_estoque);
                    produto.setCategoria(rs.getString("CATEGORIA"));
                    produtos.add(produto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    // buscar produto
    public Produto buscarProduto(int id) {
        String sql = "SELECT * FROM produto WHERE ID = ?";
        Produto produto = null;

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto();
                    produto.setNome(rs.getString("NOME"));
                    produto.setDescricao(rs.getString("DESCRICAO"));
                    produto.setPreco(rs.getDouble("PRECO"));
                    produto.setQuatidade_estoque(rs.getInt("QUANTIDADE_ESTOQUE"));
                    produto.setCategoria(rs.getString("CATEGORIA"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }

    // verificar se o produto está no banco de dados
    public boolean existeProduto(int id) {
        String sql = "SELECT * FROM produto WHERE id_produto = ?";
        boolean possui = false;

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    possui = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return possui;
    }

    // quantidade de produtos
    public int quantidadeProdutos() {
        int quantidade = 0;
        String sql = "SELECT COUNT(*) AS total FROM produto";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                quantidade = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quantidade;
    }

    // comprar produto
    public void comprarProduto(int id_carrinho_de_compras) {
        ProdutoDAO dao = new ProdutoDAO();
        int id_produto = ProdutoController.produtoId();

        // verifica se o produto está cadastrado
        if (dao.existeProduto(id_produto)) {
            int quantidade = ProdutoController.Quantidade();

            // cria o carrinho
            ItensDoCarrinho ItensDoCarrinho = new ItensDoCarrinho(id_carrinho_de_compras, id_produto, quantidade);
            ItensDoCarrinhoDAO itens_dao = new ItensDoCarrinhoDAO();
            itens_dao.adicionarProdutos(ItensDoCarrinho);
        } else {
            System.out.println("\nPRODUTO NÃO CADASTRADO!\n");
            System.out.println("--------------------------------------\n");
        }
    }
}
