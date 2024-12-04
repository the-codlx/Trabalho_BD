package mongodbquery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import java.sql.ResultSet;

import Conection.Conexao;
import Conection.MongoDBConexao;
import model.Cliente;
import org.bson.Document;
import org.bson.types.ObjectId;

public class ClienteDAO {

    public void cadastrarCliente(Cliente cliente) {
        try {
            MongoDatabase database = MongoDBConexao.getDatabase();
            MongoCollection<Document> clientesCollection = database.getCollection("cliente");

            Document clienteDoc = new Document("NOME", cliente.getNome())
                    .append("EMAIL", cliente.getEmail())
                    .append("CPF", cliente.getCpf())
                    .append("CEP", cliente.getCep())
                    .append("RUA", cliente.getRua())
                    .append("BAIRRO", cliente.getBairro())
                    .append("CIDADE", cliente.getCidade())
                    .append("NUMERO", cliente.getNumero())
                    .append("COMPLEMENTO", cliente.getComplemento())
                    .append("TELEFONE", cliente.getTelefone())
                    .append("SENHA", cliente.getSenha())
                    .append("NOME_USUARIO", cliente.getNome_Usuario());

            clientesCollection.insertOne(clienteDoc);

            cliente.setId_cliente(clienteDoc.getObjectId("_id").hashCode());

        } catch (Exception e) {
            if (e.getMessage().contains("E11000 duplicate key error")) {
                System.out.println("Nome de usuário ou email já cadastrados.");
            } else {
                System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
            }
        }
    }

