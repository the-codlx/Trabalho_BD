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
                    realizarLogin();
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

    private static void mostraOpcoesAdmin() {
        System.out.println("1 - Cadastrar Produto\n2 - Listar Produtos\n3 - Sair");
    }

    // metodo para cadastrar cliente

    private static void cadastraCliente() {
        Cliente cliente = ClienteController.solicitarDadosCliente();
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.cadastrarCliente(cliente);
    }

    // metodos para login

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

        if (ClienteDAO.verificarCredenciais(usuarioESenha[0], usuarioESenha[1])) {

            Cliente cliente = ClienteDAO.buscarCliente(usuarioESenha[0], usuarioESenha[1]);
            processarCliente(cliente);

        }

        else {

            System.out.println("Login falhou.");

        }

    }

    // metodo para gerenciar produtos

    private static void gerenciarProdutos() {

        ProdutoDAO dao = new ProdutoDAO();

        while (true) {

            System.out.println("Escolha uma opção:");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Visualizar Produtos");
            System.out.println("3. Alterar Produto");
            System.out.println("4. Excluir Produto");
            System.out.println("0. Sair");

            int opcao = Opcao(); // Método para capturar a opção do usuário

            switch (opcao) {
                case 1:
                    dao = new ProdutoDAO(ProdutoController.CadastrarProduto());
                    dao.cadastrarProduto();
                    break;

                case 2:
                    visualizarProdutos(dao);
                    break;

                case 3:
                    int id = Utils.produtoId(entrada);
                    // primeiro parâmetro busca produto pelo id e retorna o objeto, segundo
                    // parametro pede ao usuario os dados dos atributos do novo produto
                    dao.alterarProduto(id, ProdutoController.alterarProduto());

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

    private static void visualizarProdutos(ProdutoDAO dao) {

        ArrayList<Produto> produtos = dao.listarProdutos();

        ProdutoController controller = new ProdutoController();

        controller.listarProdutos(produtos);

    }

}