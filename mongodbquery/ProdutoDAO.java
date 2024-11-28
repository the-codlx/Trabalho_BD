package mongodbquery;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

import Conection.MongoDBConexao;
import Controller.ProdutoController;
import model.CarrinhoDeCompras;
import model.Cliente;
import model.ItensDoCarrinho;
import model.Produto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import Conection.MongoDBConexao;

public class ProdutoDAO {

    // cadastra produto
    public void cadastrarProduto(Produto produto) {
        MongoDatabase database = MongoDBConexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("produto");

            // Obtém o preço como BigDecimal e limita a precisão a 2 casas decimais
        BigDecimal preco = new BigDecimal(produto.getPreco());

        // Arredonda o valor para 2 casas decimais
        preco = preco.setScale(2, RoundingMode.HALF_UP);  // Arredonda para 2 casas decimais (você pode ajustar a quantidade de casas)

        // Converte o BigDecimal para Decimal128
        Decimal128 precoDecimal = new Decimal128(preco);

        Document doc = new Document("nome", produto.getNome())
                .append("descricao", produto.getDescricao())
                .append("preco", precoDecimal)
                .append("quantidade_estoque", produto.getQuatidade_estoque())
                .append("categoria", produto.getCategoria());

        collection.insertOne(doc);
    }

    // alterar produtos
    public void alterarProduto(String nome, Produto produto_novo) {
        MongoDatabase database = MongoDBConexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("produto");
    
        try {
            // Verificar se o produto existe antes de tentar atualizar
            Document produtoExistente = collection.find(Filters.eq("nome", nome)).first();
    
            if (produtoExistente == null) {
                throw new Exception("Produto com o nome '" + nome + "' não encontrado.");
            }
            

            BigDecimal preco = new BigDecimal(produto_novo.getPreco());

            // Arredonda o valor para 2 casas decimais
            preco = preco.setScale(2, RoundingMode.HALF_UP);  // Arredonda para 2 casas decimais (você pode ajustar a quantidade de casas)

            // Converte o BigDecimal para Decimal128
            Decimal128 precoDecimal = new Decimal128(preco);

            // Se o produto for encontrado, realizar a atualização
            collection.updateOne(
                Filters.eq("nome", nome),  // Filtro para encontrar o produto pelo nome
                Updates.combine(
                    Updates.set("nome", produto_novo.getNome()),
                    Updates.set("descricao", produto_novo.getDescricao()),
                    Updates.set("preco", precoDecimal),
                    Updates.set("quantidade_estoque", produto_novo.getQuatidade_estoque())
                )
            );
            
            System.out.println("Produto atualizado com sucesso.");
        } catch (Exception e) {
            // Caso ocorra algum erro (ex: produto não encontrado ou erro na conexão)
            System.err.println("Erro ao atualizar o produto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // listar produtos
    public ArrayList<Produto> listarProdutos() {
        ArrayList<Produto> produtos = new ArrayList<>();
        MongoDatabase database = MongoDBConexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("produto");
    
        
        for (Document doc : collection.find(Filters.gt("quantidade_estoque", 0))) {
            int quantidade_estoque = doc.getInteger("quantidade_estoque");
    
            if (quantidade_estoque > 0) {
                Produto produto = new Produto();
                produto.setNome(doc.getString("nome"));
                produto.setDescricao(doc.getString("descricao"));
                
                
                Decimal128 preco = doc.get("preco", Decimal128.class);
                if (preco != null) {
                    produto.setPreco(preco.doubleValue());
                }
    
                produto.setQuatidade_estoque(quantidade_estoque);
                produto.setCategoria(doc.getString("categoria"));
    
                produtos.add(produto);
            }
        }
    
        return produtos;
    }


    public void excluirProdutoPorNome(String nome) {
        MongoDatabase database = MongoDBConexao.getDatabase();
        MongoCollection<Document> produtoCollection = database.getCollection("produto");
        MongoCollection<Document> itensDoCarrinhoCollection = database.getCollection("itens_do_carrinho");
    
        try {
            // Verificar se o produto existe na coleção "produto"
            Document produtoExistente = produtoCollection.find(Filters.eq("nome", nome)).first();
    
            if (produtoExistente == null) {
                throw new Exception("Produto com o nome '" + nome + "' não encontrado.");
            }
    
            ObjectId id_produto = produtoExistente.getObjectId("_id");

            // Verificar se o produto já foi comprado (existe na coleção "itens_do_carrinho")
            Document produtoNoCarrinho = itensDoCarrinhoCollection.find(Filters.eq("_id", id_produto)).first();
    
            if (produtoNoCarrinho != null) {
                // Produto já foi comprado, não pode excluir
                System.err.println("Erro: Não é possível excluir o produto '" + nome + "' porque ele já foi comprado.");
                return;  // Retorna sem excluir
            }
    
            // Se o produto não foi comprado, realizar a exclusão
            produtoCollection.deleteOne(Filters.eq("nome", nome));
    
            System.out.println("Produto com o nome '" + nome + "' excluído com sucesso.");
        } catch (Exception e) {
            // Caso ocorra algum erro (ex: produto não encontrado ou erro na conexão)
            System.err.println("Erro ao excluir o produto: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // buscar produto
    /*public Produto buscarProduto(String nome) {
        MongoDatabase database = MongoDBConexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("produto");

        Document doc = collection.find(Filters.eq("id", id)).first();
        Produto produto = null;

        if (doc != null) {
            produto = new Produto();
            produto.setNome(doc.getString("nome"));
            produto.setDescricao(doc.getString("descricao"));
            produto.setPreco(doc.getDouble("preco"));
            produto.setQuatidade_estoque(doc.getInteger("quantidade_estoque"));
            produto.setCategoria(doc.getString("categoria"));
        }

        return produto;
    }*/

    // pesquisar produto pelo nome
    public ObjectId pesquisarProdutoPorNome(String nome) {

        ObjectId _id_produto = null;

        MongoDatabase database = MongoDBConexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("produto");

        Document doc = collection.find(Filters.eq("nome", nome)).first();

        if (doc != null) {
            _id_produto = doc.getObjectId("_id");       
        }

        return _id_produto;
    }   

    // verificar se o produto está no banco de dados
    public boolean existeProduto(String nomeProduto) {
        MongoDatabase database = MongoDBConexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("produto");

        Document doc = collection.find(Filters.eq("nome", nomeProduto)).first();
        return doc != null;
    }

    // quantidade de produtos
    public int quantidadeProdutos() {
        MongoDatabase database = MongoDBConexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("produto");

        return (int) collection.countDocuments();
    }

    // comprar produto
    public void comprarProduto(ObjectId id_carrinho_de_compras) {

        String nomeProduto = ProdutoController.nomeProduto();

        // verifica se o produto está cadastrado
        if (existeProduto(nomeProduto)) {
            int quantidade = ProdutoController.Quantidade();

            ObjectId id_produto = pesquisarProdutoPorNome(nomeProduto);
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
