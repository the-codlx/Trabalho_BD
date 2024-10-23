package sql;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import Conection.Conexao;
import model.CarrinhoDeCompras;
import model.Cliente;
import model.Produto;
import java.util.ArrayList;

public class ProdutoDAO {

        private Produto produto;

        private static PreparedStatement ps = null;
        private static ResultSet rs = null;
        private static String sql = null;

        public ProdutoDAO(Produto produto) {

            this.produto = produto;

        }

        public ProdutoDAO() {

        }



        //cadastra produto
    
        public void cadastrarProduto() {

            sql = "INSERT INTO PRODUTO (NOME, DESCRICAO, PRECO, QUANTIDADE_ESTOQUE, CATEGORIA) VALUES (?, ?, ?, ?, ?)";
            
            try {

                ps = Conexao.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, this.produto.getNome());
                ps.setString(2, this.produto.getDescricao());
                ps.setLong(3, (long) this.produto.getPreco());
                ps.setInt(4, this.produto.getQuatidade_estoque());
                ps.setString(5, this.produto.getCategoria());

                int affectedRows = ps.executeUpdate();

                

                

                ps.close();

            
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }



    //alterar produtos

    public void alterarProduto (int id, Produto produto_novo) {

        sql = "UPDATE produto SET NOME = ?, DESCRICAO = ?, PRECO = ?, QUANTIDADE_ESTOQUE = ? WHERE ID = ?";


        try {

            ps = Conexao.getConexao().prepareStatement(sql);

            ps.setString(1, produto_novo.getNome());
            ps.setString(2, produto_novo.getDescricao());
            ps.setDouble(3, produto_novo.getPreco());
            ps.setInt(4, produto_novo.getQuatidade_estoque());
            ps.setInt(5, id);

        }
        catch(SQLException e) {

            System.out.println(e);

        }

    }


    //public void alterarPreco (Produto produto);


    //exclui produto



    //listar produtos

    public ArrayList<Produto> listarProdutos() {

        ArrayList<Produto> produtos = new ArrayList<Produto>();

        String sql = "SELECT * FROM PRODUTO";

        try{
            ps = Conexao.getConexao().prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()) {

                int quantidade_estoque = rs.getInt("QUANTIDADE_ESTOQUE");

                if(quantidade_estoque > 0) 
                {
                    Produto produto = new Produto();
                    produto.setId_produto(rs.getInt("ID_PRODUTO"));
                    produto.setNome(rs.getString("NOME"));
                    produto.setDescricao(rs.getString("DESCRICAO"));
                    produto.setPreco(rs.getDouble("PRECO"));
                    produto.setQuatidade_estoque(quantidade_estoque);
                    produto.setCategoria(rs.getString("CATEGORIA"));
                    produtos.add(produto);
                }

            }

            ps.close();
            rs.close();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;

    }



    //buscar produto

    public Produto buscarProduto(int id) {

        String sql = "SELECT * FROM produto WHERE ID = ?";

        try {

            ps =  Conexao.getConexao().prepareStatement(sql);

            ps.setInt(1, id);
                
            rs = ps.executeQuery();

            if(rs.getRow() == 1) {
                
                Produto produto = new Produto();

                produto.setNome(rs.getString("NOME"));
                produto.setDescricao(rs.getString("DESCRICAO"));
                produto.setPreco(rs.getDouble("PRECO"));
                produto.setQuatidade_estoque(rs.getInt("QUANTIDADE_ESTOQUE"));
                produto.setCategoria(rs.getString("CATEGORIA"));

            }

            ps.close();
            rs.close();

        }
        catch(SQLException e) {

            System.out.println(e);

        }

        return produto;

    }
    
    
    //verificar se o produto está no banco de dados

    public boolean existeProduto(int id) {

        String sql = "SELECT * FROM produto WHERE id_produto = ?";

        boolean possui = false;

        try {

            ps =  Conexao.getConexao().prepareStatement(sql);

            ps.setInt(1, id);
                
            rs = ps.executeQuery();

            if(rs.next()) {
                
                possui = true;
            }

            ps.close();
            rs.close();

        }
        catch(SQLException e) {

            System.out.println(e);

        }

        return possui;

    } 


}