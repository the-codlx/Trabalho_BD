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
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public class CreateCollenctionsAndData {

    private static final String[] collections = { "cliente", "produto", "pedido", "itens_do_carrinho",
        "carrinho_de_compras", "relatorio"};

        public static void ensureCollectionsExist() {
            MongoDatabase database = null;
            try {
                database = MongoDBConexao.getDatabase();
                MongoIterable<String> existingCollections = database.listCollectionNames();
                
                // Transformando as coleções existentes em um Set para busca eficiente
                Set<String> existingCollectionNames = new HashSet<>();
                for (String collection : existingCollections) {
                    existingCollectionNames.add(collection.toLowerCase()); // Convertendo para minúsculo para uma comparação case-insensitive
                }
    
                // Criando ou apagando as coleções conforme necessário
                for (String collection : collections) {
                    if (existingCollectionNames.contains(collection.toLowerCase())) {
                        // Se a coleção já existe, apaga ela
                        System.out.println("Coleção já existe. Apagando a coleção: " + collection);
                        database.getCollection(collection).drop();
                    }
    
                    // Criando a coleção novamente
                    System.out.println("Criando coleção: " + collection);
                    database.createCollection(collection);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    
}

