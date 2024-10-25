package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Utils {
    
    private static Scanner entrada = new Scanner(System.in);

    
    public static String Opcoes() {
        return "------------------------------------" + "\n1 - FAZER LOGIN\n2 - REALIZAR CADASTRO\n3 - SAIR" + "\n ------------------------------------";
    }

    public static int Opcao() {
        int opcao = entrada.nextInt();
        entrada.nextLine();
        return opcao;
    }


    public static void mostraOpcoes() {
        System.out.println(Utils.Opcoes());
    }

}
