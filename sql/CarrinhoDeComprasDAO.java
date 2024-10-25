package sql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Conection.Conexao;
import model.CarrinhoDeCompras;

public class CarrinhoDeComprasDAO {
    

    public int criarCarrinhoDeCompras(int id_cliente) 
    { 

        String sql = "INSERT INTO CARRINHO_DE_COMPRAS (id_cliente) VALUES(?)";

        int id_carrinho_de_compras = verificaSeTemCarrinhoAtivo(id_cliente);

        //verifica se o cliente já possui carirnho de compras ativo e retorna o id do carrinho caso tenha
        if(id_carrinho_de_compras != -1) 
            return id_carrinho_de_compras;


        try {
        
            PreparedStatement ps = Conexao.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            ps.setInt(1, id_cliente);


            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            
            if(generatedKeys.next()) {
                
                id_carrinho_de_compras = generatedKeys.getInt(1);
            
            }

            ps.close();
        
        }
        catch(Exception e) {

            System.out.println(e);

        }

        return id_carrinho_de_compras;

    }


    public int verificaSeTemCarrinhoAtivo(int id_cliente) {

        int id_carrinho = -1;

        String sql = "SELECT * FROM CARRINHO_DE_COMPRAS WHERE ID_CLIENTE = ? AND STATUS = 'ATIVO'";

        try 
        {

            PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);

            ps.setInt(1, id_cliente);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) 
            {
                id_carrinho = rs.getInt(1);
            }


        }
        catch(SQLException e) {
            
            System.out.println(e);

        }

        return id_carrinho;

    }


    public CarrinhoDeCompras adicionarCarrinhoDeCompras(CarrinhoDeCompras carrinho) { 


        String sql = "INSERT INTO CARRINHO_DE_COMPRAS (id_cliente) VALUES(?)";

        try {
        
            PreparedStatement ps = Conexao.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            ps.setInt(1, carrinho.getId_cliente());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();

            carrinho.setId_carrinho(generatedKeys.getInt(1));

            ps.close();
            generatedKeys.close();
        
        }
        catch(Exception e) {

            System.out.println(e);

        }

        return carrinho;

    }
 

    public void inativaCarrinho(int id_carrinho) 
    {

        String sql = "UPDATE CARRINHO_DE_COMPRAS SET STATUS = 'FINALIZADO' WHERE ID_CARRINHO = ?";

        try
        {

            PreparedStatement ps = Conexao.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, id_carrinho);

            ps.executeUpdate();

            ps.close();

        }

        catch(SQLException e) {

            System.out.println(e);

        }

    }

}
