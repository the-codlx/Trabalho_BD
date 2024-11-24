package Conection;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.bson.Document;

import com.mongodb.client.*;

public class MongoDBConexao {

    private static final String HOST = "localhost";
    private static final int PORT = 27017;
    private static final String DATABASE_NAME = "bdvendas";

    private static MongoClient mongoClient = null;

    // Método para obter uma conexão com o banco de dados MongoDB
    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create("mongodb://" + HOST + ":" + PORT);
        }
        return mongoClient.getDatabase(DATABASE_NAME);
    }

    // Método para fechar a conexão (se necessário)
    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }

}
