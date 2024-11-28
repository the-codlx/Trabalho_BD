package principal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.naming.spi.DirStateFactory.Result;

import org.bson.types.ObjectId;

import Conection.Conexao;
import Conection.MongoDBConexao;
import Controller.CarrinhoController;
import Controller.ClienteController;
import Controller.ProdutoController;
import model.CarrinhoDeCompras;
import model.Cliente;
import model.ItensDoCarrinho;
import model.Pedido;
import model.Produto;
import mongodbquery.CarrinhoDeComprasDAO;
import mongodbquery.ClienteDAO;
import mongodbquery.ItensDoCarrinhoDAO;
import mongodbquery.PedidoDAO;
import mongodbquery.ProdutoDAO;
import mongodbquery.RelatorioDAO;
import utils.CreateCollenctionsAndData;
import utils.Utils;

public class Principal {

    static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {

        IniciaPrograma();

    }

    public static void painelInicial() {

        ClienteDAO ClienteDAO = new ClienteDAO();
        ProdutoDAO ProdutoDAO = new ProdutoDAO();
        PedidoDAO PedidoDAO = new PedidoDAO();

        int clientes = ClienteDAO.quantidadeClientes();
        int produtos = ProdutoDAO.quantidadeProdutos();
        int pedidos = PedidoDAO.quantidadePedidos();
        int itens = ClienteDAO.totalElementosTodasTabelas();

        System.out.println("##################################################");
        System.out.println("#              SISTEMA DE VENDAS                 #");
        System.out.println("##################################################");
        System.out.println("#                                                #");
        System.out.println("#  TOTAL DE REGISTROS EXISTENTES                 #");
        // Usando printf para formatar com largura fixa
        System.out.printf("#  1 - CLIENTES: %-31d#\n", clientes);
        System.out.printf("#  2 - PRODUTOS: %-34d#\n", produtos);
        System.out.printf("#  3 - PEDIDOS: %-33d#\n", pedidos);
        System.out.printf("#  4 - ITENS: %-33d#\n", itens);
        System.out.println("#                                                #");
        System.out.println("#                                                #");
        System.out.println("##################################################");
        System.out.println("#  CRIADO POR: Lucas Nunes                       #");
        System.out.println("#             Romullo Leal                       #");
        System.out.println("#             Lucas                              #");
        System.out.println("#                                                #");
        System.out.println("#  DISCIPLINA: BANCO DE DADOS                    #");
        System.out.println("#             2024/2                             #");
        System.out.println("#  PROFESSOR: HOWARD ROATTI                      #");
        System.out.println("##################################################");

    }

    public static void IniciaPrograma() {

        while (true) {

            painelInicial();
            System.out.println("SEJA BEM VINDO, APERTE ENTER PARA COMEÇAR.");
            entrada.nextLine();

            // imprime as opçôes na tela
            Utils.mostraOpcoes();

            // recebe a opção do usuário
            switch (Utils.Opcao()) {

                case 1:
                    GerenciarLogin();
                    break;

                case 2:
                    ClienteController.cadastraCliente();
                    break;
                case 3:
                    System.out.println("------------------------------------");
                    System.out.println("CRIANDO AS COLEÇÕES NO BANCO DE DADOS...");
                    utils.CreateCollenctionsAndData.ensureCollectionsExist();
                    break;

                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }

        }

    }

    // menu Login

    public static void GerenciarLogin() {

        // pede o usuario e a senha e joga em um map
        Map<String, String> credenciais = ClienteController.pedeUsuarioESenha();

        if (credenciais.get("NOME_USUARIO").equals("admin") && credenciais.get("SENHA").equals("1234")) {
            MenuAdmin();
            return;
        }

        // verifica se as credenciais estão no banco de dados e retorna o id do cliente
        ObjectId id_cliente = ClienteDAO.verificarCredenciaisERetornaID(credenciais.get("NOME_USUARIO"),
                credenciais.get("SENHA"));

        if (id_cliente != null) {

            MenuCliente(id_cliente);

        }

        else {

            String opcoes = "1 - TENTAR NOVAMENTE\n2 - CADASTRAR\n3 - SAIR";

            System.out.println(opcoes);

            int opcao = Utils.Opcao();

            switch (opcao) {

                case 1:
                    GerenciarLogin();
                    break;

                case 2:
                    ClienteController.cadastraCliente();
                    GerenciarLogin();
                    break;

                case 3:
                    System.exit(0);

            }

        }
    }

    // Menu de Clientes

