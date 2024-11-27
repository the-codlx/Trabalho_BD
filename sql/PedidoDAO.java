package sql;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import Conection.MongoDBConexao;

import java.util.Date;

import org.bson.Document;
import model.Pedido;
import utils.*;

public class PedidoDAO {


    public void criaPedido(Pedido pedido) {
        // Conexão com o banco de dados
        MongoDatabase database = MongoDBConexao.getDatabase();
        CarrinhoDeComprasDAO dao = new CarrinhoDeComprasDAO();
        MongoCollection<Document> collection = database.getCollection("pedido");
    
        // Criação do documento do pedido com a data atual
        Document document = new Document("id_cliente", pedido.getId_cliente())
                .append("valor_total", pedido.getValor_total())
                .append("status", "pago")
                .append("id_carrinho", pedido.getId_carrinho())
                .append("data_pedido", new Date()); // Adiciona a data atual como o campo "data_pedido"
    
        // Inserção no banco de dados
        collection.insertOne(document);
    
        // Inativa o carrinho associado ao pedido
        dao.inativaCarrinho(pedido.getId_carrinho());
    }

    public int quantidadePedidos() {
        MongoDatabase database = MongoDBConexao.getDatabase();  
        MongoCollection<Document> collection = database.getCollection("pedido");
        long count = collection.countDocuments();
        return (int) count;
    }
}
