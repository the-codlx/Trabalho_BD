package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import Conection.Conexao;
import model.CarrinhoDeCompras;
import model.ItensDoCarrinho;
import model.Produto;

public class ItensDoCarrinhoDAO {

    public void adicionarProdutos(ItensDoCarrinho itens_do_carrinho) {
        int quantidadeNoEstoque = 0;
        String quantidadeEstoque = "SELECT quantidade_estoque FROM PRODUTO WHERE ID_PRODUTO = ?";
        String updateQuantidadeEstoque = "UPDATE Produto SET quantidade_estoque = ? WHERE ID_PRODUTO = ?";
        String adicionaProdutos = "INSERT INTO ITENS_DO_CARRINHO (id_carrinho, id_produto, quantidade) VALUES(?, ?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmtQuantEstoque = conexao.prepareStatement(quantidadeEstoque)) {

            stmtQuantEstoque.setInt(1, itens_do_carrinho.getId_produto());
            ResultSet rsQuantEstoque = stmtQuantEstoque.executeQuery();

            if (rsQuantEstoque.next()) {
                quantidadeNoEstoque = rsQuantEstoque.getInt("quantidade_estoque");
            }

            if (quantidadeNoEstoque >= itens_do_carrinho.getQuantidade()) {
                try (PreparedStatement stmtAdicionaProdutos = conexao.prepareStatement(adicionaProdutos)) {
                    stmtAdicionaProdutos.setInt(1, itens_do_carrinho.getId_carrinho());
                    stmtAdicionaProdutos.setInt(2, itens_do_carrinho.getId_produto());
                    stmtAdicionaProdutos.setInt(3, itens_do_carrinho.getQuantidade());

                    int produtoAdicionado = stmtAdicionaProdutos.executeUpdate();

                    if (produtoAdicionado == 1) {
                        try (PreparedStatement stmtUpdateQuantidadeEstoque = conexao.prepareStatement(updateQuantidadeEstoque)) {
                            stmtUpdateQuantidadeEstoque.setInt(1, quantidadeNoEstoque - itens_do_carrinho.getQuantidade());
                            stmtUpdateQuantidadeEstoque.setInt(2, itens_do_carrinho.getId_produto());
                            stmtUpdateQuantidadeEstoque.executeUpdate();
                            System.out.println("Produto adicionado ao carrinho!");
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException("Quantidade do produto não disponível!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public HashMap<Produto, Integer> produtosItensDoCarrinho(int id_carrinho) {
        HashMap<Produto, Integer> produtos = new HashMap<>();
        String itensDoCarrinho = "SELECT id_produto, SUM(quantidade) AS quantidade_total FROM ITENS_DO_CARRINHO WHERE id_carrinho = ? GROUP BY id_produto;";
        String produtosSQL = "SELECT * FROM PRODUTO WHERE ID_PRODUTO = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmtItensDoCarrinho = conexao.prepareStatement(itensDoCarrinho)) {

            stmtItensDoCarrinho.setInt(1, id_carrinho);
            ResultSet rsItensDoCarrinho = stmtItensDoCarrinho.executeQuery();

            while (rsItensDoCarrinho.next()) {
                int id_produto = rsItensDoCarrinho.getInt("id_produto");
                int quantidade = rsItensDoCarrinho.getInt("quantidade_total");

                try (PreparedStatement stmtProdutosSQL = conexao.prepareStatement(produtosSQL)) {
                    stmtProdutosSQL.setInt(1, id_produto);
                    ResultSet rsProdutosSQL = stmtProdutosSQL.executeQuery();

                    if (rsProdutosSQL.next()) {
                        Produto produto = new Produto();
                        produto.setNome(rsProdutosSQL.getString("nome"));
                        produto.setDescricao(rsProdutosSQL.getString("descricao"));
                        produto.setPreco(rsProdutosSQL.getDouble("preco"));
                        produto.setId_produto(id_produto);

                        produtos.put(produto, quantidade);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return produtos;
    }

    public void removeItensDoCarrinho(int id_carrinho, int id_produto) {
        String sqlSelect = "SELECT quantidade FROM ITENS_DO_CARRINHO WHERE id_carrinho = ? AND id_produto = ?";
        String sqlDelete = "DELETE FROM ITENS_DO_CARRINHO WHERE id_carrinho = ? AND id_produto = ?";
        String sqlUpdateEstoque = "UPDATE PRODUTO SET quantidade_estoque = quantidade_estoque + ? WHERE id_produto = ?";

        try (Connection conexao = Conexao.getConexao()) {
            conexao.setAutoCommit(false);

            try (PreparedStatement psSelect = conexao.prepareStatement(sqlSelect)) {
                psSelect.setInt(1, id_carrinho);
                psSelect.setInt(2, id_produto);
                ResultSet rs = psSelect.executeQuery();

                if (rs.next()) {
                    int quantidadeNoCarrinho = rs.getInt("quantidade");

                    try (PreparedStatement psUpdateEstoque = conexao.prepareStatement(sqlUpdateEstoque)) {
                        psUpdateEstoque.setInt(1, quantidadeNoCarrinho);
                        psUpdateEstoque.setInt(2, id_produto);
                        psUpdateEstoque.executeUpdate();
                    }

                    try (PreparedStatement psDelete = conexao.prepareStatement(sqlDelete)) {
                        psDelete.setInt(1, id_carrinho);
                        psDelete.setInt(2, id_produto);
                        psDelete.executeUpdate();
                    }
                } else {
                    System.out.println("Nenhum item encontrado no carrinho para este produto.");
                }

                conexao.commit();
            } catch (SQLException e) {
                conexao.rollback();
                System.out.println("Erro ao remover itens do carrinho e atualizar o estoque: " + e.getMessage());
            } finally {
                conexao.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public double retornaValorTotal(int id_carrinho) {
        double valor_total = 0;
        HashMap<Produto, Integer> map = produtosItensDoCarrinho(id_carrinho);

        for (HashMap.Entry<Produto, Integer> entrada : map.entrySet()) {
            Produto produto = entrada.getKey();
            int quantidade = entrada.getValue();
            valor_total += produto.getPreco() * quantidade;
        }

        return valor_total;
    }
}
