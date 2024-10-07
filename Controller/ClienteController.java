package Controller;

import java.util.Scanner;

import model.Cliente;
import sql.ClienteDAO;
import sql.Login;

public class ClienteController {

    static Scanner entrada = new Scanner(System.in);

    public static Cliente cadastrarCliente () {

        Cliente cliente = new Cliente();

        System.out.println("------------------------------");
        System.out.println("Digite o nome: "); 
        String nome = entrada.nextLine();
        cliente.setNome(nome);
        System.out.println("Digite o email: ");
        String email = entrada.nextLine();
        cliente.setEmail(email);
        System.out.println("Digite o cpf: ");
        String cpf = entrada.nextLine();
        cliente.setCpf(cpf);
        System.out.println("Digite o cep: ");
        String cep = entrada.nextLine();
        cliente.setCep(cep);
        System.out.println("Digite a rua: ");
        String rua = entrada.nextLine();
        cliente.setRua(rua);
        System.out.println("Digite o bairro: ");
        String bairro = entrada.nextLine();
        cliente.setBairro(bairro);
        System.out.println("Digite a cidade: ");
        String cidade = entrada.nextLine();
        cliente.setCidade(cidade);
        System.out.println("Digite o número: ");
        String numero = entrada.nextLine();
        cliente.setNumero(numero);
        System.out.println("Digite o complemento (opcional):");
        String complemento = entrada.nextLine();
        cliente.setComplemento(complemento);
        System.out.println("Digite seu telefone:");
        String telefone = entrada.nextLine();
        cliente.setTelefone(telefone);
        System.out.println("Registre um nome de usuario:");
        String nome_usuario = entrada.nextLine();
        cliente.setNome_Usuario(nome_usuario);
        System.out.println("Registre uma senha:");
        String senha = entrada.nextLine(); 
        cliente.setSenha(senha);

        System.out.println("------------------------------");

        return cliente;

    }

    public static void LoginSistema() {

        String[] credenciais = Login.FazerLogin();

        if (ClienteDAO.verificarCredenciais(credenciais[0], credenciais[1])) {

            System.out.println("Logado com sucesso!");
            System.out.println("ID: ");


        }

        else {

            String opcoes = "1 - Tentar novamente\n2 - Cadastrar\n3 - Sair";

            System.out.println(opcoes);

            int opcao = Integer.parseInt(entrada.nextLine());

            switch (opcao) {

                case 1:
                    LoginSistema();
                    break;

                case 2:
                    Cliente cliente = ClienteController.cadastrarCliente();
                    ClienteDAO dao = new ClienteDAO();
                    dao.cadastrarCliente(cliente);
                    LoginSistema();
                    dao.fechaConexao();

                    break;

                case 3:
                    System.out.println("Saindo...");
                    break;

            }

        }

    }


}