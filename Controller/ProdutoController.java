package Controller;
import java.util.Scanner;
import model.Produto;
import java.util.ArrayList;

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
        System.out.println("Digite o id do produto");
        int id = Integer.parseInt(entrada.nextLine());
        return id;
    }

    public static int Quantidade() {
        System.out.println("Digite a quantidade");
        int quantidade = Integer.parseInt(entrada.nextLine());
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



}
