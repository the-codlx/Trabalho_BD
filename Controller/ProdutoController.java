package Controller;
import java.util.Scanner;
import model.Produto;
import sql.ProdutoDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        return produto;

    }


    public static String nomeProduto() {
        
        System.out.println("\n--------------------------------------\n");
        System.out.println("DIGITE O NOME EXATO DO PRODUTO(EM MAIÚSCULO):");
        String nomeProduto = entrada.nextLine();

        System.out.println("\n--------------------------------------");
        
        return nomeProduto;
        
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
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Descrição: " + produto.getDescricao());
            System.out.println("Preço: " + produto.getPreco());
            System.out.println("Quantidade em estoque: " + produto.getQuatidade_estoque());
            System.out.println("Categoria: " + produto.getCategoria());

            System.out.println("-----------------------------------------");
        }

    }

    public static void listarProdutosNoCarrinho(List<Produto> produtosCarrinho) {

    if (!produtosCarrinho.isEmpty()) {
        System.out.println("-----------------PRODUTOS NO CARRINHO-----------------");

        for (Produto produtoCarrinho : produtosCarrinho) {

            Produto produto = produtoCarrinho;

            System.out.println("Nome: " + produto.getNome());
            System.out.println("Descrição: " + produto.getDescricao());
            System.out.println("Preço: " + produto.getPreco());
            System.out.println("Quantidade: " + produtoCarrinho.getQuatidade_estoque());

            System.out.println("-----------------------------------------------------");
        }
    } else {
        System.out.println("-----------------------------------------------------");
        System.out.println();
        System.out.println("DESCULPA, NÃO HÁ PRODUTOS NO CARRINHO.");
        System.out.println();
        System.out.println("-----------------------------------------------------");
    }
}
    public static String retornaNomeProduto() {
        System.out.println("DIGITE O NOME DO PRODUTO (EXATAMENTE COMO APRESENTADO): ");
        String nome = entrada.nextLine();
        return nome;
    }

    public static void Produtos(ProdutoDAO dao) {
        ProdutoController.listarProdutos(dao.listarProdutos());
    }

}
