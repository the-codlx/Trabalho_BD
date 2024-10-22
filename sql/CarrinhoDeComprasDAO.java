package sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Conection.Conexao;
import model.CarrinhoDeCompras;

public class CarrinhoDeComprasDAO {
    
    private static ResultSet rs = null;
    private static PreparedStatement ps = null;


    public int criarCarrinhoDeCompras(int id_cliente) 
    { 

        int id_carrinho_de_compras = 0; 

        String sql = "INSERT INTO CARRINHO_DE_COMPRAS (id_cliente) VALUES(?)";

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


    public CarrinhoDeCompras adicionarCarrinhoDeCompras(CarrinhoDeCompras carrinho) { 


        String sql = "INSERT INTO CARRINHO_DE_COMPRAS (id_cliente, id_itens_do_carrinho) VALUES(?, ?)";

        try {
        
            PreparedStatement ps = Conexao.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            ps.setInt(1, carrinho.getId_cliente());
            ps.setInt(2, carrinho.getId_itens_do_carrinho());


            ResultSet rs = ps.executeQuery();

            ResultSet generatedKeys = ps.getGeneratedKeys();

            carrinho.setId_carrinho(generatedKeys.getInt(1));
        
        }
        catch(Exception e) {

            System.out.println(e);

        }

        return carrinho;

    }
 

}
