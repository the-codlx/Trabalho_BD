package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.*;
import Conection.Conexao;
import Conection.MongoDBConexao;
import model.CarrinhoDeCompras;
import model.ItensDoCarrinho;
import model.Produto;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

public class ItensDoCarrinhoDAO {

    public void adicionarProdutos(ItensDoCarrinho itens_do_carrinho) {
        MongoDatabase database = MongoDBConexao.getDatabase();
        MongoCollection<Document> produtosCollection = database.getCollection("produto");
        MongoCollection<Document> itensDoCarrinhoCollection = database.getCollection("itens_do_carrinho");

        Document produto = produtosCollection.find(new Document("_id", itens_do_carrinho.getId_produto())).first();
        if (produto != null) {
            int quantidadeNoEstoque = produto.getInteger("quantidade_estoque");

            if (quantidadeNoEstoque >= itens_do_carrinho.getQuantidade()) {
                Document novoItem = new Document("id_carrinho", itens_do_carrinho.getId_carrinho())
                        .append("id_produto", itens_do_carrinho.getId_produto())
                        .append("quantidade", itens_do_carrinho.getQuantidade());
                itensDoCarrinhoCollection.insertOne(novoItem);

                produtosCollection.updateOne(new Document("_id", itens_do_carrinho.getId_produto()),
                        new Document("$set", new Document("quantidade_estoque", quantidadeNoEstoque - itens_do_carrinho.getQuantidade())));
                System.out.println("Produto adicionado ao carrinho!");
            } else {
                            throw new IllegalArgumentException("Quantidade do produto não disponível!");
                        }
                    } else {
                        throw new IllegalArgumentException("Produto não encontrado!");
                    }
                }
            

                public List<Produto> produtosItensDoCarrinho(ObjectId idCarrinho) {
                    List<Produto> itensCarrinho = new ArrayList<>();
                
                    try {
                        // Obtenha a conexão com o banco de dados MongoDB
                        MongoDatabase database = MongoDBConexao.getDatabase();
                        
                        // Defina a coleção de itens do carrinho
                        MongoCollection<Document> itensCollection = database.getCollection("itens_do_carrinho");
            
                        // Realize a agregação
                        AggregateIterable<Document> resultado = itensCollection.aggregate(Arrays.asList(
                            // Filtra os itens para o carrinho específico
                            new Document("$match", new Document("id_carrinho", idCarrinho)),
                            
                            // Agrupa por id_produto e soma as quantidades
                            new Document("$group", new Document("_id", "$id_produto")
                                    .append("quantidade_total", new Document("$sum", "$quantidade")))
                        ));
            
                        // Itere sobre os resultados da agregação
                        for (Document item : resultado) {
                            // Obtenha o id_produto e a quantidade total
                            ObjectId idProduto = item.getObjectId("_id");
                            int quantidadeTotal = item.getInteger("quantidade_total");
            
                            // Agora, você pode buscar detalhes do produto (por exemplo, nome, preço)
                            Document produtoDoc = database.getCollection("produto").find(new Document("_id", idProduto)).first();
            
                            if (produtoDoc != null) {
                                Produto produto = new Produto();    
                                produto.setNome(produtoDoc.getString("nome"));
                                produto.setPreco(produtoDoc.get("preco", Decimal128.class).doubleValue());
                                produto.setDescricao(produtoDoc.getString("descricao"));
                                produto.setQuatidade_estoque(quantidadeTotal);
                                itensCarrinho.add(produto);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            
                    return itensCarrinho;
                }

            
                public void removeItensDoCarrinho(ObjectId id_carrinho, String nomeProduto) {
                    // Conexão com o banco de dados
                    MongoDatabase database = MongoDBConexao.getDatabase();
                    MongoCollection<Document> itensDoCarrinhoCollection = database.getCollection("itens_do_carrinho");
                    MongoCollection<Document> produtosCollection = database.getCollection("produto");
                
                    // Buscar o produto pelo nome para obter o id_produto
                    Document produto = produtosCollection.find(new Document("nome", nomeProduto)).first();
                
                    if (produto != null) {
                        ObjectId id_produto = produto.getObjectId("_id");
                
                        // Buscar o item do carrinho pelo id_carrinho e id_produto
                        Document item = itensDoCarrinhoCollection.find(
                            new Document("id_carrinho", id_carrinho).append("id_produto", id_produto)
                        ).first();
                
                        if (item != null) {
                            int quantidadeNoCarrinho = item.getInteger("quantidade");
                
                            // Remover o item do carrinho
                            itensDoCarrinhoCollection.deleteOne(
                                new Document("id_carrinho", id_carrinho).append("id_produto", id_produto)
                            );
                
                            // Atualizar o estoque do produto
                            produtosCollection.updateOne(
                                new Document("_id", id_produto),
                                new Document("$inc", new Document("quantidade_estoque", quantidadeNoCarrinho))
                            );
                
                            System.out.println("Item removido do carrinho e estoque atualizado!");
                        } else {
                            System.out.println("Nenhum item encontrado no carrinho para este produto.");
                        }
                    } else {
                        System.out.println("Produto com o nome '" + nomeProduto + "' não encontrado.");
                    }
                }

    public double retornaValorTotal(ObjectId id_carrinho) {
        double valor_total = 0;
        List<Produto> produtos = produtosItensDoCarrinho(id_carrinho);

        for (Produto produto : produtos) {
            int quantidade = produto.getQuatidade_estoque();
            valor_total += produto.getPreco() * quantidade;
        }

        return valor_total;
    }
}

