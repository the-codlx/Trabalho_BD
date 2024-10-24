package sql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Conection.Conexao;
import model.Pedido;

public class PedidoDAO {

    
    public void criaPedido(Pedido pedido) 
    {

        CarrinhoDeComprasDAO dao = new CarrinhoDeComprasDAO();

       String inserePedido = "INSERT INTO PEDIDO (id_cliente, valor_total, status, id_carrinho) VALUE(?, ?, ?, ?)";
       
       try 
       {

            PreparedStatement ps = Conexao.getConexao().prepareStatement(inserePedido);

            ps.setInt(1, pedido.getId_cliente());
            ps.setDouble(2, pedido.getValor_total());
            ps.setString(3, "pago");
            ps.setInt(4, pedido.getId_carrinho());


            ps.executeUpdate();

            dao.inativaCarrinho(pedido.getId_carrinho());


       }
       catch(SQLException e)
       {

        System.out.println(e);
        
        }

    }

    public int quantidadePedidos() 
    {

        int quantidade = 0;

        String sql = "SELECT COUNT(*) AS total FROM pedido";

        try {

            PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
            
                quantidade = rs.getInt("total");

            }

            ps.close();
            rs.close();

        }

        catch(SQLException e) 
        {
            System.out.println(e);
        }

        return quantidade;

    }


}
