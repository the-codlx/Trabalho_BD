package principal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import javax.naming.spi.DirStateFactory.Result;

import Conection.Conexao;
import Controller.ClienteController;
import Controller.ProdutoController;
import model.CarrinhoDeCompras;
import model.Cliente;
import model.ItensDoCarrinho;
import model.Produto;
import sql.CarrinhoDeComprasDAO;
import sql.ClienteDAO;
import sql.ItensDoCarrinhoDAO;
import sql.ProdutoDAO;
import utils.Utils;

public class Principal {

    static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            // imprime as opçôes na tela
            mostraOpcoes();

            // recebe a opção do usuário
            switch (Opcao()) {

                case 1:
                    GerenciarLogin();
                    break;

                case 2:
                    cadastraCliente();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }

        }

    }

    // métodos para mostrar as opcoes e capturar a opção do usuário

    private static String Opcoes() {
        return "1 - Fazer Login\n2 - Realizar Cadastro\n3 - Sair";
    }

    private static int Opcao() {
        int opcao = entrada.nextInt();
        entrada.nextLine();
        return opcao;
    }

    private static void mostraOpcoes() {
        System.out.println(Opcoes());
    }


    // metodo para cadastrar cliente

    private static void cadastraCliente() {
        Cliente cliente = ClienteController.solicitarDadosCliente();
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.cadastrarCliente(cliente);
    }

    // metodos para login



    public static void GerenciarLogin() {

        Map<String, String> credenciais = ClienteController.pedeUsuarioESenha();

        int id_cliente = ClienteDAO.verificarCredenciaisERetornaID(credenciais.get("usuario"), credenciais.get("senha"));

        if (id_cliente != -1) {

            MenuCliente(id_cliente);

        }

        else {

            String opcoes = "1 - Tentar novamente\n2 - Cadastrar\n3 - Sair";

            System.out.println(opcoes);

            int opcao = Integer.parseInt(entrada.nextLine());

            switch (opcao) {

                case 1:
                    GerenciarLogin();
                    break;

                case 2:
                    Cliente cliente = ClienteController.solicitarDadosCliente();
                    ClienteDAO dao = new ClienteDAO();
                    dao.cadastrarCliente(cliente);
                    GerenciarLogin();

                    break;

                case 3:
                    MenuCliente(id_cliente);
                    break;

            }

        }
    }

    // metodo para Menu de Clientes

    private static void MenuCliente(int id_cliente) {

        ProdutoDAO prod_dao = new ProdutoDAO();
        CarrinhoDeComprasDAO car_dao = new CarrinhoDeComprasDAO();
        // carrinho = new CarrinhoDeCompras(id_cliente, id_cliente)
        int id_carrinho_de_compras = car_dao.criarCarrinhoDeCompras(id_cliente);

        while (true) {

            System.out.println("Escolha uma opção:");
            System.out.println("1. Produtos");
            System.out.println("2. Comprar Produto");
            System.out.println("3. Visualizar Carrinho");
            System.out.println("4. Excluir Produtos do Carrinho");
            System.out.println("5. Excluir Carrinho de Compras");
            System.out.println("0. Sair");

            int opcao = Opcao(); // Método para capturar a opção do usuário

            switch (opcao) {
                case 1:
                    Produtos(prod_dao);
                    break;

                case 2:
                    comprarProduto(id_carrinho_de_compras);                
                    break;

                case 3:
                    

                case 4:

                case 0:
                    return; // Sai do loop e volta ao menu anterior

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;

            }
        }
    }

    // metodo para vizualizar produtos

    private static void Produtos(ProdutoDAO dao) {
        ProdutoController.listarProdutos(dao.listarProdutos());
    }


    //metodos para compra
    public static void comprarProduto(int id_carrinho_de_compras) 
    {
        int id_produto = ProdutoController.produtoId();
        int quantidade = ProdutoController.Quantidade();
        
        ItensDoCarrinho ItensDoCarrinho = new ItensDoCarrinho(id_carrinho_de_compras, id_produto, quantidade);

        ItensDoCarrinhoDAO itens_dao = new ItensDoCarrinhoDAO();

        itens_dao.adicionarProdutos(ItensDoCarrinho);

    }



}