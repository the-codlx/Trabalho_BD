package mongodbquery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

import Conection.Conexao;
import model.CarrinhoDeCompras;
import Conection.MongoDBConexao;

public class CarrinhoDeComprasDAO {

    public ObjectId criarCarrinhoDeCompras(ObjectId idCliente) {


        ObjectId idCarrinhoDeCompras = verificaSeTemCarrinhoAtivo(idCliente);

        if (idCarrinhoDeCompras != null) 
            return idCarrinhoDeCompras;

        try {
            MongoDatabase database = MongoDBConexao.getDatabase();
            MongoCollection<Document> carrinhosCollection = database.getCollection("carrinho_de_compras");


            Document novoCarrinho = new Document("id_cliente", idCliente)
                           .append("status", "ativo");

            carrinhosCollection.insertOne(novoCarrinho);

            idCarrinhoDeCompras = novoCarrinho.getObjectId("_id");

            
        } catch (Exception e) {
            System.out.println("Erro ao criar carrinho de compras: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return idCarrinhoDeCompras;
    }

    /*private int calcularProximoIdCarrinho(MongoCollection<Document> carrinhosCollection) {
        Document maxIdDoc = carrinhosCollection.aggregate(List.of(
            Aggregates.group(null, Accumulators.max("maxId", "$id_carrinho"))
        )).first();

        if (maxIdDoc == null || maxIdDoc.getInteger("maxId") == null) {
            return 1;
        }

        return maxIdDoc.getInteger("maxId") + 1;
    }*/

    public ObjectId verificaSeTemCarrinhoAtivo(ObjectId idCliente) {
        ObjectId idCarrinho = null;

        try {
            
            MongoDatabase database = MongoDBConexao.getDatabase();
            MongoCollection<Document> carrinhosCollection = database.getCollection("carrinho_de_compras");
 

            Bson filtro = Filters.and(
                Filters.eq("id_cliente", idCliente),
                Filters.eq("status", "ativo")
            );

            Document carrinhoAtivo = carrinhosCollection.find(filtro).first();

            if (carrinhoAtivo != null) {
                idCarrinho = carrinhoAtivo.getObjectId("_id"); 
            }
        } catch (Exception e) {
            System.out.println("Erro ao verificar carrinho ativo: " + e.getMessage());
            e.printStackTrace();
        }

        return idCarrinho;
    }

    /*public CarrinhoDeCompras adicionarCarrinhoDeCompras(CarrinhoDeCompras carrinho) {
        try {
            MongoDatabase database = MongoDBConexao.getDatabase();
            MongoCollection<Document> carrinhosCollection = database.getCollection("carrinho_de_compras");

            int novoIdCarrinho = calcularProximoIdCarrinho(carrinhosCollection);

            Document novoCarrinho = new Document("id_carrinho", novoIdCarrinho)
                        .append("id_cliente", carrinho.getId_cliente())
                        .append("status", "ATIVO");

            carrinhosCollection.insertOne(novoCarrinho);

            carrinho.setId_carrinho(novoIdCarrinho);

        } catch (Exception e) {
            System.out.println("Erro ao adicionar carrinho de compras: " + e.getMessage());
            e.printStackTrace();
        }

        return carrinho;
    }*/

    public void inativaCarrinho(ObjectId id_carrinho) {

        try {
            MongoDatabase database = MongoDBConexao.getDatabase();
            MongoCollection<Document> carrinhos = database.getCollection("carrinho_de_compras");

            Document filtro = new Document("_id", id_carrinho);
            Document atualizacao = new Document("$set", new Document("status", "finalizado"));

            carrinhos.updateOne(filtro, atualizacao);

        } catch (Exception e) {
            System.out.println("Erro ao atualizar o carrinho: " + e.getMessage());
        }
    }
    
}
