package sql;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.List;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Conection.MongoDBConexao;

public class RelatorioDAO {

    public void gerarRelatorioProdutosMaisVendidos() {
        // Conecta ao banco de dados
        MongoDatabase database = MongoDBConexao.getDatabase();
        MongoCollection<Document> itensDoCarrinhoCollection = database.getCollection("itens_do_carrinho");
        MongoCollection<Document> relatorioCollection = database.getCollection("relatorio");
        MongoCollection<Document> produtosCollection = database.getCollection("produto");
    
        // Criação do pipeline de agregação
        List<Bson> pipeline = Arrays.asList(
            // Agrupamento por id_produto e soma das quantidades
            Aggregates.group("$id_produto", 
                    Accumulators.sum("totalVendidos", "$quantidade")),
            
            // Ordenação em ordem decrescente por totalVendidos
            Aggregates.sort(Sorts.descending("totalVendidos")),
            
            // Limitar aos 5 primeiros resultados
            Aggregates.limit(5),
            
            // Lookup para juntar com a coleção de produtos para obter detalhes do produto
            Aggregates.lookup("produtos", "_id", "_id", "produtoDetalhes"),
            
            // Projeção para incluir os campos desejados do produto (nome, descrição, preço, quantidade_estoque)
            Aggregates.project(
                Projections.fields(
                    Projections.include("totalVendidos"),
                    Projections.computed("nome", new Document("$arrayElemAt", Arrays.asList("$produtoDetalhes.nome", 0))),
                    Projections.computed("descricao", new Document("$arrayElemAt", Arrays.asList("$produtoDetalhes.descricao", 0))),
                    Projections.computed("preco", new Document("$arrayElemAt", Arrays.asList("$produtoDetalhes.preco", 0))),
                    Projections.computed("quantidade_estoque", new Document("$arrayElemAt", Arrays.asList("$produtoDetalhes.quantidade_estoque", 0)))
                )
            )
        );
    
        // Executa o pipeline no MongoDB
        List<Document> resultado = itensDoCarrinhoCollection.aggregate(pipeline).into(new ArrayList<>());
    
        // Se houver resultados, insira-os na coleção "relatorio"
        if (!resultado.isEmpty()) {
            List<Document> relatorioDocs = new ArrayList<>();
            for (Document doc : resultado) {
                // Criando o documento para inserir no relatorio
                Document relatorioDoc = new Document("id_produto", doc.getObjectId("_id"))
                        .append("totalVendidos", doc.getInteger("totalVendidos"))
                        .append("nome", doc.getString("nome"))
                        .append("descricao", doc.getString("descricao"))
                        .append("preco", doc.getDouble("preco"))
                        .append("quantidade_estoque", doc.getInteger("quantidade_estoque"))
                        .append("data_relatorio", new java.util.Date());  // Adiciona a data do relatório
    
                // Adicionando ao lista para inserção
                relatorioDocs.add(relatorioDoc);
            }
    
            // Inserir os documentos na coleção "relatorio"
            relatorioCollection.insertMany(relatorioDocs);
    
            System.out.println("Relatório dos 5 itens mais vendidos inserido com sucesso na coleção 'relatorio'.");
        } else {
            System.out.println("Não há itens suficientes para gerar o relatório.");
        }
    }

    public List<Document> exibirPedidosCliente(ObjectId id_cliente) {
        // Conexão com o banco de dados
        MongoDatabase database = MongoDBConexao.getDatabase();
        MongoCollection<Document> pedidoCollection = database.getCollection("pedido");

        // Lista para armazenar os pedidos pagos
        List<Document> pedidosPagos = new ArrayList<>();

        // Consulta para buscar pedidos pagos do cliente
        Document filtro = new Document("id_cliente", id_cliente).append("status", "pago");

        for (Document pedido : pedidoCollection.find(filtro)) {
            pedidosPagos.add(pedido);
        }

        // Exibir resultados ou retornar a lista
        if (pedidosPagos.isEmpty()) {
            System.out.println("Nenhum pedido pago encontrado para o cliente.");
        } else {
            System.out.println("-----------------PEDIDOS PAGOS-----------------");
            for (Document pedido : pedidosPagos) {
                System.out.println("Valor Total: " + pedido.getDouble("valor_total"));
                System.out.println("Status: " + pedido.getString("status"));

                // Exibir a data do pedido
                java.util.Date dataPedido = pedido.getDate("data_pedido");
                if (dataPedido != null) {
                    System.out.println("Data do Pedido: " + dataPedido);
                } else {
                    System.out.println("Data do Pedido: Não registrada");
                }

                System.out.println("-----------------------------------------------");
            }
        }

        return pedidosPagos;
}
}
