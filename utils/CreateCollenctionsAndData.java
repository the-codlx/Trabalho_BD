package utils;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;

import org.bson.Document;
import com.mongodb.client.MongoIterable;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import Conection.Conexao;
import Conection.MongoDBConexao;

public class CreateCollenctionsAndData {

    private static final String[] collections = { "cliente", "produto", "pedido", "itensdocarrinho",
            "carrinhodecompras" };

    public static void ensureCollectionsExist(MongoDatabase database) {
        try {

            MongoIterable<String> existingCollections = database.listCollectionNames();

            for (String collection : collections) {
                if (!collectionExists(existingCollections, collection)) {

                    database.createCollection(collection);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MongoDBConexao.closeConnection();
        }

    }

    private static boolean collectionExists(MongoIterable<String> existingCollections, String collectionName) {
        for (String existingCollection : existingCollections) {
            if (existingCollection.equalsIgnoreCase(collectionName)) {
                return true;
            }
        }
        return false;
    }

    public static void InserirColecaoFromMySQL(String collection) {

        ensureCollectionsExist(MongoDBConexao.getDatabase());

        String sql = "SELECT * FROM " + collection;

        try {

            PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            // Obter a conexão com o MongoDB
            MongoDatabase mongoDatabase = MongoDBConexao.getDatabase();
            MongoCollection<Document> documentCollection = mongoDatabase.getCollection(collection);

            // Transferir dados do MySQL para o MongoDB

            int recordsMigrated = 0;

            while (rs.next()) {

                Document document = new Document();

                // Adicionar dinamicamente todas as colunas da tabela no documento
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = rs.getObject(i);
                    document.append(columnName, columnValue);
                }

                documentCollection.insertOne(document);
                recordsMigrated++;
            }

        }

        catch (Exception e) {
            System.err.println("Erro ao migrar a tabela '" + collection + "':");
            e.printStackTrace();
        } finally {
            try {
                if (Conexao.getConexao() != null)
                    Conexao.getConexao().close();
                MongoDBConexao.closeConnection(); // Fechar a conexão MongoDB
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