    public static Cliente buscarClientePeloId(String id) {
        Cliente cliente = null;

        try {
            MongoDatabase database = MongoDBConexao.getDatabase();
            MongoCollection<Document> collection = database.getCollection("cliente");

            Document filter = new Document("_id", new ObjectId(id));

            Document doc = collection.find(filter).first();

            if (doc != null) {
                cliente = new Cliente();
                cliente.setId_cliente(new ObjectId(id).hashCode());
                cliente.setNome(doc.getString("NOME"));
                cliente.setEmail(doc.getString("EMAIL"));
                cliente.setCpf(doc.getString("CPF"));
                cliente.setCep(doc.getString("CEP"));
                cliente.setRua(doc.getString("RUA"));
                cliente.setBairro(doc.getString("BAIRRO"));
                cliente.setCidade(doc.getString("CIDADE"));
                cliente.setNumero(doc.getString("NUMERO"));
                cliente.setComplemento(doc.getString("COMPLEMENTO"));
                cliente.setTelefone(doc.getString("TELEFONE"));
                cliente.setSenha(doc.getString("SENHA"));
                cliente.setNome_Usuario(doc.getString("NOME_USUARIO"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cliente;
        }

        public void excluirClientePeloNome(String nome) {
            try {
                // Obter a conexão com o banco de dados MongoDB
                MongoDatabase database = MongoDBConexao.getDatabase();
                MongoCollection<Document> collection = database.getCollection("cliente");
        
                // Verificar se o cliente existe
                Document cliente = collection.find(Filters.eq("NOME", nome)).first();
                if (cliente == null) {
                    System.out.println("Cliente com o nome '" + nome + "' não encontrado.");
                    return;
                }
        
                // Remover o cliente pelo nome
                collection.deleteOne(Filters.eq("NOME", nome));
                System.out.println("Cliente com o nome '" + nome + "' foi excluído com sucesso.");
            } catch (Exception e) {
                System.err.println("Erro ao excluir cliente: " + e.getMessage());
                e.printStackTrace();
            }
        }

    public static ObjectId verificarCredenciaisERetornaID(String Nome_Usuario, String senha) {

        Document cliente = null;
        ObjectId clienteId = null;

        try {   
            MongoDatabase database = MongoDBConexao.getDatabase();
            MongoCollection<Document> collection = database.getCollection("cliente");

           
            Document filter = new Document("NOME_USUARIO", Nome_Usuario);

            
            cliente = collection.find(filter).first();

            if (cliente != null && cliente.getString("SENHA").equals(senha)) {
                clienteId = cliente.getObjectId("_id");
            } 

        } catch (Exception e) {
            e.printStackTrace();
        }

        return clienteId;
        }

    public int quantidadeClientes() {
        int quantidade = 0;

        try {
            MongoDatabase database = MongoDBConexao.getDatabase();
            MongoCollection<Document> collection = database.getCollection("cliente");

            quantidade = (int) collection.countDocuments();

        } catch (Exception e) {
            System.out.println(e);
        }

        return quantidade;
    }

    public int totalElementosTodasTabelas() {
        String[] tabelas = {"carrinho_de_compras", "cliente", "itens_do_carrinho", "pedido", "produto", "relatorio"};
        int totalRegistros = 0;

        try {
            MongoDatabase database = MongoDBConexao.getDatabase();

            for (String tabela : tabelas) {
                // Acessando a coleção
                MongoCollection<Document> collection = database.getCollection(tabela);

                // Contando os documentos e somando ao total de registros
                int count = (int) collection.countDocuments();
                totalRegistros += count;

            }

        } catch (Exception e) {
            System.out.println("Erro ao acessar as coleções: " + e);
        }

        return totalRegistros;
    }


    public static List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();

        try {
            // Conectando ao banco de dados
            MongoDatabase database = MongoDBConexao.getDatabase();
            MongoCollection<Document> collection = database.getCollection("cliente");

            // Buscando todos os documentos na coleção "cliente"
            for (Document doc : collection.find()) {
                // Criando um novo objeto Cliente a partir do documento
                Cliente cliente = new Cliente();
                cliente.setNome(doc.getString("NOME"));
                cliente.setEmail(doc.getString("EMAIL"));
                cliente.setCpf(doc.getString("CPF"));
                cliente.setCep(doc.getString("CEP"));
                cliente.setRua(doc.getString("RUA"));
                cliente.setBairro(doc.getString("BAIRRO"));
                cliente.setCidade(doc.getString("CIDADE"));
                cliente.setNumero(doc.getString("NUMERO"));
                cliente.setComplemento(doc.getString("COMPLEMENTO"));
                cliente.setTelefone(doc.getString("TELEFONE"));
                cliente.setSenha(doc.getString("SENHA"));
                cliente.setNome_Usuario(doc.getString("NOME_USUARIO"));

                // Adicionando o cliente à lista
                clientes.add(cliente);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }

        // Retorna a lista de clientes
        return clientes;
    }


    public void alterarCliente(String nomeUsuario, Cliente clienteNovo) {
        MongoDatabase database = MongoDBConexao.getDatabase();
        MongoCollection<Document> collection = database.getCollection("cliente");

        try {
            // Verificar se o cliente existe antes de tentar atualizar
            Document clienteExistente = collection.find(Filters.eq("NOME_USUARIO", nomeUsuario)).first();

            if (clienteExistente == null) {
                throw new Exception("Cliente com o nome de usuário '" + nomeUsuario + "' não encontrado.");
            }

            // Se o cliente for encontrado, realizar a atualização
            collection.updateOne(
                Filters.eq("NOME_USUARIO", nomeUsuario),  // Filtro para encontrar o cliente pelo nome de usuário
                Updates.combine(
                    Updates.set("NOME", clienteNovo.getNome()),
                    Updates.set("EMAIL", clienteNovo.getEmail()),
                    Updates.set("CPF", clienteNovo.getCpf()),
                    Updates.set("CEP", clienteNovo.getCep()),
                    Updates.set("RUA", clienteNovo.getRua()),
                    Updates.set("BAIRRO", clienteNovo.getBairro()),
                    Updates.set("CIDADE", clienteNovo.getCidade()),
                    Updates.set("NUMERO", clienteNovo.getNumero()),
                    Updates.set("COMPLEMENTO", clienteNovo.getComplemento()),
                    Updates.set("TELEFONE", clienteNovo.getTelefone()),
                    Updates.set("SENHA", clienteNovo.getSenha())
                )
            );

            System.out.println("Cliente atualizado com sucesso.");
        } catch (Exception e) {
            // Caso ocorra algum erro (ex: cliente não encontrado ou erro na conexão)
            System.err.println("Erro ao atualizar o cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
