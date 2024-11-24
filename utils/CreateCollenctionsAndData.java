package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.spi.DirStateFactory.Result;

import org.bson.Document;
import com.mongodb.client.MongoIterable;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import Conection.Conexao;
import Conection.MongoDBConexao;
import java.util.List;
import java.util.ArrayList;

public class CreateCollenctionsAndData {

    private static final String[] collections = { "cliente", "produto", "pedido", "itens_do_carrinho",
        "carrinho_de_compras", "relatorio"};

    public static void ensureCollectionsExist() {
        MongoDatabase database = null;
        try {
            database = MongoDBConexao.getDatabase();
            MongoIterable<String> existingCollections = database.listCollectionNames();

            for (String collection : collections) {
                if (collectionExists(existingCollections, collection)) {
                    database.getCollection(collection).drop();
                }
                database.createCollection(collection);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public static void InserirDadosTodasTabelas() {
        ensureCollectionsExist();
        String sqlTables = "SHOW TABLES";
        try (
            Connection mysqlConnection = Conexao.getConexao();
            Statement stmt = mysqlConnection.createStatement();
            ResultSet rsTables = stmt.executeQuery(sqlTables)
        ) {
            MongoDatabase mongoDatabase = MongoDBConexao.getDatabase();
            while (rsTables.next()) {
                String tableName = rsTables.getString(1);
                String sqlData = "SELECT * FROM " + tableName;
                try (
                    PreparedStatement ps = mysqlConnection.prepareStatement(sqlData);
                    ResultSet rsData = ps.executeQuery()
                ) {
                    MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(tableName);
                    List<Document> documents = new ArrayList<>();
                    int recordsMigrated = 0;
                    while (rsData.next()) {
                        Document document = new Document();
                        ResultSetMetaData metaData = rsData.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = metaData.getColumnName(i);
                            Object columnValue = rsData.getObject(i);
                            document.append(columnName, columnValue);
                        }
                        documents.add(document);
                        recordsMigrated++;
                        if (documents.size() >= 1000) {
                            mongoCollection.insertMany(documents);
                            documents.clear();
                        }
                    }
                    if (!documents.isEmpty()) {
                        mongoCollection.insertMany(documents);
                    }
                    
                }
            }
            
        } catch (SQLException | MongoException e) {
            System.err.println("Erro durante a migração de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

