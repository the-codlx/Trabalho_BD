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

    public static Map<String, String> pedeUsuarioESenha() {

        System.out.println("-----------------LOGIN-----------------");
        System.out.println("Digite o nome de usuario: ");
        String nome_usuario = entrada.nextLine();

        System.out.println();

        System.out.println("Digite a senha: ");
        String senha = entrada.nextLine();

        System.out.println("--------------------------------------");

        Map<String, String> dadosLogin = new HashMap<>();
        dadosLogin.put("usuario", nome_usuario);
        dadosLogin.put("senha", senha);

        return dadosLogin;
    }



    

}