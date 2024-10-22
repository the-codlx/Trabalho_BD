package sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Conection.Conexao;
import model.CarrinhoDeCompras;
import model.ItensDoCarrinho;

public class ItensDoCarrinhoDAO {
    
    public void adicionarProdutos(ItensDoCarrinho itens_do_carrinho) { 


        String sql = "INSERT INTO ITENS_DO_CARRINHO (id_carrinho, id_produto, quantidade) VALUES(?, ?, ?)";

        try {
        
            PreparedStatement ps = Conexao.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            ps.setInt(1, itens_do_carrinho.getId_carrinho());
            ps.setInt(2, itens_do_carrinho.getId_produto());
            ps.setInt(3, itens_do_carrinho.getQuantidade());

            ps.executeUpdate();

            ps.close();
        
        }
        catch(Exception e) {

            System.out.println(e);

        }

    }

}
