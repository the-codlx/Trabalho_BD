package Controller;
import java.util.Scanner;
import model.Produto;
import sql.ProdutoDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProdutoController {
    
    static Scanner entrada = new Scanner(System.in);


    public static Produto CadastrarProduto() {
        
        System.out.println("------------------------------");

        System.out.println("Digite o nome do produto: ");
        String nome = entrada.nextLine();
        System.out.println("Digite a descrição do produto: "); 
        String descricao = entrada.nextLine();
        System.out.println("Digite o preço do produto: ");
        double preco = Double.parseDouble(entrada.nextLine());
        System.out.println("Digite a quantidade em estoque: ");
        int quantidade_estoque = Integer.parseInt(entrada.nextLine());
        System.out.println("Digite a categoria do produto: ");
        String categoria = entrada.nextLine();

        Produto produto = new Produto(nome, descricao, preco, quantidade_estoque, categoria);

        System.out.println("------------------------------");

        entrada.close();

        return produto;

    }


    public static Produto alterarProduto () {

        Produto produto = new Produto();

        System.out.println("-----------------ALTERAÇÃO-----------------");

        System.out.println("Digite o novo nome:");
        String nome = entrada.nextLine();
        produto.setNome(nome);
        System.out.println();

        System.out.println("Digite a nova descricao:");
        String descricao = entrada.nextLine();
        produto.setDescricao(descricao);
        System.out.println();

        System.out.println("Digite o novo preco:");
        double preco = Double.parseDouble(entrada.nextLine());
        produto.setPreco(preco);
        System.out.println();

        System.out.println("Digite a quantidade no estoque:");
        int quantidade_estoque = Integer.parseInt(entrada.nextLine());
        produto.setQuatidade_estoque(quantidade_estoque);
        System.out.println();

        System.out.println("-----------------------------------------");

        entrada.close();

        return produto;

    }


    public static int produtoId() {
        
        System.out.println("\n--------------------------------------\n");
        System.out.println("DIGITE O ID DO PRODUTO:");
        int id = Integer.parseInt(entrada.nextLine());

        System.out.println("\n--------------------------------------");
        
        return id;
        
    }

    public static int Quantidade() {

        System.out.println("\nDIGITE A QUANTIDADE QUE DESEJA COMPRAR:");
        int quantidade = Integer.parseInt(entrada.nextLine());

        System.out.println("\n--------------------------------------\n");
        
        return quantidade;
    }


    public static void listarProdutos(ArrayList<Produto> produtos) {

        System.out.println("-----------------PRODUTOS-----------------");

        for (Produto produto : produtos) {
            System.out.println("ID: " + produto.getId_produto());
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Descrição: " + produto.getDescricao());
            System.out.println("Preço: " + produto.getPreco());
            System.out.println("Quantidade em estoque: " + produto.getQuatidade_estoque());
            System.out.println("Categoria: " + produto.getCategoria());

            System.out.println("-----------------------------------------");
        }

    }

    public static void listarProdutosNoCarrinho(HashMap<Produto, Integer> produtos) {

        if(produtos.size() >= 1) 
        {

            System.out.println("-----------------PRODUTOS NO CARRINHO-----------------");

            for (Map.Entry<Produto, Integer> entrada : produtos.entrySet()) {
                
                Produto produto = entrada.getKey();

                System.out.println("ID: " + produto.getId_produto());
                System.out.println("Nome: " + produto.getNome());
                System.out.println("Descrição: " + produto.getDescricao());
                System.out.println("Preço: " + produto.getPreco());
                System.out.println("Quantidade: " + entrada.getValue());

                System.out.println("-----------------------------------------------------");
            }
        }
        else 
        {
            System.out.println("-----------------------------------------------------");
            System.out.println();
            System.out.println("DESCULPA, NÃO HÁ PRODUTOS NO CARRINHO.");
            System.out.println();
            System.out.println("-----------------------------------------------------");
        }

    }


    public static void Produtos(ProdutoDAO dao) {
        ProdutoController.listarProdutos(dao.listarProdutos());
    }

}
