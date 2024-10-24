package Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.Cliente;
import sql.ClienteDAO;

public class ClienteController {

    static Scanner entrada = new Scanner(System.in);


    // solicita os dados do cliente
    public static Cliente solicitarDadosCliente() {

        Cliente cliente = new Cliente();

        System.out.println("DIGITE O NOME: ");
        String nome = entrada.nextLine();
        cliente.setNome(nome);
        
        System.out.println("\n------------------------------------");
        
        System.out.println("\nDIGITE O EMAIL: ");
        String email = entrada.nextLine();
        cliente.setEmail(email);
        
        System.out.println("\n------------------------------------");
        
        System.out.println("\nDIGITE O CPF: ");
        String cpf = entrada.nextLine();
        cliente.setCpf(cpf);

        System.out.println("\n------------------------------------");

        System.out.println("\nDIGITE O CEP: ");
        String cep = entrada.nextLine();
        cliente.setCep(cep);

        System.out.println("\n------------------------------------");

        System.out.println("\nDIGITE A RUA: ");
        String rua = entrada.nextLine();
        cliente.setRua(rua);

        System.out.println("\n------------------------------------");

        System.out.println("\nDIGITE O BAIRRO: ");
        String bairro = entrada.nextLine();
        cliente.setBairro(bairro);

        System.out.println("\n------------------------------------");

        System.out.println("\nDIGITE A CIDADE: ");
        String cidade = entrada.nextLine();
        cliente.setCidade(cidade);

        System.out.println("\n------------------------------------");

        System.out.println("\nDIGITE O NÚMERO: ");
        String numero = entrada.nextLine();
        cliente.setNumero(numero);

        System.out.println("\n------------------------------------");

        System.out.println("\nDIGITE O COMPLEMENTO (OPCIONAL):");
        String complemento = entrada.nextLine();
        cliente.setComplemento(complemento);

        System.out.println("\n------------------------------------");

        System.out.println("\nDIGITE SEU TELEFONE:");
        String telefone = entrada.nextLine();
        cliente.setTelefone(telefone);

        System.out.println("\n------------------------------------");

        System.out.println("\nREGISTRE UM NOME DE USUARIO:");
        String nome_usuario = entrada.nextLine();
        cliente.setNome_Usuario(nome_usuario);

        System.out.println("\n------------------------------------");

        System.out.println("\n REGISTRE UMA SENHA:");
        String senha = entrada.nextLine();
        cliente.setSenha(senha);


        return cliente;

    }


    public static Map<String, String> pedeUsuarioESenha() {

        System.out.println("-----------------LOGIN-----------------");
        System.out.println("DIGITE O NOME DE USUARIO: ");
        String nome_usuario = entrada.nextLine();

        System.out.println();

        System.out.println("DIGITE A SENHA: ");
        String senha = entrada.nextLine();

        System.out.println("--------------------------------------");

        Map<String, String> dadosLogin = new HashMap<>();
        dadosLogin.put("usuario", nome_usuario);
        dadosLogin.put("senha", senha);

        return dadosLogin;
    }


    public static void cadastraCliente() {
            Cliente cliente = ClienteController.solicitarDadosCliente();
            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.cadastrarCliente(cliente);
        }
    

}