    private static void MenuCliente(ObjectId id_cliente) {

        ProdutoDAO prod_dao = new ProdutoDAO();
        CarrinhoDeComprasDAO car_dao = new CarrinhoDeComprasDAO();
        ItensDoCarrinhoDAO itens_car_dao = new ItensDoCarrinhoDAO();
        ObjectId id_carrinho_de_compras = car_dao.criarCarrinhoDeCompras(id_cliente);
        RelatorioDAO relatorio = new RelatorioDAO();

        while (true) {

            System.out.println("ESCOLHA UMA OPÇÃO:");
            System.out.println("1. PRODUTOS");
            System.out.println("2. GERAR RELATORIO DOS PRODUTOS MAIS VENDIDOS");
            System.out.println("3. COMPRAR PRODUTO");
            System.out.println("4. VISUALIZAR CARRINHO");
            System.out.println("5. EXCLUIR PRODUTOS DO CARRINHO");
            System.out.println("6. FINALIZAR PAGAMENTO");
            System.out.println("7. RELATORIO DE PEDIDOS DO CLIENTE");
            System.out.println("0. SAIR");

            int opcao = Utils.Opcao();

            switch (opcao) {
                case 1:
                    ProdutoController.Produtos(prod_dao);
                    System.out.println();
                    break;

                case 2:
                    relatorio.gerarRelatorioProdutosMaisVendidos();
                    break;

                case 3:
                    prod_dao.comprarProduto(id_carrinho_de_compras);
                    break;

                case 4:
                    ProdutoController
                            .listarProdutosNoCarrinho(itens_car_dao.produtosItensDoCarrinho(id_carrinho_de_compras));
                    break;
                case 5:
                    CarrinhoController.removeItemDoCarrinho(id_carrinho_de_compras, itens_car_dao);
                    break;
                case 6:
                    Pedido pedido = new Pedido(id_cliente, id_carrinho_de_compras,
                            itens_car_dao.retornaValorTotal(id_carrinho_de_compras));
                    finalizarPagamento(pedido, itens_car_dao);
                    MenuCliente(id_cliente);
                    break;

                case 7:
                    relatorio.exibirPedidosCliente(id_cliente);
                    break;
                case 0:
                    return; // Sai do loop e volta ao menu anterior

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;

            }
        }
    }

    // Menu Admin

    private static void MenuAdmin() {

        ProdutoDAO dao = new ProdutoDAO();
        ClienteDAO c_dao = new ClienteDAO();
        String nomeProduto;

        Scanner entrada = new Scanner(System.in);

        while (true) {
            System.out.println("========= MENU ADMIN =========");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Alterar Produto");
            System.out.println("3. Listar Produtos");
            System.out.println("4. Excluir Produto");
            System.out.println("5. Listar Clientes");
            System.out.println("6. Alterar Cliente");
            System.out.println("7. Excluir Cliente");
            System.out.println("8. Sair");
            System.out.println("==============================");
            System.out.println("Escolha uma opção: ");
            // int opcao = Integer.parseInt(entrada.nextLine())

            switch (Utils.Opcao()) {
                case 1:
                    dao.cadastrarProduto(ProdutoController.CadastrarProduto());
                    break;
                case 2:
                    dao.alterarProduto(ProdutoController.retornaNomeProduto(), ProdutoController.alterarProduto());
                    break;
                case 3:
                    ProdutoController.Produtos(dao);
                    break;
                case 4:
                    dao.excluirProdutoPorNome(ProdutoController.retornaNomeProduto());
                    break;
                case 5:
                    ClienteController.mostrarTodosClientes();
                    break;
                case 6:
                    c_dao.alterarCliente(ClienteController.pedeUsuario(), ClienteController.solicitarDadosCliente());
                    break;
                case 7:
                    c_dao.excluirClientePeloNome(ClienteController.nomeCliente());
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    public static void finalizarPagamento(Pedido pedido, ItensDoCarrinhoDAO dao) {

        List<Produto> produtosDoCarrinho = dao.produtosItensDoCarrinho(pedido.getId_carrinho());

        ProdutoController.listarProdutosNoCarrinho(produtosDoCarrinho);
        System.out.println();
        System.out.println();
        System.out.println("+" + "=====================================================" + "+");
        System.out.println("+" + "              CONFIRMAÇÃO DE PAGAMENTO               " + "+");
        System.out.println("+" + "=====================================================" + "+");
        System.out.printf("DESEJA REALMENTE FINALIZAR O PAGAMENTO?%n");
        System.out.printf("VALOR TOTAL: R$ %.2f%n", dao.retornaValorTotal(pedido.getId_carrinho()));
        System.out.println();
        System.out.println("1. SIM");
        System.out.println("2. NÃO");
        int opcao = Utils.Opcao();

        switch (opcao) {

            case 1:
                try {

                    PedidoDAO PedidoDAO = new PedidoDAO();
                    PedidoDAO.criaPedido(pedido);

                } catch (Exception e) {

                    System.out.println(e);

                } finally {

                    System.out.println();
                    System.out.println("------------------------------------");
                    System.out.println("PARABÉNS PELA COMPRA! PEDIDO FINALIZADO.");
                    System.out.println("------------------------------------");
                    System.out.println();

                }

                break;
            case 2:
                System.out.println();
                break;

            default:
                break;
        }

    }

}