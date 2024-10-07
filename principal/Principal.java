package principal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import javax.naming.spi.DirStateFactory.Result;

import Conection.Conexao;
import Controller.ClienteController;
import Controller.ProdutoController;
import model.Cliente;
import model.Produto;
import sql.ClienteDAO;
import sql.Login;
import sql.ProdutoDAO;

public class Principal {
    
    static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {

        
    
        while(true) {

            //imprime as opçôes na tela
            mostraOpcoes();

            //recebe a opção do usuário
            switch(Opcao()) {

                case 1:
                    cadastraCliente();
                    break;

                case 2:
                    realizarLogin();
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

    private static String Opcoes() {
            return "1 - Cadastrar Cliente\n2 - Fazer Login\n3 - Sair";
    }

    private static int Opcao() {
        int opcao = entrada.nextInt();
        entrada.nextLine();
        return opcao;
    }

    private static void cadastraCliente() {
        Cliente cliente = ClienteController.cadastrarCliente();
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.cadastrarCliente(cliente);
    }

    private static boolean fazLogin(String nome_usuario, String senha) {

        if(ClienteDAO.verificarCredenciais(nome_usuario, senha))
            return true;
        else
            return false;

    }

    private static void mostraOpcoes() {
        System.out.println(Opcoes());
    }

    private static void visualizarProdutos() {
        ArrayList<Produto> produtos = ProdutoDAO.listarProdutos();

        ProdutoController controller = new ProdutoController();

        controller.listarProdutos(produtos);
        
    }

    private static void gerenciarProdutos() {

        while(true) {

            System.out.println("Escolha uma opção:");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Visualizar Produtos");
            System.out.println("3. Sair");

            int opcao = Opcao(); // Método para capturar a opção do usuário

            switch (opcao) {
                case 1:
                    Produto produto = ProdutoController.CadastrarProduto();
                    ProdutoDAO.cadastrarProduto(produto);
                    break;

                case 2:
                    visualizarProdutos();
                    break;

                case 3:
                    return; // Sai do loop e volta ao menu anterior

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;

            }
        }
    }

    private static void processarCliente(Cliente cliente) {
        switch (cliente.getRole()) {
            case "admin":
                gerenciarProdutos();
                break;

            case "cliente":
                System.out.println("Bem vindo, " + cliente.getNome());
                break;

            default:
                System.out.println("Erro ao fazer login.");
                break;
        }
    }

    private static void realizarLogin() {
        String[] login = Login.FazerLogin();
        if (fazLogin(login[0], login[1])) {
            Cliente cliente = ClienteDAO.buscarCliente(login[0], login[1]);
            processarCliente(cliente);
        } else {
            System.out.println("Login falhou.");
        }
    }

    private static void mostraOpcoesAdmin() {
        System.out.println("1 - Cadastrar Produto\n2 - Listar Produtos\n3 - Sair");
    }

}