package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Conection.Conexao;

public class RelatorioDAO {

    public void gerarRelatorioProdutosMaisVendidos() {
        String consulta = """
            SELECT 
                p.id_produto,
                p.nome,
                SUM(ic.quantidade) AS total_vendido
            FROM 
                itens_do_carrinho ic
            JOIN 
                produto p ON ic.id_produto = p.id_produto
            JOIN 
                carrinho_de_compras cc ON ic.id_carrinho = cc.id_carrinho
            JOIN 
                pedido ped ON cc.id_carrinho = ped.id_carrinho
            WHERE 
                ped.status = 'pago'
            GROUP BY 
                p.id_produto, p.nome
            ORDER BY 
                total_vendido DESC
            LIMIT 10;
        """;

        String insertRelatorio = "INSERT INTO relatorio (data_geracao, conteudo) VALUES (CURRENT_TIMESTAMP, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmtConsulta = conexao.prepareStatement(consulta);
             ResultSet rs = stmtConsulta.executeQuery();
             PreparedStatement stmtInsert = conexao.prepareStatement(insertRelatorio)) {

            StringBuilder conteudo = new StringBuilder();

            // Processa os resultados da consulta
            System.out.println("\nPRODUTOS MAIS VENDIDOS");
            System.out.println("----------------------");

            while (rs.next()) {
                String nomeProduto = rs.getString("nome");
                int totalVendido = rs.getInt("total_vendido");

                // Adiciona ao conteúdo do relatório
                conteudo.append(nomeProduto).append(": ").append(totalVendido).append(" VENDIDOS\n");

                // Mostra no console
                System.out.println(nomeProduto + ": " + totalVendido + " VENDIDO");
            }
            System.out.println();
            // Insere o conteúdo no relatório, se houver produtos vendidos
            if (conteudo.length() > 0) {
                stmtInsert.setString(1, conteudo.toString().trim()); // Remove a última nova linha
                stmtInsert.executeUpdate();
            } else {
                System.out.println("NENHUM PRODUTO VENDIDO ENCONTRADO.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void exibirPedidosCliente(int idCliente) {
        String sql = "SELECT " +
                     "p.id_pedido AS numero_pedido, " +
                     "p.data_pedido AS data_pedido, " +
                     "p.valor_total AS valor_total, " +
                     "p.status AS status_pedido " +
                     "FROM pedido p " +
                     "WHERE p.id_cliente = ? " +  // ID do cliente passado como parâmetro
                     "ORDER BY p.data_pedido DESC";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            // Substituir o valor do ID do cliente na query
            ps.setInt(1, idCliente);

            // Executar a consulta
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("=================================================");
                System.out.println("            RELATÓRIO DE PEDIDOS DO CLIENTE      ");
                System.out.println("=================================================");

                // Percorrer os resultados e exibir no console
                while (rs.next()) {
                    int numeroPedido = rs.getInt("numero_pedido");
                    String dataPedido = rs.getString("data_pedido");
                    double valorTotal = rs.getDouble("valor_total");
                    String statusPedido = rs.getString("status_pedido");

                    // Exibir cada pedido no formato desejado
                    System.out.println("Número do Pedido: " + numeroPedido);
                    System.out.println("Data do Pedido: " + dataPedido);
                    System.out.println("Valor Total: R$ " + String.format("%.2f", valorTotal));
                    System.out.println("Status do Pedido: " + statusPedido);
                    System.out.println("-------------------------------------------------");
                }

                System.out.println("=================================================");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar os pedidos: " + e.getMessage());
        }
    }
}
